package pages;

import org.openqa.selenium.WebDriver;

public class BaseProductPage extends BasePage {

    //TODO Move common locators here
    HeaderContainer headerContainer;

    public BaseProductPage(WebDriver driver) {
        super(driver);
        headerContainer = new HeaderContainer(driver);
    }

    @Override
    protected void load(){}
    @Override
    protected void isLoaded(){}
}
