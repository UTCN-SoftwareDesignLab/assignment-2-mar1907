package service.search;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import repository.book.BookRepository;
import service.book.BookService;
import service.book.BookServiceImplementation;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SearchServiceTest {

    private BookService bookService;

    @Before
    public void setUp() throws Exception {
        bookService = new BookServiceImplementation(Mockito.mock(BookRepository.class));
    }

    @Test
    public void searchByTitle() {
    }

    @Test
    public void searchByAuthor() {
    }

    @Test
    public void searchByGenre() {
    }
}