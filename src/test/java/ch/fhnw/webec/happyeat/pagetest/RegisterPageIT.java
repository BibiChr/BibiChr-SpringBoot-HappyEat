package ch.fhnw.webec.happyeat.pagetest;

import ch.fhnw.webec.happyeat.pages.RegisterPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RegisterPageIT {

    @Value("${local.server.port}")
    int port;

    HtmlUnitDriver driver = new HtmlUnitDriver();

    @Test
    void initialPageShowsNoInfoMessages() {
        var page = RegisterPage.create(driver, port);
        assertEquals(empty(), page.getAccountInfoMessage());
    }

    @Test
    @DisplayName("Can't register with same username")
    void showErrorMessageAfterSameUsernameInput() {
        var page = RegisterPage.create(driver, port);
        assertEquals(empty(), page.getAccountInfoMessage());

        page.getFirstNameInput().sendKeys("wrong");
        page.getLastNameInput().sendKeys("wrong");
        page.getUsernameInput().sendKeys("Bibi");
        page.getPasswordInput().sendKeys("wrong");
        page.getEmailAddressInput().sendKeys("wrong@wrong.ch");
        page.getSubmitButton().submit();

        assertTrue(page.getAccountInfoMessage().isPresent());
    }

    @Test
    @DisplayName("an user can register")
    void registrationWorks() {
        var page = RegisterPage.create(driver, port);
        assertEquals(empty(), page.getAccountInfoMessage());

        page.getFirstNameInput().sendKeys("wrong");
        page.getLastNameInput().sendKeys("wrong");
        page.getUsernameInput().sendKeys("wrong");
        page.getEmailAddressInput().sendKeys("wrong@wrong.ch");
        page.getPasswordInput().sendKeys("wrong");
        page.getSubmitButton().submit();

        assertFalse(page.getAccountInfoMessage().isPresent());
        assertEquals("http://localhost:" + port + "/login/newAccount",  driver.getCurrentUrl());
    }

}
