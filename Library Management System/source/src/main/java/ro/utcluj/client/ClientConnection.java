package ro.utcluj.client;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class ClientConnection {

    public static void main(String[] args)
    {
        ConnManager.setConn();
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.out.println( "Failed to initialize LaF" );
        }
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }
}

