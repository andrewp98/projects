package ro.utcluj.client;

import ro.utcluj.intermediar.Account;
import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;

import javax.swing.*;
import java.util.List;

public class AdminViewController{

    private final IAdminView adminView;
    private final AdminVCRequest adq;

    public AdminViewController(IAdminView adminView, AdminVCRequest adq) {
        this.adminView = adminView;
        this.adq = adq;
    }

    public void addAccount() {
        String username = adminView.getUsername();
        String pass = adminView.getPassword();
        boolean isAdmin = adminView.getAdminRights();

        adq.addAccount(username, pass, isAdmin);
        adminView.showErrorMessage("Account added!");
    }

    public void addBook() {
        String title = adminView.getTitle();
        String author = adminView.getAuthor();
        String genre = adminView.getGenre();
        int quant = adminView.getQuantity();
        List<Book> bookList = adq.getBook(title);
        if (!bookList.isEmpty()) {
            adminView.showErrorMessage("Book already in store! Try modifying the quantity!");
        } else {
            adminView.showErrorMessage("Book added succesfully!");
            adq.addBook(title, author, genre, quant);
        }

    }

    public void updateAccount() {
        String username = adminView.getUsername();
        String pass = adminView.getPassword();
        boolean isAdmin = adminView.getAdminRights();
        String id = adminView.getStringId();

        adq.updateAccount(username, pass, isAdmin, id);
        adminView.showErrorMessage("Account updated!");
    }

    public void updateBook() {
        String title = adminView.getTitle();
        String author = adminView.getAuthor();
        String genre = adminView.getGenre();
        String id = adminView.getStringId();
        int quant = adminView.getQuantity();
        int rating = adminView.getRating();

        adq.updateBook(id, title, author, genre, quant, rating);
        adminView.showErrorMessage("Book updated!");
    }

    public void deleteAccount() {
        String username = adminView.getUsername();
        adq.deleteAccount(username);
        adminView.showErrorMessage("Account deleted!");
    }

    public void deleteBook() {
        String title = adminView.getTitle();
        adq.deleteBook(title);
        adminView.showErrorMessage("Book deleted!");
    }

    public void generateRaport(String s) {
        DocumentCreator docCreator = null;
        List<Account> accountList = returnAccounts();
        List<Book> bookList = returnBooks();
        List<BookAccount> bookAccList = returnBookAccounts();
        JFileChooser f = adminView.getReportLocation();
        switch (s) {
            case "PDF":
                docCreator = new PDFcreator(f, accountList, bookList, bookAccList);
                break;
            case "TEXT":
                docCreator = new TXTcreator(f, accountList, bookList, bookAccList);
                break;
            default:
                break;
        }

        docCreator.getDoc();
    }

    public List<Book> returnBooks() {
        return adq.returnBooks();
    }

    public List<Account> returnAccounts() {
        return adq.returnAccounts();
    }

    public List<BookAccount> returnBookAccounts() {
        return adq.returnBookAccounts();
    }

}