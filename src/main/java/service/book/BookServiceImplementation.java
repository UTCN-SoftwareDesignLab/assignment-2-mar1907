package service.book;

import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.book.BookRepository;
import service.book.BookService;

@Service
public class BookServiceImplementation implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void save(String title, String author, String genre, int quantity, int price) {
        //TODO create book

    }
}
