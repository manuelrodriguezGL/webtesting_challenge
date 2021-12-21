package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.logging.Logger;

public abstract class BaseStorePage extends BasePage {

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

    protected Logger logger;

    public BaseStorePage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
        logger = Logger.getGlobal();
    }

    @Override
    protected abstract void load();

    @Override
    protected abstract void isLoaded();

    @Override
    protected abstract boolean isPageLoaded();

    public int getCartItemsQuantity() {
        try {
            return (Integer.parseInt(cartItemsIcon.getAttribute("innerText")));
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    private void configureLogger() {

    }
}
