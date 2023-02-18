package ch.fhnw.webec.happyeat.exception.wronginput.shoppinglist;

import ch.fhnw.webec.happyeat.exception.wronginput.HappyEatWrongInputException;

public class WrongInputForShoppingListNameAdd extends HappyEatWrongInputException {

    public WrongInputForShoppingListNameAdd() {
        super("Du musst einen Namen eingeben. Dieser muss mind. 3 maximal 30 Zeichen haben...",
            "/shopping-lists/add",
            "hinzufügen einer shopping liste."
        );
    }

}
