package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsPage extends BasePage {

    private static final String URL = "/inventory.html";

    @FindBy(className = "product_sort_container")
    private WebElement productSortSelect;

    @FindBy(className = "inventory_list")
    private ArrayList<WebElement> inventoryList;

    ProductsPage(WebDriver driver) {
        super(driver);
        super.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get(BASE_URL + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith(URL), "The page could not be loaded! Found URL: " + currentUrl);
    }

    private Select getProductSortSelect(WebElement e) {
        return new Select(e);
    }

    public void clickProductSortSelect() throws NoSuchElementException {
        if (isElementVisible(productSortSelect)) {
            productSortSelect.click();
        } else {
            throw new NoSuchElementException("Could not find sort field!");
        }
    }

    public void selectProductSort(String sortValue) {
        clickProductSortSelect();
        getProductSortSelect(productSortSelect).selectByValue(sortValue);

//        switch (sortValue) {
//            case "az":
//                    getProductSortSelect(productSortSelect).selectByValue(sortValue);
//                break;
//            case "za":
//                break;
//            case "lohi":
//                break;
//            case "hilo":
//                break;
//            default:
//        }
    }

    private void getInventoryItems() {
        ArrayList<WebElement> i = inventoryList;
    }
}
// TODO: Get a product.. create an inventory class

