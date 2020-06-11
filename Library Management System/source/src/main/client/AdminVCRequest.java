package ro.utcluj.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ro.utcluj.client.ConnManager;
import ro.utcluj.client.ServerConnection;
import ro.utcluj.intermediar.Account;
import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;
import ro.utcluj.intermediar.RequestMessage;
import ro.utcluj.intermediar.actions.Transform;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdminVCRequest {
    private ServerConnection serverConnection;

    public List<Book> getBook(String title) {
        List<Book> bookList;
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(title);
        RequestMessage lr = new RequestMessage("SRC_BT", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        Type listOfMyClassObject = new TypeToken<ArrayList<Book>>() {}.getType();
        bookList = new Gson().fromJson(response, listOfMyClassObject);

        return bookList;
    }

    public void addAccount(String username, String pass, boolean isAdmin) {
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(username);
        args.add(pass);
        args.add(isAdmin + "");
        RequestMessage lr = new RequestMessage("ADD_ACCOUNT", "ACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
//        String response = serverConnection.getResponse();
    }

    public void addBook(String title, String author, String genre, int quant) {
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(title);
        args.add(author);
        args.add(genre);
        args.add(quant + "");
        RequestMessage lr = new RequestMessage("ADD_BOOK", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
//        String response = serverConnection.getResponse();
    }

    public void updateAccount(String username, String pass, boolean isAdmin, String id) {
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(username);
        args.add(pass);
        args.add(isAdmin + "");
        args.add(id + "");
        RequestMessage lr = new RequestMessage("UPDATE_ACCOUNT", "ACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
//        String response = serverConnection.getResponse();
    }

    public void updateBook(String id, String title, String author, String genre, int quant, int rating) {
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(id);
        args.add(title);
        args.add(author);
        args.add(genre);
        args.add(quant + "");
        args.add(rating + "");
        RequestMessage lr = new RequestMessage("UPDATE_BOOK", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
//        String response = serverConnection.getResponse();
    }

    public void deleteAccount(String username) {
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(username);
        RequestMessage lr = new RequestMessage("DELETE_ACCOUNT", "ACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
//        String response = serverConnection.getResponse();
    }

    public void deleteBook(String title) {
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(title);
        RequestMessage lr = new RequestMessage("DELETE_BOOK", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
//        String response = serverConnection.getResponse();
    }

    public List<Book> returnBooks() {
        ArrayList<Book> bookList;
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        RequestMessage lr = new RequestMessage("RET_BOOKS", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        Type listOfMyClassObject = new TypeToken<ArrayList<Book>>() {}.getType();
        bookList = new Gson().fromJson(response, listOfMyClassObject);

        return bookList;
    }

    public List<Account> returnAccounts() {
        ArrayList<Account> accList;
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        RequestMessage lr = new RequestMessage("RET_ACCOUNTS", "ACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        Type listOfMyClassObject = new TypeToken<ArrayList<Account>>() {}.getType();
        accList = new Gson().fromJson(response, listOfMyClassObject);

        return accList;
    }

    public List<BookAccount> returnBookAccounts() {
        ArrayList<BookAccount> bookList;
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        RequestMessage lr = new RequestMessage("RET_BOOKACC", "BOOKACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        Type listOfMyClassObject = new TypeToken<ArrayList<BookAccount>>() {}.getType();
        bookList = new Gson().fromJson(response, listOfMyClassObject);

        return bookList;
    }


}
