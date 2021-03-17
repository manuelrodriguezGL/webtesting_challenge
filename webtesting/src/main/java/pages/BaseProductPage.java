package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BaseProductPage extends BasePage {

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

//<<<<<<< feedback_oscar_valerio
    // Consider using dependency injection, or also this BaseProductPage class can inheritance from HeaderContainer
    // since HeaderContainer extends from BasePage, in that way you can access HeaderContainer and Base page by inheritance
//=======
    //TODO Move common locators here

    HeaderContainer headerContainer;

    public BaseProductPage(WebDriver driver) {
        super(driver);
    }

    public BaseProductPage(WebDriver driver, String baseUrl) {
        super(driver);
    }

    @Override
    protected void load(){}
    @Override
    protected void isLoaded(){}

    public int getCartItems() {
        try {
            return (Integer.parseInt(cartItemsIcon.getAttribute("innerText")));
        } catch (NoSuchElementException e) {
            return 0;
        }
    }
}
