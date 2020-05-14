package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.time.Duration;
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


}
