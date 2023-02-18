package ch.fhnw.webec.happyeat.service;

import ch.fhnw.webec.happyeat.data.IngredientCategoryRepository;
import ch.fhnw.webec.happyeat.data.IngredientRepository;
import ch.fhnw.webec.happyeat.data.RecipeRepository;
import ch.fhnw.webec.happyeat.data.ShoppingListRepository;
import ch.fhnw.webec.happyeat.exception.generell.IngredientNotFound;
import ch.fhnw.webec.happyeat.exception.notfound.RecipeNotFound;
import ch.fhnw.webec.happyeat.exception.notfound.ShoppingListNotFound;
import ch.fhnw.webec.happyeat.exception.generell.IngredientCategoryNotFound;
import ch.fhnw.webec.happyeat.model.Ingredient;
import ch.fhnw.webec.happyeat.model.IngredientCategory;
import ch.fhnw.webec.happyeat.model.Recipe;
import ch.fhnw.webec.happyeat.model.ShoppingList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetterService {


    private final IngredientRepository ingredientRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final RecipeRepository recipeRepository;
    private final ShoppingListRepository shoppingListRepository;

    public GetterService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository,
                         IngredientCategoryRepository ingredientCategoryRepository,
                         ShoppingListRepository shoppingListRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(int id) {
        return ingredientRepository.findById(id).orElseThrow(IngredientNotFound::new);
    }

    public List<IngredientCategory> getAllIngredientCategories() {
        return ingredientCategoryRepository.findAll();
    }

    public IngredientCategory getIngredientCategoryByName(String name) {
        return ingredientCategoryRepository.findByName(name).orElseThrow(IngredientCategoryNotFound::new);
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(int recipeId) {
        return recipeRepository.findById(recipeId).orElseThrow(RecipeNotFound::new);
    }

    public List<ShoppingList> getAllLists() {
        return shoppingListRepository.findAll();
    }

    public ShoppingList getListById(int shoppingListId) {
        return shoppingListRepository.findById(shoppingListId).orElseThrow(ShoppingListNotFound::new);
    }

}
