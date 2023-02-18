package ch.fhnw.webec.happyeat;

import ch.fhnw.webec.happyeat.data.ShoppingListRepository;
import ch.fhnw.webec.happyeat.data.UserRepository;
import ch.fhnw.webec.happyeat.service.GetterService;
import ch.fhnw.webec.happyeat.service.ingredient.IngredientService;
import ch.fhnw.webec.happyeat.service.ShoppingListService;
import ch.fhnw.webec.happyeat.service.user.UserService;
import ch.fhnw.webec.happyeat.service.user.UserShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HappyEatApplicationTestHelper {

    protected final ShoppingListService shoppingListService;
    protected final UserShoppingListService userShoppingListService;
    protected final UserService userService;
    protected final GetterService getterService;


    @Autowired
    protected HappyEatApplicationTestHelper(ShoppingListRepository shoppingListRepository, UserRepository userRepository,
                                            IngredientService ingredientService, GetterService getterService,
                                            UserShoppingListService userShoppingListService) {
        this.userShoppingListService = userShoppingListService;
        this.getterService = getterService;
        this.shoppingListService =
            new ShoppingListService(shoppingListRepository, ingredientService, getterService);
        this.userService = new UserService(userRepository);
    }

}
