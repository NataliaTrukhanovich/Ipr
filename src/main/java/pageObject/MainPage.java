package pageObject;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BasePage {

    @FindBy(xpath = "//img[@class='vkc__Avatar__img vkc__Avatar__size48 vkc__Avatar__square']")
    private WebElement avatar;
    @FindBy(xpath = "//h1[contains(text(), 'Вход в «Mail»')]")
    private WebElement title;
    @FindBy(id = "email")
    private WebElement email;
    @FindBy(xpath = "//span[contains(text(), '@mail.ru')]")
    private WebElement host;
    @FindBy(xpath = "//div[@class='vkc__Checkbox__icon vkc__Checkbox__checkboxOn']")
    private WebElement saveUserCheckbox;
    @FindBy(xpath = "//div[@class='vkc__Checkbox__content']")
    private WebElement saveUserCheckboxTitle;
    @FindBy(xpath = "//button[contains(@aria-label, 'сохранить данные аккаунта')]")
    private WebElement saveUserCheckboxIcon;
    @FindBy(xpath = "//button[@data-test-id='continue-button']")
    private WebElement continueButton;
    @FindBy(xpath = "//button[@data-test-id='signup-button']")
    private WebElement signupButton;
    @FindBy(xpath = "//span[@data-test-id='error-message']")
    private WebElement errorMessage;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
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
        return saveUserCheckbox.getDomAttribute("class").contains("checkboxOn");
    }

    public Boolean isContinueButtonEnabled() {
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

    public void submitButtonClick() {
        continueButton.click();
    }
}
