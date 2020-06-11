package ro.utcluj.client;

import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegularViewController {

    private final IRegularView regularView;
    private final RegularVCRequest req;

    public RegularViewController(IRegularView regularView, RegularVCRequest req){
        this.regularView = regularView;
        this.req = req;
    }

    public List<Book> searchByTitle() {
        String title = regularView.getTitle();
        List<Book> bookList;

        bookList = req.searchByTitle(title);
        return bookList;
    }

    public List<Book> searchByAuthor() {
        String auth = regularView.getAuthor();
        List<Book> bookList;
        bookList = req.searchByAuthor(auth);
        return bookList;
    }

    public List<Book> searchByGenre() {
        String genre = regularView.getGenre();
        List<Book> bookList;
        bookList = req.searchByGenre(genre);
        return bookList;
    }

    public List<Book> searchByRating() {
        int rating = regularView.getRating();
        List<Book> bookList;

        bookList = req.searchByRating(rating);
        return bookList;
    }


    public void borrowBook() {
        String title = regularView.getTitle();
        String time = regularView.getReturnDate();
        String id = regularView.getLoggedInId() + "";
        List<Book> bookList = req.searchByTitle(title);
        if (!bookList.isEmpty()) {
            if (bookList.get(0).getQuantity() > 0) {
                SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
                try {
                   Date date = formatter2.parse(time);
                   Date currentDay = formatter2.parse(formatter2.format(new Date()));
                   //verificam mai intai daca nu cumva lockdate-ul e diferit de cel din curent
                   Date checkDate = req.getLockDate(Integer.parseInt(id));
                   if(checkDate.equals(currentDay)) {
                       if (currentDay.before(date)) {
                           if (req.getSubs(Integer.parseInt(id)) > 0) {
                               List<BookAccount> ba = returnBookAccountsByID(id);
                               for (BookAccount b : ba) {
                                   if (b.getBookName().equals(title)) {
                                       regularView.showErrorMessage("Book already borrowed!");
                                       return;
                                   }
                               }
                               if (req.insertBorrow(id, title, time) == 1) {
                                   regularView.showErrorMessage(title + " borrowed successfully!");
                               }
                           } else {
                               regularView.showErrorMessage("Borrow limit reached!");
                               return;
                           }

                       } else {
                           regularView.showErrorMessage("Invalid return date!");
                       }
                   }else{
                       regularView.showErrorMessage("You cannot borrow books until the penalty month passes!");
                   }
                } catch (ParseException e) {
                    regularView.showErrorMessage("Respect the time format! Ex. 12-AUG-2010");
                }
            } else {
                //aici adaugam modalul pentru verificat daca doreste sa fie pus in queue
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Out of stock.\n" +
                        " Would You Like to be added to the waiting list?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION) {
                    req.addToWaitingList(id, title);
                }
            }
        } else {
            regularView.showErrorMessage("No such book!");
        }
    }

    public void returnBook() {
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        String title = regularView.getTitle();
        String id = regularView.getLoggedInId() + "";
        List<Book> bookList;
        List<BookAccount> tempBookAcc;
        bookList = req.searchByTitle(title);

        if (!bookList.isEmpty()) {
            tempBookAcc = req.returnBookAccountsByID(id);
            if (!tempBookAcc.isEmpty()) {
                boolean notBorrowed = true;
                for(BookAccount ba : tempBookAcc){
                    try{
                        //trebuie parsata data cartii
                        if (ba.getBookName().equals(title)) {
                            Date date = ba.getReturnDate();
                            Date currentDay = formatter2.parse(formatter2.format(new Date()));
                            //verificam mai intai daca nu cumva lockdate-ul e diferit de cel din curent
                            Date checkDate = req.getLockDate(Integer.parseInt(id));
                            if(checkDate.equals(currentDay)) {
                                //daca corespunde cu date curenta inseamna ca poate imprumuta carti
                                if (currentDay.before(date)) {//daca data e valida (gen e returnata la timp)
                                    if (req.getPenalty(Integer.parseInt(id)) > 500) {
                                        regularView.showErrorMessage("Too many penalties accumulated! You won't be able to borrow books for one month!");
                                        //setam ca nu mai are voie sa imprumute carti timp de o luna
                                        req.setLockDate(currentDay.getTime() + 2592000000L, id);
                                    } else {
                                        int i = req.executeReturnBook(id, title);
                                        if (i == 0) {
                                            regularView.showErrorMessage(title + " return successfully!");
                                            notBorrowed = false;
                                        } else {
                                            if (i == 1) {
                                                //regularView.showErrorMessage("The book you were waiting for is available now!");
                                                notBorrowed = false;
                                            }
                                        }
                                    }
                                } else {
                                    if (req.getPenalty(Integer.parseInt(id)) > 500) {
                                        regularView.showErrorMessage("Too many penalties accumulated! You won't be able to borrow books for one month!");
                                        //setam ca nu mai are voie sa imprumute carti timp de o luna
                                        req.setLockDate(currentDay.getTime() + 2592000000L, id);
                                    } else {
                                        if (req.addPenalty(id) == 1) {
                                            regularView.showErrorMessage("You returned the book too late, a penalty will be added in your account.");
                                            int i = req.executeReturnBook(id, title);
                                            if (i == 0) {
                                                regularView.showErrorMessage(title + " return successfully!");
                                                notBorrowed = false;
                                            } else {
                                                if (i == 1) {
                                                    regularView.showErrorMessage(title + " return successfully!");
                                                    notBorrowed = false;
                                                }
                                            }
                                        }
                                    }
                                }
                            } else{
                                regularView.showErrorMessage("You cannot borrow books until the penalty month passes!");
                            }
                        }
                    }catch (ParseException e) {
                        regularView.showErrorMessage("Time format error!");
                    }
                }
                if(notBorrowed) {
                    regularView.showErrorMessage("No record of you borrowing this book from this library!");
                }
            } else {
                regularView.showErrorMessage("No record of you borrowing any book from this library!");
            }
        } else {
            regularView.showErrorMessage("No such book in this library!");
        }
    }

    public void addToFav(){
        String title = regularView.getTitle();
        String id = regularView.getLoggedInId() + "";
        int ret = 0;

        if(title != null){
            ret = req.addToFav(id, title);
            if(ret == 0){
                regularView.showErrorMessage("Book already in favourites!");
            }else{
                regularView.showErrorMessage("Book added to favourites!");
            }
        }else{
            regularView.showErrorMessage("Title not found!");
        }
    }

    public void rateBook(){
        String title = regularView.getTitle();
        int rate = 1;
        int ret = regularView.getRating();

        if(ret == 0){
            return;
        }else {
            if (ret < 1 || ret > 5) {
                regularView.showErrorMessage("Rating must be between 1 and 5 stars!");
                return;
            } else {
                rate = ret;
            }
        }

        if(title != null){
            ret = req.rateBook(title, rate);
            if(ret == 1) {
                regularView.showErrorMessage("Book rated!");
            }else{
                regularView.showErrorMessage("Book not rated!");
            }
        }else{
            regularView.showErrorMessage("Title not found!");
        }
    }

    public void revBook(){
        String title = regularView.getTitle();
        String rev = regularView.getReview();
        int ret = 0;

        if(title != null){
            ret = req.revBook(title, rev);
            if(ret == 1) {
                regularView.showErrorMessage("Review added");
            }else{
                regularView.showErrorMessage("Review not added");
            }
        }else{
            regularView.showErrorMessage("Title not found!");
        }
    }

    public List<String> getReviews(){
        String id = regularView.getLoggedInId() + "";
        List<String> revs = new ArrayList<>();

        revs = req.returnRevs(id);
        return revs;
    }

    public List<Book> returnFav() {
        String id = regularView.getLoggedInId() + "";
        List<Book> bookList = new ArrayList<>();

        bookList = req.returnFav(id);
        return bookList;
    }

    public List<Book> returnBooks() {
        List<Book> bookList = new ArrayList<>();
        bookList = req.returnBooks();
        return bookList;
    }

    public List<BookAccount> returnBookAccounts() {
        List<BookAccount> bookAccounts = new ArrayList<>();
        bookAccounts = req.returnBookAccounts();
        return bookAccounts;
    }

    public List<BookAccount> returnBookAccountsByID() {
        String id = regularView.getLoggedInId() + "";
        List<BookAccount> ba = new ArrayList<>();

        ba = req.returnBookAccountsByID(id);
        return ba;
    }

    public List<BookAccount> returnBookAccountsByID(String id) {
        List<BookAccount> ba = new ArrayList<>();

        ba = req.returnBookAccountsByID(id);
        return ba;
    }
}