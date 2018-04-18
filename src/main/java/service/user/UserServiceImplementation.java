package service.user;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.user.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private AuthenticationService authenticationService;

    public UserServiceImplementation(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Notification<Boolean> addUser(String username, String password, Integer admin) {
        return authenticationService.register(username,password,admin);
    }

    @Override
    public Notification<Boolean> updateUser(long id, String username, String password, Integer admin) {
        User user = new UserBuilder()
                .setId(id)
                .setName(username)
                .setPassword(password)
                .setAdmin(admin)
                .build();

        UserValidator validator = new UserValidator(user);
        boolean userValid = validator.validate();
        Notification<Boolean> notification = new Notification<>();

        if(!userValid){
            validator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
            return notification;
        } else {
            user.setPassword(authenticationService.encodePassword(password));
            userRepository.save(user);
            notification.setResult(Boolean.TRUE);
            return notification;
        }
    }

    @Override
    public Notification<Boolean> delete(long id) {
        User user = new UserBuilder().setId(id).build();
        userRepository.delete(user);
        Notification<Boolean> notification = new Notification<>();
        notification.setResult(Boolean.TRUE);
        return notification;
    }
}
