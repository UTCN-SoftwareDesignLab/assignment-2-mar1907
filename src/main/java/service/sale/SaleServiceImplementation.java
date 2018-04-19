package service.sale;

import model.Book;
import model.Sale;
import model.User;
import model.builder.SaleBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.book.BookRepository;
import repository.sale.SaleRepository;

import java.sql.Date;
import java.util.Optional;

@Service
public class SaleServiceImplementation implements SaleService {

    private SaleRepository saleRepository;
    private BookRepository bookRepository;

    @Autowired
    public SaleServiceImplementation(SaleRepository saleRepository, BookRepository bookRepository) {
        this.saleRepository = saleRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Notification<Integer> sell(long bookId, int quantity, long userId) {
        User user = new UserBuilder().setId(userId).build();
        Optional<Book> optBook = bookRepository.findById(bookId);
        Notification<Integer> notification = new Notification<>();
        if(!optBook.isPresent()){
            notification.addError("No such book!");
            notification.setResult(0);
            return notification;
        }

        Book book = optBook.get();
        if(book.getQuantity()<quantity){
            notification.addError("Not enough books in stock");
            notification.setResult(0);
            return notification;
        }

        int price = quantity*book.getPrice();
        Sale sale = new SaleBuilder()
                .setUser(user)
                .setBook(book)
                .setDate(new Date(System.currentTimeMillis()))
                .setQuantity(quantity)
                .setPrice(price)
                .build();

        saleRepository.save(sale);
        book.setQuantity(book.getQuantity()-quantity);
        bookRepository.save(book);

        notification.setResult(price);
        return notification;
    }

    @Override
    public void deleteByUserId(long userId) {
        saleRepository.deleteByUser_Id(userId);
    }

    @Override
    public void deleteByBookId(long bookId) {
        saleRepository.deleteByBook_Id(bookId);
    }
}
