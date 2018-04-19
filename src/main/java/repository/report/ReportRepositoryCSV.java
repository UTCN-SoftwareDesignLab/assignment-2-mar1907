package repository.report;

import model.Book;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ReportRepositoryCSV implements ReportRepository {

    private static final String TITLE_AUTHOR_GENRE_PRICE = "Id,Title,Author,Genre,Price\n";
    private static final String CSQ = ",";
    private static final String CSQ1 = "\n";

    @Override
    public void createReport(List<Book> books) {
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
