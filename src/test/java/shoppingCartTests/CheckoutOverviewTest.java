package shoppingCartTests;

import Constants.GlobalTestConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CheckoutInformationPage;
import pages.CheckoutOverviewPage;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;

public class CheckoutOverviewTest extends TestCaseBase {

    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutInformationPage checkoutInformationPage;
    private ProductsInventoryPage inventory;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"sauce_user", "sauce_psw", "customerFirstName", "customerLastName", "customerZipCode"})
    public void loadShoppingCartPage(String user, String pwd, String firstName, String lastName, String zipCode) {
        try {
            inventory = login(user, pwd);
            inventory.addToCartByQuantity(6);
            checkoutInformationPage = inventory.loadShoppingCart().clickCheckoutButton();
            checkoutInformationPage.enterCustomerData(firstName, lastName, zipCode);
            checkoutOverviewPage = checkoutInformationPage.clickContinue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "Verify the UI elements for checkout overview page",
            groups = {"checkoutOverview"})
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
            groups = {"checkoutOverview"})
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
        Assert.assertEquals(checkoutOverviewPage.getTotalAmountFromItemList(),
                checkoutOverviewPage.getSubtotalAmount(),
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                        "The following values don't match: Item total (subtotal without tax)!");
    }

    @Test(description = "Verify the total values for the items on cart, plus tax",
            groups = {"checkoutOverview"})
    public void verifyCartTotal() {
        Assert.assertEquals(checkoutOverviewPage.sumTotalAmount(),
                checkoutOverviewPage.getTotalAmount(),
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                        "The following values don't match: Total and Item total plus Tax!");
    }

    @Test(description = "Verify the changes on cart total amount, by removing items",
            groups = {"checkoutOverview"})
    @Parameters({"item4Name", "item4Price", "customerFirstName", "customerLastName", "customerZipCode"})
    public void verifyCartTotalChanges(String item4Name, String item4Price, String fName, String lName, String zip) {

        double totalBefore = 0;
        double totalAfter = 0;

        totalBefore = checkoutOverviewPage.getSubtotalAmount();

        inventory = checkoutOverviewPage.cancelCheckout();
        inventory.removeFromCartByProductName(item4Name);
        checkoutOverviewPage = reloadCartOverview(fName, lName, zip);

        totalAfter = checkoutOverviewPage.getSubtotalAmount();

        // A small trick to reduce decimal places without casting to String
        double difference = Math.floor((totalBefore - totalAfter) * 100) / 100;
        Assert.assertEquals(difference,
                Double.parseDouble(item4Price.substring(item4Price.indexOf("$") + 1)),
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                        "The following values don't match: Total before and after removing one element");
    }

    @Test(description = "Verify the Cancel button works and user is taken back to inventory page",
            groups = {"checkoutOverview"})
    public void verifyCancelButton() {
        Assert.assertTrue(checkoutOverviewPage.cancelCheckout().isPageLoaded(),
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                        "The Inventory page could not be loaded by clicking Cancel button");
    }

    @Test(description = "Verify the Finish button works and user is taken to final page",
            groups = {"checkoutOverview"})
    public void verifyFinishButton() {
        Assert.assertTrue(checkoutOverviewPage.finishCheckout().isPageLoaded(),
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                        "The Checkout Finished page could not be loaded");
    }

    private CheckoutOverviewPage reloadCartOverview(String fName, String lName, String zip) {
        checkoutInformationPage = inventory.loadShoppingCart().clickCheckoutButton();
        checkoutInformationPage.enterCustomerData(fName, lName, zip);
        return checkoutInformationPage.clickContinue();
    }
}
