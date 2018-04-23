package service.search;

import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.book.BookRepository;

@Service
public class SearchServiceImplementation implements SearchService {

    private BookRepository bookRepository;

    @Autowired
    public SearchServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Iterable<Book> searchByTitleOrAuthorOrGenre(String term) {
        return bookRepository.findAllByQuery(term);
    }
}
