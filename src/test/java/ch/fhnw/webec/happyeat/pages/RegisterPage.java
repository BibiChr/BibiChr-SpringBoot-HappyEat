package ch.fhnw.webec.happyeat.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;

public class RegisterPage {

    private static final String BASE_URL = "http://localhost:";

    private static RegisterPage create(WebDriver driver, int port, String path) {
        driver.get(BASE_URL + port + path);
        return PageFactory.initElements(driver, RegisterPage.class);
    }

    public static RegisterPage create(WebDriver driver, int port) {
        return create(driver, port, "/register");
    }

    @FindBy(id = "account-info")
    private List<WebElement> accountInfo;

    @FindBy(name = "firstName")
    private WebElement firstNameInput;

    @FindBy(name = "lastName")
    private WebElement lastNameInput;

    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "emailAddress")
    private WebElement emailAddressInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(className = "button")
    private WebElement submitButton;


    public Optional<WebElement> getAccountInfoMessage() {
        return accountInfo.stream().findFirst();
    }

    public WebElement getFirstNameInput() {
        return firstNameInput;
    }

    public WebElement getLastNameInput() {
        return lastNameInput;
    }

    public WebElement getEmailAddressInput() {
        return emailAddressInput;
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
