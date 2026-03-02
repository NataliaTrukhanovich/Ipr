package UITests;

import core.BaseTest;
import org.junit.jupiter.api.*;
import pageObject.ConfirmationPage;
import pageObject.InboxPage;
import pageObject.MainPage;

import static services.ConfigProvider.config;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UiPositiveTests extends BaseTest {

    private MainPage mainPage;
    private ConfirmationPage confirmationPage;
    private InboxPage inboxPage;
    private final String URL = config.getString("url");
    private final String TESTLETTER = "Test10";

    @BeforeAll
    public void precondition() throws InterruptedException {

        driver.get(URL);

        mainPage = new MainPage(driver);
        confirmationPage = new ConfirmationPage(driver);
        inboxPage = new InboxPage(driver);

        mainPage.setEmail("testmail1025");
        mainPage.submitButtonClick();
        confirmationPage.loginWithPassword();
    }

    @Test
    @Order(1)
    public void checkUrlTest() {

        Assertions.assertEquals(driver.getCurrentUrl(), "https://e.mail.ru/inbox");

    }

    @Test
    @Order(2)
    public void loginPositiveTest() throws InterruptedException {

        Assertions.assertTrue(inboxPage.checkValidAccountName());

    }

    @Test
    @Order(3)
    public void sendMessageTest() throws InterruptedException {
        inboxPage.sendEmptyMessage(TESTLETTER);
        Thread.sleep(5000);
        inboxPage.chooseMyselfLetters();
        Assertions.assertTrue(inboxPage.checkLetter(TESTLETTER));

    }

    @Test
    @Order(4)
    public void deleteMessageTest() throws InterruptedException {
        inboxPage.deleteLetter(TESTLETTER);
        Assertions.assertFalse(inboxPage.checkLetter(TESTLETTER));
    }
}
