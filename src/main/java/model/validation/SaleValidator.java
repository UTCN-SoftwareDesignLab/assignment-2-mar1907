package model.validation;

import model.Sale;

import java.util.ArrayList;
import java.util.List;

public class SaleValidator {

    private final Sale sale;
    private final List<String> errors;

    public SaleValidator(Sale sale) {
        this.sale = sale;
        errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean validate(){
        validatePositive(sale.getQuantity(),"quantity");
        validatePositive(sale.getPrice(),"price");
        return errors.isEmpty();
    }

    private void validatePositive(Integer val, String field) {
        if(val<0){
            errors.add("Invalid " + field + "!");
        }
    }

}
