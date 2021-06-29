package pages;

import constants.LoginPageConstants;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public LoginPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    @Override
    protected void load() {
        System.out.println("Attempting to load Login page...");
        driver.get(base_url + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        if (!isPageLoaded())
            throw new Error("Login page was not loaded!");
    }

    @Override
    public boolean isPageLoaded() {
        return (isElementVisible(loginLogo)
                && isElementVisible(loginLogo)
                && isElementVisible(botLogo)
                && isElementVisible(userNameText)
                && isElementVisible(passwordText)
                && isElementVisible(loginButton));
    }

    private void enterUserName(String user) throws NoSuchElementException {
        //TODO Use bot style
        if (isElementVisible(userNameText)) {
            //WebElement e = waitElement(userNameText);
            // Use Botstyle test to remove this lines since they are duplicated on several methods:
            // https://github.com/SeleniumHQ/selenium/wiki/Bot-Style-Tests
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
            // Remove this kind of commented code, to keep you code clean :D
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
        super.botStyle.click(loginButton);

        return new ProductsInventoryPage(driver, base_url);
    }

    public boolean checkEmptyUserErrorMessage(String pwd) {
        try {
            enterUserName("");
            enterPwd(pwd);
            botStyle.click(loginButton);
            return errorMessage.getText().equals(LoginPageConstants.LOGIN_USER_ERROR_MESSAGE);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean checkEmptyPwdErrorMessage(String user) {
        try {
            enterUserName(user);
            enterPwd("");
            botStyle.click(loginButton);
            return errorMessage.getText().equals(LoginPageConstants.LOGIN_PWD_ERROR_MESSAGE);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean checkInvalidCredsErrorMessage(String user, String pwd) {
        try {
            enterUserName(user);
            enterPwd(pwd);
            botStyle.click(loginButton);
            return errorMessage.getText().equals(LoginPageConstants.LOGIN_ERROR_MESSAGE);
        } catch (Exception e) {
            throw e;
        }
    }
}