package service.search;

import model.Book;

public interface SearchService {

    Iterable<Book> searchByTitle(String title);

    Iterable<Book> searchByAuthor(String author);

    Iterable<Book> searchByGenre(String genre);
}
