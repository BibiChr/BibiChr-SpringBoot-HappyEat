package ch.fhnw.webec.happyeat.service;

import ch.fhnw.webec.happyeat.HappyEatApplicationTestHelper;
import ch.fhnw.webec.happyeat.data.ShoppingListRepository;
import ch.fhnw.webec.happyeat.data.UserRepository;
import ch.fhnw.webec.happyeat.model.User;
import ch.fhnw.webec.happyeat.service.ingredient.IngredientService;
import ch.fhnw.webec.happyeat.service.user.UserShoppingListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ITUserService extends HappyEatApplicationTestHelper {

    @Autowired
    ITUserService(ShoppingListRepository shoppingListRepository, UserRepository userRepository,
                  IngredientService ingredientService, GetterService getterService,
                  UserShoppingListService userShoppingListService) {
        super(shoppingListRepository, userRepository, ingredientService, getterService, userShoppingListService);
    }

    @Test
    @DisplayName("Test if user can be found by username")
    void findByUsername() {
        User user = userService.getUserByUsername("Bibi");

        assertEquals("Bianca", user.getFirstName());
        assertEquals("Piekenbrock", user.getLastName());
    }

    @Test
    @DisplayName("Test if not exist username throws exception")
    void findByUsernameFail() {
        Exception exception = assertThrows(UsernameNotFoundException.class,
            () -> userService.getUserByUsername("username"));

        assertNotNull(exception);
    }

    @Test
    @DisplayName("Test if user can get registered")
    void registerUser() {
        boolean success = userService.registerNewUser(
            "Maria", "Freiheit", "maria@freiheit.ch", "maria", "meins"
        );
        assertTrue(success);

        userService.deleteUserAccount("maria");
    }

    @Test
    @DisplayName("Test if user can't get registered with same username")
    void registerUserFail() {
        boolean success1 = userService.registerNewUser(
            "Maria", "Freiheit", "maria@freiheit.ch", "maria", "meins"
        );

        boolean success2 = userService.registerNewUser(
            "Maria", "Freiheit", "maria@freiheit.ch", "maria", "meins"
        );

        assertTrue(success1);
        assertFalse(success2);

        userService.deleteUserAccount("maria");
    }

}
