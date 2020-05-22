package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ShoppingCartPage extends BaseProductPage {

    @FindBy(className = "cart_quantity")
    private WebElement cartQuantity;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItemsList;

    @FindBy(className = "cart_item_label")
    private WebElement inventoryItemLink;

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
}
