package ch.fhnw.webec.happyeat.exception.wronginput.recipe;

import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;
import ch.fhnw.webec.happyeat.model.Recipe;

public class WrongInputForRecipeName extends HappyEatWrongInputException {

    public WrongInputForRecipeName() {
        super("Du musst einen Namen für das Rezept eingeben",
            "/recipe/add",
            "Rezept hinzufügen"
        );
    }

    public WrongInputForRecipeName(Recipe recipe) {
        super("Du musst einen Namen für das Rezept eingeben",
            "/recipe/" + recipe.getId() + "/edit",
            "Rezept " + recipe.getName() + " bearbeiten"
        );
    }

}
