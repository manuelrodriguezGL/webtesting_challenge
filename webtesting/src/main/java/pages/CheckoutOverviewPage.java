package pages;

import constants.CheckoutOverviewPageConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutOverviewPage extends BaseProductPage {

    private static final String URL = "/checkout-step-two.html";

    @FindBy(className = "subheader")
    private WebElement pageHeader;

    @FindBy(className = "cart_quantity_label")
    private WebElement cartQuantityLabel;

    @FindBy(className = "cart_desc_label")
    private WebElement cartDescLabel;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItemsList;

    @FindBy(className = "summary_quantity")
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

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
        super.initElements(driver, this);
    }

    @Override
    protected void load() {
        System.out.println("Attempting to load Checkout Overview page...");
        driver.get(BASE_URL + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        if(!isPageLoaded())
            throw new Error("Checkout Overview page was not loaded!");
    }

    @Override
    public boolean isPageLoaded() {
        return isElementVisible(pageHeader);
    }

    public String verifyUIElements(int qty, String itemUrl, String name, String description, String price) {
        String errorMessages = "";

        if (!isCartEmpty()) {
            try {
                errorMessages += assesElementTextEquals(cartQuantityLabel, CheckoutOverviewPageConstants.CART_QUANTITY_LABEL);
                errorMessages += assesElementTextEquals(cartDescLabel, CheckoutOverviewPageConstants.CART_DESC_LABEL);
                errorMessages += assesElementTextEquals(finishButton, CheckoutOverviewPageConstants.FINISH_BUTTON_TXT);
                errorMessages += assesElementTextEquals(cancelButton, CheckoutOverviewPageConstants.CANCEL_BUTTON_TXT);

                errorMessages += assesElementTextEquals(cartQuantityList.get(0), String.valueOf(qty));
                errorMessages += assesElementTextContains(cartItemLinkList.get(0).getAttribute("href"), itemUrl);
                errorMessages += assesElementTextEquals(cartItemNameList.get(0), name);
                errorMessages += assesElementTextEquals(cartItemDescList.get(0), description);
                errorMessages += assesElementTextEquals(cartItemPricesList.get(0), price);

                // Payment info
                errorMessages += assesElementTextEquals(summaryInfoLabelList.get(0),
                        CheckoutOverviewPageConstants.PAYMENT_INFORMATION_LABEL);
                errorMessages += assesElementTextEquals(summaryValueLabelList.get(0),
                        CheckoutOverviewPageConstants.PAYMENT_INFORMATION);

                // Shipment info
                errorMessages += assesElementTextEquals(summaryInfoLabelList.get(1),
                        CheckoutOverviewPageConstants.SHIPPING_INFORMATION_LABEL);
                errorMessages += assesElementTextEquals(summaryValueLabelList.get(1),
                        CheckoutOverviewPageConstants.SHIPPING_INFORMATION);

            } catch (Exception e) {
                errorMessages = e.getStackTrace().toString();
            }
        } else {
            errorMessages = "Cart is empty!";
        }

        return errorMessages;
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
