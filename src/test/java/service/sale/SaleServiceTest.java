package service.sale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import repository.book.BookRepository;
import repository.sale.SaleRepository;

import static org.junit.Assert.*;

public class SaleServiceTest {

    private SaleService saleService;

    @Before
    public void setUp() throws Exception {
        saleService = new SaleServiceImplementation(Mockito.mock(SaleRepository.class),Mockito.mock(BookRepository.class));
    }

    @Test
    public void sell() {
        assertNotNull(saleService.sell(1,1,1));
    }
}