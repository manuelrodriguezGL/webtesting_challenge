package productTests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import testBase.TestCaseBase;

public class ProductTest extends TestCaseBase {
    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword"})
    public void loadInventoryPage(String user, String pwd) {
        getLoginPage().login(user, pwd);
    }

    
}
