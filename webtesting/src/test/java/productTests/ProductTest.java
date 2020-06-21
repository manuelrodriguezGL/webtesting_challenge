package productTests;

import Constants.GlobalTestConstants;
import dataProviders.ProductsDataProvider;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ProductPage;
import pages.ProductsInventoryPage;
import testBase.TestCaseBase;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest extends TestCaseBase {

    private ProductsInventoryPage inventoryPage;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"validUser", "validPassword"})
    public void loadInventoryPage(String user, String pwd) {
        inventoryPage = login(user, pwd);
    }


    @Test(description = "Verify the UI elements for every individual product",
            groups = {"test"}, dataProvider = "Products", dataProviderClass = ProductsDataProvider.class)
    public void verifyProductUI(String id, String imageUrl, String name, String description, String price) {
        ProductPage product = inventoryPage.loadProductPageById(id);
        String errorMessages = "";
        try {
            errorMessages += product.assesProductValues(imageUrl, name, description, price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(errorMessages.isEmpty(), GlobalTestConstants.GLOBAL_TEST_FAILED_MESSAGE + errorMessages);
    }

}
