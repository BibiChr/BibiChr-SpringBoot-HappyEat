package ch.fhnw.webec.happyeat.data;

import ch.fhnw.webec.happyeat.model.CookingStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CookingStepRepository extends JpaRepository<CookingStep, String> {

    @Query(value = "SELECT cooking_step.id, cooking_step.description, cooking_step.step_number "
        + "FROM cooking_step "
        + "WHERE cooking_step.id NOT IN "
        + "     (SELECT recipe_cooking_steps.cooking_steps_id "
        + "       FROM recipe_cooking_steps)",
        nativeQuery = true)
    List<CookingStep> findAllCookingStepsNotInUse();
}
