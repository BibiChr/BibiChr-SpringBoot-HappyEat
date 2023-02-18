package ch.fhnw.webec.happyeat.service.recipe;

import ch.fhnw.webec.happyeat.data.CookingStepRepository;
import ch.fhnw.webec.happyeat.exception.wronginput.recipe.NotCorrectInputForRecipeStep;
import ch.fhnw.webec.happyeat.model.CookingStep;
import ch.fhnw.webec.happyeat.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CookingStepService {
    private final CookingStepRepository repository;

    public CookingStepService(CookingStepRepository repository) {
        this.repository = repository;
    }

    public void save(CookingStep cookingStep) {
        repository.save(cookingStep);
    }

    public void delete(CookingStep cookingStep) {
        repository.delete(cookingStep);
    }

    public List<CookingStep> saveCookingStepsForRecipe(Recipe recipe, List<String> step, List<String> stepDescription) {
        if (step == null
            || stepDescription == null
            || step.size() != stepDescription.size()) {
            if (recipe == null) {
                throw new NotCorrectInputForRecipeStep();
            }
            throw new NotCorrectInputForRecipeStep(recipe);
        }

        List<CookingStep> cookingSteps = new ArrayList<>();
        for (int i = 0; i < step.size(); i++) {
            if (stepDescription.get(i) == null || stepDescription.get(i).equals("")) {
                if (recipe == null) {
                    throw new NotCorrectInputForRecipeStep();
                }
                throw new NotCorrectInputForRecipeStep(recipe);
            }

            CookingStep thisCookingStep = new CookingStep(
                Integer.parseInt(step.get(i)), stepDescription.get(i)
            );
            cookingSteps.add(thisCookingStep);
            save(thisCookingStep);
        }
        return cookingSteps;
    }

    public void deleteNotUsedCookingSteps() {
        for (CookingStep i : getNotSharedCookingSteps()) {
            delete(i);
        }
    }

    private List<CookingStep> getNotSharedCookingSteps() {
        return repository.findAllCookingStepsNotInUse();
    }

}
