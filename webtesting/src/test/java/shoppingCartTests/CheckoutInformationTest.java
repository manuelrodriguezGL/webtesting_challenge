package shoppingCartTests;

import Constants.GlobalTestConstants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
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
    @Parameters({"firstNamePlaceholder", "lastNamePlaceholder", "zipPlaceHolder", "cancelButtonText", "continueButtonText"})
    public void verifyUIElements(String firstNamePlaceholder, String lastNamePlaceholder, String zipPlaceHolder,
                                 String cancelButtonText, String continueButtonText) {

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(checkoutPage.getFirstNamePlaceholder(), firstNamePlaceholder);
        softAssert.assertEquals(checkoutPage.getLastNamePlaceholder(), lastNamePlaceholder);
        softAssert.assertEquals(checkoutPage.getZipCodePlaceholder(), zipPlaceHolder);
        softAssert.assertEquals(checkoutPage.getCancelButtonText(), cancelButtonText);
        softAssert.assertEquals(checkoutPage.getContinueButtonText(), continueButtonText);

        softAssert.assertAll("Checkout Information page has wrong elements on UI or is not loaded!");

    }

    @Test(description = "Verify the error messages on all input fields for checkout information page",
            groups = {"debug"})
    @Parameters({"customerFirstName", "customerLastName", "customerZipCode",
            "firstNameError", "lastNameError", "zipCodeError"})
    public void verifyErrorMessages(String firstName, String lastName, String zipCode,
                                    String firstNameError, String lastNameError, String zipCodeError) {

        SoftAssert softAssert = new SoftAssert();

        checkoutPage.enterCustomerData("", lastName, zipCode);
        checkoutPage.clickContinue();
        checkoutPage.clearCustomerData();
        softAssert.assertEquals(checkoutPage.getErrorMessage(), firstNameError);

        checkoutPage.enterCustomerData(firstName, "", zipCode);
        checkoutPage.clickContinue();
        checkoutPage.clearCustomerData();
        softAssert.assertEquals(checkoutPage.getErrorMessage(), lastNameError);

        checkoutPage.enterCustomerData(firstName, lastName, "");
        checkoutPage.clickContinue();
        checkoutPage.clearCustomerData();
        softAssert.assertEquals(checkoutPage.getErrorMessage(), zipCodeError);

        softAssert.assertAll("Checkout information error messages are not displayed or they have changed!");
    }

    @Test(description = "Verify that user can click Cancel button and is taken back to cart page",
            groups = {"checkoutInformation"})
    public void verifyClickCancelButton() {
        boolean result = checkoutPage.clickCancel().isPageLoaded();

        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Could not click cancel button, or Cart page was not loaded!");
    }


    @Test(description = "Verify that user can click Continue button and is taken to order overview page",
            groups = {"checkoutInformation"})
    public void verifyClickContinueButton() {
        boolean result = checkoutPage.clickContinue().isPageLoaded();

        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Could not click continue button, or Overview page was not loaded!");
    }
}
