package pages;

import botStyle.BotStyle;
import constants.GlobalPageConstants;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage extends LoadableComponent {

    protected String base_url = "";

    protected WebDriver driver;
    protected BotStyle botStyle;

    public BasePage(WebDriver driver, String baseUrl) {
        this.driver = driver;
        base_url = baseUrl;
        PageFactory.initElements(driver, this);
        this.botStyle = new BotStyle(driver);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    @Override
    protected abstract void load();

    @Override
    protected abstract void isLoaded() throws Error;

    protected abstract boolean isPageLoaded();

    protected boolean isElementVisible(WebElement e) throws NoSuchElementException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, GlobalPageConstants.GLOBAL_TIMEOUT);
            return wait.until(driver -> e.isDisplayed());
        } catch (Exception ex) {
            return false;
        }
    }
}
