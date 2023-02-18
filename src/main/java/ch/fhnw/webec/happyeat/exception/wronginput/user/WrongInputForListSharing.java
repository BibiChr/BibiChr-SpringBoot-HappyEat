package ch.fhnw.webec.happyeat.exception.wronginput.user;

import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;
import ch.fhnw.webec.happyeat.model.ShoppingList;

public class WrongInputForListSharing extends HappyEatWrongInputException {

    public WrongInputForListSharing(ShoppingList shoppingList) {
        super("Du musst den Usernamen der Person angeben, mit der du teilen möchtest...",
            "/shopping-list/" + shoppingList.getId() + "/share",
            "share shopping list " + shoppingList.getName()
        );
    }

}
