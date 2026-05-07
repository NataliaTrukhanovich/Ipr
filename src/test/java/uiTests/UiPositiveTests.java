package uiTests;

import core.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import pageObject.InboxPage;
import pageObject.MainPage;

import static services.ConfigProvider.config;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("UI тесты")
@Feature("Позитивные тесты")
public class UiPositiveTests extends BaseTest {

    private MainPage mainPage;
    private InboxPage inboxPage;
    private final String URL = config.getString("ui.url");
    private String email = config.getString("ui.email");
    private String pass = config.getString("ui.pass");
    private final String TESTLETTER = "Test10";

    @BeforeAll
    public void precondition() {

        driver.get(URL);

        mainPage = new MainPage(driver);
        inboxPage = new InboxPage(driver);

        mainPage.setEmail(email);
        mainPage.clickSubmitButton();
        mainPage.clickVkButton();
        mainPage.setPass(pass);
    }

    @Test
    @Order(1)
    @Description("Проверка url загруженной страницы")
    public void checkUrlTest() {

        inboxPage.waitPageLoading();
        Assertions.assertEquals("https://e.mail.ru/inbox", driver.getCurrentUrl());

    }

    @Test
    @Order(2)
    @Description("Проверка имени аккаунта")
    public void loginPositiveTest() {
        inboxPage.closeAdIfPresent();
        Assertions.assertEquals("testmail1025@mail.ru", inboxPage.getAccountName());

    }

    @Test
    @Order(3)
    @Description("Проверка отправки письма")
    public void sendMessageTest() {
        inboxPage.sendLetterWithEmptyMessage(TESTLETTER);
        inboxPage.chooseMyselfLetters();
        Assertions.assertTrue(inboxPage.checkLetter(TESTLETTER));

    }

    @Test
    @Order(4)
    @Description("Проверка удаления письма из Входящих")
    public void deleteMessageTest() {
        inboxPage.deleteLetter(TESTLETTER);
        Assertions.assertFalse(inboxPage.checkLetter(TESTLETTER));
    }
}
