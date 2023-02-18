package ch.fhnw.webec.happyeat.exception.wronginput.ingredient;

import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;

public class NotCorrectInputForIngredientEdit extends HappyEatWrongInputException {

    public NotCorrectInputForIngredientEdit(int shoppingListId, int ingredientId) {
        super("Eine Zutat braucht einen Namen, eine Menge und eine Kategorie",
            "/ingredient/" + shoppingListId + "/" + ingredientId + "/edit",
            "ingredient edit"
        );
    }

}
