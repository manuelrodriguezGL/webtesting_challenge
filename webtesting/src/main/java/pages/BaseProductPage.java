package pages;

import org.openqa.selenium.WebDriver;

public class BaseProductPage extends BasePage {

    protected HeaderContainer headerContainer;

    public BaseProductPage(WebDriver driver) {
        super(driver);
        headerContainer = new HeaderContainer(driver);
    }
}
