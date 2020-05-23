package dao;

import model.Genre;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class GenreDaoImpl implements GenreDao {
    public Genre addGenre(Genre genre) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(genre);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("can't save genre", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return genre;
    }
}
