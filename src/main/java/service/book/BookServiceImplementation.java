package service.book;

import model.Book;
import model.builder.BookBuilder;
import model.validation.BookValidator;
import model.validation.Notification;
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
    public Notification<Boolean> save(String title, String author, String genre, int quantity, int price) {
        Book book = new BookBuilder()
                .setTitle(title)
                .setAuthor(author)
                .setGenre(genre)
                .setQuantity(quantity)
                .setPrice(price)
                .build();

        BookValidator bookValidator = new BookValidator(book);
        boolean bookValid = bookValidator.validate();
        Notification<Boolean> notification = new Notification<>();

        if(!bookValid){
            bookValidator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
            return notification;
        } else {
            bookRepository.save(book);
            notification.setResult(Boolean.TRUE);
            return notification;
        }
    }

    @Override
    public Notification<Boolean> update(long id, String title, String author, String genre, int quantity, int price) {
        Book book = new BookBuilder()
                .setId(id)
                .setTitle(title)
                .setAuthor(author)
                .setGenre(genre)
                .setQuantity(quantity)
                .setPrice(price)
                .build();

        BookValidator bookValidator = new BookValidator(book);
        boolean bookValid = bookValidator.validate();
        Notification<Boolean> notification = new Notification<>();

        if(!bookValid){
            bookValidator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
            return notification;
        } else {
            bookRepository.save(book);
            notification.setResult(Boolean.TRUE);
            return notification;
        }
    }

    @Override
    public Notification<Boolean> delete(long id) {
        Book book = new BookBuilder().setId(id).build();
        bookRepository.delete(book);
        Notification<Boolean> notification = new Notification<>();
        notification.setResult(Boolean.TRUE);
        return notification;
    }
}
