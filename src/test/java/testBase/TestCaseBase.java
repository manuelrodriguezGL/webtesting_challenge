package testBase;

import listeners.TestExecutionListener;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import pages.LoginPage;
import pages.ProductsInventoryPage;
import selenium.SeleniumBase;

@Listeners(TestExecutionListener.class)
public class TestCaseBase extends SeleniumBase {
    public static final String GLOBAL_TEST_FAILED_MESSAGE = "Test execution failed! Message: \n";

    private static LoginPage loginPage = null;
    protected String baseUrl = "";

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "headlessMode", "baseUrl"})
    public void setUp(String browserName, String headless, String _baseUrl, ITestContext context) {

        WebDriver driver = super.setup(browserName, Boolean.valueOf(headless));

        // Setting the webdriver attribute to the test context
        // This way, we avoid using static definitions of the web driver
        setTestContext(context, driver);

        baseUrl = _baseUrl;
        loginPage = getLoginPage();
        loginPage.get();
    }

    @AfterMethod(alwaysRun = true)
    public void quitBrowser() {
        super.quit();
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(getWebDriverInstance(), baseUrl);
        }
        return loginPage;
    }

    public ProductsInventoryPage login(String user, String pwd) {
        return getLoginPage().login(user, pwd);
    }

    private void setTestContext(ITestContext context, Object object) {
        context.setAttribute("WebDriver", object);
    }

}
