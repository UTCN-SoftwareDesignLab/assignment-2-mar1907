package repository.book;

import model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    Iterable<Book> findBooksByTitle(String title);

    Iterable<Book> findBooksByAuthor(String author);

    Iterable<Book> findBooksByGenre(String genre);
}
