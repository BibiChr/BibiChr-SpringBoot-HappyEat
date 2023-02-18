package ch.fhnw.webec.happyeat.util;

public final class ShoppingListUtil {
    private ShoppingListUtil() {
    }

    public static boolean nameIsNotOkay(String name) {
        return name == null || name.length() < 3 || name.length() > 30;
    }

}
