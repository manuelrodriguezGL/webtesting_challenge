package dataProviders;

import constants.InventoryPageConstants;
import org.testng.annotations.DataProvider;
import utils.ExcelFileReader;

import java.io.IOException;

public class InventoryDataProvider {
    @DataProvider(name = "Inventory")
    public static Object[][] inventoryData() throws IOException {
        return ExcelFileReader.readFile(InventoryPageConstants.INVENTORY_EXCEL_PATH,
                InventoryPageConstants.INVENTORY_EXCEL_SHEET);
    }

    @DataProvider(name = "Sort")
    public static Object[][] sortOrder() throws  IOException {
        return ExcelFileReader.readFile(InventoryPageConstants.INVENTORY_EXCEL_PATH,
                InventoryPageConstants.SORT_EXCEL_SHEET);
    }

    @DataProvider(name = "ID")
    public static Object[][] inventoryIds() throws  IOException {
        return ExcelFileReader.readFile(InventoryPageConstants.INVENTORY_EXCEL_PATH,
                InventoryPageConstants.ID_EXCEL_SHEET);
    }

    @DataProvider(name = "Names")
    public static Object[][] inventoryNames() throws  IOException {
        return ExcelFileReader.readFile(InventoryPageConstants.INVENTORY_EXCEL_PATH,
                InventoryPageConstants.NAMES_EXCEL_SHEET);
    }
}
