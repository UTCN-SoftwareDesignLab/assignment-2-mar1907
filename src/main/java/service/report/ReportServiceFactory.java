package service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.book.BookRepository;

import static constants.Constants.ReportTypes.CSV;
import static constants.Constants.ReportTypes.PDF;

@Service
public class ReportServiceFactory {

    @Autowired
    private BookRepository bookRepository;

    public ReportService getReportService(String type){
        System.out.println(type);
        if(type.equals(CSV)){
            return new ReportServiceCSV(bookRepository);
        }
        if(type.equals(PDF)){
            return new ReportServicePDFBox(bookRepository);
        }

        return null;
    }
}
