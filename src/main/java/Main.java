import dao.AuthorDao;
import dao.AuthorDaoImpl;
import dao.BookDao;
import dao.BookDaoImpl;
import dao.GenreDao;
import dao.GenreDaoImpl;
import java.util.List;
import model.Author;
import model.Book;
import model.Genre;

public class Main {
    public static void main(String[] args) {
        GenreDao genreDao = new GenreDaoImpl();
        Genre genre = new Genre();
        genre.setName("fantasy");
        genreDao.addGenre(genre);
        AuthorDao authorDao = new AuthorDaoImpl();
        Author author = new Author();
        author.setName("valera");
        authorDao.addAuthor(author);
        Author author2 = new Author();
        author.setName("maks");
        authorDao.addAuthor(author2);
        Book book = new Book();
        book.setTitle("some_book");
        book.setGenre(genre);
        book.setAuthors(List.of(author, author2));
        BookDao bookDao = new BookDaoImpl();
        bookDao.addBook(book);
        bookDao.getBooksByGenre(genre).forEach(System.out::println);
        bookDao.getAuthorBooks(author2).forEach(System.out::println);
        System.out.println(bookDao.getByTitle(book));
    }
}
