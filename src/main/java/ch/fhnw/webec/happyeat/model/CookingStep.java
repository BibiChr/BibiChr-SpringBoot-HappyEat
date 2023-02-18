package ch.fhnw.webec.happyeat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class CookingStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int stepNumber;

    @NotEmpty
    @Lob
    @Column(nullable = false)
    private String description;


    protected CookingStep() {
    }

    public CookingStep(int stepNumber, String description) {
        this.stepNumber = stepNumber;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return stepNumber + ". " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CookingStep that) {
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
