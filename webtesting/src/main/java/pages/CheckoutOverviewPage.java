package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CheckoutOverviewPage extends BaseStorePage {

    private static final String URL = "/checkout-step-two.html";

    @FindBy(className = "subheader")
    private WebElement pageHeader;

    @FindBy(className = "cart_quantity_label")
    private WebElement cartQuantityLabel;

    @FindBy(className = "cart_desc_label")
    private WebElement cartDescLabel;

    // All this elements that creates a List<WebElement> should use @FindBys, with an "s" at the end, that is the
    // equivalent to the findElements with an "s", @FindBy will return the first match found.
    // If this happens on other pages, please fix on all all afected pages
    @FindBy(className = "cart_item")
    private List<WebElement> cartItemsList;

    @FindBy(className = "cart_quantity")
    private List<WebElement> cartQuantityList;

    @FindBy(css = ".cart_item_label>a")
    private List<WebElement> cartItemLinkList;

    @FindBy(css = ".cart_item_label>a>.inventory_item_name")
    private List<WebElement> cartItemNameList;

    @FindBy(css = ".cart_item_label>.inventory_item_desc")
    private List<WebElement> cartItemDescList;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> cartItemPricesList;

    @FindBy(className = "summary_info_label")
    private List<WebElement> summaryInfoLabelList;

    @FindBy(className = "summary_value_label")
    private List<WebElement> summaryValueLabelList;

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

    public CheckoutOverviewPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    private By getInventoryItemLinkLocator(String id) {
        return By.cssSelector(CommonUtils.formatLocator("#item_{0}_title_link", id));
    }

    private By getInventoryItemNameLocator(String id) {
        return By.cssSelector(CommonUtils.formatLocator("#item_{0}_title_link", id) + ">.inventory_item_name");
    }

    private By getInventoryItemDescriptionLocator(String id) {
        return By.cssSelector(CommonUtils.formatLocator("#item_{0}_title_link", id) + "~.inventory_item_desc");
    }

    private By getInventoryItemPriceLocator(String id) {
        return By.cssSelector(CommonUtils.formatLocator("#item_{0}_title_link", id) +
                "~.item_pricebar>.inventory_item_price");
    }

    private By getInventoryItemQuantityLocator(String id) { // SauceDemo found its way to make us use Xpath anyway
        return By.xpath(CommonUtils.formatLocator(
                "//a[@id=\"item_{0}_title_link\"]/ancestor::div[@class=\"cart_item_label\"]" +
                        "/preceding-sibling::div[@class=\"cart_quantity\"]", id));
    }

    @Override
    protected void load() {
        System.out.println("Attempting to load Checkout Overview page...");
        driver.get(BASE_URL + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        if (!isPageLoaded())
            throw new Error("Checkout Overview page was not loaded!");
    }

    @Override
    public boolean isPageLoaded() {
        return isElementVisible(pageHeader);
    }

    public String getProductName(String id) {
        return waitByLocator((getInventoryItemNameLocator(id))).getText();
    }

    public String getProductDescription(String id) {
        return waitByLocator((getInventoryItemDescriptionLocator(id))).getText();
    }

    public String getProductPrice(String id) {
        return waitByLocator((getInventoryItemPriceLocator(id))).getText();
    }

    public String getProductQuantity(String id) {
        return waitByLocator(getInventoryItemQuantityLocator(id)).getText();
    }

    public String getPaymentInformationLabelText() {
        return summaryInfoLabelList.get(0).getText();
    }

    public String getPaymentInformationText() {
        return summaryValueLabelList.get(0).getText();
    }

    public String getShippingMethodLabelText() {
        return summaryInfoLabelList.get(1).getText();
    }

    public String getShippingMethodText() {
        return summaryValueLabelList.get(1).getText();
    }

    public String getQuantityLabelText() {
        return cartQuantityLabel.getText();
    }

    public String getDescriptionLabelText() {
        return cartDescLabel.getText();
    }

    public String getCancelButtonText() {
        return cancelButton.getText();
    }

    public String getFinishButtonText() {
        return finishButton.getText();
    }


    public boolean isCartEmpty() {
        try {
            return cartItemsList.size() == 0;
        } catch (NoSuchElementException e) {
            return true;
        }
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

    /***
     * Sum all the values from the list, plus the tax
     * @return A double with the result of the sum
     */
    public double sumTotalAmount() {
        if (isCartEmpty())
            throw new IndexOutOfBoundsException("There are no elements in the cart!");

        try {
            double subtotal = getPriceAmountFromList(cartItemPricesList);
            double tax = getPriceAmountFromElement(taxLabel);
            return subtotal + tax;
        } catch (Exception e) {
            throw e;
        }
    }

    /***
     * Sum all the values from the Item total label
     * @return A double with the value from Item total label
     */
    public double getSubtotalAmount() {
        if (isCartEmpty())
            throw new IndexOutOfBoundsException("There are no elements in the cart!");

        try {
            return getPriceAmountFromElement(subtotalLabel);
        } catch (Exception e) {
            throw e;
        }
    }

    /***
     * Get ths value from the Total label (elements + tax)
     * @return A double with the value from Total label
     */
    public double getTotalAmount() {
        if (isCartEmpty())
            throw new IndexOutOfBoundsException("There are no elements in the cart!");

        try {
            return getPriceAmountFromElement(totalLabel);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean itemSubtotalMatch() {
        boolean result = false;
        if (isCartEmpty())
            throw new IndexOutOfBoundsException("There are no elements in the cart!");

        try {
            result = getPriceAmountFromList(cartItemPricesList) == getSubtotalAmount();
        } catch (Exception e) {
            throw e;
        }

        return result;
    }

    public boolean itemTotalMatch() {
        boolean result = false;
        if (isCartEmpty())
            throw new IndexOutOfBoundsException("There are no elements in the cart!");

        try {
            result = getTotalAmount() == sumTotalAmount();
        } catch (Exception e) {
            throw e;
        }

        return result;
    }

    public ProductsInventoryPage cancelCheckout() {
        if (isElementVisible(cancelButton)) {
            cancelButton.click();
            return new ProductsInventoryPage(driver, BASE_URL);
        }
        return null;
    }

    public CheckoutFinishedPage finishCheckout() {
        if (isElementVisible(finishButton)) {
            finishButton.click();
            return new CheckoutFinishedPage(driver, BASE_URL);
        }
        return null;
    }
}
