package ch.fhnw.webec.happyeat.exception.generell;

public class IngredientNotFound extends HappyEatException {

    public IngredientNotFound() {
        super("Die Zutat konnte nicht gefunden werden...");
    }

}
