package ch.fhnw.webec.happyeat.service.ingredient;

import ch.fhnw.webec.happyeat.data.IngredientRepository;
import ch.fhnw.webec.happyeat.exception.wronginput.ingredient.NotCorrectInputForIngredientEdit;
import ch.fhnw.webec.happyeat.exception.wronginput.recipe.NotCorrectInputForRecipeIngredient;
import ch.fhnw.webec.happyeat.model.Ingredient;
import ch.fhnw.webec.happyeat.model.Recipe;
import ch.fhnw.webec.happyeat.service.GetterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final GetterService getterService;

    public IngredientService(IngredientRepository ingredientRepository, GetterService getterService) {
        this.ingredientRepository = ingredientRepository;
        this.getterService = getterService;
    }

    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    private void delete(Ingredient i) {
        ingredientRepository.delete(i);
    }

    public void updateIngredient(int shoppingListId, int ingredientId, String newIngredientName,
                                 String newIngredientAmount, String ingredientCategory) {
        if (newIngredientName == null || newIngredientAmount == null || ingredientCategory == null
            || newIngredientName.equals("") || newIngredientAmount.equals("") || ingredientCategory.equals("")) {
            throw new NotCorrectInputForIngredientEdit(shoppingListId, ingredientId);
        }
        Ingredient ingredient = getterService.getIngredientById(ingredientId);
        ingredient.setName(newIngredientName);
        ingredient.setAmount(newIngredientAmount);
        ingredient.setCategory(getterService.getIngredientCategoryByName(ingredientCategory));
        ingredientRepository.save(ingredient);
    }

    public void changeIngredientToBuyStatus(int ingredientId) {
        Ingredient ingredient = getterService.getIngredientById(ingredientId);

        ingredient.toggleToBuy();
        save(ingredient);
    }

    public List<Ingredient> saveIngredientsForRecipe(Recipe recipe, List<String> ingredient, List<String> amount,
                                                     List<String> ingredientCategory) {
        if (ingredient == null
            || amount == null
            || ingredientCategory == null
            || ingredient.size() != amount.size()
            || ingredient.size() != ingredientCategory.size()) {
            if (recipe == null) {
                throw new NotCorrectInputForRecipeIngredient();
            }
            throw new NotCorrectInputForRecipeIngredient(recipe);
        }

        List<Ingredient> list = new ArrayList<>();
        for (int i = 0; i < ingredient.size(); i++) {
            if (ingredient.get(i) == null
                || amount.get(i) == null
                || ingredientCategory.get(i) == null
                || ingredient.get(i).equals("")
                || amount.get(i).equals("")
                || ingredientCategory.get(i).equals("")) {
                if (recipe == null) {
                    throw new NotCorrectInputForRecipeIngredient();
                }
                throw new NotCorrectInputForRecipeIngredient(recipe);
            }

            Ingredient thisIngredient = new Ingredient(
                ingredient.get(i),
                amount.get(i),
                getterService.getIngredientCategoryByName(ingredientCategory.get(0)),
                true
            );
            save(thisIngredient);
            list.add(thisIngredient);
        }

        return list;
    }

    public void deleteNotUsedIngredients() {
        for (Ingredient i : getNotSharedIngredients()) {
            delete(i);
        }
    }

    private List<Ingredient> getNotSharedIngredients() {
        return ingredientRepository.findAllIngredientsNotInUse();
    }

}
