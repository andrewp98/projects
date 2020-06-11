package ro.utcluj.server;

import com.google.gson.Gson;
import ro.utcluj.intermediar.Account;
import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;
import ro.utcluj.intermediar.RequestMessage;
import ro.utcluj.intermediar.actions.Transform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestHandler {

    public RequestHandler() {
    }

    public String handleRequestMessage(String msg, Server.Connection conn) {
        LoginServices loginServices = new LoginDAO();
        IQuerryRVC q = new QuerryRVC();
        IQuerrryAVC qa = new QuerryAVC();
        String ret = new String();
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");

        RequestMessage rm = Transform.desReqM(msg);
        ArrayList reqList = new ArrayList(rm.getArgs());

        switch (rm.getRequestName()){
            case "LOGIN_PASS":
                List<Account> accountCriteria = loginServices.getAccountByUsername(reqList.get(0).toString());
                ret = new Gson().toJson(accountCriteria);
                break;
            case "SRC_BT":
                ArrayList<Book> bookList = (ArrayList<Book>) q.searchByTitleQuerry(reqList.get(0).toString());
                ret = new Gson().toJson(bookList);
                break;
            case "SRC_BR":
                ArrayList<Book> b1 = (ArrayList<Book>) q.searchByRatingQuerry(Integer.parseInt(reqList.get(0).toString()));
                ret = new Gson().toJson(b1);
                break;
            case "SRC_BA":
                ArrayList<Book> b2 = (ArrayList<Book>) q.searchByAuthorQuerry(reqList.get(0).toString());
                ret = new Gson().toJson(b2);
                break;
            case "SRC_BG":
                ArrayList<Book> b3 = (ArrayList<Book>) q.searchByGenreQuerry(reqList.get(0).toString());
                ret = new Gson().toJson(b3);
                break;
            case "RET_BOOKS":
                List<Book> bookList1 = q.returnBooksQuerry();
                ret = new Gson().toJson(bookList1);
                break;
            case "RET_BOOKACC":
                ArrayList<BookAccount> bookAccounts = (ArrayList<BookAccount>) q.returnBookAccountsQerry();
                ret = new Gson().toJson(bookAccounts);
                break;
            case "RET_BOOKACCBYID":
                ArrayList<BookAccount> ba2 = (ArrayList<BookAccount>) q.returnBookAccountByID(reqList.get(0).toString());
                ret = new Gson().toJson(ba2);
                break;
            case "INSERT_BORROW":
                for(int i = 0; i < 3; i++)
                    System.out.println("ELEMENT: " + reqList.get(i).toString());
                try {
                    Date date = formatter2.parse(reqList.get(2).toString());
                    q.insertBorrow(reqList.get(0).toString(), reqList.get(1).toString(), date);
                    ret = "1";
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "EXECUTE_RET":
                ret = new Gson().toJson(q.executeReturnBook(reqList.get(0).toString(), reqList.get(1).toString()));
                break;
            case "ADD_ACCOUNT":
                qa.addAccountQuerry(reqList.get(0).toString(), reqList.get(1).toString(), Boolean.parseBoolean(reqList.get(2).toString()));
                ret = "Account added.";
                break;
            case "ADD_BOOK":
                qa.addBookQuerry(reqList.get(0).toString(), reqList.get(1).toString(),reqList.get(2).toString(), Integer.parseInt(reqList.get(3).toString()));
                ret = "Book Added.";
                break;
            case "UPDATE_ACCOUNT":
                qa.updateAccount(reqList.get(3).toString(), reqList.get(0).toString(), reqList.get(1).toString(), Boolean.parseBoolean(reqList.get(2).toString()));
                ret = "Account updated.";
                break;
            case "UPDATE_BOOK":
                qa.updateBook(reqList.get(0).toString(), reqList.get(1).toString(),reqList.get(2).toString(), reqList.get(3).toString(), Integer.parseInt(reqList.get(4).toString()), Integer.parseInt(reqList.get(5).toString()));
                ret = "Book updated.";
                break;
            case "DELETE_ACCOUNT":
                qa.deleteAccount(reqList.get(0).toString());
                ret = "Account deleted.";
                break;
            case "DELETE_BOOK":
                qa.deleteBook(reqList.get(0).toString());
                ret = "Book deleted.";
                break;
            case "RET_ACCOUNTS":
                ArrayList<Account> accList = (ArrayList<Account>) qa.returnAccountList();
                ret = new Gson().toJson(accList);
                break;
            case "ADD_TOFAV":
                String id = reqList.get(0).toString();
                String title = reqList.get(1).toString();

                ret = new Gson().toJson(q.addToFav(id, title));
                break;
            case "RET_FAV":
                ArrayList<Book> favBooks = (ArrayList<Book>) q.returnFavourites(reqList.get(0).toString());
                ret = new Gson().toJson(favBooks);
                break;
            case "ADD_PENALTY":
                ret = new Gson().toJson(q.addPenalty(reqList.get(0).toString()));
                break;
            case "ADD_TOWAIT":
                q.addToWaitingList(reqList.get(0).toString(), reqList.get(1).toString());
                ret = "ADDED TO WAITING LIST";
                break;
            case "RATE_BOOK":
                ret = new Gson().toJson(q.rateBook(reqList.get(0).toString(), Integer.parseInt(reqList.get(1).toString())));
                break;
            case "REV_BOOK":
                ret = new Gson().toJson(q.revBook(reqList.get(0).toString(), reqList.get(1).toString()));
                break;
            case "LOCK_ACC":
                ret = new Gson().toJson(q.lockAccount(Long.parseLong(reqList.get(0).toString()), reqList.get(1).toString()));
                break;
            /*case "RET_REV":
                ArrayList<String> revs = (ArrayList<String>) q.retRevs(reqList.get(0).toString());
                ret = new Gson().toJson(revs);
                break;*/
        }

        return ret;
    }
}
