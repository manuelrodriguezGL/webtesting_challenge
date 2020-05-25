package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartPage extends BaseProductPage {

    private static final String URL = "/cart.html";

    @FindBy(className = "cart_quantity")
    private WebElement cartQuantity;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItemsList;

    @FindBy(css = ".cart_item_label>a")
    private List<WebElement> inventoryItemLinkList;

    @FindBy(css = ".item_pricebar>div")
    private List<WebElement> itemPricesList;

    @FindBy(className = "inventory_item_price")
    private WebElement itemPrice;

    @FindBy(css = ".btn_secondary.cart_button")
    private List<WebElement> removeFromCartButtonsList;

    @FindBy(css = ".cart_footer>.btn_secondary")
    private WebElement continueShoppingButton;

    @FindBy(css = ".btn_action.checkout_button")
    private WebElement checkoutButton;

    public ShoppingCartPage(WebDriver driver) {
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

    public boolean removeFromCartById(int id) {

        if (removeFromCartButtonsList.size() == 0) {
            throw new IndexOutOfBoundsException("There are no products added to cart");
        }

        String numberToFind = String.valueOf(id);
        int i = 0;
        for (WebElement e : inventoryItemLinkList) {
            if (e.getAttribute("href").contains(numberToFind)) {
                removeFromCartButtonsList.get(i).click();
                return (removeFromCartButtonsList.size() == headerContainer.getCartItems());
            }
            i++;
        }

        return false;
    }

    public boolean removeAllFromCart() {

        if (removeFromCartButtonsList.size() == 0) {
            throw new IndexOutOfBoundsException("There are no products added to cart");
        }

        for (WebElement e : removeFromCartButtonsList) {
            e.click();
        }

        return (removeFromCartButtonsList.size() == headerContainer.getCartItems());
    }

    public ProductsInventoryPage clickContinueShoppingButton() {
        return (isElementVisible(continueShoppingButton)) ? new ProductsInventoryPage(driver) : null;
    }

    public CheckoutInformation clickCheckoutButton() {
        return (isElementVisible(checkoutButton)) ? new CheckoutInformation(driver) : null;
    }

}
