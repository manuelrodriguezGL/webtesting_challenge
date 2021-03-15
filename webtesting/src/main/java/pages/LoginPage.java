package pages;

import constants.LoginPageConstants;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPage extends BasePage {

    private static final String URL = "";

    @FindBy(tagName = "title")
    private WebElement title;

    @FindBy(className = "login_logo")
    private WebElement loginLogo;

    @FindBy(className = "bot_column")
    private WebElement botLogo;

    @FindBy(id = "user-name")
    private WebElement userNameText;

    @FindBy(id = "password")
    private WebElement passwordText;

    @FindBy(className = "btn_action")
    private WebElement loginButton;

    @FindBy(className = "error-button")
    private WebElement errorButton;

    @FindBy(css = "h3[data-test=\"error\"")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
        //super.initElements(driver, this);
    }

    @Override
    protected void load() {
        System.out.println("Attempting to load Login page...");
        driver.get(BASE_URL + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        String currentUrl = driver.getCurrentUrl();

        if(!isPageLoaded())
            throw new Error("Login page was not loaded!");
    }

    @Override
    public boolean isPageLoaded(){
        return isElementVisible(loginLogo);
    }

    private void enterUserName(String user) throws NoSuchElementException {
        if (isElementVisible(userNameText)) {
            //WebElement e = waitElement(userNameText);
            userNameText.clear();
            userNameText.sendKeys(user);
        } else {
            throw new NoSuchElementException("Could not find user name field!");
        }
    }

    // TODO Patron de diseno Botstyle
    // TODO https://github.com/SeleniumHQ/selenium/wiki/Bot-Style-Tests
    private void enterPwd(String pwd) throws NoSuchElementException {
        if (isElementVisible(userNameText)) {
            //WebElement e = waitElement(passwordText);
            passwordText.clear();
            passwordText.sendKeys(pwd);
        } else {
            throw new NoSuchElementException("Could not find password field!");
        }
    }

    public ProductsInventoryPage login(String user, String pwd) throws NoSuchElementException {

        enterUserName(user);
        enterPwd(pwd);
        loginButton.click();

        return new ProductsInventoryPage(driver);
    }

    public String assesPageElements() {
        String errorMessages = "";

        errorMessages += assesElementTextEquals(title, LoginPageConstants.LOGIN_TITLE);
        errorMessages += assesUIElement(loginLogo);
        errorMessages += assesUIElement(botLogo);
        errorMessages += assesUIElement(userNameText);
        errorMessages += assesUIElement(passwordText);
        errorMessages += assesUIElement(loginButton);

        return errorMessages;
    }

    public boolean checkEmptyUserErrorMessage(String pwd) {
        try {
            enterUserName("");
            enterPwd(pwd);
            loginButton.click();
            return errorMessage.getText().equals(LoginPageConstants.LOGIN_USER_ERROR_MESSAGE);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean checkEmptyPwdErrorMessage(String user) {
        try {
            enterUserName(user);
            enterPwd("");
            loginButton.click();
            return errorMessage.getText().equals(LoginPageConstants.LOGIN_PWD_ERROR_MESSAGE);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean checkInvalidCredsErrorMessage(String user, String pwd) {
        try {
            enterUserName(user);
            enterPwd(pwd);
            loginButton.click();
            return errorMessage.getText().equals(LoginPageConstants.LOGIN_ERROR_MESSAGE);
        } catch (Exception e) {
            throw e;
        }
    }
}
