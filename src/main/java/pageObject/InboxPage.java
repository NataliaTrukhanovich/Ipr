package pageObject;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InboxPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(InboxPage.class);

    @FindBy(xpath = "//a[contains(@class, 'compose-button')]")
    private WebElement composeButton;

    public InboxPage(WebDriver driver) {
        super(driver);
    }


    @Step("Ожидаем загрузку страницы")
    public boolean isPageLoading(String url) {
        return waitForUrl(url);
    }

    @Step("Получение email текущего авторизованного пользователя")
    public String getAccountName() {

        clickElement(By.xpath("//div[contains(@class, 'ph-project__user-icon-mask')]"));

        WebElement sidebar = getElement(By.xpath("//div[contains(@class,'ph-sidebar')]"));
        WebElement email = sidebar.findElement(By.xpath("//div[contains(@class,'ph-desc__email')]"));
        return email.getDomAttribute("aria-label").trim();
    }

    @Step("Отправка письма на этот же почтовый ящик с темой сообщения {subject}")
    public void sendLetterWithEmptyMessage(String subject) {

        composeButton.click();

        typeText(By.xpath("//input[@tabindex='100']"), "testmail1025@mail.ru");

        typeText(By.xpath("//input[@name='Subject']"), subject);

        logger.info("Нажимаем кнопку 'Отправить'");
        clickElement(By.xpath("//button[@data-test-id='send']"));

        logger.info("Подтверждаем 'Отправить без темы'");
        clickElement(By.xpath("//div[@data-test-id='confirmation:empty-letter']//span[contains(text(),'Отправить')]"));

        clickElement(By.xpath("//span[@title='Закрыть']"));
    }

    @Step("Переходим в папку Входящие")
    public void chooseMyselfLetters() {
        clickElement(By.xpath("//div[@class='mt-h-c__content']//span[contains(@class, 'mt-t_tomyself')]"));
    }

    @Step("Поиск письма с темой {subject}")
    public boolean checkLetter(String subject) {
        try {
            getElement(By.xpath("//span[contains(text(), '" + subject + "')]"));
            return true;
        } catch (NoSuchElementException e) {
            logger.info("Письмо " + subject + " не найдено");
            return false;
        }
    }

    @Step("Удаление письма с темой {subject}")
    public void deleteLetter(String subject) {
        logger.info("Выбираем письмо с темой " + subject);
        clickElement(By.xpath("//span[contains(text(), '" + subject + "')]"));

        logger.info("Выбираем 'Удалить' и 'Вернуться'");
        clickElement(By.xpath("//span[@title='Удалить']"));

        clickElement(By.xpath("//span[@title='Вернуться']"));
    }

    @Step("Закрываем рекламный баннер, если он появляется")
    public void closeAdIfPresent() {

        try {
            clickElement(By.xpath("//div[contains(@id,'trg-b')]//div[contains(@style, 'display')]//*"));
            logger.info("Кнопка найдена. Нажимаем её, чтобы закрыть рекламу");

        } catch (TimeoutException | ElementNotInteractableException e) {
            logger.info("Реклама не появилась или уже закрыта");
        }
    }

    public void navigateToInbox() {
        clickElement(By.xpath("//a[@data-folder-link-id='0']"));
    }
}
