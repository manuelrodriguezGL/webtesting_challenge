package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutFinishedPage extends BaseStorePage {

    private static final String URL = "/checkout-complete.html";

    @FindBy(className = "complete-header")
    private WebElement pageHeader;

    @FindBy(className = "complete-text")
    private WebElement completeOrderText;

    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

    public CheckoutFinishedPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    @Override
    protected void load() {
        logger.info("Info: Attempting to load Checkout Finished page...");
        System.out.println("Attempting to load Checkout Finished page...");
        driver.get(baseUrl + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        if (!isPageLoaded())
            throw new Error("Checkout Finished page was not loaded!");
    }

    @Override
    public boolean isPageLoaded() {
        return isElementVisible(pageHeader);
    }

    public String getPageHeaderText() {
        return pageHeader.getText();
    }

    public String getOrderCompleteText() {
        return completeOrderText.getText();
    }

    public String getBackHomeButtonText() {
        return backHomeButton.getText();
    }


    public ProductsInventoryPage clickBackHomeButton() {
        botStyle.click(backHomeButton);
        return new ProductsInventoryPage(driver, baseUrl);
    }
}
