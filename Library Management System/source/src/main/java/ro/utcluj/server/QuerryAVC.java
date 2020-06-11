package ro.utcluj.server;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ro.utcluj.intermediar.Account;
import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;

import java.util.ArrayList;
import java.util.List;

public class QuerryAVC /*extends Observable*/ implements IQuerrryAVC {

    public void addAccountQuerry(String username, String pass, boolean isAdmin){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        try{
            tr = session.beginTransaction();
            session.save(new Account(username, pass, isAdmin));
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    public List<Book> getBook(String title){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<Book> bookList = new ArrayList<Book>();
        try{
            tr = session.beginTransaction();

            bookList = new ArrayList<Book>(session.createQuery("from Book where title='" + title + "'", Book.class).list());

            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return bookList;
    }

    public void addBookQuerry(String title, String author, String genre, int quant) {
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        try{
            tr = session.beginTransaction();
            session.save(new Book(genre, title, author, quant));
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
//        setChanged();
//        notifyObservers();
    }

    public void updateAccount(String id, String username, String pass, boolean isAdmin){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        try{
            tr = session.beginTransaction();
            String hqlUpdate = "update Account a set a.isAdmin = :newRights, a.password = :newPass, a.userName = :newUser where a.id = :id";
            int updatedEntities = session.createQuery( hqlUpdate )
                    .setBoolean( "newRights",  isAdmin)
                    .setString("newPass", pass)
                    .setString("newUser", username)
                    .setString( "id", id)
                    .executeUpdate();
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    public void updateBook(String id, String title, String author, String genre, int quant, int rating){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        try{
            tr = session.beginTransaction();
            String hqlUpdate = "update Book b set b.title = :newTitle, b.genre = :newGen, b.author = :newAuth, " +
                    "b.quantity = :newQuant, b.rating = :newRating where b.id = :id";
            int updatedEntities = session.createQuery( hqlUpdate )
                    .setString( "newTitle",  title)
                    .setString("newGen", genre)
                    .setString("newAuth", author)
                    .setInteger("newQuant", quant)
                    .setInteger("newRating", rating)
                    .setString( "id", id)
                    .executeUpdate();
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    public void deleteAccount(String username){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        try{
            tr = session.beginTransaction();
            List<Account> accounts = session.createQuery("from Account where userName='" + username +"'", Account.class).list();
            session.delete(accounts.get(0));
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    public void deleteBook(String title){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        try{
            tr = session.beginTransaction();
            List<Book> bookList = session.createQuery("from Book where title='" + title +"'", Book.class).list();
            session.delete(bookList.get(0));
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    public List<Book> returnBookList(){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<Book> bookList;
        try{
            tr = session.beginTransaction();
            bookList = new ArrayList<Book>(session.createQuery("from Book", Book.class).list());
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return bookList;
    }

    public List<Account> returnAccountList(){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<Account> accounts;
        try{
            tr = session.beginTransaction();
            accounts = new ArrayList<Account>(session.createQuery("from Account", Account.class).list());
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return accounts;
    }

    public List<BookAccount> returnBookAccountList(){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<BookAccount> bookList;
        try{
            tr = session.beginTransaction();
            bookList = new ArrayList<BookAccount>(session.createQuery("from BookAccount", BookAccount.class).list());
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return bookList;
    }
}
