package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BaseStorePage extends BasePage {

    @FindBy(className = "header_label")
    private WebElement headerLabel;

    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartLink;

    @FindBy(css = ".bm-item-list>a")
    private WebElement menuItems;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(css = "[data-icon=\"shopping-cart\"]")
    private WebElement shoppingCartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartItemsIcon;

    public BaseStorePage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    @Override
    protected void load() {
    }

    @Override
    protected void isLoaded() {
    }

    public int getCartItems() {
        try {
            return (Integer.parseInt(cartItemsIcon.getAttribute("innerText")));
        } catch (NoSuchElementException e) {
            return 0;
        }
    }
}
