package service.report;

import org.springframework.stereotype.Service;

import static constants.Constants.ReportTypes.CSV;
import static constants.Constants.ReportTypes.PDFBOX;

@Service
public class ReportServiceFactory {

    public ReportService getReportService(String type){
        if(type.equals(CSV)){
            return new ReportServiceCSV();
        }
        if(type.equals(PDFBOX)){
            return new ReportServicePDFBox();
        }

        return null;
    }
}
