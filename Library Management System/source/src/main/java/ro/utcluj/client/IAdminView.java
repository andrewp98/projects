package ro.utcluj.client;

import javax.swing.*;

interface IAdminViewProvider {

    void showErrorMessage(String message);

    JFileChooser getReportLocation();

}
interface IAccountProvider {

    long getID();

    String getUsername();

    String getPassword();

    boolean getAdminRights();

    int getRating();

}

public interface IAdminView extends IAccountProvider, IBookDataProvider, IAdminViewProvider{
}
