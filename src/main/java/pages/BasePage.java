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

    protected CommonUtils commonUtils;
    protected EventLogger logger;
    protected String baseUrl = "";
    protected WebDriver driver;
    protected BotStyle botStyle;
    private long timeout;

    // Just a security measure, so no one calls this constructor
    private BasePage() {
    }

    public BasePage(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        PageFactory.initElements(driver, this);
        this.botStyle = new BotStyle(driver);

        logger = new EventLogger();
        commonUtils = new CommonUtils();
        initializeTimeOut();
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

    /**
     * Time out variable initialization
     * To take some of code churn from constructor
     */
    private void initializeTimeOut() {
        try {
            timeout = Long.parseLong(commonUtils.getPropertyValue("global_timeout"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
