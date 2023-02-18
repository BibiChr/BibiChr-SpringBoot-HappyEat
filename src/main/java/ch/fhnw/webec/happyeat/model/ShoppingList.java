package ch.fhnw.webec.happyeat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(min = 3, max = 30)
    @Column(nullable = false)
    private String name;

    @ManyToMany
    private List<Ingredient> ingredientList = new ArrayList<>();

    protected ShoppingList() {
    }

    public ShoppingList(String name) {
        this.name = name;
        this.ingredientList = new ArrayList<>();
    }

    public ShoppingList(String name, List<Ingredient> ingredientList) {
        this.name = name;
        this.ingredientList = ingredientList;
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

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void addIngredientToList(Ingredient ingredient) {
        ingredientList.add(ingredient);
    }

    public void removeIngredientFromList(Ingredient ingredient) {
        ingredientList.remove(ingredient);
    }

    public void removeAllIngredientsFromList() {
        ingredientList = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ShoppingList that) {
            return id == that.id;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
