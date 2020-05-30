package loginTests;

import Constants.GlobalTestConstants;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import testBase.TestCaseBase;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends TestCaseBase {

    @Test(description = "Verify items on login page",
            groups = {"UI"})
    @Parameters({"validUser", "validPassword"})
    public void verifyLoginUI() {
        String errorMessages = getLoginPage().assesPageElements();
        assertTrue(errorMessages.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE + errorMessages);
    }

    @Test(description = "Perform a valid login action",
            groups = {"login"})
    @Parameters({"validUser", "validPassword"})
    public void validLogin(String user, String pwd) {
        getLoginPage().login(user, pwd);
    }
}
