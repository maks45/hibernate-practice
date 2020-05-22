package dao;

import model.Author;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class AuthorDaoImpl implements AuthorDao {

    @Override
    public Author addAuthor(Author author) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(author);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("can't save author", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return author;
    }
}
