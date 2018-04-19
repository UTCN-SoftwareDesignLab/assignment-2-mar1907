package repository.report;

import model.Book;

import java.util.List;

public interface ReportRepository {

    void createReport(List<Book> books);
}
