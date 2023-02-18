package ch.fhnw.webec.happyeat.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ShoppingListPage {

    private static final String BASE_URL = "http://localhost:";

    private static ShoppingListPage create(WebDriver driver, int port, String path) {
        driver.get(BASE_URL + port + path);
        return PageFactory.initElements(driver, ShoppingListPage.class);
    }

    public static ShoppingListPage create(WebDriver driver, int port) {
        return create(driver, port, "/shopping-lists");
    }

    @FindBy(className = "list-card")
    private List<WebElement> listCards;

    public List<WebElement> getListCards() {
        return listCards;
    }

}
