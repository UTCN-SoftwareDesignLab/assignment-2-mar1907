package service.search;

import model.Book;

public interface SearchService {

    public Iterable<Book> searchByTitleOrAuthorOrGenre(String term);
}
