package service.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.sale.SaleRepository;

@Service
public class SaleServiceImplementation implements SaleService {

    private SaleRepository saleRepository;

    @Autowired
    public SaleServiceImplementation(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }
}
