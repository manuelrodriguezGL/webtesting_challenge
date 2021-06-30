package dataProviders;

import org.testng.annotations.DataProvider;
import utils.CommonUtils;
import utils.ExcelFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CartDataProvider {
    @DataProvider(name = "Cart")
    public static Object[][] cartData() throws IOException {
        ArrayList<String> propertiesArray =
                CommonUtils.getPropertiesArray(new ArrayList<>(Arrays.asList("cart_excel_path", "cart_excel_sheet")));

        return ExcelFileReader.readFile(propertiesArray.get(0),
                propertiesArray.get(1));
    }
}
