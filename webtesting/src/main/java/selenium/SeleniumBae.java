package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.Console;

public class SeleniumBae {

    private WebDriver driver;

    //TODO: Implement remaining multi-browser logic
    public void setup(String browser, boolean headless){

        if(browser == null || browser.isEmpty())
            throw new NullPointerException("Browser name can't be empty or null");

        switch (browser)
        {
            case "Chrome" :
                driver = new ChromeDriver();
                break;
            case "Firefox":
                driver = new FirefoxDriver();
                break;
            case "Safari":
                driver =  new SafariDriver();
                break;
            default:
                System.out.println("No browser has been provided!");
        }
    }
}
