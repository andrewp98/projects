package ro.utcluj.intermediar;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Entity
@Table( name = "BookAccount" )
public class BookAccount {

    private int id;
    private int accountId;
    private String bookName;
    private Date returnDate;
    private String timeLeft;

    public BookAccount(int accountId, String bookName, Date returnDate) {
        Date date = new Date();
        SimpleDateFormat formatter2=new SimpleDateFormat("dd-MMM-yyyy");
        try {
            date = formatter2.parse(formatter2.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<TimeUnit,Long> aux = computeDiff(date, returnDate);
        this.accountId = accountId;
        this.bookName = bookName;
        this.returnDate = returnDate;
        this.timeLeft =  aux.toString().substring(6, 9) + " days";
    }

    public static Map<TimeUnit,Long> computeDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);
        Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
        long milliesRest = diffInMillies;
        for ( TimeUnit unit : units ) {
            long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;
            result.put(unit,diff);
        }
        return result;
    }

    public BookAccount(int accountId, String bookName) {
        this.accountId = accountId;
        this.bookName = bookName;
    }

    public BookAccount(){
        // this form used by Hibernate
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String getTimeLeft() {
        return timeLeft;
    }
}
