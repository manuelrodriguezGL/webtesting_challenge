package dataProviders;

import constants.InventoryPageConstants;
import org.testng.annotations.DataProvider;
import utils.ExcelFileReader;

import java.io.IOException;

public class InventoryDataProvider {
    @DataProvider(name = "Inventory")
    public static Object[][] inventoryData() throws IOException {
        // Thanks StackOverflow https://stackoverflow.com/a/52087820
        return ExcelFileReader.readFile(InventoryPageConstants.INVENTORY_EXCEL_PATH,
                InventoryPageConstants.INVENTORY_EXCEL_SHEET);
    }

    @DataProvider(name = "Sort")
    public static Object[][] sortOrder() throws  IOException {
        return ExcelFileReader.readFile(InventoryPageConstants.INVENTORY_EXCEL_PATH,
                InventoryPageConstants.SORT_EXCEL_SHEET);
    }
}
