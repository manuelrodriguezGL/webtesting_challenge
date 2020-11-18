package pages;

import constants.CheckoutInformationConstants;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.jupiter.api.Assertions.assertTrue;

enum ErrorValues {
    FIRST("Error: First Name is required"),
    LAST("Error: Last Name is required"),
    POSTAL("Error: Postal Code is required");

    private final String errorCode;

    ErrorValues(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}

public class CheckoutInformationPage extends BaseProductPage {

    public static final String FIRST_NAME_ERROR = "FIRST";
    public static final String LAST_NAME_ERROR = "LAST";
    public static final String POSTAL_CODE_ERROR = "POSTAL";
    private static final String URL = "//checkout-step-one.html";

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(css = ".cart_cancel_link.btn_secondary")
    private WebElement cancelButton;

    @FindBy(css = ".btn_primary.cart_button")
    private WebElement continueButton;

    @FindBy(css = "h3[data-test]")
    private WebElement errorMessage;

    public CheckoutInformationPage(WebDriver driver) {
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

    private boolean areFieldsVisible() {
        return (isElementVisible(firstNameInput) && isElementVisible(lastNameInput) &&
                isElementVisible(postalCodeInput));
    }

    public String verifyUIElements() {
        String errorMessages = "";

        try {
            errorMessages += assesElementTextContains(firstNameInput.getAttribute("placeholder"),
                    CheckoutInformationConstants.NAME_PLACEHOLDER);
            errorMessages += assesElementTextContains(lastNameInput.getAttribute("placeholder"),
                    CheckoutInformationConstants.LAST_NAME_PLACEHOLDER);
            errorMessages += assesElementTextContains(postalCodeInput.getAttribute("placeholder"),
                    CheckoutInformationConstants.ZIP_PLACEHOLDER);
            errorMessages += assesElementTextEquals(cancelButton, CheckoutInformationConstants.CANCEL_BUTTON_TXT);
            errorMessages += assesElementTextEquals(continueButton, CheckoutInformationConstants.CONTINUE_BUTTON_TXT);
        } catch (Exception e) {
            errorMessages += e.getStackTrace().toString();
        }

        return errorMessages;
    }

    public String verifyErrorMessages(String firstName, String lastName, String zipCode) {
        String errorMessages = "";
        try {
            if (areFieldsVisible()) {

                enterCustomerData("", lastName, zipCode);
                clickContinue();
                errorMessages += checkErrorMessage(FIRST_NAME_ERROR) ? "" : "First name error message not found! ";
                clearCustomerData();

                enterCustomerData(firstName, "", zipCode);
                clickContinue();
                errorMessages += checkErrorMessage(LAST_NAME_ERROR) ? "" : "Last name error message not found! ";
                clearCustomerData();

                enterCustomerData(firstName, lastName, "");
                clickContinue();
                errorMessages += checkErrorMessage(POSTAL_CODE_ERROR) ? "" : "Postal code error message not found! ";
                clearCustomerData();
            } else {
                errorMessages += "Fields are not visible!";
            }
        } catch (Exception e) {
            throw e;
        }
        return errorMessages;
    }

    public ProductsInventoryPage clickCancel() {

        if (isElementVisible(cancelButton)) {
            cancelButton.click();
            return new ProductsInventoryPage(driver);
        }
        return null;
    }

    public CheckoutOverviewPage clickContinue() {

        if (isElementVisible(continueButton)) {
            continueButton.click();
            return new CheckoutOverviewPage(driver);
        }
        return null;
    }

    public void enterCustomerData(String firstName, String lastName, String postalCode) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        postalCodeInput.sendKeys(postalCode);
    }

    public void clearCustomerData() {
        // Interestingly, after the clear() method was used, then the values were back on fields.
        // SO I had to use a safer sendKeys()
        // Based on the Oracle of knowledge:
        // https://stackoverflow.com/questions/50677760/selenium-clear-command-doesnt-clear-the-element
        //TODO: Find the OS so it detects if Windows or MacOS keyboard
        firstNameInput.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        firstNameInput.sendKeys(Keys.BACK_SPACE);

        lastNameInput.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        lastNameInput.sendKeys(Keys.BACK_SPACE);

        postalCodeInput.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        postalCodeInput.sendKeys(Keys.BACK_SPACE);

    }

    public boolean checkErrorMessage(String errorCode) {
        String errorMsg = errorMessage.getAttribute("innerText");
        switch (ErrorValues.valueOf(errorCode)) {
            case FIRST:
                return errorMsg.equals(ErrorValues.FIRST.getErrorCode());
            case LAST:
                return errorMsg.equals(ErrorValues.LAST.getErrorCode());
            case POSTAL:
                return errorMsg.equals(ErrorValues.POSTAL.getErrorCode());
            default:
                return false;
        }
    }
}
