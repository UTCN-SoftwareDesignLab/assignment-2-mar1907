package model.validation;

import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final String NAME_VALIDATION_REGEX = "[A-Za-z ]+";

    private final User user;
    private final List<String> errors;

    public UserValidator(User user) {
        this.user = user;
        this.errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean validate(){
        validatePassword(user.getPassword());
        validateUsername(user.getUsername());
        return errors.isEmpty();
    }

    private void validateUsername(String username) {
        if (!Pattern.compile(NAME_VALIDATION_REGEX).matcher(username).matches()) {
            errors.add("Invalid name!");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.add("Password too short!");
        }
        if (!containsSpecialCharacter(password)) {
            errors.add("Password must contain at least one special character!");
        }
        if (!containsDigit(password)) {
            errors.add("Password must contain at least one number!");
        }
    }

    private boolean containsSpecialCharacter(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        return m.find();
    }

    private static boolean containsDigit(String s) {
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }
}
