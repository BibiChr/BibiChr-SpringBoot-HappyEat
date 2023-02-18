package ch.fhnw.webec.happyeat.service;

import ch.fhnw.webec.happyeat.HappyEatApplicationTestHelper;
import ch.fhnw.webec.happyeat.data.ShoppingListRepository;
import ch.fhnw.webec.happyeat.data.UserRepository;
import ch.fhnw.webec.happyeat.model.ShoppingList;
import ch.fhnw.webec.happyeat.model.User;
import ch.fhnw.webec.happyeat.service.ingredient.IngredientService;
import ch.fhnw.webec.happyeat.service.user.UserShoppingListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ITShoppingListService extends HappyEatApplicationTestHelper {

    @Autowired
    ITShoppingListService(ShoppingListRepository shoppingListRepository, UserRepository userRepository,
                          IngredientService ingredientService, GetterService getterService,
                          UserShoppingListService userShoppingListService) {
        super(shoppingListRepository, userRepository, ingredientService, getterService, userShoppingListService);
    }

    @Test
    @Transactional
    @DisplayName("Test if shoppingList gets deleted")
    void deleteTest() {
        User user = userService.getUserByUsername("Bibi");
        ShoppingList shoppingList = user.getShoppingLists().get(1);
        assertEquals("Haushalt", shoppingList.getName());

        userShoppingListService.removeShoppingListFromUser("Bibi", shoppingList.getId());

        List<ShoppingList> lists = getterService.getAllLists();
        assertFalse(lists.contains(shoppingList));
    }

    @Test
    @Transactional
    @DisplayName("Test if shoppingList gets not deleted if two user uses it")
    void deleteTestFail() {
        User user = userService.getUserByUsername("Bibi");
        ShoppingList shoppingList = user.getShoppingLists().get(0);
        assertEquals("Wocheneinkauf", shoppingList.getName());

        userShoppingListService.removeShoppingListFromUser("Bibi", shoppingList.getId());

        List<ShoppingList> lists = getterService.getAllLists();
        assertTrue(lists.contains(shoppingList));
    }

}
