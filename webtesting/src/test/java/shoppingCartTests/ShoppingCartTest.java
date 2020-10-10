package shoppingCartTests;

import Constants.GlobalTestConstants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ShoppingCartPage;
import testBase.TestCaseBase;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartTest extends TestCaseBase {

    private ShoppingCartPage shoppingCartPage;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword"})
    public void loadShoppingCartPage(String user, String pwd) {
        shoppingCartPage = login(user, pwd).loadShoppingCart();
    }

    @Test(description = "Verify the shopping cart is empty", groups = {"shoppingCart"})
    public void verifyEmptyShoppingCart() {
        assertTrue(shoppingCartPage.isCartEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Shopping cart is not empty!");
    }


}
