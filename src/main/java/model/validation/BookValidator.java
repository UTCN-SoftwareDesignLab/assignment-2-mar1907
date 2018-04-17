package model.validation;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BookValidator {

    private static final String NAME_VALIDATION_REGEX = "[A-Za-z ]+";

    private final Book user;
    private final List<String> errors;

    public BookValidator(Book user) {
        this.user = user;
        errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean validate(){
        validateName(user.getTitle(),"title");
        validateName(user.getAuthor(),"author");
        validateName(user.getGenre(),"genre");
        validatePositive(user.getPrice(),"price");
        validatePositive(user.getQuantity(),"quantity");
        return errors.isEmpty();
    }

    private void validateName(String name, String field) {
        if (!Pattern.compile(NAME_VALIDATION_REGEX).matcher(name).matches()) {
            errors.add("Invalid " + field +"!");
        }
    }

    private void validatePositive(Integer val, String field) {
        if(val<0){
            errors.add("Invalid " + field + "!");
        }
    }
}
