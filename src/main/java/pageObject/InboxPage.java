package pageObject;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InboxPage extends BasePage {

    @FindBy(xpath = "//a[contains(@class, 'compose-button')]")
    private WebElement composeButton;

    public InboxPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ожидаем загрузку страницы")
    public void waitPageLoading() {
        waitForUrl("https://e.mail.ru/inbox");
    }

    @Step("Проверка, что выполнен вход в аккаунт {expectedEmail}")
    public boolean checkValidAccountName(String expectedEmail) throws InterruptedException {

        WebElement profileIcon = driver.findElement(By.xpath("//div[contains(@class, 'ph-project__user-icon-mask')]"));
        Thread.sleep(5000);
        profileIcon.click();

        WebElement sidebar = driver.findElement(By.xpath("//div[contains(@class,'ph-sidebar')]"));
        WebElement email = sidebar.findElement(By.xpath("//div[contains(@class,'ph-desc__email')]"));
        String actualEmail = email.getDomAttribute("aria-label").trim();
        return actualEmail.equals(expectedEmail);
    }

    @Step("Отправка письма на этот же почтовый ящик с темой сообщения {subject}")
    public void sendLetterWithEmptyMessage(String subject) throws InterruptedException {
        composeButton.click();
        WebElement inputTo = driver.findElement(By.xpath("//input[@tabindex='100']"));
        inputTo.sendKeys("testmail1025@mail.ru");
        WebElement inputSubject = driver.findElement(By.xpath("//input[@name='Subject']"));
        inputSubject.sendKeys(subject);
        WebElement sendButton = driver.findElement(By.xpath("//button[@data-test-id='send']"));
        sendButton.click();
        WebElement confirmEmptyLetterButtton = driver.findElement(By.xpath("//div[@data-test-id='confirmation:empty-letter']//span[contains(text(),'Отправить')]"));
        confirmEmptyLetterButtton.click();

        Thread.sleep(2000);
        WebElement closeButton = driver.findElement(By.xpath("//span[@title='Закрыть']"));
        closeButton.click();
    }

    @Step("Переходим в папку Входящие")
    public void chooseMyselfLetters() {
        WebElement myselfLetters = driver.findElement(By.xpath("//div[@class='mt-h-c__content']//span[contains(@class, 'mt-t_tomyself')]"));
        myselfLetters.click();
    }

    @Step("Поиск письма с темой {subject}")
    public boolean checkLetter(String subject) {
        try {
            driver.findElement(By.xpath("//span[contains(text(), '" + subject + "')]"));
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Письмо " + subject + " не найдено");
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
}
