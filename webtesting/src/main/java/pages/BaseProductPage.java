package pages;

import org.openqa.selenium.WebDriver;

public class BaseProductPage extends BasePage {

    HeaderContainer headerContainer;

    static final String ADD_TO_CART_TXT = "ADD TO CART";
    static final String REMOVE_FROM_CART_TXT = "REMOVE";

    public BaseProductPage(WebDriver driver) {
        super(driver);
        headerContainer = new HeaderContainer(driver);
    }
}
