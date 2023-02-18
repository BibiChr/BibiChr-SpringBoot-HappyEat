package ch.fhnw.webec.happyeat.data;

import ch.fhnw.webec.happyeat.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {

    Optional<Ingredient> findById(int id);

    @Query(value = "SELECT ingredient.id, ingredient.amount, ingredient.name, ingredient.category_id, ingredient.to_buy "
        + "FROM ingredient "
        + "WHERE ingredient.id NOT IN "
        + "     (SELECT shopping_list_ingredient_list.ingredient_list_id "
        + "       FROM shopping_list_ingredient_list) "
        + "AND ingredient.id NOT IN "
        + "     (SELECT recipe_ingredient_list.ingredient_list_id "
        + "       FROM recipe_ingredient_list)",
        nativeQuery = true)
    List<Ingredient> findAllIngredientsNotInUse();
}
