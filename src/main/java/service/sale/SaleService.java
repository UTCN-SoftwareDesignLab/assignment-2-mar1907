package service.sale;

import model.validation.Notification;

public interface SaleService {

    Notification<Integer> sell(long bookId, int quantity, long userId);

    void deleteByUserId(long userId);

    void deleteByBookId(long bookId);
}
