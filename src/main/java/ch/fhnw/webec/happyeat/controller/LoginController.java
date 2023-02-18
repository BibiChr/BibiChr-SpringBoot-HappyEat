package ch.fhnw.webec.happyeat.controller;

import ch.fhnw.webec.happyeat.util.ModelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping(path = {"/login", "/login/{accountInfo}"})
    public String showLogin(
        Model model,
        @RequestParam(value = "error", required = false) String loginError,
        @PathVariable(value = "accountInfo", required = false) String accountInfo,
        HttpServletRequest request
    ) {
        handleRequestParams(model, loginError, accountInfo);
        ModelUtil.addToken(model, request);
        return "page/login";
    }


    private void handleRequestParams(Model model, String loginError, String accountInfo) {
        if (loginError != null) {
            model.addAttribute("loginError", "");
        }
        if (accountInfo != null && accountInfo.equals("newAccount")) {
            model.addAttribute("accountInfo",
                "Willkommen bei HappyEat! Melde dich jetzt mit deinem neuen Account an.");
        }
    }

}
