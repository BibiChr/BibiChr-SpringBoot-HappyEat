package ch.fhnw.webec.happyeat.exception.generell;

public class IngredientCategoryNotFound extends HappyEatException {

    public IngredientCategoryNotFound() {
        super("Die Kategorie konnte nicht gefunden werden...");
    }

}
