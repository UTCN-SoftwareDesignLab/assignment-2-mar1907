package controller;

import service.book.BookService;
import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/demo")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(path = "/get")
    public @ResponseBody String addNewBook(@RequestParam String title,
                                   @RequestParam String author,
                                   @RequestParam String genre,
                                   @RequestParam int quantity,
                                   @RequestParam int price){
        bookService.save(title,author,genre,quantity,price);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Book> getAllUsers() {
        return bookService.findAll();
    }
}