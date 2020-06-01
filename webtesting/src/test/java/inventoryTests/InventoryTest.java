package inventoryTests;

import Constants.GlobalTestConstants;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import testBase.TestCaseBase;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryTest extends TestCaseBase {

    @BeforeClass
    @Parameters({"validUser", "validPassword"})
    public void loadInventoryPage(String user, String pwd)
    {
        getLoginPage().login(user, pwd);
    }

    @Test(description = "Verify items on inventory page",
            groups = {"UI"})
    public void verifyInventoryUI() {
        String errorMessages = getLoginPage().assesPageElements();
        assertTrue(errorMessages.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE + errorMessages);
    }
}
