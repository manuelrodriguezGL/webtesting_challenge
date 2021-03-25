package shoppingCartTests;

import Constants.GlobalTestConstants;
import dataProviders.CartDataProvider;
import dataProviders.InventoryDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProductsInventoryPage;
import pages.ShoppingCartPage;
import testBase.TestCaseBase;

import java.util.NoSuchElementException;
import java.util.Objects;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartTest extends TestCaseBase {

    private ShoppingCartPage shoppingCartPage;
    private ProductsInventoryPage inventory;

    private void loadShoppingCartPage(String id) {
        inventory.addToCartById(id);
        shoppingCartPage = inventory.loadShoppingCart();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword"})
    public void loadInventoryPage(String user, String pwd) {
        inventory = login(user, pwd);
    }

    @Test(description = "Verify the shopping cart is empty",
            groups = {"shoppingCart"})
    public void verifyShoppingCartIsEmpty() {
        try {
            assertTrue(shoppingCartPage.isCartEmpty(),
                    GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE + " Shopping cart is not empty!");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "Verify the UI elements for every individual product",
            groups = {"debug"}, dataProvider = "Cart", dataProviderClass = CartDataProvider.class)
    public void verifyShoppingCartUI(String id, String name, String description, String price) {

        SoftAssert softAssert = new SoftAssert();
        loadShoppingCartPage(id);

        softAssert.assertEquals(shoppingCartPage.getProductName(id), name);
        softAssert.assertEquals(shoppingCartPage.getProductDescription(id), description);
        softAssert.assertEquals(shoppingCartPage.getProductPrice(id), price);
        softAssert.assertAll("Product information does not match the values on file for ID: " + id);
    }

    @Test(description = "Verify that every individual product can be removed from cart",
            groups = {"shoppingCart"}, dataProvider = "ID", dataProviderClass = InventoryDataProvider.class)
    public void verifyProductRemoveFromCart(String productId) {
        //TODO refactor
        boolean result = false;

        try {
            loadShoppingCartPage(productId);

            result = (shoppingCartPage.removeFromCartById(productId));
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not remove product with ID %s from cart!", productId));
    }

    @Test(description = "Verify that all products can be removed from cart",
            groups = {"shoppingCart"})
    @Parameters({"totalProducts"})
    public void verifyRemoveAllFromCart(String quantity) {
        //TODO refactor
        boolean result = false;

        try {
            inventory.addToCartByQuantity(parseInt(quantity));
            shoppingCartPage = inventory.loadShoppingCart();
            result = shoppingCartPage.removeAllFromCart();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not remove all %s items from cart!", quantity));
    }


    @Test(description = "Verify that every individual product has a Continue Shopping button on cart",
            groups = {"shoppingCart"}, dataProvider = "ID", dataProviderClass = InventoryDataProvider.class)
    public void verifyContinueShoppingButton(String productId) {
        //TODO refactor
        boolean result = false;

        try {
            loadShoppingCartPage(productId);

            result = (Objects.nonNull(shoppingCartPage.clickContinueShoppingButton())
                    && inventory.removeFromCartById(productId));
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not find Continue Shopping button for product with ID %s", productId));
    }

}
