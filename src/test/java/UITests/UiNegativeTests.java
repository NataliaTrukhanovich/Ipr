package UITests;

import io.qameta.allure.Description;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
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
    @Description("Проверка наличия обязательных элементов дизайна окна авторизации")
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
    @Description("Проверка, что чекбокс 'Сохранить вход' включен и кнопка 'Войти' неактивна")
    public void checkboxOnAndContinueButtonDisableTest() {
        Assertions.assertTrue(mainPage.isSaveUserCheckboxOn());
        Assertions.assertFalse(mainPage.isContinueButtonEnabled(), "Кнопка 'Войти' активна. Должна быть неактивной.");
    }

    @Test
    @Order(3)
    @Description("Проверка, что кнопка 'Войти' активна, когда введён email")
    public void activeContinueButtonTest() {
        mainPage.setEmail("abc");
        Assertions.assertTrue(mainPage.isContinueButtonEnabled(), "Кнопка 'Войти' неактивна. Должна быть активной.");
    }

    @Test
    @Order(4)
    @Description("Проверка сообщения об ошибке, когда введён неверный email")
    public void invalidEmailNameTest() {
        mainPage.setEmail("ы");
        mainPage.clickSubmitButton();
        Assertions.assertTrue(mainPage.isErrorMessageDisplayed());
        Assertions.assertEquals(mainPage.getErrorMessageText(), "Вы ввели несуществующее имя аккаунта [100]");
    }
}
