package service.sale;

import model.User;
import model.validation.Notification;

public interface SaleService {

    Notification<Integer> sell(long bookId, int quantity, long userId);
}
