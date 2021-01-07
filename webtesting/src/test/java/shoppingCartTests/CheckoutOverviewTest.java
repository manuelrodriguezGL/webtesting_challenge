package shoppingCartTests;

import Constants.GlobalTestConstants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CheckoutInformationPage;
import pages.CheckoutOverviewPage;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutOverviewTest extends TestCaseBase {

    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutInformationPage checkoutInformationPage;
    private ProductsInventoryPage inventory;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword", "item4ID", "customerFirstName", "customerLastName", "customerZipCode"})
    public void loadShoppingCartPage(String user, String pwd, String item4ID, String fName, String lName, String zip) {
        try {
            inventory = login(user, pwd);
            inventory.addToCartById(item4ID);
            checkoutInformationPage = inventory.loadShoppingCart().clickCheckoutButton();
            checkoutInformationPage.enterCustomerData(fName, lName, zip);
            checkoutOverviewPage = checkoutInformationPage.clickContinue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "Verify the UI elements for checkout overview page",
            groups = {"checkoutOverview"})
    @Parameters({"qtyOne", "item4URL", "item4Name", "item4Desc", "item4Price"})
    public void verifyUIElements(String qtyOne, String itemURL, String name, String description, String price) {
        String errorMessages = "";
        try {
            errorMessages = checkoutOverviewPage.verifyUIElements(Integer.valueOf(qtyOne), itemURL, name, description, price);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(errorMessages.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Checkout Overview page has wrong elements on UI or is not loaded!");
    }

}
