package ch.fhnw.webec.happyeat.exception.notfound;

public class ShoppingListNotFound extends HappyEatNotFoundException {

    public ShoppingListNotFound() {
        super("Die Einkaufsliste konnte nicht gefunden werden...",
            "/shopping-list",
            "the shopping lists"
        );
    }

}
