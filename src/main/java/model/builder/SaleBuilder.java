package model.builder;

import model.Book;
import model.Sale;
import model.User;

import java.sql.Date;

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

    public SaleBuilder setPrice(int price){
        sale.setPrice(price);
        return this;
    }

    public SaleBuilder setDate(Date date){
        sale.setDate(date);
        return this;
    }

    public Sale build(){
        return sale;
    }
}
