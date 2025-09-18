package PageObject;

import core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BasePage {

    @FindBy(xpath = "//img[@class='vkc__Avatar__img vkc__Avatar__size48 vkc__Avatar__square']")
    private WebElement avatar;
    @FindBy(xpath = "//span[@class='vkuiPlaceholder__title vkuiTitle__sizeYNone vkuiTitle__level2 vkuiTypography__host vkuiTypography__normalize vkuiTypography__weight2 vkuiRootComponent__host']")
    private WebElement title;
    @FindBy(id = "email")
    private WebElement email;
    @FindBy(xpath = "//span[@class='vkuiSelect__title vkuiText__host vkuiText__sizeYNone vkuiTypography__host vkuiTypography__normalize vkuiTypography__weight3 vkuiRootComponent__host']")
    private WebElement host;
    @FindBy(xpath = "//div[@class='vkc__Checkbox__icon vkc__Checkbox__checkboxOn']")
    private WebElement saveUserCheckbox;
    @FindBy(xpath = "//div[@class='vkc__Checkbox__content']")
    private WebElement saveUserCheckboxTitle;
    @FindBy(xpath = "//button[@class='vkc__SaveUserCheckbox__iconButton vkc__SaveUserCheckbox__rightIconButton vkuiInternalTappable vkuiIconButton__host vkuiIconButton__sizeYNone vkuiTappable__host vkuiTappable__sizeXNone vkuiTappable__hasPointerNone vkuiClickable__host vkuiClickable__realClickable vkuistyles__-focus-visible vkuiRootComponent__host']")
    private WebElement saveUserCheckboxIcon;
    @FindBy(xpath = "//button[@data-test-id='continue-button']")
    private WebElement continueButton;
    @FindBy(xpath = "//button[@data-test-id='signup-button']")
    private WebElement signupButton;
    @FindBy(xpath = "//span[@data-test-id='error-message']")
    private WebElement errorMessage;

    public MainPage() {
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

    public String isContinueButtonDisabled() {
        return continueButton.getDomAttribute("disabled");
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }

    public String getErrorMessageText(){
        return errorMessage.getText();
    }

    public void setEmail(String emailName) {
        email.sendKeys(emailName);
    }

    public void submitButtonClick() {
        continueButton.click();
    }
}
