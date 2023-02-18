package ch.fhnw.webec.happyeat.exception.wronginput.user;

import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;
import ch.fhnw.webec.happyeat.model.Recipe;

public class WrongInputForRecipeSharing extends HappyEatWrongInputException {

    public WrongInputForRecipeSharing(Recipe recipe) {
        super("Du musst den Usernamen der Person angeben, mit der du teilen möchtest...",
            "/recipe/" + recipe.getId() + "/share",
            "share recipe " + recipe.getName()
        );
    }

}
