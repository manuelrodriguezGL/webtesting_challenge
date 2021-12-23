package dataProviders;

import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ProductsDataProvider extends BaseDataProvider {
    @DataProvider(name = "Products")
    public Object[][] inventoryProducts() throws IOException {
        ArrayList<String> propertiesArray =
                commonUtils.getPropertiesArray(new ArrayList<>(Arrays.asList("product_excel_path", "product_excel_sheet")));

        return excelFileReader.readFile(propertiesArray.get(0), propertiesArray.get(1));
    }
}
