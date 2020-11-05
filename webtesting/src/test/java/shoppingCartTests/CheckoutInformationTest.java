package shoppingCartTests;

import Constants.GlobalTestConstants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CheckoutInformationPage;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutInformationTest extends TestCaseBase {

    private CheckoutInformationPage checkoutPage;
    private ProductsInventoryPage inventory;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword", "item4ID"})
    public void loadShoppingCartPage(String user, String pwd, String item4ID) {
        inventory = login(user, pwd);
        inventory.addToCartById(item4ID);
        checkoutPage = inventory.loadShoppingCart().clickCheckoutButton();
    }

    @Test(description = "Verify the UI elements for checkout information page",
            groups = {"checkoutInformation"})
    public void verifyUIElements() {
        String errorMessages = "";
        try {
            errorMessages = checkoutPage.verifyUIElements();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(errorMessages.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Checkout Information page has wrong elements on UI or is not loaded!");
    }
}
