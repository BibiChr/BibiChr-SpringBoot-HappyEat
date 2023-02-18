package ch.fhnw.webec.happyeat.exception.wronginput.user;

import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;

public class NotCorrectInputForPassword extends HappyEatWrongInputException {

    public NotCorrectInputForPassword() {
        super("Du musst ein Password eingeben", "/profile/edit", "profile edit");
    }

}
