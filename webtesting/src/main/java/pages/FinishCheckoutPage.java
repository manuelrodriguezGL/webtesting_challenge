package pages;

import org.openqa.selenium.WebDriver;

public class FinishCheckoutPage extends BaseProductPage {
    public FinishCheckoutPage(WebDriver driver) {
        super(driver);
        super.initElements(driver, this);
    }
}
