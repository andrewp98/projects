package ro.utcluj.client;


import ro.utcluj.intermediar.Account;

import java.util.List;

public class LoginController {

    private final ILoginView loginView;
    private final LoginVCRequest loq;

    public LoginController(ILoginView loginView, LoginVCRequest loq) {
        this.loginView = loginView;
        this.loq = loq;
    }


    public void login(){
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        List<Account> accountCriteria = loq.getAccountCriteria(username);

        if (!accountCriteria.isEmpty()) {
            for (Account account : accountCriteria) {
                Account accTemp = account;
                if (account.getPassword().contentEquals(password)) {
                    if (account.getIsAdmin()) {
                        loginView.showAdminView();
                    } else {
                        loginView.showRegularView(accTemp);
                    }
                } else {
                    loginView.showErrorMessage("Invalid username/password");
                }
            }
        } else {
            loginView.showErrorMessage("Invalid username/password");
        }
    }
}
