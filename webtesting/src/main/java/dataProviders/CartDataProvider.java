package dataProviders;

import constants.ShoppingCartPageConstants;
import org.testng.annotations.DataProvider;
import utils.ExcelFileReader;

import java.io.IOException;

public class CartDataProvider {
    @DataProvider(name = "Cart")
    public static Object[][] cartData() throws IOException {
        return ExcelFileReader.readFile(ShoppingCartPageConstants.CART_EXCEL_PATH,
                ShoppingCartPageConstants.CART_EXCEL_SHEET);
    }
}
