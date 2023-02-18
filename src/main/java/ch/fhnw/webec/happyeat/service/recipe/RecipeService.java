package ch.fhnw.webec.happyeat.service.recipe;

import ch.fhnw.webec.happyeat.data.RecipeRepository;
import ch.fhnw.webec.happyeat.exception.wronginput.recipe.WrongInputForRecipeName;
import ch.fhnw.webec.happyeat.model.CookingStep;
import ch.fhnw.webec.happyeat.model.Ingredient;
import ch.fhnw.webec.happyeat.model.Recipe;
import ch.fhnw.webec.happyeat.service.GetterService;
import ch.fhnw.webec.happyeat.service.ingredient.IngredientService;
import ch.fhnw.webec.happyeat.util.PictureUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final CookingStepService cookingStepService;
    private final IngredientService ingredientService;
    private final GetterService getterService;

    public RecipeService(RecipeRepository recipeRepository, CookingStepService cookingStepService,
                         IngredientService ingredientService, GetterService getterService) {
        this.recipeRepository = recipeRepository;
        this.cookingStepService = cookingStepService;
        this.ingredientService = ingredientService;
        this.getterService = getterService;
    }

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    public Recipe createAndSaveRecipe(String name, List<String> ingredient, List<String> amount,
                                      List<String> ingredientCategory, List<String> step,
                                      List<String> stepDescription) {
        if (name == null || name.equals("")) {
            throw new WrongInputForRecipeName();
        }

        List<Ingredient> ingredientList =
            ingredientService.saveIngredientsForRecipe(null, ingredient, amount, ingredientCategory);
        List<CookingStep> cookingSteps = cookingStepService.saveCookingStepsForRecipe(null, step, stepDescription);

        Recipe recipe = new Recipe(name, ingredientList, cookingSteps);
        return save(recipe);
    }

    public void editRecipe(int id, String name, List<String> ingredient, List<String> amount,
                           List<String> ingredientCategory, List<String> step, List<String> stepDescription) {
        Recipe recipe = getterService.getRecipeById(id);
        if (name == null || name.equals("")) {
            throw new WrongInputForRecipeName(recipe);
        }
        recipe.setName(name);
        recipe.setIngredientList(
            ingredientService.saveIngredientsForRecipe(recipe, ingredient, amount, ingredientCategory));
        recipe.setCookingSteps(cookingStepService.saveCookingStepsForRecipe(recipe, step, stepDescription));
        save(recipe);

        ingredientService.deleteNotUsedIngredients();
        cookingStepService.deleteNotUsedCookingSteps();
    }

    public void uploadRecipeImage(MultipartFile file, int recipeId) {
        Recipe recipe = getterService.getRecipeById(recipeId);
        try {
            recipe.addImageData(PictureUtil.compressImage(file.getBytes()));
            save(recipe);
        } catch (Exception ignored) {
            //Exception ignored
        }
    }

    public byte[] getImageData(int recipeId) {
        Recipe recipe = getterService.getRecipeById(recipeId);
        return PictureUtil.decompressImage(recipe.getImageData());
    }

    public void deleteNotUsedRecipes() {
        for (Recipe recipe : new CopyOnWriteArrayList<>(getNotSharedRecipes())) {
            recipe.removeAllIngredientsFromRecipe();
            save(recipe);
            delete(recipe);
            ingredientService.deleteNotUsedIngredients();
        }
    }

    private List<Recipe> getNotSharedRecipes() {
        return recipeRepository.findAllRecipesNotInUse();
    }

}
