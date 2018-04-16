package model.builder;

import model.Book;
import model.Sale;
import model.User;

public class SaleBuilder {

    private Sale sale;

    public SaleBuilder() {
        sale = new Sale();
    }

    public SaleBuilder setQuantity(int quantity){
        sale.setQuantity(quantity);
        return this;
    }

    public SaleBuilder setUser(User user){
        sale.setUser(user);
        return this;
    }

    public SaleBuilder setBook(Book book){
        sale.setBook(book);
        return this;
    }

    public Sale build(){
        return sale;
    }
}
