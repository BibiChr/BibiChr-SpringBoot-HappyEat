package ch.fhnw.webec.happyeat.service;

import ch.fhnw.webec.happyeat.data.ShoppingListRepository;
import ch.fhnw.webec.happyeat.exception.wronginput.ingredient.NotCorrectInputForIngredientAdd;
import ch.fhnw.webec.happyeat.exception.wronginput.shoppinglist.WrongInputForShoppingListNameEdit;
import ch.fhnw.webec.happyeat.model.Ingredient;
import ch.fhnw.webec.happyeat.model.ShoppingList;
import ch.fhnw.webec.happyeat.service.ingredient.IngredientService;
import ch.fhnw.webec.happyeat.util.ShoppingListUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final IngredientService ingredientService;
    private final GetterService getterService;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, IngredientService ingredientService,
                               GetterService getterService) {
        this.shoppingListRepository = shoppingListRepository;
        this.ingredientService = ingredientService;
        this.getterService = getterService;
    }

    public ShoppingList save(ShoppingList shoppingList) {
        return shoppingListRepository.save(shoppingList);
    }

    public void delete(ShoppingList shoppingList) {
        shoppingListRepository.delete(shoppingList);
    }

    public ShoppingList editShoppingList(int shoppingListId, String name) {
        ShoppingList shoppingList = getterService.getListById(shoppingListId);
        if (ShoppingListUtil.nameIsNotOkay(name)) {
            throw new WrongInputForShoppingListNameEdit(shoppingList);
        }
        shoppingList.setName(name);
        return save(shoppingList);
    }

    public ShoppingList addIngredientToShoppingList(int shoppingListId, String newIngredientName,
                                                    String newIngredientAmount, String newIngredientCategory) {
        ShoppingList shoppingList = getterService.getListById(shoppingListId);

        if (newIngredientName == null || newIngredientAmount == null || newIngredientCategory == null
                || newIngredientName.equals("") || newIngredientAmount.equals("") || newIngredientCategory.equals("")) {
            throw new NotCorrectInputForIngredientAdd(shoppingList);
        }

        Ingredient newIngredient = new Ingredient(newIngredientName, newIngredientAmount,
                getterService.getIngredientCategoryByName(newIngredientCategory), true);
        newIngredient = ingredientService.save(newIngredient);

        shoppingList.addIngredientToList(newIngredient);
        shoppingList = shoppingListRepository.save(shoppingList);
        return shoppingList;
    }

    public void removeIngredientFromShoppingList(int shoppingListId, int ingredientId) {
        ShoppingList shoppingList = getterService.getListById(shoppingListId);
        Ingredient ingredient = getterService.getIngredientById(ingredientId);

        shoppingList.removeIngredientFromList(ingredient);
        save(shoppingList);

        ingredientService.deleteNotUsedIngredients();
    }

    public void deleteNotUsedShoppingLists() {
        for (ShoppingList shoppingList : new CopyOnWriteArrayList<>(getNotSharedShoppingLists())) {
            shoppingList.removeAllIngredientsFromList();
            save(shoppingList);
            delete(shoppingList);
            ingredientService.deleteNotUsedIngredients();
        }
    }

    private List<ShoppingList> getNotSharedShoppingLists() {
        return shoppingListRepository.findAllShoppingListsNotInUse();
    }

}
