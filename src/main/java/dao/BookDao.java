package dao;

import java.util.List;
import model.Author;
import model.Book;
import model.Genre;

public interface BookDao {
    Book addBook(Book book);

    Book getByTitle(Book book);

    List<Book> getAuthorBooks(Author author);

    List<Book> getBooksByGenre(Genre genre);
}
