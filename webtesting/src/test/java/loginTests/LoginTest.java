package loginTests;

import Constants.GlobalTestConstants;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends TestCaseBase {

    @Test(description = "Verify items on login page",
            groups = {"UI"})
    @Parameters({"loginPageTitle"})
    public void verifyLoginUI(String loginPageTitle) {

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(getLoginPage().getPageTitle(), loginPageTitle);
        softAssert.assertTrue(getLoginPage().isPageLoaded());

        softAssert.assertAll("Login page has some missing elements!");
    }

    @Test(description = "Verify the error message for wrong credentials",
            groups = {"UI"})
    @Parameters({"invalidUser", "invalidPassword"})
    public void verifyInvalidLoginErrorMessage(String invalidUser, String invalidPassword) {
        LoginPage login = getLoginPage();
        assertTrue(login.checkInvalidCredsErrorMessage(invalidUser, invalidPassword),
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                        "Incorrect error message for invalid credentials");
    }

    @Test(description = "Verify the error message for empty username",
            groups = {"UI"})
    @Parameters({"invalidPassword"})
    public void verifyEmptyUserNameErrorMessage(String invalidPassword) {
        LoginPage login = getLoginPage();
        assertTrue(login.checkEmptyUserErrorMessage(invalidPassword),
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                        "Incorrect error message for empty user name");
    }

    @Test(description = "Verify the error message for empty password",
            groups = {"UI"})
    @Parameters({"invalidUser"})
    public void verifyEmptyPasswordErrorMessage(String invalidUser) {
        LoginPage login = getLoginPage();
        assertTrue(login.checkEmptyPwdErrorMessage(invalidUser),
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                        "Incorrect error message for empty password");
    }

    @Test(description = "Perform a valid login action",
            groups = {"login"})
    @Parameters({"validUser", "validPassword"})
    public void validLogin(String user, String pwd) {
        ProductsInventoryPage productsInventoryPage = getLoginPage().login(user, pwd);
        assertTrue(productsInventoryPage.isPageLoaded());
    }

    @Test(description = "Perform a logout action",
            groups = {"login"})
    @Parameters({"validUser", "validPassword"})
    public void logout(String user, String pwd) {
        ProductsInventoryPage productsInventoryPage = getLoginPage().login(user, pwd);
        assertTrue(productsInventoryPage.logout().isPageLoaded());
    }
}
