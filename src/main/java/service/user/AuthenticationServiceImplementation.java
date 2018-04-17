package service.user;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.user.UserRepository;

import java.security.MessageDigest;
import java.util.Optional;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {

    private UserRepository userRepository;

    @Autowired
    public AuthenticationServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password) {
        User user = new UserBuilder()
                .setName(username)
                .setPassword(password)
                .setAdmin(0)
                .build();

        UserValidator validator = new UserValidator(user);
        boolean userValid = validator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if(!userValid){
            validator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
            return userRegisterNotification;
        } else {
            user.setPassword(encodePassword(password));
            userRepository.save(user);
            userRegisterNotification.setResult(Boolean.TRUE);
            return userRegisterNotification;
        }
    }

    @Override
    public Notification<User> login(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, encodePassword(password));
        Notification<User> userNotification = new Notification<>();
        if(optionalUser.isPresent()){
            userNotification.setResult(optionalUser.get());
            return userNotification;
        } else {
            userNotification.addError("No such user");
            return userNotification;
        }
    }

    @Override
    public String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
