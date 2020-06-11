package ro.utcluj.server;

import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;

import java.util.Date;
import java.util.List;

public interface IQuerryRVC {

    List<Book> searchByTitleQuerry(String title);

    List<Book> searchByRatingQuerry(int rating);

    List<Book> searchByAuthorQuerry(String auth);

    List<Book> searchByGenreQuerry(String genre);

    void insertBorrow(String id, String title, Date date);

    List<BookAccount> returnBookAccountByID(String id);

    int executeReturnBook(String id, String title);

    List<Book> returnBooksQuerry();

    List<BookAccount> returnBookAccountsQerry();

    int addPenalty(String id);

    int addToFav(String id, String title);

    List<Book> returnFavourites(String id);

    void addToWaitingList(String id, String title);

    int rateBook(String title, int rate);

    int revBook(String title, String rev);

    int lockAccount(Long time, String id);
}
