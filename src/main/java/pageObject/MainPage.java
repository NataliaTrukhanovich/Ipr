package pageObject;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {

    @FindBy(xpath = "//div[contains(@class, 'vkc__ServiceAvatar-module__img')]//img")
    private WebElement avatar;
    @FindBy(xpath = "//h1[contains(text(), 'Вход в «Mail»')]")
    private WebElement title;
    @FindBy(id = "email")
    private WebElement email;
    @FindBy(xpath = "//span[contains(text(), '@mail.ru')]")
    private WebElement host;
    @FindBy(xpath = "//div[contains(@class, 'checkboxOn')]")
    private WebElement saveUserCheckbox;
    @FindBy(xpath = "//span[contains(text(), 'Сохранить вход')]")
    private WebElement saveUserCheckboxTitle;
    @FindBy(xpath = "//button[contains(@aria-label, 'сохранить данные аккаунта')]")
    private WebElement saveUserCheckboxIcon;
    @FindBy(xpath = "//button[@data-test-id='continue-button']")
    private WebElement continueButton;
    @FindBy(xpath = "//button[@data-test-id='signup-button']")
    private WebElement signupButton;
    @FindBy(xpath = "//span[@data-test-id='error-message']")
    private WebElement errorMessage;

    @FindBy(xpath = "//span[contains(text(), 'Пропустить')]")
    private WebElement vkButton;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAvatarDisplayed() {
        return avatar.isDisplayed();
    }

    public boolean isTitleDisplayed() {
        return title.isDisplayed();
    }

    public boolean isEmailDisplayed() {
        return email.isDisplayed();
    }

    public boolean isHostDisplayed() {
        return host.isDisplayed();
    }

    public boolean isSaveUserCheckboxDisplayed() {
        return saveUserCheckbox.isDisplayed();
    }

    public boolean isSaveUserCheckboxTitleDisplayed() {
        return saveUserCheckboxTitle.isDisplayed();
    }

    public boolean isSaveUserCheckboxIconDisplayed() {
        return saveUserCheckboxIcon.isDisplayed();
    }

    public boolean isContinueButtonDisplayed() {
        return continueButton.isDisplayed();
    }

    public boolean isSignupButtonDisplayed() {
        return signupButton.isDisplayed();
    }

    public boolean isSaveUserCheckboxOn() {
        return saveUserCheckbox.isDisplayed();
    }

    public boolean isContinueButtonEnabled() {
        return continueButton.isEnabled();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

    public String getErrorMessageText() {
        return errorMessage.getText();
    }

    public void setEmail(String emailName) {
        email.sendKeys(emailName);
    }

    public void clickSubmitButton() {
        continueButton.click();
    }

    public void clickVkButton(){
        vkButton.click();
    }

    public void setPass(String pass) {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                    By.id("pass")));
            WebElement inputPassword = driver.findElement(By.name("password"));
            inputPassword.sendKeys(pass);
            driver.switchTo().defaultContent();
        } catch (NoSuchElementException e) {
            WebElement inputPassword = driver.findElement(By.name("password"));
            inputPassword.sendKeys(pass);
        }

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-test-id='submit']")));
        submitButton.click();
    }

}
