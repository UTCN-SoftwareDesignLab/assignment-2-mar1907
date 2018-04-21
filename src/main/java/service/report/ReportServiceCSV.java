package service.report;

import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import repository.book.BookRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ReportServiceCSV implements ReportService {

    private BookRepository bookRepository;

    public ReportServiceCSV(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private static final String TITLE_AUTHOR_GENRE_PRICE = "Id,Title,Author,Genre,Price\n";
    private static final String CSQ = ",";
    private static final String CSQ1 = "\n";

    @Override
    public void createReport() {
        List<Book> books = StreamSupport.stream(bookRepository.findAll().spliterator(),false)
                .filter(b->b.getQuantity()==0)
                .collect(Collectors.toCollection(ArrayList::new));


        try {
            FileWriter writer = new FileWriter("OutOfStockBooks.csv");
            writer.append(TITLE_AUTHOR_GENRE_PRICE);
            for(Book book:books){
                writer.append(book.getId()+"");
                writer.append(CSQ);
                writer.append(book.getTitle());
                writer.append(CSQ);
                writer.append(book.getAuthor());
                writer.append(CSQ);
                writer.append(book.getGenre());
                writer.append(CSQ);
                writer.append(book.getPrice()+"");
                writer.append(CSQ1);
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
