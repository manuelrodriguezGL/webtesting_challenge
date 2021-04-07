package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutInformationPage extends BaseStorePage {
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

    @FindBy(className = "error-button")
    private WebElement errorMessageButton;

    @FindBy(className = "checkout_info_container")
    private WebElement checkoutInfoContainer;

    public CheckoutInformationPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    @Override
    protected void load() {
        System.out.println("Attempting to load Checkout Information page...");
        driver.get(BASE_URL + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        if (!isPageLoaded())
            throw new Error("Checkout Information page was not loaded!");
    }

    public boolean isPageLoaded() {
        return isElementVisible(checkoutInfoContainer);
    }

    public String getFirstNamePlaceholder() {
        return firstNameInput.getAttribute("placeholder");
    }

    public String getLastNamePlaceholder() {
        return lastNameInput.getAttribute("placeholder");
    }

    public String getZipCodePlaceholder() {
        return postalCodeInput.getAttribute("placeholder");
    }

    public String getCancelButtonText() {
        return cancelButton.getAttribute("innerText");
    }

    public String getContinueButtonText() {
        return continueButton.getAttribute("value");
    }

    public String getErrorMessage() {
        return errorMessage.getAttribute("innerText");
    }

    //TODO a test to check for closing error message
    public void clickErrorMessageButton() {
        errorMessageButton.click();
    }

    public ShoppingCartPage clickCancel() {

        if (isElementVisible(cancelButton)) {
            cancelButton.click();
            return new ShoppingCartPage(driver, BASE_URL);
        }
        return null;
    }

    public CheckoutOverviewPage clickContinue() {

        if (isElementVisible(continueButton)) {
            continueButton.click();
            return new CheckoutOverviewPage(driver, BASE_URL);
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
}
