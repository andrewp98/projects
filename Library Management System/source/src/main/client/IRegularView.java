package ro.utcluj.client;

interface IBookDataProvider {

    String getTitle();

    String getAuthor();

    String getGenre();

    String getStringId();

    int getQuantity();

    long getLoggedInId();
}

interface IRegularViewProvider {

    void showErrorMessage(String message);

    void refreshTables();

}

public interface IRegularView extends IBookDataProvider, IRegularViewProvider {

    String getReturnDate();

    int getRating();

    String getReview();

}
