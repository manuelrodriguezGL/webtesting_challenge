package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutOverviewPage extends BaseProductPage {

    private static final String URL = "/checkout-step-two.html";

    @FindBy(className = "subheader")
    private WebElement pageHeader;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItemsList;

    @FindBy(css = ".cart_item_label>a")
    private List<WebElement> cartItemLinksList;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> cartItemsPricesList;

    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(css = ".cart_cancel_link.btn_secondary")
    private WebElement cancelButton;

    @FindBy(css = ".btn_action.cart_button")
    private WebElement finishButton;

    public CheckoutOverviewPage(WebDriver driver) {
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
        assertTrue(isPageLoaded() &&
                currentUrl.endsWith(URL), "The page could not be loaded! Found URL: " + currentUrl);
    }

    @Override
    public boolean isPageLoaded() {
        return isElementVisible(pageHeader);
    }

    private ArrayList<Double> getPricesList(List<WebElement> list) {
        ArrayList<Double> pricesList = new ArrayList<>();
        for (WebElement e : list) {
            // Remove the $ character from each price
            String valueText = e.getAttribute("innerText");
            //e.getText();
            pricesList.add(Double.parseDouble(valueText.substring(valueText.indexOf("$") + 1)));
        }
        return pricesList;
    }

    private double getPriceAmountFromList(List<WebElement> list) {
        double total = 0.0;
        for (Double d : getPricesList(list)) {
            total += d;
        }
        return total;
    }

    private double getPriceAmountFromElement(WebElement e) {
        String valueText = e.getAttribute("innerText");
        return Double.parseDouble(valueText.substring(valueText.indexOf("$") + 1));
    }

    public double sumTotalAmount() {
        if (cartItemsList.size() <= 0)
            throw new IndexOutOfBoundsException("There are no elements in the cart!");

        try {
            double subtotal = getPriceAmountFromList(cartItemsPricesList);
            double tax = getPriceAmountFromElement(taxLabel);
            return subtotal + tax;
        } catch (Exception e) {
            throw e;
        }
    }

    public double getTotalAmount() {
        if (cartItemsList.size() <= 0)
            throw new IndexOutOfBoundsException("There are no elements in the cart!");

        try {
            return getPriceAmountFromElement(totalLabel);
        } catch (Exception e) {
            throw e;
        }
    }

    public ProductsInventoryPage cancelCheckout() {
        if (isElementVisible(cancelButton)) {
            cancelButton.click();
            return new ProductsInventoryPage(driver);
        }
        return null;
    }

    public CheckoutFinishedPage finishCheckout() {
        if (isElementVisible(finishButton)) {
            finishButton.click();
            return new CheckoutFinishedPage(driver);
        }
        return null;
    }
}
