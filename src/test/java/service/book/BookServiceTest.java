package service.book;

import application.Application;
import model.Book;
import model.builder.BookBuilder;
import model.validation.Notification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import repository.book.BookRepository;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class BookServiceTest {

    private BookService bookService;

    @Before
    public void setUp() throws Exception {
        bookService = new BookServiceImplementation(Mockito.mock(BookRepository.class));
    }

    @Test
    public void findAll() {
        Iterable<Book> iterable = bookService.findAll();
        assertNotNull(iterable);
    }

    @Test
    public void save() {
        Notification<Boolean> notification = bookService.save("title", "author", "genre", 10, 10);
        assertEquals(notification.getResult(),Boolean.TRUE);
    }

    @Test
    public void update() {
        Notification<Boolean> notification = bookService.update(1,"title", "author", "genre", 10, 10);
        assertEquals(notification.getResult(),Boolean.TRUE);
    }

    @Test
    public void delete() {
        Notification<Boolean> notification = bookService.delete(1);
        assertEquals(notification.getResult(),Boolean.TRUE);
    }
}