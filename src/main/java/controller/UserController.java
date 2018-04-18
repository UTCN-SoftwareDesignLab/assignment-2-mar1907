package controller;

import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.book.BookService;
import service.sale.SaleService;
import service.search.SearchService;

@Controller
public class UserController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private SaleService saleService;

    @RequestMapping(value = "/user", method = RequestMethod.GET, params = {"!action"})
    public String index(Model model)
    {
        model.addAttribute("userText","user");
        return "user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, params = {"action"})
    public String login(@RequestParam String val, @RequestParam String action, Model model) {
        switch (action){
            case "titles":
                model.addAttribute("searchResult",searchService.searchByTitle(val));
                break;
            case "authors":
                model.addAttribute("searchResult",searchService.searchByAuthor(val));
                break;
            case "genres":
                model.addAttribute("searchResult",searchService.searchByGenre(val));
                break;
        }
        return "user";
    }
}
