package ch.fhnw.webec.happyeat.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Auser")  // Ohne das A gibt es immer SQL-Fehler. Laut Recherche mag H2 nicht, dass man user nutzt.
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(nullable = false)
    private String firstName;

    @NotEmpty
    @Column(nullable = false)
    private String lastName;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String username;

    @NotEmpty
    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> authorities;

    @ManyToMany
    private List<ShoppingList> shoppingLists;

    @ManyToMany
    private List<Recipe> recipes;

    @Lob
    private byte[] imageData;

    protected User() {
    }

    public User(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = Set.of("USER");
        this.shoppingLists = new ArrayList<>();
        this.recipes = new ArrayList<>();
    }

    public User(String firstName, String lastName, String email, String username, String password,
                Set<String> authorities, List<ShoppingList> shoppingLists, List<Recipe> recipes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.shoppingLists = new ArrayList<>(shoppingLists);
        this.recipes = new ArrayList<>(recipes);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream().map(SimpleGrantedAuthority::new).toList();
    }

    public int get() {
        return this.id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public List<ShoppingList> getShoppingLists() {
        return new ArrayList<>(shoppingLists);
    }

    public void addShoppingList(ShoppingList toAdd) {
        shoppingLists.add(toAdd);
    }

    public void removeShoppingList(ShoppingList toRemove) {
        shoppingLists.remove(toRemove);
    }

    public List<Recipe> getRecipes() {
        return new ArrayList<>(recipes);
    }

    public void addRecipe(Recipe toAdd) {
        recipes.add(toAdd);
    }

    public void removeRecipe(Recipe toRemove) {
        recipes.remove(toRemove);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void addImageData(byte[] data) {
        this.imageData = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User that) {
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
