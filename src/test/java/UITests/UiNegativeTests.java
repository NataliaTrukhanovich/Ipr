package UITests;

import org.junit.jupiter.api.*;
import pageObject.MainPage;
import core.BaseTest;

import static services.ConfigProvider.config;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UiNegativeTests extends BaseTest {
    private MainPage mainPage;
    private String url = config.getString("url");

    @BeforeAll
    public void precondition() {
        driver.get(url);

        mainPage = new MainPage(driver);
    }

    @BeforeEach
    public void refreshPage() {
        driver.navigate().refresh();
    }

    @Test
    @Order(1)
    public void designTest() {
        Assertions.assertTrue(mainPage.isAvatarDisplayed(), "Отсутствует логотип");
        Assertions.assertTrue(mainPage.isTitleDisplayed(), "Отсутствует заголовок");
        Assertions.assertTrue(mainPage.isEmailDisplayed(), "Отсутствует поле 'Имя ящика'");
        Assertions.assertTrue(mainPage.isHostDisplayed(), "Не указан хост @mail.ru");
        Assertions.assertTrue(mainPage.isSaveUserCheckboxDisplayed(), "Отсутствует чекбокс сохранения данных для входа");
        Assertions.assertTrue(mainPage.isSaveUserCheckboxTitleDisplayed(), "Отсутствует подпись чекбокса 'Сохранить вход'");
        Assertions.assertTrue(mainPage.isSaveUserCheckboxIconDisplayed(), "Отсутствует иконка всплывающего сообщения");
        Assertions.assertTrue(mainPage.isContinueButtonDisplayed(), "Отсутствует кнопка 'Войти'");
        Assertions.assertTrue(mainPage.isSignupButtonDisplayed(), "Отсутствует кнопка 'Создать аккаунт'");
    }

    @Test
    @Order(2)
    public void checkboxOnAndContinueButtonDisableTest() {
        Assertions.assertTrue(mainPage.isSaveUserCheckboxOn());
        Assertions.assertFalse(mainPage.isContinueButtonEnabled(), "Кнопка 'Войти' активна. Должна быть неактивной.");
    }

    @Test
    @Order(3)
    public void activeContinueButtonTest() {
        mainPage.setEmail("abc");
        Assertions.assertTrue(mainPage.isContinueButtonEnabled(), "Кнопка 'Войти' неактивна. Должна быть активной.");
    }

    @Test
    @Order(4)
    public void invalidEmailNameTest() {
        mainPage.setEmail("ф");
        mainPage.submitButtonClick();
        Assertions.assertTrue(mainPage.isErrorMessageDisplayed());
        Assertions.assertEquals(mainPage.getErrorMessageText(), "Вы ввели несуществующее имя аккаунта [100]");
    }
}
