package ch.fhnw.webec.happyeat.pagetest;

import ch.fhnw.webec.happyeat.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class LoginPageIT {

    @Value("${local.server.port}")
    int port;

    HtmlUnitDriver driver = new HtmlUnitDriver();

    @Test
    void initialPageShowsNoInfoMessages() {
        var page = LoginPage.create(driver, port);
        assertEquals(empty(), page.getErrorMessage());
        assertEquals(empty(), page.getAccountInfoMessage());
    }

    @Test
    @DisplayName("Shows Error if wrong credentials got entered")
    void showErrorMessageAfterWrongLoginCredentials() {
        var page = LoginPage.create(driver, port);
        assertEquals(empty(), page.getErrorMessage());

        page.getUsernameInput().sendKeys("wrong");
        page.getPasswordInput().sendKeys("wrong");
        page.getSubmitButton().submit();

        assertNotNull(page.getErrorMessage());
    }

}
