package ro.utcluj.intermediar;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class BookTest {

    @Test
    void getGenre() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        String str = bk.getGenre();

        assertEquals("Drama", str);
    }

    @Test
    void setGenre() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        bk.setGenre("a");
        String str = bk.getGenre();

        assertEquals("a", str);
    }

    @Test
    void getTitle() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        String str = bk.getTitle();

        assertEquals("Poezii", str);
    }

    @Test
    void setTitle() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        bk.setTitle("A");
        String str = bk.getTitle();

        assertEquals("A", str);
    }

    @Test
    void getAuthor() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        String str = bk.getAuthor();

        assertEquals("Eminescu", str);
    }

    @Test
    void setAuthor() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        bk.setAuthor("Arghezi");
        String str = bk.getAuthor();

        assertEquals("Arghezi", str);
    }

    @Test
    void getQuantity() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        int a = bk.getQuantity();

        assertEquals(3, a);
    }

    @Test
    void setQuantity() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        bk.setQuantity(4);
        int a = bk.getQuantity();

        assertEquals(4, a);
    }

    @Test
    void getInAsteptare() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        ArrayList<Account> lst = new ArrayList<>();
        Account acc = new Account("user", "user", false);
        lst.add(acc);
        bk.setInAsteptare(lst);
        ArrayList<Account> test = bk.getInAsteptare();

        assertEquals(test, lst);
    }

    @Test
    void setInAsteptare() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        ArrayList<Account> lst = new ArrayList<>();
        Account acc = new Account("user", "user", false);
        lst.add(acc);
        bk.setInAsteptare(lst);
        ArrayList<Account> test = bk.getInAsteptare();

        assertEquals(test, lst);
    }

    @Test
    void addWait() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        ArrayList<Account> lst = new ArrayList<>();
        Account acc = new Account("user", "user", false);
        lst.add(acc);
        bk.setInAsteptare(lst);
        bk.removeWait(acc);
        ArrayList<Account> test = bk.getInAsteptare();
        boolean b = test.isEmpty();

        assertEquals(true, b);
    }

    @Test
    void removeWait() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        ArrayList<Account> lst = new ArrayList<>();
        Account acc = new Account("user", "user", false);
        bk.setInAsteptare(lst);
        bk.addWait(acc);
        ArrayList<Account> test = bk.getInAsteptare();

        assertEquals(test, lst);
    }

    @Test
    void getRating() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        bk.setRating(3);
        int a = bk.getRating();

        assertEquals(3, a);
    }

    @Test
    void setRating() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        bk.setRating(3);
        int a = bk.getRating();

        assertEquals(3, a);
    }

    @Test
    void getReview() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        List<String> lst = new ArrayList<>();
        lst.add("ceva");
        bk.setReview(lst);
        List<String> test = bk.getReview();

        assertEquals(lst, test);
    }

    @Test
    void setReview() {
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);

        List<String> lst = new ArrayList<>();
        lst.add("ceva");
        bk.setReview(lst);
        List<String> test = bk.getReview();

        assertEquals(lst, test);
    }

    @Test
    void notifyAvb() {
    }
}