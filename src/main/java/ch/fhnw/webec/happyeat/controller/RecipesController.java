package ch.fhnw.webec.happyeat.controller;

import ch.fhnw.webec.happyeat.model.Recipe;
import ch.fhnw.webec.happyeat.service.GetterService;
import ch.fhnw.webec.happyeat.service.recipe.RecipeService;
import ch.fhnw.webec.happyeat.service.user.UserRecipeService;
import ch.fhnw.webec.happyeat.service.user.UserService;
import ch.fhnw.webec.happyeat.util.ModelUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RecipesController extends ExceptionController {
    private static final String REDIRECT_RECIPES = "redirect:/recipe/";
    private static final String RECIPE = "recipe";
    private final RecipeService recipeService;
    private final UserService userService;
    private final UserRecipeService userRecipeService;
    private final GetterService getterService;

    public RecipesController(RecipeService recipeService, UserService userService, UserRecipeService userRecipeService,
                             GetterService getterService) {
        this.recipeService = recipeService;
        this.userService = userService;
        this.userRecipeService = userRecipeService;
        this.getterService = getterService;
    }

    @GetMapping("/recipes")
    public String showRecipes(Model model) {
        model.addAttribute("recipes", userRecipeService.getAllRecipesOfUser(userService.getUsernameOfCurrentUser()));
        return "/page/recipe/recipes";
    }

    @GetMapping("/recipe/{recipeId}")
    public String showRecipeDetails(@PathVariable int recipeId, Model model, HttpServletRequest request) {
        model.addAttribute(RECIPE, getterService.getRecipeById(recipeId));
        model.addAttribute("recipePhoto", "/recipe-picture/" + recipeId);
        ModelUtil.addToken(model, request);
        return "/page/recipe/recipe-details";
    }

    @GetMapping("/recipe/add")
    public String showAddRecipe(Model model, HttpServletRequest request) {
        model.addAttribute("ingredientCategories", getterService.getAllIngredientCategories());
        ModelUtil.addToken(model, request);
        model.addAttribute("subBarText", "Neues Rezept hinzufügen");
        model.addAttribute("postLink", "/recipe/add");
        model.addAttribute("abortLink", "/recipes");
        return "/page/recipe/recipe-add-edit";
    }

    @GetMapping("/recipe/{recipeId}/edit")
    public String showEditRecipe(@PathVariable int recipeId, Model model, HttpServletRequest request) {
        Recipe recipe = getterService.getRecipeById(recipeId);
        model.addAttribute(RECIPE, recipe);
        model.addAttribute("ingredientCategories", getterService.getAllIngredientCategories());
        ModelUtil.addToken(model, request);
        model.addAttribute("subBarText", "Rezept " + recipe.getName() + " bearbeiten.");
        model.addAttribute("postLink", "/recipe/" + recipe.getId() + "/edit");
        model.addAttribute("abortLink", "/recipe/" + recipe.getId());
        return "/page/recipe/recipe-add-edit";
    }

    @GetMapping("/recipe/{recipeId}/delete")
    public String deleteRecipe(@PathVariable int recipeId) {
        userRecipeService.removeRecipeFromUser(userService.getUsernameOfCurrentUser(), recipeId);
        return "redirect:/recipes";
    }

    @GetMapping("/recipe-picture/{recipeId}")
    public ResponseEntity<?> getRecipePicture(@PathVariable("recipeId") int recipeId) {
        byte[] image = recipeService.getImageData(recipeId);
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.valueOf("image/png"))
            .body(image);
    }

    @PostMapping("/recipe/add")
    public String addRecipe(@RequestParam String name, @RequestParam List<String> ingredient,
                            @RequestParam List<String> amount, @RequestParam List<String> ingredientCategory,
                            @RequestParam List<String> step, @RequestParam List<String> stepDescription
    ) {
        Recipe recipe =
            userRecipeService.addRecipeToUser(userService.getUsernameOfCurrentUser(), name, ingredient, amount,
                ingredientCategory, step, stepDescription);
        return REDIRECT_RECIPES + recipe.getId();
    }

    @PostMapping("/recipe/{recipeId}/edit")
    public String editRecipe(@PathVariable int recipeId, @RequestParam String name,
                             @RequestParam(required = false) List<String> ingredient,
                             @RequestParam(required = false) List<String> amount,
                             @RequestParam(required = false) List<String> ingredientCategory,
                             @RequestParam(required = false) List<String> step,
                             @RequestParam(required = false) List<String> stepDescription
    ) {
        recipeService.editRecipe(recipeId, name, ingredient, amount, ingredientCategory, step, stepDescription);
        return REDIRECT_RECIPES + recipeId;
    }

    @PostMapping("/recipe/{recipeId}/add-photo")
    public String saveRecipePicture(@PathVariable int recipeId, @RequestParam("image") MultipartFile file) {
        recipeService.uploadRecipeImage(file, recipeId);
        return REDIRECT_RECIPES + recipeId;
    }

    @GetMapping("/recipe/{recipeId}/share")
    public String shareRecipe(@PathVariable int recipeId, Model model, HttpServletRequest request) {
        ModelUtil.addToken(model, request);
        model.addAttribute(RECIPE, getterService.getRecipeById(recipeId));
        model.addAttribute("users", userService.getUsersNotCurrentUser());
        return "/page/recipe/recipe-share";
    }

    @PostMapping("/recipe/{recipeId}/share")
    public String shareRecipe(@PathVariable int recipeId, @RequestParam String username) {
        userRecipeService.shareRecipe(username, recipeId);
        return REDIRECT_RECIPES + recipeId;
    }

}
