package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutFinishedPage extends BaseProductPage {

    private static final String URL = "/checkout-complete.html";

    @FindBy(className = "complete-header")
    private WebElement pageHeader;

    public CheckoutFinishedPage(WebDriver driver) {
        super(driver);
        super.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get(BASE_URL + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        assertTrue(isElementVisible(pageHeader));
    }

    @Override
    public boolean isPageLoaded() {
        return isElementVisible(pageHeader);
    }
}
