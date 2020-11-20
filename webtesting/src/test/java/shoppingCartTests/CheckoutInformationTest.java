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
            groups = {"excluded"})
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

    @Test(description = "Verify the error messages on all input fields for checkout information page",
            groups = {"excluded"})
    @Parameters({"customerFirstName", "customerLastName", "customerZipCode"})
    public void verifyErrorMessages(String firstName, String lastName, String zipCode) {
        String errorMessages = "";
        try {
            errorMessages = checkoutPage.verifyErrorMessages(firstName, lastName, zipCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(errorMessages.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Checkout information error messages are not displayed or they have changed!");
    }

    @Test(description = "Verify that user can click Cancel button and is taken back to cart page",
            groups = {"excluded"})
    public void verifyClickCancelButton()
    {
        boolean result = checkoutPage.clickCancel().isPageLoaded();

        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Could not click cancel button, or Cart page was not loaded!");
    }


    @Test(description = "Verify that user can click Continue button and is taken to order overview page",
            groups = {"checkoutInformation"})
    public void verifyClickContinueButton()
    {
        boolean result = checkoutPage.clickContinue().isPageLoaded();

        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Could not click continue button, or Overview page was not loaded!");
    }
}
