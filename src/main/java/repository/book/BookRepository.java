package repository.book;

import model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    Iterable<Book> findBooksByTitleOrAuthorOrGenre(String title, String author, String genre);
}
