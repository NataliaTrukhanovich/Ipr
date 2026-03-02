package pageObject;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class InboxPage extends BasePage {

    @FindBy(xpath = "//a[contains(@class, 'compose-button')]")
    private WebElement composeButton;

    public InboxPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public Boolean checkValidAccountName() throws InterruptedException {

        WebElement profileIcon = driver.findElement(By.xpath("//div[contains(@class, 'ph-project__user-icon-mask')]"));
        Thread.sleep(5000);
        profileIcon.click();

        WebElement sidebar = driver.findElement(By.xpath("//div[contains(@class,'ph-sidebar')]"));
        WebElement email = sidebar.findElement(By.xpath("//div[contains(@class,'ph-desc__email')]"));
        String actualEmail = email.getDomAttribute("aria-label").trim();
        if (actualEmail.equals("testmail1025@mail.ru")) {
            return true;
        }

        return false;
    }

    public void sendEmptyMessage(String subject) throws InterruptedException {
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

    public void chooseMyselfLetters() {
        WebElement myselfLetters = driver.findElement(By.xpath("//div[@class='mt-h-c__content']//span[contains(@class, 'mt-t_tomyself')]"));
        myselfLetters.click();
    }

    public boolean checkLetter(String subject) throws InterruptedException {
        Thread.sleep(2000);
        List<WebElement> lettersSubjects = new ArrayList<>();
        lettersSubjects = driver.findElements(By.xpath("//span[@class='ll-sj__normal']"));
        for (WebElement element : lettersSubjects
        ) {
            if (element.getText().equals(subject)) return true;
        }
        return false;
    }

    public void deleteLetter(String subject) {
        WebElement letter = driver.findElement(By.xpath("//span[contains(text(), '" + subject + "')]"));
        letter.click();

        WebElement delButton = driver.findElement(By.xpath("//span[@title='Удалить']"));
        delButton.click();

        WebElement backButton = driver.findElement(By.xpath("//span[@title='Вернуться']"));
        backButton.click();
    }
}
