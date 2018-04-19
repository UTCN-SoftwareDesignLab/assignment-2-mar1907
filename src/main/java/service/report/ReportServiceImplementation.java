package service.report;

import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.book.BookRepository;
import repository.report.ReportRepository;
import repository.report.ReportRepositoryFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReportServiceImplementation implements ReportService{

    private ReportRepositoryFactory factory;
    private BookRepository bookRepository;

    @Autowired
    public ReportServiceImplementation(ReportRepositoryFactory factory, BookRepository bookRepository) {
        this.factory = factory;
        this.bookRepository = bookRepository;
    }

    @Override
    public void createReport(String type) {
        ReportRepository reportRepository = factory.getReportRepository(type);

        Iterable<Book> books = bookRepository.findAll();
        List <Book> filteredBooks = StreamSupport
                .stream(books.spliterator(),false)
                .filter(b->b.getQuantity()==0)
                .collect(Collectors.toCollection(ArrayList::new));

        reportRepository.createReport(filteredBooks);
    }
}
