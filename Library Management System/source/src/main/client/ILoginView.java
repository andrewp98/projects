package ro.utcluj.client;

import ro.utcluj.intermediar.Account;

interface ILoginDataProvider {

    String getUsername();

    String getPassword();

}

interface IViewProvider {

    void showAdminView();

    void showRegularView(Account acc);

    void showErrorMessage(String message);
}

public interface ILoginView extends ILoginDataProvider, IViewProvider {
}
