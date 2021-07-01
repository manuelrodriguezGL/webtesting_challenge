package loginTests;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;

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
        Assert.assertTrue(login.checkInvalidCredsErrorMessage(invalidUser, invalidPassword),
                GLOBAL_TEST_FAILED_MESSAGE +
                        "Incorrect error message for invalid credentials");
    }

    @Test(description = "Verify the error message for empty username",
            groups = {"UI"})
    @Parameters({"invalidPassword"})
    public void verifyEmptyUserNameErrorMessage(String invalidPassword) {
        LoginPage login = getLoginPage();
        Assert.assertTrue(login.checkEmptyUserErrorMessage(invalidPassword),
                GLOBAL_TEST_FAILED_MESSAGE +
                        "Incorrect error message for empty user name");
    }

    @Test(description = "Verify the error message for empty password",
            groups = {"UI"})
    @Parameters({"invalidUser"})
    public void verifyEmptyPasswordErrorMessage(String invalidUser) {
        LoginPage login = getLoginPage();
        Assert.assertTrue(login.checkEmptyPwdErrorMessage(invalidUser),
                GLOBAL_TEST_FAILED_MESSAGE +
                        "Incorrect error message for empty password");
    }

    @Test(description = "Perform a valid login action",
            groups = {"debug"})
    @Parameters({"sauce_user", "sauce_psw"})
    public void validLogin(String user, String pwd) {
        ProductsInventoryPage productsInventoryPage = getLoginPage().login(user, pwd + "!");
        Assert.assertTrue(productsInventoryPage.isPageLoaded());
    }

    @Test(description = "Perform a logout action",
            groups = {"login"})
    @Parameters({"sauce_user", "sauce_psw"})
    public void logout(String user, String pwd) {
        ProductsInventoryPage productsInventoryPage = getLoginPage().login(user, pwd);
        Assert.assertTrue(productsInventoryPage.logout().isPageLoaded());
    }
}
