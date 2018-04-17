package service.user;

import model.User;
import model.validation.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.user.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Notification<Boolean> addUser(String username, String password, Integer admin) {
        return null;
    }

    @Override
    public Notification<Boolean> updateUser(long id, String username, String password, Integer admin) {
        return null;
    }

    @Override
    public Notification<Boolean> delete(long id) {
        return null;
    }
}
