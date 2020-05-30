package loginTests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import testBase.TestCaseBase;

public class LoginTest extends TestCaseBase {

    @Test(description = "Perform a valid login action",
            groups = {"login"})
    @Parameters({"validUser", "validPassword"})
    public void validLogin(String user, String pwd) {
        
    }
}
