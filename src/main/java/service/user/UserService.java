package service.user;

import model.User;
import model.validation.Notification;

public interface UserService {

    Iterable<User> getAll();

    Notification<Boolean> addUser(String username, String password, Integer admin);

    Notification<Boolean> updateUser(long id, String username, String password, Integer admin);

    Notification<Boolean> delete(long id);

}
