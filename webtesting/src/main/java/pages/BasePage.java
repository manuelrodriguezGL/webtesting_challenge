package pages;

import constants.GlobalPageConstants;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Function;

public class BasePage extends LoadableComponent {

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

    protected boolean isElementVisible(WebElement e) throws NoSuchElementException {
        WebDriverWait wait = new WebDriverWait(driver, GlobalPageConstants.GLOBAL_TIMEOUT);
        return wait.until(driver -> e.isDisplayed());
    }

    protected boolean isTextEquals(WebElement e, String text) {
        return e.getAttribute("innerText").equals(text);
    }

    protected String assesElementText(WebElement e, String text) {
        String errorMessage = "";

        try {
            if (!isTextEquals(e, text))
                errorMessage = "Element text is different from compared value! " +
                        "| Element value: " + e.getAttribute("innerText") + " | Compared value: " + text + "\n";
        } catch (Exception ex) {
            errorMessage = Arrays.toString(ex.getStackTrace()) + "\n";
        }

        return errorMessage;
    }

    protected String assesUIElement(WebElement element) {

        String errorMessage = "";
        try {
            isElementVisible(element);
        } catch (Exception e) {
            errorMessage = Arrays.toString(e.getStackTrace()) + "\n";
        }

        return errorMessage;
    }

    @Deprecated
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
}
