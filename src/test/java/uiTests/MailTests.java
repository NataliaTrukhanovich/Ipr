package uiTests;

import core.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pageObject.InboxPage;
import pageObject.MainPage;

import static services.ConfigProvider.config;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Web тесты. Почтовый ящик пользователя")
@Epic("UI")
@Feature("Почтовый ящик пользователя")
public class MailTests extends BaseTest {

    private InboxPage inboxPage;
    private final String URL = config.getString("ui.url");
    private final String email = config.getString("ui.email");
    private final String pass = config.getString("ui.pass");
    private final String TESTLETTER = "Test10";

    @BeforeAll
    public void precondition() {

        driver.get(URL);

        MainPage mainPage = new MainPage(driver);
        inboxPage = new InboxPage(driver);

        mainPage.setEmail(email);
        mainPage.clickSubmitButton();
        mainPage.clickVkButton();
        mainPage.setPass(pass);
    }

    @Test
    @Story("Навигация")
    @Description("Проверка url загруженной страницы")
    public void checkUrlTest() {

        inboxPage.waitPageLoading();
        Assertions.assertEquals("https://e.mail.ru/inbox", driver.getCurrentUrl());

    }

    @Test
    @Story("Профиль пользователя")
    @Description("Проверка имени аккаунта")
    public void loginPositiveTest() {
        inboxPage.closeAdIfPresent();
        Assertions.assertEquals("testmail1025@mail.ru", inboxPage.getAccountName());

    }

    @Test
    @Story("Отправка писем")
    @Description("Проверка отправки письма")
    public void sendMessageTest() {
        inboxPage.sendLetterWithEmptyMessage(TESTLETTER);
        inboxPage.chooseMyselfLetters();
        Assertions.assertTrue(inboxPage.checkLetter(TESTLETTER));

    }

    @Test
    @Story("Удаление писем")
    @Description("Проверка удаления письма из Входящих")
    public void deleteMessageTest() {
        inboxPage.deleteLetter(TESTLETTER);
        Assertions.assertFalse(inboxPage.checkLetter(TESTLETTER), "Письмо с темой " + TESTLETTER + " не найдено");
    }
}
