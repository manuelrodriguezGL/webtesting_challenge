package inventoryTests;

import Constants.GlobalTestConstants;
import constants.InventoryPageConstants;
import dataProviders.InventoryDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;
import utils.ExcelFileReader;

import java.io.IOException;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryTest extends TestCaseBase {

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword"})
    public void loadInventoryPage(String user, String pwd) {
        login(user, pwd);
    }

    // Not a real data provider tets per se, but still it's worth using files
    @Test(description = "Verify items on inventory page",
            groups = {"inventory"})
    @Parameters({"validUser", "validPassword"})
    public void verifyInventoryUI(String user, String pwd) {
        // I think that this line can be reused since it is replicated on all test cases in this testsuite,
        // in the beforemethod for example
        ProductsInventoryPage page = new ProductsInventoryPage(getWebDriverInstance());
        String errorMessages = "";
        try {
            errorMessages = page.assesInventoryItemValues(ExcelFileReader.readFile(InventoryPageConstants.INVENTORY_EXCEL_PATH,
                    InventoryPageConstants.INVENTORY_EXCEL_SHEET));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(errorMessages.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE + errorMessages);
    }

    @Test(description = "Verify the items can be sorted by different values",
            groups = {"inventory"}, dataProvider = "Sort", dataProviderClass = InventoryDataProvider.class)
    public void verifySortChange(String sortOrder) {
        ProductsInventoryPage page = new ProductsInventoryPage(getWebDriverInstance());
        boolean result = false;

        try {
            result = page.changeProductSort(sortOrder);
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
        ProductsInventoryPage page = new ProductsInventoryPage(getWebDriverInstance());
        boolean result = false;

        try {
            result = page.addToCartByQuantity(parseInt(quantity));
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
        ProductsInventoryPage page = new ProductsInventoryPage(getWebDriverInstance());
        boolean result = false;

        try {
            result = page.addToCartById(productId);
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
        ProductsInventoryPage page = new ProductsInventoryPage(getWebDriverInstance());
        boolean result = false;

        try {
            result = (page.addToCartByQuantity(parseInt(quantity)) &&
                    page.removeFromCartByQuantity(parseInt(quantity)));
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
        ProductsInventoryPage page = new ProductsInventoryPage(getWebDriverInstance());
        boolean result = false;

        try {
            result = (page.addToCartById(productId) &&
                    page.removeFromCartById(productId));
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not remove product with ID %s from cart!", productId));
    }
}
