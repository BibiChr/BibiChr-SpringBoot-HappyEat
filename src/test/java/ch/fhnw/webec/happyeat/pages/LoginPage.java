package ch.fhnw.webec.happyeat.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;

public class LoginPage {

    private static final String BASE_URL = "http://localhost:";

    private static LoginPage create(WebDriver driver, int port, String path) {
        driver.get(BASE_URL + port + path);
        return PageFactory.initElements(driver, LoginPage.class);
    }

    public static LoginPage create(WebDriver driver, int port) {
        return create(driver, port, "/login");
    }

    @FindBy(id = "login__error")
    private List<WebElement> errorMessage;

    @FindBy(id = "account__info")
    private List<WebElement> accountInfoMessage;

    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(className = "button")
    private WebElement submitButton;


    public Optional<WebElement> getErrorMessage() {
        return errorMessage.stream().findFirst();
    }

    public Optional<WebElement> getAccountInfoMessage() {
        return accountInfoMessage.stream().findFirst();
    }

    public WebElement getUsernameInput() {
        return usernameInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }
}
