package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.sale.SaleService;
import service.user.AuthenticationService;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SaleService saleService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @Order(value = 1)
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody String login(@RequestParam String username, @RequestParam String password) {
        return username+password;
    }
}
