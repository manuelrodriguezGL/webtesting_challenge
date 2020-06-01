package inventoryTests;

import Constants.GlobalTestConstants;
import constants.InventoryPageConstants;
import dataProviders.InventoryDataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;
import utils.ExcelFileReader;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryTest extends TestCaseBase {

//    @BeforeClass
//    @Parameters({"validUser", "validPassword"})
//    public void loadInventoryPage(String user, String pwd) {
//        getLoginPage().login(user, pwd);
//    }

    //TODO Gotta modify this test and the corresponding page, to make it work like a real data provider test
    @Test(description = "Verify items on inventory page",
            groups = {"inventory"})
    @Parameters({"validUser", "validPassword"})
    public void verifyInventoryUI(String user, String pwd) {
        ProductsInventoryPage page = getLoginPage().login(user, pwd);
        String errorMessages = "";
        try {
            errorMessages = page.assesInventoryItemValues(ExcelFileReader.readFile(InventoryPageConstants.INVENTORY_EXCEL_PATH,
                    InventoryPageConstants.INVENTORY_EXCEL_SHEET));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(errorMessages.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE + errorMessages);
    }
}
