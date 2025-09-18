import PageObject.MainPage;
import core.BaseTest;
import org.junit.jupiter.api.*;

import static services.configProvider.config;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UiTests extends BaseTest {
    MainPage mainPage;
    private String url = config.getString("url");

    @BeforeAll
    public void precondition() {
        driver.get(url);
    }

    @BeforeEach
    public void refreshPage() {
        driver.navigate().refresh();
        mainPage = new MainPage();
    }

    @Test
    public void designTest() {
        Assertions.assertTrue(mainPage.isAvatarDisplayed(), "Отсутствует логотип");
        Assertions.assertTrue(mainPage.isTitleDisplayed());
        Assertions.assertTrue(mainPage.isEmailDisplayed());
        Assertions.assertTrue(mainPage.isHostDisplayed());
        Assertions.assertTrue(mainPage.isSaveUserCheckboxDisplayed());
        Assertions.assertTrue(mainPage.isSaveUserCheckboxTitleDisplayed());
        Assertions.assertTrue(mainPage.isSaveUserCheckboxIconDisplayed());
        Assertions.assertTrue(mainPage.isContinueButtonDisplayed());
        Assertions.assertTrue(mainPage.isSignupButtonDisplayed());
    }

    @Test
    public void activeButtonsTest() {
        Assertions.assertTrue(mainPage.isSaveUserCheckboxOn());
        Assertions.assertNotNull(mainPage.isContinueButtonDisabled(), "Кнопка 'Войти' активна. Должна быть неактивной.");
    }

    @Test
    public void activeButtonTest() {
        mainPage.setEmail("abc");
        Assertions.assertNull(mainPage.isContinueButtonDisabled(), "Кнопка 'Войти' неактивна. Должна быть активной.");
    }

    @Test
    public void invalidEmailNameTest() {
        mainPage.setEmail("ф");
        mainPage.submitButtonClick();
        Assertions.assertTrue(mainPage.isErrorMessageDisplayed());
        Assertions.assertEquals(mainPage.getErrorMessageText(), "Вы ввели несуществующее имя аккаунта [100]");
    }
}
