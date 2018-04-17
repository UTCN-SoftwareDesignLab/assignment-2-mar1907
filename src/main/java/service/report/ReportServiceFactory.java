package service.report;

import static constants.Constants.ReportTypes.CSV;
import static constants.Constants.ReportTypes.JASPER;
import static constants.Constants.ReportTypes.PDFBOX;

public class ReportServiceFactory {

    public ReportService getReportService(String type){
        if(type.equals(CSV)){
            return new ReportServiceCSV();
        }
        if(type.equals(JASPER)){
            return new ReportServicePDFJasper();
        }
        if(type.equals(PDFBOX)){
            return new ReportServicePDFBox();
        }

        return null;
    }
}
