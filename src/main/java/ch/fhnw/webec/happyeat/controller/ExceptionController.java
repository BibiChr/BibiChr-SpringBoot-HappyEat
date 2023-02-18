package ch.fhnw.webec.happyeat.controller;

import ch.fhnw.webec.happyeat.exception.generell.HappyEatException;
import ch.fhnw.webec.happyeat.exception.notfound.HappyEatNotFoundException;
import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ExceptionController {

    private static final String HEADER = "header";
    private static final String INFO_MESSAGE = "infoMessage";
    private static final String BACK_PAGE = "backPage";
    private static final String LOCATION = "location";
    private static final String PAGE404 = "/page/404";


    @ExceptionHandler(HappyEatException.class)
    @ResponseStatus(BAD_REQUEST)
    public String happyEatException(Model model, HappyEatException e) {
        model.addAttribute(HEADER, "There was a problem");
        model.addAttribute(INFO_MESSAGE, e.getMessage());
        model.addAttribute(BACK_PAGE, "/shopping-lists");
        model.addAttribute(LOCATION, "shopping lists");
        return PAGE404;
    }

    @ExceptionHandler(HappyEatNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    public String happyEatNotFoundException(Model model, HappyEatNotFoundException e) {
        model.addAttribute(HEADER, e.getHeader());
        model.addAttribute(INFO_MESSAGE, e.getMessage());
        model.addAttribute(BACK_PAGE, e.getLinkBack());
        model.addAttribute(LOCATION, e.getLocation());
        return PAGE404;
    }

    @ExceptionHandler(HappyEatWrongInputException.class)
    @ResponseStatus(BAD_REQUEST)
    public String happyEatWrongInputException(Model model, HappyEatWrongInputException e) {
        model.addAttribute(HEADER, e.getHeader());
        model.addAttribute(INFO_MESSAGE, e.getMessage());
        model.addAttribute(BACK_PAGE, e.getLinkBack());
        model.addAttribute(LOCATION, e.getLocation());
        return PAGE404;
    }

}
