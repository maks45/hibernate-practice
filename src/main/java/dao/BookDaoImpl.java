package dao;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Author;
import model.Book;
import model.Genre;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class BookDaoImpl implements BookDao {

    @Override
    public Book addBook(Book book) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("can't save book", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return book;
    }

    @Override
    public Book getByTitle(Book book) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);
            query.select(root).where(criteriaBuilder.equal(root.get("title"), book.getTitle()));
            return session.createQuery(query).getSingleResult();
        } catch (HibernateException e) {
            throw new RuntimeException("can't find book", e);
        }
    }

    @Override
    public List<Book> getAuthorBooks(Author author) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);
            query.select(root).where(criteriaBuilder.isMember(author,
                    root.get("authors")));
            return session.createQuery(query).getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("can't find book by author ", e);
        }
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);
            query.select(root).where(criteriaBuilder.equal(root.get("genre"), genre));
            return session.createQuery(query).getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("can't find books by genre ", e);
        }
    }
}
