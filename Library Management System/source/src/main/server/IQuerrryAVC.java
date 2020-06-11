package ro.utcluj.server;

import ro.utcluj.intermediar.Account;
import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;

import java.util.List;

public interface IQuerrryAVC {

    void addAccountQuerry(String username, String pass, boolean isAdmin);

    List<Book> getBook(String title);

    void addBookQuerry(String title, String author, String genre, int quant);

    void updateAccount(String id, String username, String pass, boolean isAdmin);

    void updateBook(String id, String title, String author, String genre, int quant, int rating);

    void deleteAccount(String username);

    void deleteBook(String title);

    List<Book> returnBookList();

    List<Account> returnAccountList();

    List<BookAccount> returnBookAccountList();

}
