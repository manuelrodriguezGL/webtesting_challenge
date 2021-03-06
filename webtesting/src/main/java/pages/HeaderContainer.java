package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderContainer extends BasePage {

    @FindBy(className = "header_label")
    private WebElement headerLabel;

    @FindBy(css = ".shopping_cart_container>a")
    private WebElement shoppingCartLink;

    @FindBy(css = ".bm-item-list>a")
    private WebElement menuItems;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(css = "[data-icon=\"shopping-cart\"]")
    private WebElement shoppingCartIcon;

    @FindBy(css = ".fa-layers-counter.shopping_cart_badge")
    private WebElement cartItemsIcon;

    public HeaderContainer(WebDriver driver) {
        super(driver);
        super.initElements(driver, this);
    }

    public ShoppingCartPage clickShoppingCartLink() {
        if (isElementVisible(shoppingCartLink)) {
            shoppingCartLink.click();
            return new ShoppingCartPage(driver);
        }
        return null;
    }

    public int getCartItems() throws NoSuchElementException {
        try {
            return (Integer.parseInt(cartItemsIcon.getAttribute("innerText")));
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    public LoginPage logout() throws NoSuchElementException {
        if (isElementVisible(logoutLink)) {
            logoutLink.click();
            return new LoginPage(driver);
        }
        return null;
    }

}
