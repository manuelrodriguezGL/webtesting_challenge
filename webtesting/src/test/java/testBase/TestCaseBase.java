package testBase;

import listeners.TestExecutionListener;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductsInventoryPage;
import selenium.SeleniumBase;

@Listeners(TestExecutionListener.class)
public class TestCaseBase extends SeleniumBase {
    private static LoginPage loginPage = null;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browserName", "headless"})
    public void setUp(String browserName, String headless) {
        System.out.println("Setting up Selenium driver for browser: " + browserName);
        super.setup(browserName, Boolean.valueOf(headless));
        System.out.println("Loading login page...");
        loginPage = new LoginPage(getWebDriverInstance());
        loginPage.get();
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

    public ProductsInventoryPage login(String user, String pwd){
        return getLoginPage().login(user, pwd);
    }
}
