package ch.fhnw.webec.happyeat.service.ingredient;

import ch.fhnw.webec.happyeat.data.IngredientCategoryRepository;
import ch.fhnw.webec.happyeat.model.IngredientCategory;
import org.springframework.stereotype.Service;

@Service
public class IngredientCategoryService {

    private final IngredientCategoryRepository ingredientCategoryRepository;

    public IngredientCategoryService(IngredientCategoryRepository repo) {
        this.ingredientCategoryRepository = repo;
    }

    public void save(IngredientCategory ingredientCategory) {
        ingredientCategoryRepository.save(ingredientCategory);
    }

    public void delete(IngredientCategory ingredientCategory) {
        ingredientCategoryRepository.delete(ingredientCategory);
    }

}
