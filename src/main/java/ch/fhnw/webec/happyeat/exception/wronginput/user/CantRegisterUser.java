package ch.fhnw.webec.happyeat.exception.wronginput.user;

import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;

public class CantRegisterUser extends HappyEatWrongInputException {

    public CantRegisterUser() {
        super(
            "Registrierung fehlgeschlagen...", "/register", "registration");
    }

}
