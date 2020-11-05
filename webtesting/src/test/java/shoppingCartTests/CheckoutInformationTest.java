package shoppingCartTests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CheckoutInformationPage;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;

public class CheckoutInformationTest  extends TestCaseBase {

    private CheckoutInformationPage checkoutPage;
    private ProductsInventoryPage inventory;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword", "item4ID"})
    public void loadShoppingCartPage(String user, String pwd, String item4ID) {
        inventory = login(user, pwd);
        inventory.addToCartById(item4ID);
        checkoutPage = inventory.loadShoppingCart().clickCheckoutButton();
    }

    @Test(description = "Verify the UI elements for every individual product",
            groups = {"checkoutInformation"})
    public void verifyUIElements(){}
}
