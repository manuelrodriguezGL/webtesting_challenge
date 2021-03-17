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

    private ProductsInventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword"})
    public void loadInventoryPage(String user, String pwd) {
        login(user, pwd);
        inventoryPage = new ProductsInventoryPage(getWebDriverInstance(), baseUrl);
    }

    /**
     * This test makes use of a direct file reader, instead of a Data provider.
     * This is for educational purposes only.
     */
    @Test(description = "Verify items on inventory page",
            groups = {"debug"})
    public void verifyInventoryUI() {
        String errorMessages = "";
        try {
            errorMessages = inventoryPage.assesInventoryItemValues(
                    ExcelFileReader.readFile(InventoryPageConstants.INVENTORY_EXCEL_PATH,
                    InventoryPageConstants.INVENTORY_EXCEL_SHEET));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(errorMessages.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE + errorMessages);
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
        boolean result = false;

        try {
            result = inventoryPage.addToCartById(productId);
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
        boolean result = false;

        try {
            result = (inventoryPage.addToCartById(productId) &&
                    inventoryPage.removeFromCartById(productId));
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        assertTrue(result, GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE +
                String.format("Could not remove product with ID %s from cart!", productId));
    }
}
