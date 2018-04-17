package model.builder;

import model.Book;

public class BookBuilder {

    private Book book;

    public BookBuilder(){
        book = new Book();
    }

    public BookBuilder setId(long id){
        book.setId(id);
        return this;
    }

    public BookBuilder setTitle(String title){
        book.setTitle(title);
        return this;
    }

    public BookBuilder setAuthor(String author){
        book.setAuthor(author);
        return this;
    }

    public BookBuilder setGenre(String genre){
        book.setGenre(genre);
        return this;
    }

    public BookBuilder setQuantity(int quantity){
        book.setQuantity(quantity);
        return this;
    }

    public BookBuilder setPrice(int price){
        book.setPrice(price);
        return this;
    }

    public Book build(){
        return book;
    }
}
