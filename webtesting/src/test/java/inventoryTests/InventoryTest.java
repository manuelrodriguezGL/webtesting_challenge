package inventoryTests;

import Constants.GlobalTestConstants;
import dataProviders.InventoryDataProvider;
import dataProviders.ProductsDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryTest extends TestCaseBase {

    private ProductsInventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword"})
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
        softAssert.assertAll("Product information does not match the values on file for ID: " + id);

    }

    @Test(description = "Verify the items can be sorted by different values",
            groups = {"inventory"}, dataProvider = "Sort", dataProviderClass = InventoryDataProvider.class)
    public void verifySortChange(String sortOrder) {
        boolean result = false;

        try {
            result = inventoryPage.changeProductSort(sortOrder);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }

        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                "Could not change product sort!: " + sortOrder);
    }

    @Test(description = "Verify that all products can be added to cart",
            groups = {"inventory"})
    @Parameters({"totalProducts"})
    public void verifyAddAllToCart(String quantity) {
        boolean result = false;
        //TODO refactor
        try {
            result = inventoryPage.addToCartByQuantity(parseInt(quantity));
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not add all %s items to cart!", quantity));
    }

    @Test(description = "Verify that every individual product can be added to cart",
            groups = {"inventory"}, dataProvider = "ID", dataProviderClass = InventoryDataProvider.class)
    public void verifyAddIndividuallyToCart(String productId) {
        //TODO refactor
        boolean result = false;

        try {
            //result = inventoryPage.addToCartById(productId);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not add product with ID %s to cart!", productId));
    }

    @Test(description = "Verify that all products can be removed from cart",
            groups = {"inventory"})
    @Parameters({"totalProducts"})
    public void verifyRemoveAllFromCart(String quantity) {
        //TODO refactor
        boolean result = false;

        try {
            result = (inventoryPage.addToCartByQuantity(parseInt(quantity)) &&
                    inventoryPage.removeFromCartByQuantity(parseInt(quantity)));
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not remove all %s items from cart!", quantity));
    }

    @Test(description = "Verify that every individual product can be removed from cart",
            groups = {"inventory"}, dataProvider = "ID", dataProviderClass = InventoryDataProvider.class)
    public void verifyRemoveIndividuallyFromCart(String productId) {
        //TODO refactor
        boolean result = false;

        try {
            //   result = (inventoryPage.addToCartById(productId) &&
            //         inventoryPage.removeFromCartById(productId));
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not remove product with ID %s from cart!", productId));
    }
}
