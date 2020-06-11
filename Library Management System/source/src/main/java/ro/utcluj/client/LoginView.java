package ro.utcluj.client;

import ro.utcluj.intermediar.Account;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame implements ILoginView {

    private final JTextField usernameTextField;
    private final JTextField passwordTextField;
    JButton loginButton ;
    JLabel UserLabel ;
    JLabel PassLabel ;

    public LoginView() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500,500);
        GridBagLayout g = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(g);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2 - 100;

        // Move the window
        this.setLocation(x, y);

        c.gridx = 0;
        c.gridy = 0;
        String text1 = "<html>" +
                "<font size='6' color='black'>Username  </font>" +
                "</html>";
        UserLabel = new JLabel(text1);

        add(UserLabel,c);

        c.gridx = 2; //for username txtfield
        c.gridy = 0;
        usernameTextField = new JTextField(15); //name of textfield
        usernameTextField.setFont(new Font(Font.SANS_SERIF, 1, 14));
        add(usernameTextField,c);

        c.gridx = 0; //for password label
        c.gridy = 1;
        String text2 = "<html>" +
                "<font size='6' color='black'>Password  </font>" +
                "</html>";
        PassLabel = new JLabel(text2);
        add(PassLabel,c);

        c.gridx = 2; //for password txtfield
        c.gridy = 1;
        passwordTextField = new JPasswordField(15);
        passwordTextField.setFont(new Font(Font.SERIF, 1, 14));
        add(passwordTextField,c);

        c.gridx = 2;//login button
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,0);  //top padding
        loginButton = new JButton("Login");
        add(loginButton,c);


        LoginVCRequest loq = new LoginVCRequest();
        LoginController loginController = new LoginController(this, loq);
        loginButton.addActionListener(e -> loginController.login());
    }

    @Override
    public void showAdminView() {
        this.setVisible(false);
        new AdminView().setVisible(true);
    }

    @Override
    public void showRegularView(Account acc) {
        this.setVisible(false);
        RegularView regularView = new RegularView(acc);
        regularView.setVisible(true);
    }

    @Override
    public String getUsername() {
        return usernameTextField.getText();
    }

    @Override
    public String getPassword() {
        return passwordTextField.getText();
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


}
