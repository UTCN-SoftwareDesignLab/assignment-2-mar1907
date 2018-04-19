package controller;

import model.Book;
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
import service.recommendation.RecommendationService;
import service.report.ReportService;
import service.search.SearchService;
import service.user.UserService;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.security.GeneralSecurityException;

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

    @Autowired
    private RecommendationService<Book> recommendationService;

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
        return "manage-books";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET, params = {"action"})
    public String viewAll(Model model, HttpSession session) {
        if(!isLogged(session)){
            return "redirect:/";
        }
        Iterable<User> users = userService.getAll();
        model.addAttribute("users",users);
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

    @RequestMapping(value = "/admin/books", method = RequestMethod.GET, params = {"action=books"})
    public String viewAllBooks(Model model, HttpSession session){
        if(!isLogged(session)){
            return "redirect:/";
        }

        model.addAttribute("books",bookService.findAll());

        return "manage-books";
    }

    @RequestMapping(value = "/admin/books", method = RequestMethod.GET, params = {"action=search"})
    public String searchBooks(Model model, @RequestParam String title, HttpSession session){
        if(!isLogged(session)){
            return "redirect:/";
        }

        try {
            model.addAttribute("booksFound",recommendationService.recomendByTitle(title));
        } catch (GeneralSecurityException | IOException e) {
            model.addAttribute("searchResult","Search failed");
        }

        return "manage-books";
    }

    @RequestMapping(value = "/admin/books", method = RequestMethod.POST, params = "action=createSearch")
    public String createSearchBook(@RequestParam int bid, @RequestParam String btitle, @RequestParam int bprice,
                                   @RequestParam int bquantity, Model model, HttpSession session){
        if(!isLogged(session)){
            return "redirect:/";
        }

        Book book;

        try {
            book = recommendationService.recomendByTitle(btitle).get(bid);
        } catch (GeneralSecurityException | IOException e) {
            model.addAttribute("result","Search failed");
            return "manage-books";
        }

        Notification<Boolean> notification = bookService.save(book.getTitle(),book.getAuthor(),book.getGenre(),bquantity,bprice);
        if(notification.hasErrors()){
            model.addAttribute("result", notification.getFormattedErrors());
        } else {
            model.addAttribute("result", "Creation succesful!");
        }

        return "manage-books";
    }

    @RequestMapping(value = "/admin/books", method = RequestMethod.POST, params = "action=create")
    public String createBook(@RequestParam String btitle,@RequestParam String bauthor,@RequestParam String bgenre,
                             @RequestParam int bprice, @RequestParam int bquantity, Model model, HttpSession session){
        if(!isLogged(session)){
            return "redirect:/";
        }

        Notification<Boolean> notification = bookService.save(btitle,bauthor,bgenre,bquantity,bprice);

        if(notification.hasErrors()){
            model.addAttribute("result", notification.getFormattedErrors());
        } else {
            model.addAttribute("result", "Creation succesful!");
        }

        return "manage-books";
    }

    @RequestMapping(value = "/admin/books", method = RequestMethod.POST, params = "action=update")
    public String updateBook(@RequestParam int bid, @RequestParam String btitle,@RequestParam String bauthor,@RequestParam String bgenre,
                             @RequestParam int bprice, @RequestParam int bquantity, Model model, HttpSession session){
        if(!isLogged(session)){
            return "redirect:/";
        }

        Notification<Boolean> notification = bookService.update(bid,btitle,bauthor,bgenre,bquantity,bprice);

        if(notification.hasErrors()){
            model.addAttribute("result", notification.getFormattedErrors());
        } else {
            model.addAttribute("result", "Update succesful!");
        }

        return "manage-books";
    }

    @RequestMapping(value = "/admin/books", method = RequestMethod.POST, params = "action=delete")
    public String deleteBook(@RequestParam int bid, Model model, HttpSession session){
        if(!isLogged(session)){
            return "redirect:/";
        }

        Notification<Boolean> notification = bookService.delete(bid);
        if(notification.hasErrors()){
            model.addAttribute("result",notification.getFormattedErrors());
        } else {
            model.addAttribute("result","Delete succesful!");
        }

        return "manage-books";
    }


    private boolean isLogged(HttpSession session){
        User user = (User) session.getAttribute("user");
        return user != null && user.getIsAdmin() == 1;
    }
}
