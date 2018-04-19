package repository.sale;

import model.Sale;
import org.springframework.data.repository.CrudRepository;

public interface SaleRepository extends CrudRepository<Sale,Long> {

    void deleteByBook_Id(long book_id);

    void deleteByUser_Id(long user_id);
}
