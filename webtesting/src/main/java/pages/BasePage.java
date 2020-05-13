package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import selenium.BotStyle;

public class BasePage extends LoadableComponent {

    protected WebDriver driver;
    protected BotStyle botDriver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.botDriver = new BotStyle(driver);
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
