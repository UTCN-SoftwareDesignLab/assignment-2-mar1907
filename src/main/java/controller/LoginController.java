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
import service.user.AuthenticationService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SaleService saleService;

    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    @Order(value = 1)
    public String home() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @Order(value = 1)
    public String index(HttpSession session) {
        session.setAttribute("user",null);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, params = "action=login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession httpSession) {
        User user;
        Notification<User> notification = authenticationService.login(username,password);
        if(notification.hasErrors()){
            model.addAttribute("registerText",notification.getFormattedErrors());
            return "redirect:/login?error";
        } else {
            user = notification.getResult();
            httpSession.setAttribute("user",user);
            if(user.getIsAdmin()==1){
                return "redirect:admin";
            } else {
                return "redirect:user";
            }
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, params = "action=register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        Notification<Boolean> notification = authenticationService.register(username,password,0);
        if(notification.hasErrors()){
            model.addAttribute("registerText",notification.getFormattedErrors());
        } else {
            if(!notification.getResult()){
                model.addAttribute("registerText","Could not register, try again later");
            }
            else {
                model.addAttribute("registerText","Registered!");
            }
        }

        return "login";
    }
}
