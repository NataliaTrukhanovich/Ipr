package UITests;

import core.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import pageObject.ConfirmationPage;
import pageObject.InboxPage;
import pageObject.MainPage;

import static services.ConfigProvider.config;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("UI тесты")
@Feature("Позитивные тесты")
public class UiPositiveTests extends BaseTest {

    private MainPage mainPage;
    private ConfirmationPage confirmationPage;
    private InboxPage inboxPage;
    private final String URL = config.getString("url");
    private String email = config.getString("email");
    private String pass = config.getString("pass");
    private final String TESTLETTER = "Test10";

    @BeforeAll
    public void precondition() throws InterruptedException {

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
    public void loginPositiveTest() throws InterruptedException {

        Assertions.assertTrue(inboxPage.checkValidAccountName("testmail1025@mail.ru"));

    }

    @Test
    @Order(3)
    @Description("Проверка отправки письма")
    public void sendMessageTest() throws InterruptedException {
        inboxPage.sendLetterWithEmptyMessage(TESTLETTER);
        Thread.sleep(5000);
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
