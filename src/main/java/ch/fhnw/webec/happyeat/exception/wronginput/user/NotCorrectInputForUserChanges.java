package ch.fhnw.webec.happyeat.exception.wronginput.user;

import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;

public class NotCorrectInputForUserChanges extends HappyEatWrongInputException {

    public NotCorrectInputForUserChanges() {
        super("Du musst einen Usernamen und eine Email adresse eingeben",
            "/profile/edit",
            "profile edit"
        );
    }

}
