package ch.fhnw.webec.happyeat.config;

import ch.fhnw.webec.happyeat.model.Ingredient;
import ch.fhnw.webec.happyeat.model.User;
import ch.fhnw.webec.happyeat.service.ingredient.IngredientCategoryService;
import ch.fhnw.webec.happyeat.service.ingredient.IngredientService;
import ch.fhnw.webec.happyeat.service.recipe.RecipeService;
import ch.fhnw.webec.happyeat.service.ShoppingListService;
import ch.fhnw.webec.happyeat.service.user.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class InitialDatabaseInput implements CommandLineRunner {

    private static final String JSON_FILE = "users.json";
    private final UserService userService;
    private final IngredientService ingredientService;
    private final IngredientCategoryService ingredientCategoryService;
    private final ShoppingListService shoppingListService;
    private final RecipeService recipeService;
    private final ObjectMapper mapper;

    public InitialDatabaseInput(ObjectMapper mapper, UserService userService, IngredientService ingredientService,
                                IngredientCategoryService ingredientCategoryService,
                                ShoppingListService shoppingListService, RecipeService recipeService) {
        this.mapper = mapper;
        this.userService = userService;
        this.ingredientService = ingredientService;
        this.ingredientCategoryService = ingredientCategoryService;
        this.shoppingListService = shoppingListService;
        this.recipeService = recipeService;
    }

    @Override
    public void run(String... args) throws IOException {
        addSampleUser();
    }

    public void addSampleUser() throws IOException {
        if (userService.getAllUser().isEmpty()) {
            List<User> users = loadSampleUsers(mapper);

            saveIngredientCategories(users);
            saveIngredients(users);
            saveShoppingLists(users);
            saveRecipes(users);

            users.forEach(userService::registerNewUser);
        }
    }

    private void saveIngredientCategories(List<User> users) {
        users.stream()
            .flatMap(u -> u.getShoppingLists().stream())
            .flatMap(l -> l.getIngredientList().stream())
            .map(Ingredient::getCategory)
            .forEach(ingredientCategoryService::save);

        users.stream()
            .flatMap(u -> u.getRecipes().stream())
            .flatMap(r -> r.getIngredientList().stream())
            .map(Ingredient::getCategory)
            .forEach(ingredientCategoryService::save);
    }

    private void saveRecipes(List<User> users) {
        users.stream()
            .flatMap(u -> u.getRecipes().stream())
            .forEach(recipeService::save);
    }

    private void saveShoppingLists(List<User> users) {
        users.stream()
            .flatMap(u -> u.getShoppingLists().stream())
            .forEach(shoppingListService::save);
    }

    private void saveIngredients(List<User> users) {
        users.stream()
            .flatMap(u -> u.getShoppingLists().stream())
            .flatMap(l -> l.getIngredientList().stream())
            .forEach(ingredientService::save);

        users.stream()
            .flatMap(u -> u.getRecipes().stream())
            .flatMap(r -> r.getIngredientList().stream())
            .forEach(ingredientService::save);
    }

    public static List<User> loadSampleUsers(ObjectMapper mapper) throws IOException {
        return mapper.readValue(InitialDatabaseInput.class.getResource(JSON_FILE),
            new TypeReference<>() {
            });
    }

}
