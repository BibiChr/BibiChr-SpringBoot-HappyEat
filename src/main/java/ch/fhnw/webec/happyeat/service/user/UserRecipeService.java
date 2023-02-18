package ch.fhnw.webec.happyeat.service.user;

import ch.fhnw.webec.happyeat.exception.wronginput.user.WrongInputForRecipeSharing;
import ch.fhnw.webec.happyeat.model.Recipe;
import ch.fhnw.webec.happyeat.model.User;
import ch.fhnw.webec.happyeat.service.GetterService;
import ch.fhnw.webec.happyeat.service.recipe.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRecipeService {

    private final GetterService getterService;
    private final RecipeService recipeService;
    private final UserService userService;


    public UserRecipeService(GetterService getterService, RecipeService recipeService, UserService userService) {
        this.getterService = getterService;
        this.recipeService = recipeService;
        this.userService = userService;
    }

    public List<Recipe> getAllRecipesOfUser(String username) {
        return userService.getUserByUsername(username).getRecipes();
    }

    public Recipe addRecipeToUser(String username, String name, List<String> ingredient, List<String> amount,
                                  List<String> ingredientCategory, List<String> step, List<String> stepDescription) {
        Recipe recipe =
            recipeService.createAndSaveRecipe(name, ingredient, amount, ingredientCategory, step, stepDescription);

        addRecipeToUser(username, recipe);
        return recipe;
    }

    private void addRecipeToUser(String username, Recipe list) {
        User user = userService.getUserByUsername(username);
        user.addRecipe(list);
        userService.save(user);
    }

    public void shareRecipe(String username, int recipeId) {
        Recipe recipe = getterService.getRecipeById(recipeId);
        if (username == null || username.equals("")) {
            throw new WrongInputForRecipeSharing(recipe);
        }
        addRecipeToUser(username, recipe);
    }

    public void removeRecipeFromUser(String username, int recipeId) {
        Recipe recipe = getterService.getRecipeById(recipeId);

        User user = userService.getUserByUsername(username);
        user.removeRecipe(recipe);
        userService.save(user);

        recipeService.deleteNotUsedRecipes();
    }

}
