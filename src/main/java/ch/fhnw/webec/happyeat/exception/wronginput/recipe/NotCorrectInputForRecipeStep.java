package ch.fhnw.webec.happyeat.exception.wronginput.recipe;

import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;
import ch.fhnw.webec.happyeat.model.Recipe;

public class NotCorrectInputForRecipeStep extends HappyEatWrongInputException {

    public NotCorrectInputForRecipeStep() {
        super("Es gab einen Fehler bei der Eingabe der Schritte. "
                + "Du musst für jeden Schritt eine Beschreibung angeben",
            "/recipe/add",
            "Rezept hinzufügen"
        );
    }

    public NotCorrectInputForRecipeStep(Recipe recipe) {
        super("Es gab einen Fehler bei der Eingabe der Schritte. "
                + "Du musst für jeden Schritt eine Beschreibung angeben",
            "/recipe/" + recipe.getId() + "/edit",
            "Rezept " + recipe.getName() + " bearbeiten"
        );
    }

}
