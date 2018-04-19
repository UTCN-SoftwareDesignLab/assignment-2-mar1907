package repository.report;

import org.springframework.stereotype.Repository;

import static constants.Constants.ReportTypes.CSV;
import static constants.Constants.ReportTypes.PDF;

@Repository
public class ReportRepositoryFactory {

    public ReportRepository getReportRepository(String type){
        System.out.println(type);
        if(type.equals(CSV)){
            return new ReportRepositoryCSV();
        }
        if(type.equals(PDF)){
            return new ReportRepositoryPDFBox();
        }

        return null;
    }
}
