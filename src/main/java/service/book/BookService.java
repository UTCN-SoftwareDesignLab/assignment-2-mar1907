package service.book;

import model.Book;

public interface BookService {

    Iterable<Book> findAll();

    void save(String title, String author, String genre, int quantity, int price);
}
