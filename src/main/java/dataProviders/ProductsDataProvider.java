package dataProviders;

import constants.ProductPageConstants;
import org.testng.annotations.DataProvider;
import utils.ExcelFileReader;

import java.io.IOException;

public class ProductsDataProvider {
    @DataProvider(name = "Products")
    public static Object[][] inventoryProducts() throws IOException {
        return ExcelFileReader.readFile(ProductPageConstants.PRODUCT_EXCEL_PATH,
                ProductPageConstants.PRODUCT_EXCEL_SHEET);
    }
}
