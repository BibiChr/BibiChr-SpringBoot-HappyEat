package ch.fhnw.webec.happyeat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private String amount;

    @NotNull
    @Column(nullable = false)
    private Boolean toBuy;

    @ManyToOne
    private IngredientCategory category;

    protected Ingredient() {
    }

    public Ingredient(String name, String amount, IngredientCategory category, Boolean toBuy) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.toBuy = toBuy;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public IngredientCategory getCategory() {
        return category;
    }

    public void setCategory(IngredientCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return amount + " " + name;
    }

    public Boolean getToBuy() {
        return toBuy;
    }

    public void toggleToBuy() {
        toBuy = !toBuy;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Ingredient that) {
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
