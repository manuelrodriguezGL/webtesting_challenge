package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class BotStyle {

    private static WebDriver driver;

    public BotStyle(WebDriver driver) {
        this.driver = driver;
    }


}
