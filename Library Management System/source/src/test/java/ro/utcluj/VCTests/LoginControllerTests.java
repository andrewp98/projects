package ro.utcluj.VCTests;

import org.junit.jupiter.api.Test;
import ro.utcluj.client.ILoginView;
import ro.utcluj.client.LoginController;
import ro.utcluj.client.LoginVCRequest;
import ro.utcluj.intermediar.Account;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class LoginControllerTests {
    @Test
    public void givenAdminUsernameAndPassword_login_showAdminView() {
        ILoginView loginView = mock(ILoginView.class);
        LoginVCRequest loq = mock(LoginVCRequest.class);
        LoginController controller = new LoginController(loginView, loq);

        when(loginView.getUsername()).thenReturn("admin");
        when(loginView.getPassword()).thenReturn("admin");

        List<Account> acc = new ArrayList<>();
        Account a = new Account("admin", "admin", true);
        acc.add(a);

        when(loq.getAccountCriteria("admin")).thenReturn(acc);

        controller.login();

        verify(loginView).getPassword();
        verify(loginView).getUsername();
        verify(loq).getAccountCriteria("admin");
        verify(loginView).showAdminView();
    }

    @Test
    public void givenRegularUsernameAndPassword_login_showRegularView() {
        ILoginView loginView = mock(ILoginView.class);
        LoginVCRequest loq = mock(LoginVCRequest.class);
        LoginController controller = new LoginController(loginView, loq);

        when(loginView.getUsername()).thenReturn("user");
        when(loginView.getPassword()).thenReturn("user");

        List<Account> acc = new ArrayList<>();
        Account a = new Account("user", "user", false);
        acc.add(a);

        when(loq.getAccountCriteria("user")).thenReturn(acc);

        controller.login();

        verify(loginView).getPassword();
        verify(loginView).getUsername();
        verify(loq).getAccountCriteria("user");
        verify(loginView).showRegularView(a);
    }

    @Test
    public void givenInvalidUsernameAndPassword_login_showErrorMessage() {
        ILoginView loginView = mock(ILoginView.class);
        LoginVCRequest loq = mock(LoginVCRequest.class);
        LoginController controller = new LoginController(loginView, loq);

        when(loginView.getUsername()).thenReturn("admin");
        when(loginView.getPassword()).thenReturn("worng");

        List<Account> acc = new ArrayList<>();
        Account a = new Account("admin", "admin", true);
        acc.add(a);

        when(loq.getAccountCriteria("admin")).thenReturn(acc);

        controller.login();

        verify(loginView).getPassword();
        verify(loginView).getUsername();
        verify(loq).getAccountCriteria("admin");
        verify(loginView).showErrorMessage("Invalid username/password");
    }

    @Test
    public void givenInvalidUsernameAndPassword_login_showErrorMessage2() {
        ILoginView loginView = mock(ILoginView.class);
        LoginVCRequest loq = mock(LoginVCRequest.class);
        LoginController controller = new LoginController(loginView, loq);

        when(loginView.getUsername()).thenReturn("admin");
        when(loginView.getPassword()).thenReturn("worng");

        List<Account> acc = new ArrayList<>();

        when(loq.getAccountCriteria("admin")).thenReturn(acc);

        controller.login();

        verify(loginView).getPassword();
        verify(loginView).getUsername();
        verify(loq).getAccountCriteria("admin");
        verify(loginView).showErrorMessage("Invalid username/password");
    }



    class TestLoginView implements ILoginView
    {
        private final String username;
        private final String password;

        TestLoginView(String username, String password)
        {
            this.username = username;
            this.password = password;
        }
        @Override
        public void showAdminView() {

        }

        @Override
        public void showRegularView(Account accTemps) {

        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public String getPassword() {
            return password;
        }

        public String shownErrorMessage;
        @Override
        public void showErrorMessage(String message) {
            shownErrorMessage = message;
        }
    }
}
