package ch.fhnw.webec.happyeat.controller;

import ch.fhnw.webec.happyeat.service.user.UserService;
import ch.fhnw.webec.happyeat.util.ModelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController extends ExceptionController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegister(Model model, HttpServletRequest request) {
        ModelUtil.addToken(model, request);
        return "/page/register";
    }

    @PostMapping("/register")
    public String registerNewUser(Model model, @RequestParam String firstName, @RequestParam String lastName,
                                  @RequestParam String emailAddress, @RequestParam String username,
                                  @RequestParam String password) {
        if (userService.registerNewUser(firstName, lastName, emailAddress, username, password)) {
            return "redirect:/login/newAccount";
        } else {
            model.addAttribute("accountInfo",
                "Der Benutzername oder die Emailadresse werden bereits benutzt.");
            return "/page/register";
        }
    }

}
