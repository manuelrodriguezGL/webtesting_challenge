package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutInformation extends BaseProductPage {

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

    public CheckoutInformation(WebDriver driver) {
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

    public ProductsInventoryPage cancelCheckout() {
        return (isElementVisible(cancelButton)) ? new ProductsInventoryPage(driver) : null;
    }
}
