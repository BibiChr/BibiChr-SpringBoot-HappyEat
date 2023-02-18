package ch.fhnw.webec.happyeat.service;

import ch.fhnw.webec.happyeat.config.InitialDatabaseInput;
import ch.fhnw.webec.happyeat.data.UserRepository;
import ch.fhnw.webec.happyeat.model.ShoppingList;
import ch.fhnw.webec.happyeat.model.User;
import ch.fhnw.webec.happyeat.service.user.UserService;
import ch.fhnw.webec.happyeat.service.user.UserShoppingListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Optional;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {
    UserService userService;
    UserShoppingListService userShoppingListService;
    UserRepository userRepositoryMock;

    UserServiceTest() throws IOException {
        var mapper = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        var users = InitialDatabaseInput.loadSampleUsers(mapper);

        var getterServiceMock = Mockito.mock(GetterService.class);
        var shoppingListServiceMock = Mockito.mock(ShoppingListService.class);

        userRepositoryMock = Mockito.mock(UserRepository.class);
        when(userRepositoryMock.findAll()).thenReturn(users);
        when(userRepositoryMock.findByUsername("Bibi")).thenReturn(Optional.of(users.get(0)));

        var shoppingLists = users.stream()
            .flatMap(u -> u.getShoppingLists().stream())
            .toList();
        when(getterServiceMock.getAllLists()).thenReturn(shoppingLists);

        userService = new UserService(userRepositoryMock);
        userShoppingListService = new UserShoppingListService(getterServiceMock, shoppingListServiceMock, userService);
    }

    @Test
    @DisplayName("Add new Shopping list")
    void addShoppingListToUserTest() {
        User u = userService.getAllUser().get(0);
        ShoppingList shoppingList = new ShoppingList("MyList");

        userShoppingListService.addShoppingListToUser(u.getUsername(), "MyList");

        u.addShoppingList(shoppingList);
        verify(userRepositoryMock).save(u);
    }

    @Test
    @DisplayName("Edit user")
    void editUserTest() {
        User u = userService.getAllUser().get(0);
        String oldUsername = u.getUsername();
        String newMail = "bibi@bibi.ch";
        u.setEmail(newMail);
        String newUsername = "Maya";
        u.setUsername(newUsername);
        when(userRepositoryMock.save(any())).thenReturn(u);

        boolean success = userService.editUser(oldUsername, newMail, newUsername);

        assertTrue(success);
    }

    @Test
    @DisplayName("Fail to edit user")
    void editUserFailTest() {
        User u = userService.getAllUser().get(0);
        String oldUsername = u.getUsername();
        String newMail = "bibi@bibi.ch";
        u.setEmail(newMail);
        String newUsername = "claudine";
        u.setUsername(newUsername);
        when(userRepositoryMock.save(any())).thenReturn(u);

        boolean success = userService.editUser(oldUsername, newMail, newUsername);

        assertTrue(success);
    }

    @Test
    @DisplayName("Register new User")
    void registerNewUserTest() {
        boolean success = userService.registerNewUser(
            "Bianca",
            "Christen",
            "mail1@mail.ch",
            "username",
            "12345"
        );

        assertTrue(success);
    }

    @Test
    @DisplayName("Can't register new User if username is already taken")
    void registerNewUserWithSameUsernameTest() {
        boolean success = userService.registerNewUser(
            "Bianca",
            "Christen",
            "mail1@mail.ch",
            "Bibi",
            "12345"
        );

        assertFalse(success);
    }

    @Test
    @DisplayName("Can't register new User if mail is already taken")
    void registerNewUserWithSameMailTest() {
        boolean success = userService.registerNewUser(
            "Bianca",
            "Christen",
            "bianca@piekenbrock.de",
            "Bibi",
            "12345"
        );

        assertFalse(success);
    }
}
