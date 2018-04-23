package service.search;

import model.Book;

public interface SearchService {

    Iterable<Book> searchByTitleOrAuthorOrGenre(String term);
}
