package ro.utcluj.intermediar;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    @Test
    void getUserName() {
        Account acc = new Account("user", "user", false);

        String username = acc.getUserName();

        assertEquals("user", username);
    }

    @Test
    void setUserName() {
        Account acc = new Account("user", "user", false);

        acc.setUserName("user1");
        String username = acc.getUserName();

        assertEquals("user1", username);
    }

    @Test
    void getPassword() {
        Account acc = new Account("user", "user", false);

        String pass = acc.getPassword();

        assertEquals("user", pass);
    }

    @Test
    void setPassword() {
        Account acc = new Account("user", "user", false);

        acc.setPassword("pass");
        String pass = acc.getPassword();

        assertEquals("pass", pass);
    }

    @Test
    void getIsAdmin() {
        Account acc = new Account("user", "user", false);

        Boolean b = acc.getIsAdmin();

        assertEquals(false, b);
    }

    @Test
    void setIsAdmin() {
        Account acc = new Account("user", "user", false);

        acc.setIsAdmin(true);
        Boolean b = acc.getIsAdmin();

        assertEquals(true, b);
    }

    @Test
    void getFavourites() {
        Account acc = new Account("user", "user", false);

        ArrayList<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);

        acc.setFavourites(books);
        ArrayList<Book> test = acc.getFavourites();

        assertEquals(books, test);
    }

    @Test
    void setFavourites() {
        Account acc = new Account("user", "user", false);

        ArrayList<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);

        acc.setFavourites(books);
        ArrayList<Book> test = acc.getFavourites();

        assertEquals(books, test);
    }

    @Test
    void getPenalty() {
        Account acc = new Account("user", "user", false, 2);

        int a = acc.getPenalty();

        assertEquals(2, a);
    }

    @Test
    void setPenalty() {
        Account acc = new Account("user", "user", false, 2);

        acc.setPenalty(3);
        int a = acc.getPenalty();

        assertEquals(3, a);
    }

    @Test
    void getSubscription() {
        Account acc = new Account("user", "user", false, 2);

        acc.setSubscription(4);
        int a = acc.getSubscription();

        assertEquals(4, a);
    }

    @Test
    void setSubscription() {
        Account acc = new Account("user", "user", false, 2);

        acc.setSubscription(5);
        int a = acc.getSubscription();

        assertEquals(5, a);
    }
}