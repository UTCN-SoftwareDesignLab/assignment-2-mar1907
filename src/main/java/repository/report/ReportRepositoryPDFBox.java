package repository.report;

import model.Book;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class ReportRepositoryPDFBox implements ReportRepository {

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
            contentStream.drawString("Id Title Author Genre Price");
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
                    contentStream.drawString("Title Author Genre Price");
                    contentStream.newLine();
                }
                Book book = books.get(i);
                String line = book.getId() + " " + book.getTitle()+" "+book.getAuthor()+" "+book.getGenre()+" "+book.getPrice();
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
