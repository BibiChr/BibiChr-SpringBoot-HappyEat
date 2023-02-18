package ch.fhnw.webec.happyeat.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ShoppingListCreatePage {

    private static final String BASE_URL = "http://localhost:";

    private static ShoppingListCreatePage create(WebDriver driver, int port, String path) {
        driver.get(BASE_URL + port + path);
        return PageFactory.initElements(driver, ShoppingListCreatePage.class);
    }

    public static ShoppingListCreatePage create(WebDriver driver, int port) {
        return create(driver, port, "/shopping-list/add");
    }

    @FindBy(name = "name")
    private WebElement shoppingListNameInput;

    @FindBy(className = "button")
    private WebElement submitButton;

    public WebElement getShoppingListNameInputField() {
        return shoppingListNameInput;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

}
