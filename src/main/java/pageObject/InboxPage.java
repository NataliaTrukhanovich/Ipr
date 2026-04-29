package pageObject;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    public void waitPageLoading() {
        waitForUrl("https://e.mail.ru/inbox");
    }

    @Step("Получение email текущего авторизованного пользователя")
    public String getAccountName() {

        WebElement profileIcon = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class, 'ph-project__user-icon-mask')]")));
        profileIcon.click();

        WebElement sidebar = driver.findElement(By.xpath("//div[contains(@class,'ph-sidebar')]"));
        WebElement email = sidebar.findElement(By.xpath("//div[contains(@class,'ph-desc__email')]"));
        return email.getDomAttribute("aria-label").trim();
    }

    @Step("Отправка письма на этот же почтовый ящик с темой сообщения {subject}")
    public void sendLetterWithEmptyMessage(String subject) {
        composeButton.click();
        WebElement inputTo = driver.findElement(By.xpath("//input[@tabindex='100']"));
        inputTo.sendKeys("testmail1025@mail.ru");
        WebElement inputSubject = driver.findElement(By.xpath("//input[@name='Subject']"));
        inputSubject.sendKeys(subject);
        WebElement sendButton = driver.findElement(By.xpath("//button[@data-test-id='send']"));
        sendButton.click();
        WebElement confirmEmptyLetterButtton = driver.findElement(By.xpath("//div[@data-test-id='confirmation:empty-letter']//span[contains(text(),'Отправить')]"));
        confirmEmptyLetterButtton.click();

        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@title='Закрыть']")));
        closeButton.click();
    }

    @Step("Переходим в папку Входящие")
    public void chooseMyselfLetters() {
        WebElement myselfLetters = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='mt-h-c__content']//span[contains(@class, 'mt-t_tomyself')]")));
        myselfLetters.click();
    }

    @Step("Поиск письма с темой {subject}")
    public boolean checkLetter(String subject) {
        try {
            driver.findElement(By.xpath("//span[contains(text(), '" + subject + "')]"));
            return true;
        } catch (NoSuchElementException e) {
            logger.info("Письмо " + subject + " не найдено");
            return false;
        }
    }

    @Step("Удаление письма с темой {subject}")
    public void deleteLetter(String subject) {
        WebElement letter = driver.findElement(By.xpath("//span[contains(text(), '" + subject + "')]"));
        letter.click();

        WebElement delButton = driver.findElement(By.xpath("//span[@title='Удалить']"));
        delButton.click();

        WebElement backButton = driver.findElement(By.xpath("//span[@title='Вернуться']"));
        backButton.click();
    }

    @Step("Закрываем рекламный баннер, если он появляется")
    public void closeAdIfPresent() {

        try {
            WebElement closeButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[contains(@id,'trg-b')]//div[contains(@style, 'display')]//*")));
            logger.info("Кнопка найдена. Нажимаем её, чтобы закрыть рекламу");
            closeButton.click();

        } catch (TimeoutException e) {
            logger.info("Реклама не появилась или уже закрыта");
        }
    }
}
