package pages;

import botStyle.BotStyle;
import logger.EventLogger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CommonUtils;

import java.io.IOException;

public abstract class BasePage extends LoadableComponent {

    private static long timeout;

    /**
     * Static initialization of the timeout variable
     */
    static {
        try {
            timeout = Long.parseLong(CommonUtils.getPropertyValue("global_timeout"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected EventLogger logger;
    protected String baseUrl = "";

    protected WebDriver driver;
    protected BotStyle botStyle;

    public BasePage(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        PageFactory.initElements(driver, this);
        this.botStyle = new BotStyle(driver);

        logger = new EventLogger();
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
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            return wait.until(driver -> e.isDisplayed());
        } catch (Exception ex) {
            return false;
        }
    }
}
