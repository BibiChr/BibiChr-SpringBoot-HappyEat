package ch.fhnw.webec.happyeat.exception.notfound;

public class RecipeNotFound extends HappyEatNotFoundException {

    public RecipeNotFound() {
        super("Das Rezept konnte nicht gefunden werden...",
            "/recipes",
            "den Rezepten"
        );
    }

}
