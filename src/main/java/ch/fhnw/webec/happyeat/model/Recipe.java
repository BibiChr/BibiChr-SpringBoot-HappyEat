package ch.fhnw.webec.happyeat.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @OneToMany
    private List<Ingredient> ingredientList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<CookingStep> cookingSteps = new ArrayList<>();

    @Lob
    private byte[] imageData;

    protected Recipe() {
    }

    public Recipe(String name, List<Ingredient> ingredientList, List<CookingStep> cookingSteps) {
        this.name = name;
        this.ingredientList = ingredientList;
        this.cookingSteps = cookingSteps;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public void setCookingSteps(List<CookingStep> cookingSteps) {
        this.cookingSteps = cookingSteps;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public List<CookingStep> getCookingSteps() {
        return cookingSteps;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void addImageData(byte[] data) {
        this.imageData = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Recipe that) {
            return id == that.id;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void removeAllIngredientsFromRecipe() {
        ingredientList = new ArrayList<>();
    }

}
