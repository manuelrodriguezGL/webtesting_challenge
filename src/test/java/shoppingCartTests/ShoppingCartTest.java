package shoppingCartTests;

import Constants.GlobalTestConstants;
import dataProviders.CartDataProvider;
import dataProviders.InventoryDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProductsInventoryPage;
import pages.ShoppingCartPage;
import testBase.TestCaseBase;

import static java.lang.Integer.parseInt;

public class ShoppingCartTest extends TestCaseBase {

    private ShoppingCartPage shoppingCartPage;
    private ProductsInventoryPage inventory;

    private void loadShoppingCartPage(String id) {
        inventory.addToCartById(id);
        shoppingCartPage = inventory.loadShoppingCart();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"sauce_user", "sauce_psw"})
    public void loadInventoryPage(String user, String pwd) {
        inventory = login(user, pwd);
    }

    @Test(description = "Verify the shopping cart is empty",
            groups = {"shoppingCart"})
    public void verifyShoppingCartIsEmpty() {
        Assert.assertTrue(shoppingCartPage.isCartEmpty(),
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE + " Shopping cart is not empty!");
    }

    @Test(description = "Verify the UI elements for every individual product",
            groups = {"debug"}, dataProvider = "Cart", dataProviderClass = CartDataProvider.class)
    public void verifyShoppingCartUI(String id, String name, String description, String price) {

        SoftAssert softAssert = new SoftAssert();
        loadShoppingCartPage(id);

        if (!shoppingCartPage.isCartEmpty()) {
            softAssert.assertEquals(shoppingCartPage.getProductName(id), name);
            softAssert.assertEquals(shoppingCartPage.getProductDescription(id), description);
            softAssert.assertEquals(shoppingCartPage.getProductPrice(id), "$" + price);
            softAssert.assertAll(GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                    String.format("Product information does not match the values on file for ID: %s", id));
        } else {
            softAssert.fail("Shopping cart is not empty!");
        }
    }

    @Test(description = "Verify that every individual product can be removed from cart",
            groups = {"shoppingCart"}, dataProvider = "ID", dataProviderClass = InventoryDataProvider.class)
    public void verifyProductRemoveFromCart(String productId) {
        loadShoppingCartPage(productId);
        int beforeQuantity = shoppingCartPage.getCartItemsQuantity();
        shoppingCartPage.clickRemoveButton(productId);

        Assert.assertEquals(shoppingCartPage.getCartItemsQuantity(), --beforeQuantity,
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                        String.format("Could not remove product with ID %s from cart!", productId));
    }

    @Test(description = "Verify that all products can be removed from cart",
            groups = {"shoppingCart"})
    @Parameters({"totalProducts"})
    public void verifyRemoveAllFromCart(String quantity) {
        inventory.addToCartByQuantity(parseInt(quantity));
        shoppingCartPage = inventory.loadShoppingCart();

        shoppingCartPage.clickAllRemoveButtons();

        Assert.assertEquals(shoppingCartPage.getCartItemsQuantity(), 0,
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                        String.format("Could not remove all %s items from cart!", quantity));
    }


    @Test(description = "Verify that there's a Continue Shopping cart that takes user back to Inventory page",
            groups = {"shoppingCart"}, dataProvider = "ID", dataProviderClass = InventoryDataProvider.class)
    public void verifyContinueShoppingButton(String productId) {
        loadShoppingCartPage(productId);
        Assert.assertNotNull(shoppingCartPage.clickContinueShoppingButton(),
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                        String.format("Could not click Continue Shopping button for product with ID %s", productId));
    }
}
