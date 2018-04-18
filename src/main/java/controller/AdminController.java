package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import repository.book.BookRepository;
import service.book.BookService;
import service.user.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @Order(value = 1)
    public String index(Model model)
    {
        model.addAttribute("adminText","admin");
        return "admin";
    }
}
