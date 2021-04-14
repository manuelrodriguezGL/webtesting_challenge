package shoppingCartTests;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CheckoutFinishedPage;
import pages.CheckoutInformationPage;
import pages.CheckoutOverviewPage;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;

public class CheckoutFinishedTest extends TestCaseBase {

    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutInformationPage checkoutInformationPage;
    private ProductsInventoryPage inventory;
    private CheckoutFinishedPage checkoutFinishedPage;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword", "customerFirstName", "customerLastName", "customerZipCode"})
    public void loadCheckoutFinishedPage(String user, String pwd, String firstName, String lastName, String zipCode) {
        try {
            inventory = login(user, pwd);
            inventory.addToCartByQuantity(6);
            checkoutInformationPage = inventory.loadShoppingCart().clickCheckoutButton();
            checkoutInformationPage.enterCustomerData(firstName, lastName, zipCode);
            checkoutOverviewPage = checkoutInformationPage.clickContinue();
            checkoutFinishedPage = checkoutOverviewPage.finishCheckout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "Verify the UI elements for checkout finished page",
            groups = {"checkoutFinished"})
    @Parameters({"pageHeaderText", "orderCompleteText", "backHomeButtonText"})
    public void verifyCheckoutOverviewUIElements(String pageHeaderText, String orderCompleteText,
                                                 String backHomeButtonText) {
        try {
            SoftAssert softAssert = new SoftAssert();

            softAssert.assertEquals(checkoutFinishedPage.getPageHeaderText(), pageHeaderText);
            softAssert.assertEquals(checkoutFinishedPage.getOrderCompleteText(), orderCompleteText);
            softAssert.assertEquals(checkoutFinishedPage.getBackHomeButtonText(), backHomeButtonText);

            softAssert.assertAll("Checkout Finished page has wrong elements on UI, or is not loaded!");
        } catch (NoSuchElementException e) {
            Assert.fail("Elements not found on page!", e);
        } catch (Exception e) {
            Assert.fail("Could not run test!", e);
        }
    }

    @Test(description = "Verify that cart icon has no numbers after checkout is finished",
            groups = {"checkoutFinished"})
    public void verifyCartIconIsCleared() {
        try {
            Assert.assertTrue(checkoutFinishedPage.getCartItemsQuantity() == 0,
                    "Cart icon was not updated after checkout finished!");
        } catch (NoSuchElementException e) {
            Assert.fail("Cart icon element not found", e);
        } catch (Exception e) {
            Assert.fail("Could not run test!", e);
        }
    }

    @Test(description = "Verify that clicking on BACK HOME button takes user to inventory page",
            groups = {"checkoutFinished"})
    public void verifyBackHomeButton() {
        try {
            Assert.assertNotNull(checkoutFinishedPage.clickBackHomeButton(), "Could not load Inventory Page!");
        } catch (Exception e) {
            Assert.fail("Could not run test!", e);
        }
    }
}
