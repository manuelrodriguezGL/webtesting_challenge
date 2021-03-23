package productTests;

import Constants.GlobalTestConstants;
import dataProviders.InventoryDataProvider;
import dataProviders.ProductsDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ProductPage;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest extends TestCaseBase {

    private ProductsInventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword"})
    public void loadInventoryPage(String user, String pwd) {
        inventoryPage = login(user, pwd);
    }


    @Test(description = "Verify the UI elements for every individual product",
            groups = {"debug"}, dataProvider = "Products", dataProviderClass = ProductsDataProvider.class)
    public void verifyProductUI(String id, String imageUrl, String name, String description, String price) {
        ProductPage product = inventoryPage.loadProductPageById(id);

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(product.getProductImageUrl().contains(imageUrl),
                "Product image not present or URL is not the same, for ID: " + id);
        softAssert.assertEquals(product.getProductName(), name,
                "Product name not present or value is not the same, for ID: " + id);
        softAssert.assertEquals(product.getProductDescription(), description,
                "Product description not present or value is not the same, for ID: " + id);
        softAssert.assertEquals(product.getProductPrice(), price,
                "Product price not present or value is not the same, for ID: " + id);
        softAssert.assertAll("Product information does not match the values on file for ID: " + id);

    }

    @Test(description = "Verify that the Back button works on every product page",
            groups = {"product"}, dataProvider = "ID", dataProviderClass = InventoryDataProvider.class)
    public void verifyProductGoBackButton(String productId) {
        ProductPage product = inventoryPage.loadProductPageById(productId);
        boolean result = false;

        try {
            result = (product.goBack() != null);
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not go back on product with ID %s!", productId));
    }

    @Test(description = "Verify that every individual product can be added to cart",
            groups = {"product"}, dataProvider = "ID", dataProviderClass = InventoryDataProvider.class)
    public void verifyProductAddToCart(String productId) {
        ProductPage product = inventoryPage.loadProductPageById(productId);
        boolean result = false;

        try {
            result = product.clickAddToCartButton();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not add product with ID %s to cart!", productId));
    }

    @Test(description = "Verify that every individual product can be removed from cart",
            groups = {"product"}, dataProvider = "ID", dataProviderClass = InventoryDataProvider.class)
    public void verifyProductRemoveFromCart(String productId) {
        ProductPage product = inventoryPage.loadProductPageById(productId);
        boolean result = false;

        try {
            result = (product.clickAddToCartButton() && product.clickRemoveButton());
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not remove product with ID %s from cart!", productId));
    }

}
