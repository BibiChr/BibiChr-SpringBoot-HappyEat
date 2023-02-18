package ch.fhnw.webec.happyeat.exception.wronginput.shoppinglist;

import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;
import ch.fhnw.webec.happyeat.model.ShoppingList;

public class WrongInputForShoppingListNameEdit extends HappyEatWrongInputException {

    public WrongInputForShoppingListNameEdit(ShoppingList shoppingList) {
        super("Du musst einen Namen eingeben. Dieser muss mind. 3 maximal 30 Zeichen haben...",
            "/shopping-list/" + shoppingList.getId() + "/edit",
            "shopping liste " + shoppingList.getName() + " bearbeiten"
        );
    }

}
