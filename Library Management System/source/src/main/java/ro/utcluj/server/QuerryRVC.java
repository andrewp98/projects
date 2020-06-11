package ro.utcluj.server;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ro.utcluj.intermediar.Account;
import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

public class QuerryRVC extends Observable implements IQuerryRVC {

//    private

    private Account getAccount(String id){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<Account> accounts = new ArrayList<>();
        Account account = new Account();
        try{
            tr = session.beginTransaction();
            accounts = new ArrayList<Account>(session.createQuery("from Account where id = :actId", Account.class).setString( "actId", id).list());
            account = accounts.get(0);
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return account;
    }

    public List<Book> searchByTitleQuerry(String title){
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

    public List<Book> searchByRatingQuerry(int rating){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<Book> bookList = new ArrayList<Book>();
        try{
            tr = session.beginTransaction();

            bookList = new ArrayList<Book>(session.createQuery("from Book where rating='" + rating + "'", Book.class).list());

            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return bookList;
    }

    public List<Book> searchByAuthorQuerry(String auth){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<Book> bookList;
        try{
            tr = session.beginTransaction();
            bookList = new ArrayList<Book>(session.createQuery("from Book where author='" + auth + "'", Book.class).list());
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return bookList;
    }

    public List<Book> searchByGenreQuerry(String genre){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<Book> bookList;
        try{
            tr = session.beginTransaction();
            bookList = new ArrayList<Book>(session.createQuery("from Book where genre='" + genre + "'", Book.class).list());
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return bookList;
    }

    public int rateBook(String title, int rate){
        ArrayList<Book> books = new ArrayList<>();
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        try{
            tr = session.beginTransaction();

            books = new ArrayList<Book>(session.createQuery("from Book where title='" + title + "'", Book.class).list());
            Book bk = books.get(0);
            session.evict(bk);

            //logica pentru calculat ratingul

            bk.setRating(rate);
            session.update(bk);

            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return 1;
    }

    public int revBook(String title, String rev){
        ArrayList<Book> books = new ArrayList<>();
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        try{
            tr = session.beginTransaction();

            books = new ArrayList<Book>(session.createQuery("from Book where title='" + title + "'", Book.class).list());
            Book bk = books.get(0);
            session.evict(bk);

            //updatam lista de review-uri
            if(bk.getReview() == null){
                ArrayList<String> ls = new ArrayList<String>();
                ls.add(rev);
                bk.setReview(ls);
            }else{
                bk.getReview().add(rev);
            }
            session.update(bk);

            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return 1;
    }

    public int addPenalty(String id){
        ArrayList<Account> accounts = new ArrayList<>();
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        try{
            tr = session.beginTransaction();

            accounts = new ArrayList<Account>(session.createQuery("from Account where id = :actId", Account.class).setString( "actId", id).list());
            Account acc = accounts.get(0);
            session.evict(acc);

            int pen = acc.getPenalty() + 100;
            acc.setPenalty(pen);
            session.update(acc);

            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }

        return 1;
    }

    public int addToFav(String id, String title){
        ArrayList<Book> bookList = new ArrayList<Book>();
        ArrayList<Account> accounts = new ArrayList<>();
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        try{
            tr = session.beginTransaction();
            bookList = new ArrayList<Book>(session.createQuery("from Book where title='" + title + "'", Book.class).list());
            Book bk = bookList.get(0);
//            System.out.println(bk.getTitle());
            accounts = new ArrayList<Account>(session.createQuery("from Account where id = :actId", Account.class).setString( "actId", id).list());
            Account acc = accounts.get(0);
//            System.out.println(acc.getUserName());
            if(acc.getFavourites() == null){
                bookList = new ArrayList<Book>();
            }else{
                bookList = acc.getFavourites();
                for(Book b : bookList){
                    if(bk.getTitle().equals(b.getTitle())){
                        tr.commit();
                        return 0;
                    }
                }
            }
            bookList.add(bk);

            session.evict(acc);
            acc.setFavourites(bookList);
            session.update(acc);

            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return 1;
    }

    public List<Book> returnFavourites(String id){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<Book> bookList;
        ArrayList<Account> accounts = new ArrayList<>();
        try{
            tr = session.beginTransaction();
            accounts = new ArrayList<Account>(session.createQuery("from Account where id = :actId", Account.class).setString( "actId", id).list());
            Account acc = accounts.get(0);
            bookList = acc.getFavourites();
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return bookList;
    }

    /*public List<String> retRevs(String id){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<String> revs;
        ArrayList<Book> bookList;
        ArrayList<Account> accounts = new ArrayList<>();
        try{
            tr = session.beginTransaction();
            accounts = new ArrayList<Account>(session.createQuery("from Account where id = :actId", Account.class).setString( "actId", id).list());
            Account acc = accounts.get(0);
            bookList = acc.getFavourites();
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return bookList;
    }*/

    public void insertBorrow(String id, String title, Date date){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        try{
            tr = session.beginTransaction();

            Account acc = getAccount(id);
            session.evict(acc);
            acc.setSubscription(acc.getSubscription() - 1);
            session.update(acc);

            session.save(new BookAccount(Integer.parseInt(id), title, date));
            String hqlUpdate = "update Book set quantity = quantity - 1 where title = :actBookName";
            int updatedEntities = session.createQuery( hqlUpdate )
                    .setString( "actBookName", title)
                    .executeUpdate();
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    public List<BookAccount> returnBookAccountByID(String id){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<BookAccount> bookList;
        try{
            tr = session.beginTransaction();
            bookList = new ArrayList<BookAccount>(session.createQuery("from BookAccount where accountId='" + id + "'", BookAccount.class).list());
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return bookList;
    }

    public int executeReturnBook(String id, String title){
        int retVal = 0;
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<Book> bookList = new ArrayList<>();
        try{
            tr = session.beginTransaction();
            //asta e partea de observer -- ne luam cartea verificam daca stocul e 0
            //daca e atunci insemana ca urmeaza sa notificam pe cineva
            //doar apelam metoda de notify
            Account acc = getAccount(id);
            session.evict(acc);
            acc.setSubscription(acc.getSubscription() + 1);
            session.update(acc);

            bookList = new ArrayList<Book>(session.createQuery("from Book where title='" + title + "'", Book.class).list());
            Book b = bookList.get(0);
            if(b.getQuantity() == 0){
                if(b.getInAsteptare() != null){
                    String o = "The book you were waiting for is available now!";
                    b.notifyAvb(o);
//                    setChanged();
//                    notifyObservers(o);
                    System.out.println("DID NOTIFIYED");
                    retVal = 1;
                }
            }
            String hqlUpdate = "update Book set quantity = quantity + 1 where title = :actBookName";
            int updatedEntities = session.createQuery( hqlUpdate )
                    .setString( "actBookName", title)
                    .executeUpdate();
            String hqlUpdate2 = "delete from BookAccount where accountId = :actId AND bookName = :actBookName";
            int updatedEntities2 = session.createQuery( hqlUpdate2 )
                    .setString( "actId", id)
                    .setString("actBookName", title)
                    .executeUpdate();
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        if(retVal == 1){
            removeFromWaitingList(id, title);
        }
        return retVal;
    }

    public List<Book> returnBooksQuerry(){
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

    public List<BookAccount> returnBookAccountsQerry(){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<BookAccount> bookAccList;
        try{
            tr = session.beginTransaction();
            bookAccList = new ArrayList<BookAccount>(session.createQuery("from BookAccount", BookAccount.class).list());
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return bookAccList;
    }

    public void addToWaitingList(String id, String title/*, String arg*/){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<Book> bookList = new ArrayList<>();
//        RegularViewController rvc = new Gson().fromJson(arg, RegularViewController.class);
        try{
            tr = session.beginTransaction();

            bookList = new ArrayList<Book>(session.createQuery("from Book where title='" + title + "'", Book.class).list());
            Book bk = bookList.get(0);
            accounts = new ArrayList<Account>(session.createQuery("from Account where id = :actId", Account.class).setString( "actId", id).list());
            Account acc = accounts.get(0);

            if(bk.getInAsteptare() == null){
                accounts = new ArrayList<Account>();
            }else{
                accounts = bk.getInAsteptare();
            }
            accounts.add(acc);

            session.evict(bk);
            bk.setInAsteptare(accounts);
            session.update(bk);
//            bk.addObserver(rvc);

            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    private void removeFromWaitingList(String id, String title){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<Book> bookList = new ArrayList<>();
        try{
            tr = session.beginTransaction();

            bookList = new ArrayList<Book>(session.createQuery("from Book where title='" + title + "'", Book.class).list());
            Book bk = bookList.get(0);
            accounts = new ArrayList<Account>(session.createQuery("from Account where id = :actId", Account.class).setString( "actId", id).list());
            Account acc = accounts.get(0);

            accounts.remove(acc);

            session.evict(bk);
            bk.setInAsteptare(accounts);
            session.update(bk);

            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    public int lockAccount(Long time, String id){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<Book> bookList = new ArrayList<>();
        try{
            tr = session.beginTransaction();

            accounts = new ArrayList<Account>(session.createQuery("from Account where id = :actId", Account.class).setString( "actId", id).list());
            Account acc = accounts.get(0);

            session.evict(acc);

            Date d = new Date(time);
            acc.setLockDate(d);
            session.update(acc);

            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return 1;
    }

}
