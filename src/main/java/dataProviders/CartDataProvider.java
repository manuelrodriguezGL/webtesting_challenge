package dataProviders;

import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CartDataProvider extends BaseDataProvider {
    @DataProvider(name = "Cart")
    public Object[][] cartData() throws IOException {
        ArrayList<String> propertiesArray =
                commonUtils.getPropertiesArray(new ArrayList<>(Arrays.asList("cart_excel_path", "cart_excel_sheet")));

        return excelFileReader.readFile(propertiesArray.get(0), propertiesArray.get(1));
    }
}
