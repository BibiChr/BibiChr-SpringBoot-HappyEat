package ch.fhnw.webec.happyeat.data;

import ch.fhnw.webec.happyeat.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    @Query(value = "SELECT recipe.id, recipe.image_data, recipe.name "
        + "FROM recipe "
        + "WHERE recipe.id NOT IN "
        + "     (SELECT auser_recipes.recipes_id "
        + "       FROM auser_recipes )",
        nativeQuery = true)
    List<Recipe> findAllRecipesNotInUse();
}
