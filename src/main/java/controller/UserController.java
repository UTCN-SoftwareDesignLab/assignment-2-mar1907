package controller;

import model.User;
import model.validation.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.sale.SaleService;
import service.search.SearchService;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private SaleService saleService;

    @RequestMapping(value = "/user", method = RequestMethod.GET, params = {"!action"})
    public String index(HttpSession session)
    {
        if(!isLogged(session)){
            return "redirect:/";
        }
        return "user";
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    @Order(value = 1)
    public String logout(HttpSession session)
    {
        if(!isLogged(session)){
            return "redirect:/";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, params = {"action"})
    public String search(@RequestParam String val, @RequestParam String action, Model model, HttpSession session) {
        if(!isLogged(session)){
            return "redirect:/";
        }
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

    @RequestMapping(value = "/user", method = RequestMethod.POST, params = "action=sell")
    public String sell(@RequestParam int bid, @RequestParam int quantity, Model model, HttpSession session){
        if(!isLogged(session)){
            return "redirect:/";
        }
        User user = ((User)session.getAttribute("user"));
        Notification<Integer> notification = saleService.sell(bid,quantity,user.getId());
        if(notification.hasErrors()){
            model.addAttribute("saleResult",notification.getFormattedErrors());
        } else {
            model.addAttribute("saleResult","Sale succesful, price: " + notification.getResult()+"");
        }

        return "user";
    }

    private boolean isLogged(HttpSession session){
        User user = (User) session.getAttribute("user");
        return user != null && user.getIsAdmin() == 0;
    }
}
