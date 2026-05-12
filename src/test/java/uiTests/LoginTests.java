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
import pageObject.MainPage;

import static services.ConfigProvider.config;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Web тесты. Форма авторизации")
@Epic("UI")
@Feature("Форма авторизации")
public class LoginTests extends BaseTest {

    private MainPage mainPage;
    private final String url = config.getString("ui.url");

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
    @Description("Проверка наличия обязательных элементов дизайна окна авторизации")
    @Story("Отображение формы авторизации")
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
    @Description("Проверка, что чекбокс 'Сохранить вход' включен и кнопка 'Войти' неактивна")
    @Story("Валидация формы")
    public void checkboxOnAndContinueButtonDisableTest() {
        Assertions.assertTrue(mainPage.isSaveUserCheckboxOn(), "Чекбокс 'Сохранить вход' выключен. Должен быть включен");
        Assertions.assertFalse(mainPage.isContinueButtonEnabled(), "Кнопка 'Войти' активна. Должна быть неактивной");
    }

    @Test
    @Description("Проверка, что кнопка 'Войти' активна, когда введён email")
    @Story("Валидация формы")
    public void activeContinueButtonTest() {
        mainPage.setEmail("abc");
        Assertions.assertTrue(mainPage.isContinueButtonEnabled(), "Кнопка 'Войти' неактивна. Должна быть активной");
    }

    @Test
    @Description("Проверка сообщения об ошибке, когда введён неверный email")
    @Story("Ошибки авторизации")
    public void invalidEmailNameTest() {
        mainPage.setEmail("ы");
        mainPage.clickSubmitButton();
        Assertions.assertTrue(mainPage.isErrorMessageDisplayed());
        Assertions.assertEquals("Вы ввели несуществующее имя аккаунта [100]", mainPage.getErrorMessageText());
    }
}
