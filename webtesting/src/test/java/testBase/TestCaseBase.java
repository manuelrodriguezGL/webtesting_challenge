package testBase;

import listeners.ScreenshotListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import pages.LoginPage;
import selenium.SeleniumBase;

@Listeners(ScreenshotListener.class)
public class TestCaseBase extends SeleniumBase {
    private static LoginPage loginPage = null;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browserName", "headless"})
    public void setUp(String browserName, String headless) {
        System.out.println("Setting up Selenium driver for browser: " + browserName);
        super.setup(browserName, Boolean.valueOf(headless));
        System.out.println("Loading login page...");
        loginPage = new LoginPage(getWebDriverInstance());
    }

    @AfterMethod(alwaysRun = true)
    public void quitBrowser() {
        System.out.println("Quitting driver...");
        super.quit();
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(getWebDriverInstance());
        }
        return loginPage;
    }
}
