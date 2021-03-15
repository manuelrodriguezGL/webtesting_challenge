package pages;

import org.openqa.selenium.WebDriver;

public class BaseProductPage extends BasePage {

//<<<<<<< feedback_oscar_valerio
    // Consider using dependency injection, or also this BaseProductPage class can inheritance from HeaderContainer
    // since HeaderContainer extends from BasePage, in that way you can access HeaderContainer and Base page by inheritance
//=======
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
