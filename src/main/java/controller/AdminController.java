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
import service.book.BookService;
import repository.report.ReportRepository;
import repository.report.ReportRepositoryFactory;
import service.report.ReportService;
import service.user.UserService;

import javax.servlet.http.HttpSession;

import static constants.Constants.ReportTypes.CSV;
import static constants.Constants.ReportTypes.PDF;

@Controller
public class AdminController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @Order(value = 1)
    public String index(HttpSession session)
    {
        if(!isLogged(session)){
            return "redirect:/";
        }
        return "admin";
    }

    @RequestMapping(value = "/admin/logout", method = RequestMethod.GET)
    @Order(value = 1)
    public String logout(HttpSession session)
    {
        if(!isLogged(session)){
            return "redirect:/";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    @Order(value = 1)
    public String usersIndex(HttpSession session)
    {
        if(!isLogged(session)){
            return "redirect:/";
        }
        return "manage-users";
    }

    @RequestMapping(value = "/admin/books", method = RequestMethod.GET)
    @Order(value = 1)
    public String booksIndex(HttpSession session)
    {
        if(!isLogged(session)){
            return "redirect:/";
        }
        return "admin";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET, params = {"action"})
    public String viewAll(Model model, HttpSession session) {
        if(!isLogged(session)){
            return "redirect:/";
        }
        Iterable<User> users = userService.getAll();
        model.addAttribute("viewAll",users);
        return "manage-users";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.POST, params = "action=create")
    public String create(@RequestParam String uname, @RequestParam String upass, @RequestParam boolean uadmin, Model model, HttpSession session){
        if(!isLogged(session)){
            return "redirect:/";
        }
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
                         @RequestParam boolean uadmin, Model model, HttpSession session){
        if(!isLogged(session)){
            return "redirect:/";
        }
        Notification<Boolean> notification = userService.updateUser(uid,uname,upass,uadmin?1:0);
        if(notification.hasErrors()){
            model.addAttribute("result",notification.getFormattedErrors());
        } else {
            model.addAttribute("result","Update succesful!");
        }

        return "manage-users";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.POST, params = "action=delete")
    public String delete(@RequestParam int uid, Model model, HttpSession session){
        if(!isLogged(session)){
            return "redirect:/";
        }
        Notification<Boolean> notification = userService.delete(uid);
        if(notification.hasErrors()){
            model.addAttribute("result",notification.getFormattedErrors());
        } else {
            model.addAttribute("result","Delete succesful!");
        }

        return "manage-users";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET, params = {"action"})
    public String generateReports(Model model, @RequestParam String action, HttpSession session) {
        if(!isLogged(session)){
            return "redirect:/";
        }

        reportService.createReport(action);
        model.addAttribute("report",action + " report generated!");

        return "admin";
    }

    private boolean isLogged(HttpSession session){
        User user = (User) session.getAttribute("user");
        return user != null && user.getIsAdmin() == 1;
    }
}
