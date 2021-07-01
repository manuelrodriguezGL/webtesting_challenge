package dataProviders;

import org.testng.annotations.DataProvider;
import utils.CommonUtils;
import utils.ExcelFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ProductsDataProvider {
    @DataProvider(name = "Products")
    public static Object[][] inventoryProducts() throws IOException {
        ArrayList<String> propertiesArray =
                CommonUtils.getPropertiesArray(new ArrayList<>(Arrays.asList("product_excel_path", "product_excel_sheet")));

        return ExcelFileReader.readFile(propertiesArray.get(0), propertiesArray.get(1));
    }
}
