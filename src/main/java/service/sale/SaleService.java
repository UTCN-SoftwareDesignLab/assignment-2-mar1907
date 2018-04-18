package service.sale;

import model.User;
import model.validation.Notification;

public interface SaleService {

    //sale needs to know the registered user
    void saveUser(User user);

    Notification<Integer> sell(long bookId, int quantity);
}
