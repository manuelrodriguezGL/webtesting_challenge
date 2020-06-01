package inventoryTests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import testBase.TestCaseBase;

public class InventoryTest extends TestCaseBase {

    @BeforeClass
    @Parameters({"validUser", "validPassword"})
    public void loadInventoryPage(String user, String pwd)
    {
        getLoginPage().login(user, pwd);
    }

    //@Test
}
