package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumBase {

    private static WebDriver driver;

    /**
     * Method to initialize the web driver
     * @param browser String representing the name of the browser
     * @param headless Boolean value to decide whether to run headless or not
     */
    //TODO: Implement remaining multi-browser logic
    public WebDriver setup(String browser, boolean headless) {

        if (browser == null || browser.isEmpty())
            throw new NullPointerException("Browser name can't be empty or null");

        switch (browser) {
            case "Chrome":
                chromeSetup(headless);
                break;
            case "Firefox":
                driver = new FirefoxDriver();
                break;
            case "Safari":
                driver = new SafariDriver();
                break;
            default:
                System.out.println("No browser has been provided!");
        }

        return driver;
    }

    private void chromeSetup(boolean headless) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(headless);
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
    }

    /**
     * Disposes the session and quits the driver
     */
    public void quit(){
        driver.quit();
    }

    public static WebDriver getWebDriverInstance()
    {
        return driver;
    }
}
