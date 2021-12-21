package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.CommonUtils;

import java.util.List;
import java.util.NoSuchElementException;

public class CheckoutOverviewPage extends BaseStorePage {

    private static final String URL = "/checkout-step-two.html";

    @FindBy(id = "checkout_summary_container")
    private WebElement checkoutSummaryContainer;

    @FindBy(className = "cart_quantity_label")
    private WebElement cartQuantityLabel;

    @FindBy(className = "cart_desc_label")
    private WebElement cartDescLabel;

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
        driver.get(baseUrl + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        if (!isPageLoaded())
            throw new Error("Checkout Overview page was not loaded!");
    }

    @Override
    public boolean isPageLoaded() {
        return isElementVisible(checkoutSummaryContainer);
    }

    public String getProductName(String id) {
        return botStyle.waitByLocator((getInventoryItemNameLocator(id))).getText();
    }

    public String getProductDescription(String id) {
        return botStyle.waitByLocator((getInventoryItemDescriptionLocator(id))).getText();
    }

    public String getProductPrice(String id) {
        return botStyle.waitByLocator((getInventoryItemPriceLocator(id))).getText();
    }

    public String getProductQuantity(String id) {
        return botStyle.waitByLocator(getInventoryItemQuantityLocator(id)).getText();
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

    private double getPriceAmountFromElement(WebElement e) {
        String valueText = e.getAttribute("innerText");
        return Double.parseDouble(valueText.substring(valueText.indexOf("$") + 1));
    }

    public double getTotalAmountFromItemList() {
        double total = 0.0;
        for (WebElement e : cartItemPricesList) {
            // Remove the $ character from each price
            total += getPriceAmountFromElement(e);
        }
        return total;
    }

    /***
     * Sum all the values from the list, plus the tax
     * @return A double with the result of the sum
     */
    public double sumTotalAmount() {
        if (isCartEmpty())
            throw new IndexOutOfBoundsException("There are no elements in the cart!");

        try {
            double subtotal = getTotalAmountFromItemList();
            double tax = getPriceAmountFromElement(taxLabel);
            return subtotal + tax;
        } catch (Exception e) {
            throw e;
        }
    }

    /***
     * Get the values from the Item total label
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

    public ProductsInventoryPage cancelCheckout() {
        botStyle.click(cancelButton);
        return new ProductsInventoryPage(driver, baseUrl);
    }

    public CheckoutFinishedPage finishCheckout() {
        botStyle.click(finishButton);
        return new CheckoutFinishedPage(driver, baseUrl);
    }
}
