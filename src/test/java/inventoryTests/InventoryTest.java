package inventoryTests;

import Constants.GlobalTestConstants;
import dataProviders.InventoryDataProvider;
import dataProviders.ProductsDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;

import java.util.List;

import static java.lang.Integer.parseInt;

public class InventoryTest extends TestCaseBase {

    private ProductsInventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"sauce_user", "sauce_psw"})
    public void loadInventoryPage(String user, String pwd) {
        login(user, pwd);
        inventoryPage = new ProductsInventoryPage(getWebDriverInstance(), baseUrl);
    }

    @Test(description = "Verify items on inventory page",
            groups = {"inventory"}, dataProvider = "Products", dataProviderClass = ProductsDataProvider.class)
    public void verifyInventoryUI(String id, String imageUrl, String name, String description, String price) {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(inventoryPage.getProductImageUrl(id).contains(imageUrl),
                "Product image not present or URL is not the same, for ID: " + id);
        softAssert.assertEquals(inventoryPage.getProductName(id), name,
                "Product name not present or value is not the same, for ID: " + id);
        softAssert.assertEquals(inventoryPage.getProductDescription(id), description,
                "Product description not present or value is not the same, for ID: " + id);
        softAssert.assertEquals(inventoryPage.getProductPrice(id), price,
                "Product price not present or value is not the same, for ID: " + id);
        softAssert.assertAll(GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Product information does not match the values on file for ID: " + id);

    }

    @Test(description = "Verify the items can be sorted by different name values",
            groups = {"inventory"})
    @Parameters({"sortNameAz", "sortNameZa"})
    public void verifySortChangeByName(String sortNameAz, String sortNameZa) {
        SoftAssert softAssert = new SoftAssert();

        int itemListSize = inventoryPage.getItemNames().size();

        // We get current first and last item from UI
        String firstItem = inventoryPage.getItemNames().get(0).getAttribute("innerText");
        String lastItem = inventoryPage.getItemNames().get(itemListSize - 1).getAttribute("innerText");

        inventoryPage.clickProductSortSelect();
        inventoryPage.changeValueProductSortSelect(sortNameAz);

        // We compare first and last elements after the sort change on UI
        softAssert.assertEquals(inventoryPage.getItemNames().get(0).getAttribute("innerText"),
                firstItem, "Could not change sort order: A to Z");
        softAssert.assertEquals(inventoryPage.getItemNames().get(itemListSize - 1).getAttribute("innerText"),
                lastItem, "Could not change sort order: A to Z");

        inventoryPage.clickProductSortSelect();
        inventoryPage.changeValueProductSortSelect(sortNameZa);

        softAssert.assertEquals(inventoryPage.getItemNames().get(0).getAttribute("innerText"),
                lastItem, "Could not change sort order: Z to A");
        softAssert.assertEquals(inventoryPage.getItemNames().get(itemListSize - 1).getAttribute("innerText"),
                firstItem, "Could not change sort order: Z to A");

        softAssert.assertAll(GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE + "Items could not be sorted by name!");
    }

    @Test(description = "Verify the items can be sorted by different price values",
            groups = {"inventory"})
    @Parameters({"sortPriceLohi", "sortPriceHilo"})
    public void verifySortChangeByPrice(String sortPriceLohi, String sortPriceHilo) {
        SoftAssert softAssert = new SoftAssert();

        // We get a list of prices, that we can compare to
        // and sort them in the way we would expect the app to do it
        List<Double> itemPrices = inventoryPage.getSortedInventoryByPrice();

        // We get current first and last item from sorted prices list
        // On previous step, we had to strip the '$' sign on every price
        String firstItem = "$" + itemPrices.get(0).toString();
        String lastItem = "$" + itemPrices.get(itemPrices.size() - 1).toString();

        inventoryPage.clickProductSortSelect();
        inventoryPage.changeValueProductSortSelect(sortPriceLohi);

        // We compare our actual prices from first and last elements on UI, against the sorted list
        softAssert.assertEquals(inventoryPage.getItemPrices().get(0).getAttribute("innerText"),
                firstItem, "Could not change sort order: Lowest to highest price");
        softAssert.assertEquals(inventoryPage.getItemPrices().get(itemPrices.size() - 1).getAttribute("innerText"),
                lastItem, "Could not change sort order: Lowest to highest price");

        inventoryPage.clickProductSortSelect();
        inventoryPage.changeValueProductSortSelect(sortPriceHilo);

        softAssert.assertEquals(inventoryPage.getItemPrices().get(0).getAttribute("innerText"),
                lastItem, "Could not change sort order: Highest  to lowest price");
        softAssert.assertEquals(inventoryPage.getItemPrices().get(itemPrices.size() - 1).getAttribute("innerText"),
                firstItem, "Could not change sort order: Highest  to lowest price");

        softAssert.assertAll(GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE + "Items could not be sorted by price!");
    }

    @Test(description = "Verify that all products can be added to cart",
            groups = {"inventory"})
    @Parameters({"totalProducts"})
    public void verifyAddAllToCart(String quantity) {

        int expectedQuantity = parseInt(quantity);

        inventoryPage.addToCartByQuantity(expectedQuantity);

        int quantityAdded = inventoryPage.getCartItemsQuantity();

        Assert.assertEquals(quantityAdded, expectedQuantity, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not add all %s items to cart!", quantity));
    }

    @Test(description = "Verify that every individual product can be added to cart",
            groups = {"inventory"}, dataProvider = "Names", dataProviderClass = InventoryDataProvider.class)
    public void verifyAddIndividuallyToCart(String productId, String productName) {
        SoftAssert softAssert = new SoftAssert();

        // Get current quantity on cart
        int originalQuantity = inventoryPage.getCartItemsQuantity();
        // Add products to cart
        inventoryPage.addToCartById(productId);

        // Get new quantity on cart
        int quantityAdded = inventoryPage.getCartItemsQuantity();

        // If the original quantity + 1 item = added quantity, then the test passes
        softAssert.assertEquals(originalQuantity + 1, quantityAdded,
                String.format("Could not add the following item to cart: %s-%s!", productId, productName));
        softAssert.assertEquals(inventoryPage.getProductRemoveFromCartButtonText(productName), "REMOVE",
                String.format("The button text didn't change for the following item %s-%s!", productId, productName));

        softAssert.assertAll(GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not add product to cart: %s-%s!", productId, productName));
    }

    @Test(description = "Verify that all products can be removed from cart",
            groups = {"inventory"})
    @Parameters({"totalProducts"})
    public void verifyRemoveAllFromCart(String quantity) {
        int expectedQuantity = parseInt(quantity);

        // Add products to cart
        inventoryPage.addToCartByQuantity(expectedQuantity);
        // Get current quantity on cart
        int originalQuantity = inventoryPage.getCartItemsQuantity();

        // Remove products from cart
        inventoryPage.removeFromCartByQuantity(expectedQuantity);
        // Get remaining quantity on cart
        int quantityRemoved = inventoryPage.getCartItemsQuantity();

        // If the remaining quantity + removed items = original quantity, then the test passes
        Assert.assertEquals(quantityRemoved + originalQuantity, expectedQuantity,
                GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                        String.format("Could not remove all %s items from cart!", quantity));
    }

    @Test(description = "Verify that every individual product can be removed from cart",
            groups = {"inventory"}, dataProvider = "Names", dataProviderClass = InventoryDataProvider.class)
    public void verifyRemoveIndividuallyFromCart(String productId, String productName) {

        SoftAssert softAssert = new SoftAssert();

        // Add products to cart
        inventoryPage.addToCartById(productId);
        // Get current quantity on cart
        int originalQuantity = inventoryPage.getCartItemsQuantity();

        // Remove products from cart
        inventoryPage.removeFromCartByProductName(productName);
        // Get remaining quantity on cart
        int quantityRemoved = inventoryPage.getCartItemsQuantity();

        // If the remaining quantity + 1 item = original quantity, then the test passes
        softAssert.assertEquals(quantityRemoved + 1, originalQuantity,
                String.format("Could not remove the following item from cart: %s!", productName));
        softAssert.assertEquals(inventoryPage.getProductAddToCartButtonText(productId), "ADD TO CART",
                String.format("The button text didn't change for the following item %s!", productName));
        softAssert.assertAll(GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Could not remove product from cart: " + productName);
    }
}
