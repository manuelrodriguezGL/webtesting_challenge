package pages;

import org.openqa.selenium.WebDriver;

public class ShoppingCartPage extends BaseProductPage {
    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        super.initElements(driver, this);
    }
}
