package ch.fhnw.webec.happyeat.pagetest;

import ch.fhnw.webec.happyeat.pages.LoginPage;
import ch.fhnw.webec.happyeat.pages.ShoppingListCreatePage;
import ch.fhnw.webec.happyeat.pages.ShoppingListPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ShoppingListsPageIT {

    @Value("${local.server.port}")
    int port;

    HtmlUnitDriver driver = new HtmlUnitDriver();
    private ShoppingListPage shoppingListPage;

    @BeforeEach
    void setUp() {
        var page = LoginPage.create(driver, port);
        page.getUsernameInput().sendKeys("Bibi");
        page.getPasswordInput().sendKeys("1234");
        page.getSubmitButton().submit();
        shoppingListPage = ShoppingListPage.create(driver, port);
    }

    @Test
    @Transactional
    @DisplayName("Tests CR for shoppinglist")
    void createReadShoppingList() {
        assertEquals(2, shoppingListPage.getListCards().size());

        var shoppingListCreatePage = ShoppingListCreatePage.create(driver, port);
        shoppingListCreatePage.getShoppingListNameInputField().sendKeys("My Shoppinglist");
        shoppingListCreatePage.getSubmitButton().submit();

        shoppingListPage = ShoppingListPage.create(driver, port);

        assertFalse(shoppingListPage.getListCards().isEmpty());
        assertEquals(3, shoppingListPage.getListCards().size());
    }

}
