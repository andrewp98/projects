package ro.utcluj.intermediar;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table( name = "Account" )
public class Account implements Serializable {
    private Long id;
    private String userName;
    private String password;
    private boolean isAdmin;
    private ArrayList<Book> favourites;
    private int penalty;
    private int subscription;
    private Date lockDate;

    public Account(String userName, String password, boolean isAdmin) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Account(String userName, String password, boolean isAdmin, int penalty) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.penalty = penalty;
    }

    public Account(String userName, String password, int penalty, int subscription) {
        this.userName = userName;
        this.password = password;
        this.penalty = penalty;
        this.subscription = subscription;
    }

    public Account(){
        // this form used by Hibernate
    }

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public ArrayList<Book> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<Book> favourites) {
        this.favourites = favourites;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getSubscription() {
        return subscription;
    }

    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }

    public Date getLockDate() {
        return lockDate;
    }

    public void setLockDate(Date lockDate) {
        this.lockDate = lockDate;
    }
}
