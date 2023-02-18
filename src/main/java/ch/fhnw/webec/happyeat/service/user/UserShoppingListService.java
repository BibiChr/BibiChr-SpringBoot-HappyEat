package ch.fhnw.webec.happyeat.service.user;

import ch.fhnw.webec.happyeat.exception.wronginput.shoppinglist.WrongInputForShoppingListNameAdd;
import ch.fhnw.webec.happyeat.exception.wronginput.user.WrongInputForListSharing;
import ch.fhnw.webec.happyeat.model.ShoppingList;
import ch.fhnw.webec.happyeat.model.User;
import ch.fhnw.webec.happyeat.service.GetterService;
import ch.fhnw.webec.happyeat.service.ShoppingListService;
import ch.fhnw.webec.happyeat.util.ShoppingListUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserShoppingListService {

    private final GetterService getterService;
    private final ShoppingListService shoppingListService;
    private final UserService userService;


    public UserShoppingListService(GetterService getterService, ShoppingListService shoppingListService,
                                   UserService userService) {
        this.getterService = getterService;
        this.shoppingListService = shoppingListService;
        this.userService = userService;
    }

    public List<ShoppingList> getAllShoppingListsOfUser(String username) {
        return userService.getUserByUsername(username).getShoppingLists();
    }

    public ShoppingList addShoppingListToUser(String username, String name) {
        if (ShoppingListUtil.nameIsNotOkay(name)) {
            throw new WrongInputForShoppingListNameAdd();
        }
        ShoppingList shoppingList = shoppingListService.save(new ShoppingList(name));
        addShoppingListToUser(username, shoppingList);
        return shoppingList;
    }

    public void shareShoppingList(String username, int shoppingListId) {
        ShoppingList shoppingList = getterService.getListById(shoppingListId);
        if (username == null || username.equals("")) {
            throw new WrongInputForListSharing(shoppingList);
        }
        addShoppingListToUser(username, shoppingList);
    }

    private void addShoppingListToUser(String username, ShoppingList shoppingList) {
        User user = userService.getUserByUsername(username);
        user.addShoppingList(shoppingList);
        userService.save(user);
    }

    public void removeShoppingListFromUser(String username, int shoppingListId) {
        ShoppingList shoppingList = getterService.getListById(shoppingListId);

        User user = userService.getUserByUsername(username);
        user.removeShoppingList(shoppingList);
        userService.save(user);

        shoppingListService.deleteNotUsedShoppingLists();
    }

}
