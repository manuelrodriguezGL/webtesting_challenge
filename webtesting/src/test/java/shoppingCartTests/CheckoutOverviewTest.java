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
            inventory.addToCartByQuantity(6);
            checkoutInformationPage = inventory.loadShoppingCart().clickCheckoutButton();
            checkoutInformationPage.enterCustomerData(fName, lName, zip);
            checkoutOverviewPage = checkoutInformationPage.clickContinue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "Verify the UI elements for checkout overview page",
            groups = {"login"})
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

    @Test(description = "Verify the subtotal without tax value for the items on cart",
            groups = {"login"})
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
            groups = {"login"})
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

    private CheckoutOverviewPage reloadCartOverview(String fName, String lName, String zip) {
        checkoutInformationPage = inventory.loadShoppingCart().clickCheckoutButton();
        checkoutInformationPage.enterCustomerData(fName, lName, zip);
        return checkoutInformationPage.clickContinue();
    }
}
