package ch.fhnw.webec.happyeat.config;

import ch.fhnw.webec.happyeat.model.Ingredient;
import ch.fhnw.webec.happyeat.model.IngredientCategory;
import ch.fhnw.webec.happyeat.model.Recipe;
import ch.fhnw.webec.happyeat.model.ShoppingList;
import ch.fhnw.webec.happyeat.model.User;
import ch.fhnw.webec.happyeat.service.GetterService;
import ch.fhnw.webec.happyeat.service.ingredient.IngredientCategoryService;
import ch.fhnw.webec.happyeat.service.ingredient.IngredientService;
import ch.fhnw.webec.happyeat.service.recipe.RecipeService;
import ch.fhnw.webec.happyeat.service.ShoppingListService;
import ch.fhnw.webec.happyeat.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ITInitialDatabaseInput {
    private final UserService userService;
    private final IngredientService ingredientService;
    private final IngredientCategoryService ingredientCategoryService;
    private final ShoppingListService shoppingListService;
    private final RecipeService recipeService;
    private final GetterService getterService;
    private final ObjectMapper mapper;

    @Autowired
    public ITInitialDatabaseInput(UserService userService, IngredientService ingredientService,
                                  IngredientCategoryService ingredientCategoryService,
                                  ShoppingListService shoppingListService, RecipeService recipeService,
                                  GetterService getterService, ObjectMapper mapper) {
        this.userService = userService;
        this.ingredientService = ingredientService;
        this.ingredientCategoryService = ingredientCategoryService;
        this.shoppingListService = shoppingListService;
        this.recipeService = recipeService;
        this.getterService = getterService;
        this.mapper = mapper;
    }

    @Test
    @Transactional
    @DisplayName("Test if the input into database works correct...")
    void loadData() throws IOException {
        new InitialDatabaseInput(mapper, userService, ingredientService, ingredientCategoryService, shoppingListService,
            recipeService).addSampleUser();

        List<IngredientCategory> ingredientCategories = getterService.getAllIngredientCategories();
        List<Ingredient> ingredients = getterService.getAllIngredients();
        List<Recipe> recipes = getterService.getAllRecipes();
        List<ShoppingList> lists = getterService.getAllLists();
        List<User> users = userService.getAllUser();

        assertEquals(6, ingredientCategories.size());
        assertEquals(7, ingredients.size());
        assertEquals(2, recipes.size());
        assertEquals(4, users.size());

        assertEquals(2, users.get(0).getRecipes().size());
        assertEquals(1, users.get(1).getRecipes().size());
        assertEquals(1, users.get(1).getShoppingLists().size());
        assertEquals(0, users.get(2).getRecipes().size());
        assertEquals(0, users.get(2).getShoppingLists().size());
        assertEquals(0, users.get(3).getRecipes().size());
        assertEquals(0, users.get(3).getShoppingLists().size());
    }

    @Test
    @DisplayName("Test to check if initial data loader works")
    void test() throws IOException {
        List<User> users = InitialDatabaseInput.loadSampleUsers(mapper);

        //Users
        assertEquals(4, users.size());
        assertEquals("Bianca", users.get(0).getFirstName());
        assertEquals("Piekenbrock", users.get(0).getLastName());
        assertEquals("Bibi", users.get(0).getUsername());
        assertEquals(2, users.get(0).getRecipes().size());

        assertEquals("Claudine", users.get(1).getFirstName());
        assertEquals("Christen", users.get(1).getLastName());
        assertEquals(1, users.get(1).getRecipes().size());
        assertEquals(1, users.get(1).getShoppingLists().size());

        assertEquals(0, users.get(2).getRecipes().size());
        assertEquals(0, users.get(2).getShoppingLists().size());
        assertEquals(0, users.get(3).getRecipes().size());
        assertEquals(0, users.get(3).getShoppingLists().size());

        //ShoppingLists
        List<ShoppingList> lists = users.get(0).getShoppingLists();
        assertEquals(2, lists.size());
        assertEquals("Wocheneinkauf", lists.get(0).getName());
        assertEquals("Milch", lists.get(0).getIngredientList().get(0).getName());
        assertEquals("Haushalt", lists.get(1).getName());
        assertEquals("Zewa", lists.get(1).getIngredientList().get(1).getName());
        assertEquals("Reinigungsmittel", lists.get(1).getIngredientList().get(1).getCategory().getName());
    }
}
