package shoppingCartTests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import pages.CheckoutOverviewPage;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;

public class CheckoutOverviewTest extends TestCaseBase {

    private CheckoutOverviewPage checkoutOverviewPage;
    private ProductsInventoryPage inventory;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword", "item4ID", "customerFirstName", "customerLastName", "customerZipCode"})
    public void loadShoppingCartPage(String user, String pwd, String item4ID, String fName, String lName, String zip) {
        try {
            inventory = login(user, pwd);
            inventory.addToCartById(item4ID);
            inventory.loadShoppingCart().clickCheckoutButton().enterCustomerData(fName, lName, zip);
            checkoutOverviewPage = inventory.loadShoppingCart().clickCheckoutButton().clickContinue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
