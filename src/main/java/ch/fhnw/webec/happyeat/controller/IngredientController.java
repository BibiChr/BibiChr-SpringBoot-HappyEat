package ch.fhnw.webec.happyeat.controller;

import ch.fhnw.webec.happyeat.model.Ingredient;
import ch.fhnw.webec.happyeat.service.GetterService;
import ch.fhnw.webec.happyeat.service.ShoppingListService;
import ch.fhnw.webec.happyeat.service.ingredient.IngredientService;
import ch.fhnw.webec.happyeat.util.ModelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/ingredient/{shoppingListId}/{ingredientId}")
public class IngredientController extends ExceptionController {

    private static final String REDIRECT_SHOPPING_LISTS = "redirect:/shopping-list/";
    private final IngredientService ingredientService;
    private final ShoppingListService shoppingListService;
    private final GetterService getterService;


    public IngredientController(IngredientService ingredientService, ShoppingListService shoppingListService,
                                GetterService getterService) {
        this.ingredientService = ingredientService;
        this.shoppingListService = shoppingListService;
        this.getterService = getterService;
    }

    @GetMapping("/changeToBuy")
    public String showChangeIngredientToBuy(@PathVariable int shoppingListId, @PathVariable int ingredientId) {
        ingredientService.changeIngredientToBuyStatus(ingredientId);
        return REDIRECT_SHOPPING_LISTS + shoppingListId;
    }

    @GetMapping("/edit")
    public String showChangeIngredient(@PathVariable int shoppingListId, @PathVariable int ingredientId, Model model,
                                       HttpServletRequest request) {
        ModelUtil.addToken(model, request);
        Ingredient i = getterService.getIngredientById(ingredientId);
        model.addAttribute("ingredient", i);
        model.addAttribute("ingredientCategories", getterService.getAllIngredientCategories());
        model.addAttribute("shoppingList", getterService.getListById(shoppingListId));
        model.addAttribute("subBarText", i.getName() + " anpassen");
        return "/page/shopping-list/ingredient-edit";
    }

    @PostMapping("/delete")
    public String deleteIngredient(@PathVariable int shoppingListId, @PathVariable int ingredientId) {
        shoppingListService.removeIngredientFromShoppingList(shoppingListId, ingredientId);
        return REDIRECT_SHOPPING_LISTS + shoppingListId;
    }

    @PostMapping("/edit")
    public String changeIngredient(@PathVariable int shoppingListId, @PathVariable int ingredientId,
                                   @RequestParam String newIngredientName,
                                   @RequestParam String newIngredientAmount,
                                   @RequestParam String ingredientCategory) {
        ingredientService.updateIngredient(shoppingListId, ingredientId, newIngredientName, newIngredientAmount,
            ingredientCategory);
        return REDIRECT_SHOPPING_LISTS + shoppingListId;
    }

}
