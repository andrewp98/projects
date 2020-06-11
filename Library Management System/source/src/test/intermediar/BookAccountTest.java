package ro.utcluj.intermediar;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookAccountTest {

    @Test
    void getId() {
        BookAccount ba = new BookAccount(1, "Poezii");

        int a = ba.getId();

        assertEquals(0, a);
    }

    @Test
    void setId() {
        BookAccount ba = new BookAccount(1, "Poezii");

        ba.setId(2);
        int a = ba.getId();

        assertEquals(2, a);
    }

    @Test
    void getAccountId() {
        BookAccount ba = new BookAccount(1, "Poezii");

        int a = ba.getAccountId();

        assertEquals(1, a);
    }

    @Test
    void setAccountId() {
        BookAccount ba = new BookAccount(1, "Poezii");

        ba.setAccountId(4);
        int a = ba.getAccountId();

        assertEquals(4, a);
    }

    @Test
    void getBookName() {
        BookAccount ba = new BookAccount(1, "Poezii");

        String res = ba.getBookName();

        assertEquals("Poezii", res);
    }

    @Test
    void setBookName() {
        BookAccount ba = new BookAccount(1, "Poezii");

        ba.setBookName("A");
        String res = ba.getBookName();

        assertEquals("A", res);
    }

    @Test
    void setReturnDate() {
        Date date = new Date();
        SimpleDateFormat formatter2=new SimpleDateFormat("dd-MMM-yyyy");
        try {
            date = formatter2.parse("12-AUG-2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BookAccount ba = new BookAccount(1, "Poezii");
        ba.setReturnDate(date);
        String res = ba.getReturnDate().toString();

        assertEquals("Wed Aug 12 00:00:00 EEST 2020", res);
    }

    @Test
    void setTimeLeft() {
        BookAccount ba = new BookAccount(1, "Poezii");

        ba.setTimeLeft("12-AUG-2020");
        String res = ba.getTimeLeft();

        assertEquals("12-AUG-2020", res);
    }

    @Test
    void getReturnDate() {
        Date date = new Date();
        SimpleDateFormat formatter2=new SimpleDateFormat("dd-MMM-yyyy");
        try {
            date = formatter2.parse("12-AUG-2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BookAccount ba = new BookAccount(1, "Poezii");
        ba.setReturnDate(date);
        String res = ba.getReturnDate().toString();

        assertEquals("Wed Aug 12 00:00:00 EEST 2020", res);
    }

    @Test
    void getTimeLeft() {
        BookAccount ba = new BookAccount(1, "Poezii");

        ba.setTimeLeft("12-AUG-2020");
        String res = ba.getTimeLeft();

        assertEquals("12-AUG-2020", res);
    }
}