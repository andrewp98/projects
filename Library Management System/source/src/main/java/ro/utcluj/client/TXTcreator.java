package ro.utcluj.client;

import ro.utcluj.intermediar.Account;
import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TXTcreator implements DocumentCreator{

    private static int id = 0;
    private JFileChooser f;
    private List<Account> accountList;
    private List<Book> bookList;
    private List<BookAccount> bookAccList;

    public TXTcreator(JFileChooser f, List<Account> accountList, List<Book> bookList, List<BookAccount> bookAccList) {
        id++;
        this.f = f;
        this.accountList = accountList;
        this.bookList = bookList;
        this.bookAccList = bookAccList;
    }

    @Override
    public void getDoc(){
        try
        {
            FileWriter fileWriter = new FileWriter(f.getSelectedFile() + "\\" + "Raport" + id +".txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("Borrowed books:");
            if(bookAccList.isEmpty()){
                printWriter.println("None.");
            }else{
                for(BookAccount b : bookAccList){
                    printWriter.println("User with id " + b.getAccountId() + " and name: " + accountList.get(b.getAccountId()-1).getUserName() + " borrowed " + b.getBookName());
                }
            }
            for(Book b : bookList){
                printWriter.println("Book title: " + b.getTitle() +  " Author: " + b.getAuthor() + " Available: " + b.getQuantity());
            }
            fileWriter.close();
            printWriter.close();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
