package shoppingCartTests;

import Constants.GlobalTestConstants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
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
            inventory.addToCartByQuantity(6);
            checkoutInformationPage = inventory.loadShoppingCart().clickCheckoutButton();
            checkoutInformationPage.enterCustomerData(fName, lName, zip);
            checkoutOverviewPage = checkoutInformationPage.clickContinue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "Verify the UI elements for checkout overview page",
            groups = {"debug"})
    @Parameters({"cartQuantityLabel", "cartDescriptionLabel", "cancelButtonText", "finishButtonText",
            "paymentInformationLabel", "shippingInformationLabel"})
    public void verifyCheckoutOverviewUIElements(String cartQuantityLabel, String cartDescriptionLabel,
                                                 String cancelButtonText, String finishButtonText,
                                                 String paymentInformationLabel, String shippingInformationLabel) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(checkoutOverviewPage.getQuantityLabelText(), cartQuantityLabel);
        softAssert.assertEquals(checkoutOverviewPage.getDescriptionLabelText(), cartDescriptionLabel);
        softAssert.assertEquals(checkoutOverviewPage.getCancelButtonText(), cancelButtonText);
        softAssert.assertEquals(checkoutOverviewPage.getFinishButtonText(), finishButtonText);
        softAssert.assertEquals(checkoutOverviewPage.getPaymentInformationLabelText(), paymentInformationLabel);
        softAssert.assertEquals(checkoutOverviewPage.getShippingMethodLabelText(), shippingInformationLabel);

        softAssert.assertAll("Checkout Overview page has wrong elements on UI, or is not loaded!");
    }

    @Test(description = "Verify the UI elements for products added to checkout overview page",
            groups = {"debug"})
    @Parameters({"qtyOne", "item4ID", "item4Name", "item4Desc", "item4Price",
            "paymentInformationCard", "shippingInformationMethod"})
    public void verifyProductUIElements(String qtyOne, String itemID, String name, String description, String price,
                                        String paymentInformationCard, String shippingInformationMethod) {

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(checkoutOverviewPage.getProductName(itemID), name);
        softAssert.assertEquals(checkoutOverviewPage.getProductDescription(itemID), description);
        softAssert.assertEquals(checkoutOverviewPage.getProductPrice(itemID), price);
        softAssert.assertEquals(checkoutOverviewPage.getProductQuantity(itemID), qtyOne);
        softAssert.assertEquals(checkoutOverviewPage.getPaymentInformationText(), paymentInformationCard);
        softAssert.assertEquals(checkoutOverviewPage.getShippingMethodText(), shippingInformationMethod);

        softAssert.assertAll("Checkout Overview page has wrong elements on UI for product, or is not loaded!");
    }

    @Test(description = "Verify the subtotal without tax value for the items on cart",
            groups = {"checkoutOverview"})
    public void verifyCartSubtotal() {
        boolean result = false;
        try {
            result = checkoutOverviewPage.itemSubtotalMatch();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "The following values don't match: Item total (subtotal without tax)!");
    }

    @Test(description = "Verify the total values for the items on cart",
            groups = {"checkoutOverview"})
    public void verifyCartTotal() {
        boolean result = false;
        try {
            result = checkoutOverviewPage.itemTotalMatch();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "The following values don't match: Total and Item total plus Tax!");
    }

    @Test(description = "Verify the changes on cart total amount, by removing items",
            groups = {"checkoutOverview"})
    @Parameters({"item4ID", "item4Price", "customerFirstName", "customerLastName", "customerZipCode"})
    public void verifyCartTotalChanges(String item4ID, String item4Price, String fName, String lName, String zip) {
        boolean result = false;
        double totalBefore = 0;
        double totalAfter = 0;
        try {
            totalBefore = checkoutOverviewPage.getSubtotalAmount();

            inventory = checkoutOverviewPage.cancelCheckout();
            inventory.removeFromCartById(item4ID);
            checkoutOverviewPage = reloadCartOverview(fName, lName, zip);

            totalAfter = checkoutOverviewPage.getSubtotalAmount();

            // A small trick to reduce decimal places without casting to String
            double difference = Math.floor((totalBefore - totalAfter) * 100) / 100;
            result = difference == Double.parseDouble(item4Price.substring(item4Price.indexOf("$") + 1));

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "The following values don't match: Total before and after removing one element");
    }

    @Test(description = "Verify the Cancel button works and user is taken back to inventory page",
            groups = {"checkoutOverview"})
    public void verifyCancelButton() {
        boolean result = false;
        try {
            result = checkoutOverviewPage.cancelCheckout().isPageLoaded();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "The Inventory page could not be loaded");
    }

    @Test(description = "Verify the Finish button works and user is taken to final page",
            groups = {"checkoutOverview"})
    public void verifyFinishButton() {
        boolean result = false;
        try {
            result = checkoutOverviewPage.finishCheckout().isPageLoaded();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "The Finish page could not be loaded");
    }

    private CheckoutOverviewPage reloadCartOverview(String fName, String lName, String zip) {
        checkoutInformationPage = inventory.loadShoppingCart().clickCheckoutButton();
        checkoutInformationPage.enterCustomerData(fName, lName, zip);
        return checkoutInformationPage.clickContinue();
    }
}
