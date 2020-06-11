package ro.utcluj.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ro.utcluj.intermediar.Account;
import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;
import ro.utcluj.intermediar.RequestMessage;
import ro.utcluj.intermediar.actions.Transform;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegularVCRequest {
    private ServerConnection serverConnection;

    public Book getBookByTitle(String title){
        List<Book> bookList = searchByTitle(title);
        for(Book b : bookList){
            if(b.getTitle().equals(title)){
                return b;
            }
        }
        return null;
    }

    public List<Book> searchByRating(int rating) {
        ArrayList<Book> bookList;
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(rating + "");
        RequestMessage lr = new RequestMessage("SRC_BR", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        Type listOfMyClassObject = new TypeToken<ArrayList<Book>>() {}.getType();
        bookList = new Gson().fromJson(response, listOfMyClassObject);

        return bookList;
    }

    public List<Book> searchByTitle(String title) {
        ArrayList<Book> bookList;
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

    public List<Book> searchByAuthor(String auth) {
        ArrayList<Book> bookList;
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(auth);
        RequestMessage lr = new RequestMessage("SRC_BA", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        Type listOfMyClassObject = new TypeToken<ArrayList<Book>>() {}.getType();
        bookList = new Gson().fromJson(response, listOfMyClassObject);

        return bookList;
    }

    public List<Book> searchByGenre(String genre) {
        ArrayList<Book> bookList;
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(genre);
        RequestMessage lr = new RequestMessage("SRC_BG", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        Type listOfMyClassObject = new TypeToken<ArrayList<Book>>() {}.getType();
        bookList = new Gson().fromJson(response, listOfMyClassObject);

        return bookList;
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

    public List<BookAccount> returnBookAccounts() {
        ArrayList<BookAccount> bookList;
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        RequestMessage lr = new RequestMessage("RET_BOOKACC", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        Type listOfMyClassObject = new TypeToken<ArrayList<BookAccount>>() {}.getType();
        bookList = new Gson().fromJson(response, listOfMyClassObject);

        return bookList;
    }

    public List<BookAccount> returnBookAccountsByID(String id) {
        ArrayList<BookAccount> bookList = new ArrayList<>();
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(id);
        RequestMessage lr = new RequestMessage("RET_BOOKACCBYID", "BOOKACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        Type listOfMyClassObject = new TypeToken<ArrayList<BookAccount>>() {}.getType();
        bookList = new Gson().fromJson(response, listOfMyClassObject);

        return bookList;
    }

    public int insertBorrow(String id, String title, String time){
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(id);
        args.add(title);
        args.add(time);
        RequestMessage lr = new RequestMessage("INSERT_BORROW", "BOOKACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        return Integer.parseInt(response);
    }

    public int executeReturnBook(String id, String title){
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(id);
        args.add(title);
        RequestMessage lr = new RequestMessage("EXECUTE_RET", "BOOKACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        return new Gson().fromJson(response, Integer.class);
    }

    public int addToFav(String id, String title){
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(id);
        args.add(title);
        RequestMessage lr = new RequestMessage("ADD_TOFAV", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        return new Gson().fromJson(response, Integer.class);
    }



    public List<Book> returnFav(String id) {
        ArrayList<Book> bookList;
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(id);
        RequestMessage lr = new RequestMessage("RET_FAV", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        Type listOfMyClassObject = new TypeToken<ArrayList<Book>>() {}.getType();
        bookList = new Gson().fromJson(response, listOfMyClassObject);

        return bookList;
    }

    public int addPenalty(String id){
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(id);
        RequestMessage lr = new RequestMessage("ADD_PENALTY", "ACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        return new Gson().fromJson(response, Integer.class);
    }

    public int addToWaitingList(String id, String title){
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(id);
        args.add(title);
        RequestMessage lr = new RequestMessage("ADD_TOWAIT", "ACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        return new Gson().fromJson(response, Integer.class);
    }

    public int rateBook(String title, int rate){
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(title);
        args.add(rate+"");
        RequestMessage lr = new RequestMessage("RATE_BOOK", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        return new Gson().fromJson(response, Integer.class);
    }

    public int revBook(String title, String rev){
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(title);
        args.add(rev);
        RequestMessage lr = new RequestMessage("REV_BOOK", "ACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        return new Gson().fromJson(response, Integer.class);
    }

    public List<String> returnRevs(String id){
        ArrayList<String> revList;
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(id);
        RequestMessage lr = new RequestMessage("RET_REV", "BOOK", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        Type listOfMyClassObject = new TypeToken<ArrayList<String>>() {}.getType();
        revList = new Gson().fromJson(response, listOfMyClassObject);

        return revList;
    }

    public int getSubs(int id){
        List<Account> accounts = new AdminVCRequest().returnAccounts();
        int ret = 1;
        for(Account a : accounts){
            if(a.getId() == id){
                ret = a.getSubscription();
            }
        }
        return ret;
    }

    public int getPenalty(int id){
        List<Account> accounts = new AdminVCRequest().returnAccounts();
        int ret = 1;
        for(Account a : accounts){
            if(a.getId() == id){
                ret = a.getPenalty();
            }
        }
        return ret;
    }

    public Date getLockDate(int id){
        List<Account> accounts = new AdminVCRequest().returnAccounts();
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = null;
        for(Account a : accounts){
            if(a.getId() == id){
                d = a.getLockDate();
            }
        }
        if(d == null){
            try {
                d = formatter2.parse(formatter2.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return d;
    }

    public int setLockDate(Long time, String id){
        serverConnection = ConnManager.getConn();

        List<String> args = new ArrayList<>();
        args.add(time + "");
        args.add(id);
        RequestMessage lr = new RequestMessage("LOCK_ACC", "ACCOUNT", args);
        String message = Transform.serialize(lr);

        serverConnection.sendRequest(message);
        String response = serverConnection.getResponse();
        return new Gson().fromJson(response, Integer.class);
    }

}
