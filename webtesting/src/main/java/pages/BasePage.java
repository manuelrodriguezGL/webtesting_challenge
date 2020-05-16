package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class BasePage extends LoadableComponent {

    private static final long GLOBAL_TIMEOUT = 15;

    protected static final String BASE_URL = "https://www.saucedemo.com";
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected void initElements(WebDriver driver, Object page) {
        PageFactory.initElements(driver, page);
    }

    @Override
    protected void load() {
    }

    @Override
    protected void isLoaded() throws Error {
    }

    protected WebElement waitElement(WebElement e) {
        FluentWait wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        WebElement el = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return e.isDisplayed() ? e : null;
            }
        });
        return el;
    }

    protected boolean isElementVisible(WebElement e) throws NoSuchElementException {
        WebDriverWait wait = new WebDriverWait(driver, GLOBAL_TIMEOUT);
        return wait.until(driver -> e.isDisplayed());
    }
}
