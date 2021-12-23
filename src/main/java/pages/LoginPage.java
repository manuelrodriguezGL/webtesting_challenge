package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    private final String LOGIN_USER_ERROR_MESSAGE = "Epic sadface: Username is required";
    private final String LOGIN_PWD_ERROR_MESSAGE = "Epic sadface: Password is required";
    private final String LOGIN_ERROR_MESSAGE = "Epic sadface: Username and password do not match any user in this service";
    private final String URL = "";

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
        logger.getLogger().info("Info: Attempting to load Login page...");
        driver.get(baseUrl + URL);
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
        botStyle.clearTextField(userNameText);
        botStyle.type(userNameText, user);
    }

    private void enterPwd(String pwd) throws NoSuchElementException {
        botStyle.clearTextField(passwordText);
        botStyle.type(passwordText, pwd);
    }

    public ProductsInventoryPage login(String user, String pwd) throws NoSuchElementException {

        enterUserName(user);
        enterPwd(pwd);
        super.botStyle.click(loginButton);

        return new ProductsInventoryPage(driver, baseUrl);
    }

    public boolean checkEmptyUserErrorMessage(String pwd) {
        try {
            enterUserName("");
            enterPwd(pwd);
            botStyle.click(loginButton);
            return errorMessage.getText().equals(LOGIN_USER_ERROR_MESSAGE);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean checkEmptyPwdErrorMessage(String user) {
        try {
            enterUserName(user);
            enterPwd("");
            botStyle.click(loginButton);
            return errorMessage.getText().equals(LOGIN_PWD_ERROR_MESSAGE);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean checkInvalidCredsErrorMessage(String user, String pwd) {
        try {
            enterUserName(user);
            enterPwd(pwd);
            botStyle.click(loginButton);
            return errorMessage.getText().equals(LOGIN_ERROR_MESSAGE);
        } catch (Exception e) {
            throw e;
        }
    }
}
