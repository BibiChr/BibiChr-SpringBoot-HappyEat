package ch.fhnw.webec.happyeat.data;

import ch.fhnw.webec.happyeat.model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, String> {

    Optional<IngredientCategory> findByName(String name);

}
