package pages;

import constants.ShoppingCartPageConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartPage extends BaseStorePage {

    private static final String URL = "/cart.html";

    @FindBy(className = "subheader")
    private WebElement pageHeader;

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

    @FindBy(css = ".item_pricebar>.inventory_item_price")
    private List<WebElement> cartItemPricesList;

    @FindBy(className = "inventory_item_price")
    private WebElement itemPrice;

    @FindBy(css = ".btn_secondary.cart_button")
    private List<WebElement> removeFromCartButtonsList;

    @FindBy(css = ".cart_footer>.btn_secondary")
    private WebElement continueShoppingButton;

    @FindBy(css = ".btn_action.checkout_button")
    private WebElement checkoutButton;

    public ShoppingCartPage(WebDriver driver, String base_url) {
        super(driver, base_url);
    }

    @Override
    protected void load() {
        System.out.println("Attempting to load Shopping Cart page...");
        driver.get(BASE_URL + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        if(!isPageLoaded())
            throw new Error("Shopping cart page was not loaded!");
    }

    @Override
    public boolean isPageLoaded()
    {
        return isElementVisible(pageHeader);
    }

    public String verifyEmptyCartUIElements() {

        String errorMessages = "";

        if (isCartEmpty()) {
            try {
                errorMessages += assesElementTextEquals(cartQuantityLabel, ShoppingCartPageConstants.CART_QUANTITY_LABEL);
                errorMessages += assesElementTextEquals(cartDescLabel, ShoppingCartPageConstants.CART_DESC_LABEL);
                errorMessages += assesElementTextEquals(continueShoppingButton, ShoppingCartPageConstants.CONTINUE_SHOPPING_BUTTON_TXT);
                errorMessages += assesElementTextEquals(checkoutButton, ShoppingCartPageConstants.CHECKOUT_BUTTON_TXT);
            } catch (Exception e) {
                errorMessages = e.getStackTrace().toString();
            }
        } else {
            errorMessages = "Cart is not empty!";
        }

        return errorMessages;
    }

    public String verifyProductCartUIElements(int qty, String imageUrl, String name, String description, String price) {

        String errorMessages = "";

        if (!isCartEmpty()) {
            try {
                errorMessages += assesElementTextEquals(cartQuantityLabel, ShoppingCartPageConstants.CART_QUANTITY_LABEL);
                errorMessages += assesElementTextEquals(cartDescLabel, ShoppingCartPageConstants.CART_DESC_LABEL);
                errorMessages += assesElementTextEquals(continueShoppingButton, ShoppingCartPageConstants.CONTINUE_SHOPPING_BUTTON_TXT);
                errorMessages += assesElementTextEquals(checkoutButton, ShoppingCartPageConstants.CHECKOUT_BUTTON_TXT);

                errorMessages += assesElementTextEquals(cartQuantityList.get(0), String.valueOf(qty));
                errorMessages += assesElementTextContains(cartItemLinkList.get(0).getAttribute("href"), imageUrl);
                errorMessages += assesElementTextEquals(cartItemNameList.get(0), name);
                errorMessages += assesElementTextEquals(cartItemDescList.get(0), description);
                errorMessages += assesElementTextEquals(cartItemPricesList.get(0), price);

                errorMessages += assesElementTextEquals(removeFromCartButtonsList.get(0), ShoppingCartPageConstants.REMOVE_BUTTON_TXT);

            } catch (Exception e) {
                errorMessages = e.getStackTrace().toString();
            }
        } else {
            errorMessages = "Cart is empty!";
        }

        return errorMessages;
    }

    public boolean removeFromCartById(String id) {

        if (isCartEmpty()) {
            throw new IndexOutOfBoundsException("There are no products added to cart");
        }

        int i = 0;
        for (WebElement e : cartItemLinkList) {
            int currentQty = cartItemLinkList.size();
            if (e.getAttribute("href").contains(id)) {
                removeFromCartButtonsList.get(i).click();
                return (removeFromCartButtonsList.size() == getCartItems()
                    && cartItemLinkList.size() == --currentQty);
            }
            i++;
        }

        return false;
    }

    public boolean removeAllFromCart() {
        // As mentioned in other section, the method say that it remove all items from the cart, I would say that
        // the validations should not be here in the responsability of this method, it would be better to have it
        // in other methods and then call them from the test case and do the validation in the test case.
        if (isCartEmpty()) {
            throw new IndexOutOfBoundsException("There are no products added to cart");
        }

        for (WebElement e : removeFromCartButtonsList) {
            e.click();
        }

        return (removeFromCartButtonsList.size() == getCartItems()
            && cartItemsList.size() == 0);
    }

    public boolean isCartEmpty() {
        try {
            return cartItemsList.size() == 0;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public ProductsInventoryPage clickContinueShoppingButton() {
        if (isElementVisible(continueShoppingButton)) {
            continueShoppingButton.click();
            return new ProductsInventoryPage(driver, BASE_URL);
        }
        return null;
    }

    public CheckoutInformationPage clickCheckoutButton() {
        if (isElementVisible(checkoutButton)) {
            checkoutButton.click();
            return new CheckoutInformationPage(driver, BASE_URL);
        }
        return null;
    }
}
