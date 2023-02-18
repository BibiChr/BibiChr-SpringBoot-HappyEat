package ch.fhnw.webec.happyeat.data;

import ch.fhnw.webec.happyeat.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Integer> {

    @Query(value = "SELECT shopping_list.id, shopping_list.name "
        + "FROM shopping_list "
        + "WHERE shopping_list.id NOT IN "
        + "     (SELECT auser_shopping_lists.shopping_lists_id "
        + "       FROM auser_shopping_lists)",
        nativeQuery = true)
    List<ShoppingList> findAllShoppingListsNotInUse();
}
