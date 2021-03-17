package testBase;

import listeners.TestExecutionListener;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.ProductsInventoryPage;
import selenium.SeleniumBase;

@Listeners(TestExecutionListener.class)
public class TestCaseBase extends SeleniumBase {
    private static LoginPage loginPage = null;
    protected SoftAssert softAssert;
    protected String baseUrl = "";

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browserName", "headless", "baseUrl"})
    public void setUp(String browserName, String headless, String _baseUrl) {
        System.out.println("Setting up Selenium driver for browser: " + browserName);
        super.setup(browserName, Boolean.valueOf(headless));
        System.out.println("Loading login page...");

        softAssert = new SoftAssert();

        baseUrl = _baseUrl;
        loginPage = new LoginPage(getWebDriverInstance(), baseUrl);
        loginPage.get();
    }

    @AfterMethod(alwaysRun = true)
    public void quitBrowser() {
        System.out.println("Quitting driver...");
        super.quit();
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(getWebDriverInstance(), baseUrl);
        }
        return loginPage;
    }

    public ProductsInventoryPage login(String user, String pwd){
        return getLoginPage().login(user, pwd);
    }
}
