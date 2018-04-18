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
import repository.book.BookRepository;
import service.book.BookService;
import service.user.UserService;

@Controller
public class AdminController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @Order(value = 1)
    public String index()
    {
        return "admin";
    }

    @RequestMapping(value = "/admin/logout", method = RequestMethod.GET)
    @Order(value = 1)
    public String logout()
    {
        return "redirect:/";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    @Order(value = 1)
    public String usersIndex()
    {
        return "manage-users";
    }

    @RequestMapping(value = "/admin/books", method = RequestMethod.GET)
    @Order(value = 1)
    public String booksIndex()
    {
        return "admin";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET, params = {"action"})
    public String viewAll(Model model) {
        Iterable<User> users = userService.getAll();
        model.addAttribute("viewAll",users);
        return "manage-users";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.POST, params = "action=create")
    public String create(@RequestParam String uname, @RequestParam String upass, @RequestParam boolean uadmin, Model model){
        Notification<Boolean> notification = userService.addUser(uname,upass,uadmin?1:0);
        if(notification.hasErrors()){
            model.addAttribute("result",notification.getFormattedErrors());
        } else {
            model.addAttribute("result","Creation succesful!");
        }

        return "manage-users";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.POST, params = "action=update")
    public String update(@RequestParam int uid, @RequestParam String uname, @RequestParam String upass,
                         @RequestParam boolean uadmin, Model model){
        Notification<Boolean> notification = userService.updateUser(uid,uname,upass,uadmin?1:0);
        if(notification.hasErrors()){
            model.addAttribute("result",notification.getFormattedErrors());
        } else {
            model.addAttribute("result","Update succesful!");
        }

        return "manage-users";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.POST, params = "action=delete")
    public String delete(@RequestParam int uid, Model model){
        Notification<Boolean> notification = userService.delete(uid);
        if(notification.hasErrors()){
            model.addAttribute("result",notification.getFormattedErrors());
        } else {
            model.addAttribute("result","Delete succesful!");
        }

        return "manage-users";
    }
}
