package service.report;

import model.Book;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import repository.book.BookRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ReportServicePDFBox implements ReportService {

    private BookRepository bookRepository;

    public ReportServicePDFBox(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void createReport() {
        List<Book> books = StreamSupport.stream(bookRepository.findAll().spliterator(),false)
                                .filter(b->b.getQuantity()==0)
                                .collect(Collectors.toCollection(ArrayList::new));

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try {
            PDPageContentStream contentStream = new PDPageContentStream(document,page);

            PDFont font = PDType1Font.HELVETICA_BOLD;

            contentStream.beginText();
            contentStream.setFont(font,12);
            contentStream.moveTextPositionByAmount( 100, 700 );
            contentStream.setLeading(14.5f);
            contentStream.drawString("Id; Title; Author; Genre; Price");
            contentStream.newLine();
            for(int i = 0; i < books.size(); i++){
                if(i%43==0&&i>0){
                    contentStream.endText();
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document,page);
                    contentStream.beginText();
                    contentStream.setFont(font,12);
                    contentStream.moveTextPositionByAmount( 100, 700 );
                    contentStream.setLeading(14.5f);
                    contentStream.drawString("Title; Author; Genre; Price");
                    contentStream.newLine();
                }
                Book book = books.get(i);
                String line = book.getId() + "; " + book.getTitle()+"; "+book.getAuthor()+"; "+book.getGenre()+"; "+book.getPrice();
                contentStream.drawString(line);
                contentStream.newLine();
            }
            contentStream.endText();
            contentStream.close();

            document.save("OutOfStockBooks.pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
