package repository.book;

import model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Query("from Book as b where b.title like %?1% or b.author like %?1% or b.genre like %?1%")
    List<Book> findAllByQuery(String q);
}
