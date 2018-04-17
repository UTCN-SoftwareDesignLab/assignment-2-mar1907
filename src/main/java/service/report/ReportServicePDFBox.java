package service.report;

import model.Book;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ReportServicePDFBox implements ReportService {

    @Override
    public void createReport(List<Book> books) {
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
            contentStream.drawString("Title Author Genre Price");
            contentStream.newLine();
            for(Book book:books){
                String line = book.getTitle()+" "+book.getAuthor()+" "+book.getGenre()+" "+book.getPrice();
                contentStream.drawString(line);
                contentStream.newLine();
            }
            contentStream.endText();
            contentStream.close();

            document.save("OutOfStockBooksPDFBOX"+(new Date().toString())+".pdf");
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
