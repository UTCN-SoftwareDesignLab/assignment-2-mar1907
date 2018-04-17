package service.book;

import model.Book;
import model.validation.Notification;

public interface BookService {

    Iterable<Book> findAll();

    Notification<Boolean> save(String title, String author, String genre, int quantity, int price);

    Notification<Boolean> update(long id, String title, String author, String genre, int quantity, int price);

    Notification<Boolean> delete(long id);
}
