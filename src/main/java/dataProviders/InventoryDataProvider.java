package dataProviders;

import org.testng.annotations.DataProvider;
import utils.CommonUtils;
import utils.ExcelFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class InventoryDataProvider {

    @DataProvider(name = "Inventory")
    public Object[][] inventoryData() throws IOException {
        return getExcelFile("inventory_excel_path", "inventory_excel_sheet");
    }

    @DataProvider(name = "Sort")
    public Object[][] sortOrder() throws IOException {
        return getExcelFile("inventory_excel_path", "sort_excel_sheet");
    }

    @DataProvider(name = "ID")
    public Object[][] inventoryIds() throws IOException {
        return getExcelFile("inventory_excel_path", "id_excel_sheet");
    }

    @DataProvider(name = "Names")
    public Object[][] inventoryNames() throws IOException {
        return getExcelFile("inventory_excel_path", "names_excel_sheet");
    }

    private ArrayList<String> getPropertiesArray(String excel_path, String excel_sheet) throws IOException {
        return CommonUtils.getPropertiesArray(new ArrayList<>(Arrays.asList(excel_path, excel_sheet)));
    }

    private String[][] getExcelFile(String excel_path, String excel_sheet) throws IOException {
        ArrayList<String> propertiesArray =
                getPropertiesArray(excel_path, excel_sheet);
        return ExcelFileReader.readFile(propertiesArray.get(0), propertiesArray.get(1));
    }
}
