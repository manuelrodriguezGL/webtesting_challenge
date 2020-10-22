package shoppingCartTests;

import Constants.GlobalTestConstants;
import dataProviders.CartDataProvider;
import dataProviders.ProductsDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ProductPage;
import pages.ProductsInventoryPage;
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
        String errorMessage = "";
        try {
            errorMessage = shoppingCartPage.verifyEmptyCartUIElements();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(errorMessage.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Shopping cart is not empty!");
    }

    @Test(description = "Verify the UI elements for every individual product",
            groups = {"product"}, dataProvider = "Cart", dataProviderClass = CartDataProvider.class)
    public void verifyProductUI(String id, String imageUrl, String name, String description, String price) {

        ProductsInventoryPage inventory = shoppingCartPage.clickContinueShoppingButton();
        String errorMessages = "";
        try {
            inventory.addToCartById(id);
            errorMessages += product.assesProductValues(imageUrl, name, description, price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(errorMessages.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE + errorMessages);
    }


}
