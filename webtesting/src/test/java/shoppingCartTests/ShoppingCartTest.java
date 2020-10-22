package shoppingCartTests;

import Constants.GlobalTestConstants;
import dataProviders.CartDataProvider;
import dataProviders.InventoryDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ProductsInventoryPage;
import pages.ShoppingCartPage;
import testBase.TestCaseBase;

import java.util.Objects;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartTest extends TestCaseBase {

    private ShoppingCartPage shoppingCartPage;
    private ProductsInventoryPage inventory;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword"})
    public void loadShoppingCartPage(String user, String pwd) {
        inventory = login(user, pwd);
    }

    @Test(description = "Verify the shopping cart is empty",
            groups = {"login"})
    public void verifyEmptyShoppingCart() {
        String errorMessage = "";
        try {
            shoppingCartPage = inventory.loadShoppingCart();
            errorMessage = shoppingCartPage.verifyEmptyCartUIElements();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(errorMessage.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Shopping cart is not empty!");
    }

    @Test(description = "Verify the UI elements for every individual product",
            groups = {"login"}, dataProvider = "Cart", dataProviderClass = CartDataProvider.class)
    public void verifyProductUI(String id, String imageUrl, String name, String description, String price) {

        String errorMessages = "";
        try {
            inventory.addToCartById(id);
            shoppingCartPage = inventory.loadShoppingCart();
            errorMessages += shoppingCartPage.verifyProductCartUIElements(1, imageUrl, name, description, price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Error message: " + errorMessages);
        assertTrue(errorMessages.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE + errorMessages);
    }

    @Test(description = "Verify that every individual product can be removed from cart",
            groups = {"login"}, dataProvider = "ID", dataProviderClass = InventoryDataProvider.class)
    public void verifyProductRemoveFromCart(String productId) {
        boolean result = false;

        try {
            inventory.addToCartById(productId);
            shoppingCartPage = inventory.loadShoppingCart();
            result = (shoppingCartPage.removeFromCartById(productId));
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not remove product with ID %s from cart!", productId));
    }

    @Test(description = "Verify that all products can be removed from cart",
            groups = {"login"})
    @Parameters({"totalProducts"})
    public void verifyRemoveAllFromCart(String quantity) {
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
        boolean result = false;

        try {
            inventory.addToCartById(productId);
            shoppingCartPage = inventory.loadShoppingCart();
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
