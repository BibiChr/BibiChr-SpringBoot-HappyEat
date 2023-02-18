package ch.fhnw.webec.happyeat.exception.wronginput.ingredient;

import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;
import ch.fhnw.webec.happyeat.model.ShoppingList;

public class NotCorrectInputForIngredientAdd extends HappyEatWrongInputException {

    public NotCorrectInputForIngredientAdd(ShoppingList shoppingList) {
        super("Eine Zutat braucht einen Namen, eine Menge und eine Kategorie",
            "/shopping-list/" + shoppingList.getId(),
            "shopping list " + shoppingList.getName()
        );
    }

}
