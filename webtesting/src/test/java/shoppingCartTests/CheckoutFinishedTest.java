package shoppingCartTests;

import Constants.GlobalTestConstants;
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
}
