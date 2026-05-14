package uiTests;

import core.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void navigateToInboxPage() {
        inboxPage.navigateToInbox();
    }

    @Test
    @Story("Навигация")
    @Description("Проверка url загруженной страницы")
    public void checkUrlTest() {

        Assertions.assertTrue(inboxPage.isPageLoading("/inbox"));

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

        var letterTopic = "Test10";
        inboxPage.sendLetterWithEmptyMessage(letterTopic);
        inboxPage.chooseMyselfLetters();
        Assertions.assertTrue(inboxPage.checkLetter(letterTopic));

    }

    @Test
    @Story("Удаление писем")
    @Description("Проверка удаления письма из Входящих")
    public void deleteMessageTest() {

        var letterTopic = "Test101";
        inboxPage.sendLetterWithEmptyMessage(letterTopic);
        inboxPage.chooseMyselfLetters();
        inboxPage.deleteLetter(letterTopic);
        Assertions.assertFalse(inboxPage.checkLetter(letterTopic), "Письмо с темой " + letterTopic + " всё ещё в почтовом ящике");
    }

}
