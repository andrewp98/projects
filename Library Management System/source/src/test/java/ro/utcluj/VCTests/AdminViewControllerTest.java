package ro.utcluj.VCTests;

import org.junit.jupiter.api.Test;
import ro.utcluj.client.AdminVCRequest;
import ro.utcluj.client.AdminViewController;
import ro.utcluj.client.IAdminView;
import ro.utcluj.intermediar.Book;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class AdminViewControllerTest {

    @Test
    void addAccount() {
        IAdminView adminView = mock(IAdminView.class);
        AdminVCRequest adq = mock(AdminVCRequest.class);

        AdminViewController adminViewController = new AdminViewController(adminView, adq);
        when(adminView.getUsername()).thenReturn("admin");
        when(adminView.getPassword()).thenReturn("admin");
        when(adminView.getAdminRights()).thenReturn(true);

//        when(adq.addAccount("admin", "admin", true)).thenReturn(null);
        adminViewController.addAccount();

        verify(adminView).getUsername();
        verify(adminView).getPassword();
        verify(adminView).getAdminRights();
        verify(adminView).showErrorMessage("Account added!");
        verify(adq).addAccount("admin", "admin", true);
    }

    @Test
    void addBook_PerfectCase() {
        IAdminView adminView = mock(IAdminView.class);
        AdminVCRequest adq = mock(AdminVCRequest.class);

        AdminViewController adminViewController = new AdminViewController(adminView, adq);
        when(adminView.getTitle()).thenReturn("Poezii");
        when(adminView.getAuthor()).thenReturn("Eminescu");
        when(adminView.getGenre()).thenReturn("Drama");
        when(adminView.getQuantity()).thenReturn(4);

        List<Book> books = new ArrayList<>();

        when(adq.getBook("Poezii")).thenReturn(books);
        adminViewController.addBook();

        verify(adminView).getTitle();
        verify(adminView).getAuthor();
        verify(adminView).getGenre();
        verify(adminView).getQuantity();
        verify(adminView).showErrorMessage("Book added succesfully!");
        verify(adq).addBook("Poezii", "Eminescu", "Drama", 4);
    }

    @Test
    void addBook_AlreadyCase() {
        IAdminView adminView = mock(IAdminView.class);
        AdminVCRequest adq = mock(AdminVCRequest.class);

        AdminViewController adminViewController = new AdminViewController(adminView, adq);
        when(adminView.getTitle()).thenReturn("Poezii");
        when(adminView.getAuthor()).thenReturn("Eminescu");
        when(adminView.getGenre()).thenReturn("Drama");
        when(adminView.getQuantity()).thenReturn(4);

        List<Book> books = new ArrayList<>();
        Book bk = new Book("Drama", "Poezii", "Eminescu", 3);
        books.add(bk);

        when(adq.getBook("Poezii")).thenReturn(books);
        adminViewController.addBook();

        verify(adminView).getTitle();
        verify(adminView).getAuthor();
        verify(adminView).getGenre();
        verify(adminView).getQuantity();
        verify(adminView).showErrorMessage("Book already in store! Try modifying the quantity!");
    }

    @Test
    void updateAccount() {
        IAdminView adminView = mock(IAdminView.class);
        AdminVCRequest adq = mock(AdminVCRequest.class);

        AdminViewController adminViewController = new AdminViewController(adminView, adq);
        when(adminView.getUsername()).thenReturn("admin");
        when(adminView.getPassword()).thenReturn("admin");
        when(adminView.getAdminRights()).thenReturn(true);
        when(adminView.getStringId()).thenReturn("1");

        adminViewController.updateAccount();

        verify(adminView).getUsername();
        verify(adminView).getPassword();
        verify(adminView).getAdminRights();
        verify(adminView).getStringId();
        verify(adminView).showErrorMessage("Account updated!");
        verify(adq).updateAccount("admin", "admin", true, "1");
    }

    @Test
    void updateBook() {
        IAdminView adminView = mock(IAdminView.class);
        AdminVCRequest adq = mock(AdminVCRequest.class);

        AdminViewController adminViewController = new AdminViewController(adminView, adq);
        when(adminView.getTitle()).thenReturn("Poezii");
        when(adminView.getAuthor()).thenReturn("Eminescu");
        when(adminView.getGenre()).thenReturn("Drama");
        when(adminView.getQuantity()).thenReturn(4);
        when(adminView.getStringId()).thenReturn("1");
        when(adminView.getRating()).thenReturn(2);

        adminViewController.updateBook();

        verify(adminView).getTitle();
        verify(adminView).getAuthor();
        verify(adminView).getGenre();
        verify(adminView).getQuantity();
        verify(adminView).getStringId();
        verify(adminView).showErrorMessage("Book updated!");
        verify(adq).updateBook("1", "Poezii", "Eminescu", "Drama", 4, 2);
    }

    @Test
    void deleteAccount() {
        IAdminView adminView = mock(IAdminView.class);
        AdminVCRequest adq = mock(AdminVCRequest.class);

        AdminViewController adminViewController = new AdminViewController(adminView, adq);
        when(adminView.getUsername()).thenReturn("admin");
        adminViewController.deleteAccount();

        verify(adminView).getUsername();
        verify(adminView).showErrorMessage("Account deleted!");
        verify(adq).deleteAccount("admin");
    }

    @Test
    void deleteBook() {
        IAdminView adminView = mock(IAdminView.class);
        AdminVCRequest adq = mock(AdminVCRequest.class);

        AdminViewController adminViewController = new AdminViewController(adminView, adq);
        when(adminView.getTitle()).thenReturn("Poezii");
        adminViewController.deleteBook();

        verify(adminView).getTitle();
        verify(adminView).showErrorMessage("Book deleted!");
        verify(adq).deleteBook("Poezii");
    }

    @Test
    void generateRaport() {
        IAdminView adminView = mock(IAdminView.class);
        AdminVCRequest adq = mock(AdminVCRequest.class);

        AdminViewController adminViewController = new AdminViewController(adminView, adq);

        adminViewController.generateRaport("PDF");

        verify(adminView.getReportLocation());
    }

    @Test
    void returnBooks() {
        IAdminView adminView = mock(IAdminView.class);
        AdminVCRequest adq = mock(AdminVCRequest.class);

        AdminViewController adminViewController = new AdminViewController(adminView, adq);
        adminViewController.returnBooks();

        verify(adq).returnBooks();
    }

    @Test
    void returnAccounts() {
        IAdminView adminView = mock(IAdminView.class);
        AdminVCRequest adq = mock(AdminVCRequest.class);

        AdminViewController adminViewController = new AdminViewController(adminView, adq);
        adminViewController.returnAccounts();

        verify(adq).returnAccounts();
    }

    @Test
    void returnBookAccounts(){
        IAdminView adminView = mock(IAdminView.class);
        AdminVCRequest adq = mock(AdminVCRequest.class);

        AdminViewController adminViewController = new AdminViewController(adminView, adq);
        adminViewController.returnBookAccounts();

        verify(adq).returnBookAccounts();
    }


}
