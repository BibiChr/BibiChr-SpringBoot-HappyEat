package ch.fhnw.webec.happyeat.controller;

import ch.fhnw.webec.happyeat.model.ShoppingList;
import ch.fhnw.webec.happyeat.service.GetterService;
import ch.fhnw.webec.happyeat.service.ShoppingListService;
import ch.fhnw.webec.happyeat.service.user.UserService;
import ch.fhnw.webec.happyeat.service.user.UserShoppingListService;
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
@RequestMapping(path = {"/shopping-lists", "/shopping-list"})
public class ShoppingListsController extends ExceptionController {
    private static final String REDIRECT_SHOPPING_LISTS = "redirect:/shopping-list/";
    private static final String SHOPPING_LIST = "shoppingList";
    private final ShoppingListService shoppingListService;
    private final UserService userService;
    private final UserShoppingListService userShoppingListService;
    private final GetterService getterService;

    public ShoppingListsController(ShoppingListService shoppingListService, UserService userService,
                                   UserShoppingListService userShoppingListService, GetterService getterService) {
        this.shoppingListService = shoppingListService;
        this.userService = userService;
        this.userShoppingListService = userShoppingListService;
        this.getterService = getterService;
    }

    @GetMapping("")
    public String showShoppingLists(Model model) {
        model.addAttribute("shoppingLists",
            userShoppingListService.getAllShoppingListsOfUser(userService.getUsernameOfCurrentUser()));
        return "/page/shopping-list/shopping-lists";
    }

    @GetMapping("/{shoppingListId}")
    public String showShoppingList(@PathVariable int shoppingListId, Model model) {
        model.addAttribute(SHOPPING_LIST, getterService.getListById(shoppingListId));
        model.addAttribute("ingredientCategories", getterService.getAllIngredientCategories());
        return "/page/shopping-list/shopping-list-details";
    }

    @GetMapping("/add")
    public String showAddShoppingList(Model model, HttpServletRequest request) {
        ModelUtil.addToken(model, request);
        model.addAttribute("subBarText", "Neue Liste hinzufügen");
        model.addAttribute("postLink", "/shopping-lists/add");
        model.addAttribute("cancelLink", "/shopping-lists");
        return "/page/shopping-list/shopping-list-add-edit";
    }

    @GetMapping("/{shoppingListId}/edit")
    public String showEditShoppingList(@PathVariable int shoppingListId, Model model, HttpServletRequest request) {
        ModelUtil.addToken(model, request);
        ShoppingList shoppingList = getterService.getListById(shoppingListId);
        model.addAttribute(SHOPPING_LIST, shoppingList);
        model.addAttribute("subBarText", "Liste " + shoppingList.getName() + " anpassen");
        model.addAttribute("postLink", "/shopping-list/"+shoppingList.getId() + "/edit");
        model.addAttribute("cancelLink", "/shopping-list/"+shoppingList.getId());
        return "/page/shopping-list/shopping-list-add-edit";
    }

    @PostMapping("/add")
    public String addShoppingList(@RequestParam String name) {
        ShoppingList shoppingList =
            userShoppingListService.addShoppingListToUser(userService.getUsernameOfCurrentUser(), name);
        return REDIRECT_SHOPPING_LISTS + shoppingList.getId();
    }

    @PostMapping("/{shoppingListId}/edit")
    public String editShoppingList(@PathVariable int shoppingListId, @RequestParam String name) {
        ShoppingList shoppingList = shoppingListService.editShoppingList(shoppingListId, name);
        return REDIRECT_SHOPPING_LISTS + shoppingList.getId();
    }

    @PostMapping("/{shoppingListId}/delete")
    public String deleteShoppingList(@PathVariable int shoppingListId) {
        userShoppingListService.removeShoppingListFromUser(userService.getUsernameOfCurrentUser(), shoppingListId);
        return "redirect:/shopping-lists";
    }

    @PostMapping("/{shoppingListId}/addIngredient")
    public String addIngredientToShoppingList(@PathVariable int shoppingListId, @RequestParam String newIngredient,
                                              @RequestParam String newIngredientAmount,
                                              @RequestParam String ingredientCategory) {
        ShoppingList shoppingList =
            shoppingListService.addIngredientToShoppingList(shoppingListId, newIngredient, newIngredientAmount,
                ingredientCategory);
        return REDIRECT_SHOPPING_LISTS + shoppingList.getId();
    }


    @GetMapping("/{shoppingListId}/share")
    public String shareShoppingList(@PathVariable int shoppingListId, Model model, HttpServletRequest request) {
        ModelUtil.addToken(model, request);
        model.addAttribute(SHOPPING_LIST, getterService.getListById(shoppingListId));
        model.addAttribute("users", userService.getUsersNotCurrentUser());
        return "/page/shopping-list/shopping-list-share";
    }

    @PostMapping("{shoppingListId}/share")
    public String shareShoppingList(@PathVariable int shoppingListId, @RequestParam String username) {
        userShoppingListService.shareShoppingList(username, shoppingListId);
        return REDIRECT_SHOPPING_LISTS + shoppingListId;
    }

}
