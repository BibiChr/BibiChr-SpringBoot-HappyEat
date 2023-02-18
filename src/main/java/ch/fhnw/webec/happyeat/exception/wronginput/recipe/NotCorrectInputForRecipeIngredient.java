package ch.fhnw.webec.happyeat.exception.wronginput.recipe;

import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;
import ch.fhnw.webec.happyeat.model.Recipe;

public class NotCorrectInputForRecipeIngredient extends HappyEatWrongInputException {

    public NotCorrectInputForRecipeIngredient() {
        super("Es gab einen Fehler bei der Eingabe der Zutaten. "
                + "Du musst für jede Zutat einen Namen, die Menge und eine Kategorie angeben",
            "/recipe/add",
            "Rezept hinzufügen"
        );
    }

    public NotCorrectInputForRecipeIngredient(Recipe recipe) {
        super("Es gab einen Fehler bei der Eingabe der Zutaten. "
                + "Du musst für jede Zutat einen Namen, die Menge und eine Kategorie angeben",
            "/recipe/" + recipe.getId() + "/edit",
            "Rezept " + recipe.getName() + " bearbeiten"
        );
    }

}
