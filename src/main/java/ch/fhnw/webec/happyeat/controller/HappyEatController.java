package ch.fhnw.webec.happyeat.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HappyEatController implements ErrorController {

    @GetMapping("/")
    public String showIndex() {
        return "/page/about";
    }

    @RequestMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("header", "Page not found");
        model.addAttribute("backPage", "/shopping-lists");
        model.addAttribute("location", "the shopping lists");
        return "/page/404";
    }

}
