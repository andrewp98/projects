package ro.utcluj.VCTests;

import org.junit.jupiter.api.Test;
import ro.utcluj.client.IRegularView;
import ro.utcluj.client.RegularVCRequest;
import ro.utcluj.client.RegularViewController;
import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

class RegularViewControllerTest {

    @Test
    void searchByTitle() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);
        when(regularView.getTitle()).thenReturn("Poezii");

        List<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);

        when(req.searchByTitle("Poezii")).thenReturn(books);

        controller.searchByTitle();

        verify(regularView).getTitle();
        verify(req).searchByTitle("Poezii");
    }

    @Test
    void searchByAuthor() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);
        when(regularView.getAuthor()).thenReturn("Eminescu");

        List<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);

        when(req.searchByAuthor("Eminescu")).thenReturn(books);

        controller.searchByAuthor();

        verify(regularView).getAuthor();
        verify(req).searchByAuthor("Eminescu");
    }

    @Test
    void searchByGenre() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);
        when(regularView.getGenre()).thenReturn("Drama");

        List<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);

        when(req.searchByGenre("Drama")).thenReturn(books);

        controller.searchByGenre();

        verify(regularView).getGenre();
        verify(req).searchByGenre("Drama");
    }

    @Test
    void borrowBook_NoSuchBookCase() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getReturnDate()).thenReturn("12-AUG-2020");
        when(regularView.getLoggedInId()).thenReturn(1L);
        when(regularView.getTitle()).thenReturn("Poezii");

        List<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = null;
        try {
            d = formatter2.parse(formatter2.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        when(req.getLockDate(1)).thenReturn(d);
        when(req.insertBorrow("1", "Poezii", "12-AUG-2020")).thenReturn(1);

        controller.borrowBook();

        verify(regularView).getTitle();
        verify(regularView).getReturnDate();
        verify(regularView).getLoggedInId();
        verify(regularView).showErrorMessage("No such book!");
    }

    @Test
    void borrowBook_PerfectCase() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getReturnDate()).thenReturn("12-AUG-2020");
        when(regularView.getLoggedInId()).thenReturn(1L);
        when(regularView.getTitle()).thenReturn("Poezii");

        List<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = null;
        try {
            d = formatter2.parse(formatter2.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        when(req.getLockDate(1)).thenReturn(d);
        when(req.searchByTitle("Poezii")).thenReturn(books);
        when(req.getSubs(1)).thenReturn(1);
        when(req.insertBorrow("1", "Poezii", "12-AUG-2020")).thenReturn(1);


        controller.borrowBook();

        verify(regularView).getTitle();
        verify(regularView).getReturnDate();
        verify(regularView).getLoggedInId();
        verify(regularView).showErrorMessage("Poezii borrowed successfully!");
    }

    @Test
    void borrowBook_BorrowLimitCase() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getReturnDate()).thenReturn("12-AUG-2020");
        when(regularView.getLoggedInId()).thenReturn(1L);
        when(regularView.getTitle()).thenReturn("Poezii");

        List<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = null;
        try {
            d = formatter2.parse(formatter2.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        when(req.getLockDate(1)).thenReturn(d);
        when(req.searchByTitle("Poezii")).thenReturn(books);
        when(req.getSubs(1)).thenReturn(0);
        when(req.insertBorrow("1", "Poezii", "12-AUG-2020")).thenReturn(1);


        controller.borrowBook();

        verify(regularView).getTitle();
        verify(regularView).getReturnDate();
        verify(regularView).getLoggedInId();
        verify(regularView).showErrorMessage("Borrow limit reached!");
    }

    @Test
    void borrowBook_WorngDateCase() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getReturnDate()).thenReturn("12-09-2020");
        when(regularView.getLoggedInId()).thenReturn(1L);
        when(regularView.getTitle()).thenReturn("Poezii");

        List<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);


        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = null;
        try {
            d = formatter2.parse(formatter2.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        when(req.getLockDate(1)).thenReturn(d);
        when(req.searchByTitle("Poezii")).thenReturn(books);
        when(req.getSubs(1)).thenReturn(1);
        when(req.insertBorrow("1", "Poezii", "12-AUG-2020")).thenReturn(1);


        controller.borrowBook();

        verify(regularView).getTitle();
        verify(regularView).getReturnDate();
        verify(regularView).getLoggedInId();
        verify(regularView).showErrorMessage("Respect the time format! Ex. 12-AUG-2010");
    }

    @Test
    void borrowBook_LockedCase() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getReturnDate()).thenReturn("12-AUG-2020");
        when(regularView.getLoggedInId()).thenReturn(1L);
        when(regularView.getTitle()).thenReturn("Poezii");

        List<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);

        when(req.searchByTitle("Poezii")).thenReturn(books);
        when(req.getLockDate(1)).thenReturn(new Date(1633035600000L));


        controller.borrowBook();

        verify(regularView).getTitle();
        verify(regularView).getReturnDate();
        verify(regularView).getLoggedInId();
        verify(req).searchByTitle("Poezii");
        verify(req).getLockDate(1);
        verify(regularView).showErrorMessage("You cannot borrow books until the penalty month passes!");
    }


    @Test
    void returnBook() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getTitle()).thenReturn("Poezii");
        when(regularView.getLoggedInId()).thenReturn(1L);

        List<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);

        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        try {
            date = formatter2.parse("12-AUG-2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<BookAccount> bookAccounts = new ArrayList<>();
        BookAccount ba = new BookAccount(1, "Poezii", date);
        bookAccounts.add(ba);
        Date d = null;
        try {
            d = formatter2.parse(formatter2.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        when(req.getLockDate(1)).thenReturn(d);

        when(req.searchByTitle("Poezii")).thenReturn(books);
        when(req.returnBookAccountsByID("1")).thenReturn(bookAccounts);
        when(req.executeReturnBook("1", "Poezii")).thenReturn(0);
        when(req.getPenalty(1)).thenReturn(100);
        controller.returnBook();

        verify(regularView).getTitle();
        verify(regularView).getLoggedInId();
        verify(regularView).showErrorMessage("Poezii return successfully!");
    }

    @Test
    void returnBook_PentaltyCase() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getTitle()).thenReturn("Poezii");
        when(regularView.getLoggedInId()).thenReturn(1L);

        List<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);

        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        try {
            date = formatter2.parse("12-AUG-2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<BookAccount> bookAccounts = new ArrayList<>();
        BookAccount ba = new BookAccount(1, "Poezii", date);
        bookAccounts.add(ba);
        Date d = null;
        try {
            d = formatter2.parse(formatter2.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        when(req.getLockDate(1)).thenReturn(d);
        when(req.searchByTitle("Poezii")).thenReturn(books);
        when(req.returnBookAccountsByID("1")).thenReturn(bookAccounts);
        when(req.executeReturnBook("1", "Poezii")).thenReturn(0);
        when(req.getPenalty(1)).thenReturn(600);
        controller.returnBook();

        verify(regularView).getTitle();
        verify(regularView).getLoggedInId();
        verify(regularView).showErrorMessage("Too many penalties accumulated! You won't be able to borrow books for one month!");
    }

    @Test
    void addToFav_PerfectCase() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getLoggedInId()).thenReturn(1L);
        when(regularView.getTitle()).thenReturn("Poezii");

        when(req.getSubs(1)).thenReturn(1);
        when(req.addToFav("1", "Poezii")).thenReturn(1);
        controller.addToFav();

        verify(regularView).getTitle();
        verify(regularView).getLoggedInId();
        verify(regularView).showErrorMessage("Book added to favourites!");
    }

    @Test
    void addToFav_AlreadyCase() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getLoggedInId()).thenReturn(1L);
        when(regularView.getTitle()).thenReturn("Poezii");

        when(req.getSubs(1)).thenReturn(1);
        when(req.addToFav("1", "Poezii")).thenReturn(0);
        controller.addToFav();

        verify(regularView).getTitle();
        verify(regularView).getLoggedInId();
        verify(regularView).showErrorMessage("Book already in favourites!");
    }

    @Test
    void addToFav_NullCase() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getLoggedInId()).thenReturn(1L);
        when(regularView.getTitle()).thenReturn(null);

        when(req.getSubs(1)).thenReturn(1);
        when(req.addToFav("1", "Poezii")).thenReturn(0);
        controller.addToFav();

        verify(regularView).getTitle();
        verify(regularView).getLoggedInId();
        verify(regularView).showErrorMessage("Title not found!");
    }

    @Test
    void rateBook_PerfectCase() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getTitle()).thenReturn("Poezii");
        when(regularView.getRating()).thenReturn(2);

        when(req.rateBook("Poezii", 2)).thenReturn(1);
        controller.rateBook();

        verify(regularView).getTitle();
        verify(regularView).getRating();
        verify(regularView).showErrorMessage("Book rated!");
    }

    @Test
    void revBook_PerfectCase() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getTitle()).thenReturn("Poezii");
        when(regularView.getReview()).thenReturn("Ceva");

        when(req.revBook("Poezii", "Ceva")).thenReturn(1);
        controller.revBook();

        verify(regularView).getTitle();
        verify(regularView).getReview();
        verify(regularView).showErrorMessage("Review added");
    }

    @Test
    void returnFav() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);

        when(regularView.getTitle()).thenReturn("Poezii");
        when(regularView.getLoggedInId()).thenReturn(1L);

        List<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);

        when(req.returnFav("1")).thenReturn(books);

        controller.returnFav();

        verify(regularView).getLoggedInId();
        verify(req).returnFav("1");
    }

    @Test
    void returnBooks() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);
        controller.returnBooks();

        verify(req).returnBooks();
    }

    @Test
    void returnBookAccounts() {
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        RegularViewController controller = new RegularViewController(regularView, req);
        controller.returnBookAccounts();

        verify(req).returnBookAccounts();
    }

    @Test
    void returnBookAccountsByID(){
        IRegularView regularView = mock(IRegularView.class);
        RegularVCRequest req = mock(RegularVCRequest.class);

        when(regularView.getLoggedInId()).thenReturn(1L);

        RegularViewController controller = new RegularViewController(regularView, req);
        controller.returnBookAccountsByID("1");

        verify(req).returnBookAccountsByID("1");
    }

}
