package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class SeleniumBae {

    private WebDriver driver;

    /**
     * Method to initialize the web driver
     * @param browser String representing the name of the browser
     * @param headless Boolean value to decide whether to run headless or not
     */
    //TODO: Implement remaining multi-browser logic
    public void setup(String browser, boolean headless) {

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
    }

    private void chromeSetup(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(headless);
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
    }

    /**
     * Disposes the session and quits the driver
     */
    private void quit(){
        driver.quit();
    }
}
