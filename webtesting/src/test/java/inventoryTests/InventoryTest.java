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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryTest extends TestCaseBase {

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword"})
    public void loadInventoryPage(String user, String pwd) {
        getLoginPage().login(user, pwd);
    }

    // Not a real data provider tets per se, but still it's worth using files
    @Test(description = "Verify items on inventory page",
            groups = {"inventory"})
    @Parameters({"validUser", "validPassword"})
    public void verifyInventoryUI(String user, String pwd) {
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

    @Test(description = "Verify the items can be sorted by sifferent values",
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
}
