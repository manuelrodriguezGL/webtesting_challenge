package pages;

import constants.LoginPageConstants;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPage extends BasePage {

    private static final String URL = "/index.html";

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
        super.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get(BASE_URL + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith(URL), "The page could not be loaded! Found URL: " + currentUrl);
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

        errorMessages += assesElementText(title, LoginPageConstants.LOGIN_TITLE);
        errorMessages += assesUIElement(loginLogo);
        errorMessages += assesUIElement(botLogo);
        errorMessages += assesUIElement(userNameText);
        errorMessages += assesUIElement(passwordText);
        errorMessages += assesUIElement(loginButton);

        return errorMessages;
    }

}
