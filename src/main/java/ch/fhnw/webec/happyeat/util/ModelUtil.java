package ch.fhnw.webec.happyeat.util;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public final class ModelUtil {

    private ModelUtil() { }

    public static void addToken(Model model, HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }
    }

}
