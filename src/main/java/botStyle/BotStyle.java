package botStyle;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CommonUtils;

import java.io.IOException;

public class BotStyle {
    private final CommonUtils commonUtils;
    private long timeout;
    private WebDriver driver;

    public BotStyle(WebDriver driver) {
        this.driver = driver;
        commonUtils = new CommonUtils();

        try {
            timeout = Long.parseLong(commonUtils.getPropertyValue("global_timeout"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void type(WebElement element, String text) throws NoSuchElementException {
        click(element);
        clearTextField(element);
        element.sendKeys(text);
    }

    public void clearTextField(WebElement element) throws NoSuchElementException {
        element.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        element.sendKeys(Keys.BACK_SPACE);
    }

    public void click(WebElement element) throws NoSuchElementException {
        waitByWebElement(element).click();
    }

    public void click(By locator) throws NoSuchElementException {
        waitByLocator(locator).click();
    }

    //// Waits ////

    /**
     * Explicit wait, that expects for a Web Element to appear on page body
     *
     * @param element Web Element representing page element
     * @return The WebElement found after wait
     */
    public WebElement waitByWebElement(WebElement element) throws NoSuchElementException {
        return (new WebDriverWait(this.driver, timeout))
                .until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Explicit wait, that expects for an element to appear on page body
     *
     * @param locator By locator taken from page
     * @return A WebElement found using By locator
     */
    public WebElement waitByLocator(By locator) throws NoSuchElementException {
        return (new WebDriverWait(this.driver, timeout))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    /////
}
