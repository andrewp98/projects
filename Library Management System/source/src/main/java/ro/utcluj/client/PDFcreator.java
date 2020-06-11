package ro.utcluj.client;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import ro.utcluj.intermediar.Account;
import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PDFcreator implements DocumentCreator{

    private static int id = 0;
    private JFileChooser f;
    private List<Account> accountList;
    private List<Book> bookList;
    private List<BookAccount> bookAccList;

    public PDFcreator(JFileChooser f, List<Account> accountList, List<Book> bookList, List<BookAccount> bookAccList) {
        id++;
        this.f = f;
        this.accountList = accountList;
        this.bookList = bookList;
        this.bookAccList = bookAccList;
    }

    @Override
    public void getDoc(){
        Document document = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(f.getSelectedFile() + "\\" + "Raport" + id +".pdf"));
            document.open();
            document.add(new Paragraph("Borrowed books:"));
            if(bookAccList.isEmpty()){
                document.add(new Paragraph("None."));
            }else{
                for(BookAccount b : bookAccList){
                    document.add(new Paragraph("User with id " + b.getAccountId() + " and name: " + accountList.get(b.getAccountId()-1).getUserName() + " borrowed " + b.getBookName()));
                }
            }
            document.add(new Paragraph("Books in library:"));
            for(Book b : bookList){
                document.add(new Paragraph("Book title: " + b.getTitle() +  " Author: " + b.getAuthor() + " Available: " + b.getQuantity()));
            }
            document.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
