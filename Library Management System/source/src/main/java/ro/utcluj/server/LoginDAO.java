package ro.utcluj.server;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import ro.utcluj.intermediar.Account;

import java.util.List;

public class LoginDAO implements LoginServices {

    public List getAccountByUsername(String username){
        Transaction tr = null;
        Session session = AppSes.getSession().openSession();
        List<Account> accountCriteria;
        try{
            tr = session.beginTransaction();
            Criteria cr = session.createCriteria(Account.class);
            cr.add(Restrictions.like("userName", username));
            accountCriteria = cr.list();
            tr.commit();
        }catch(HibernateException e){
            tr.rollback();
            throw e;
        }finally {
            session.close();
        }
        return accountCriteria;
    }


}
