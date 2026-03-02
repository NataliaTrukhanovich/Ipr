package pageObject;

import core.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static services.ConfigProvider.config;

public class ConfirmationPage extends BasePage {
    @FindBy(xpath = "//a[@data-test-id='auth-problems']")
    private WebElement authProblemButton;
    @FindBy(xpath = "//li[@data-test-id='auth-by-password']")
    private WebElement authByPasswordButton;
    @FindBy(name = "password")
    private WebElement inputPassword;

    @FindBy(xpath = "//div[@class='submit-button-wrap']//button")
    private WebElement loginButton;

    private String email = config.getString("email");
    private String pass = config.getString("pass");

    public ConfirmationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void loginWithPassword() throws InterruptedException {
        Thread.sleep(20000);
        wait.until(ExpectedConditions.visibilityOf(authProblemButton)).click();
        Thread.sleep(10000);
        wait.until(ExpectedConditions.visibilityOf(authByPasswordButton)).click();
        wait.until(ExpectedConditions.visibilityOf(inputPassword)).sendKeys(pass);
        loginButton.click();
        Thread.sleep(3000);

        try {
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@data-test-id='tether-iframe']")));
            WebElement tetherContentCloseButton = driver.findElement(By.xpath("//button[@data-test-id='forced-email-bind-cancel-button']"));
            tetherContentCloseButton.click();
        } catch (NoSuchElementException e) {
            System.out.println("Диалоговое окно отсутствует");
        }

    }
}
