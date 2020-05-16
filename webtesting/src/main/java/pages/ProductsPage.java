package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsPage extends BasePage {

    private static final String URL = "/inventory.html";
    private static final String DEFAULT_SORT = "az";

    @FindBy(className = "product_sort_container")
    private WebElement productSortSelect;

    @FindBy(css = ".inventory_list>.inventory_item")
    private List<WebElement> inventoryList;

    @FindBy(css = ".inventory_item_img>a")
    private List<WebElement> itemURLs;

    @FindBy(css = ".inventory_item_img>a>img")
    private List<WebElement> itemImages;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item_desc")
    private List<WebElement> itemDescriptions;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(css = ".btn_primary.btn_inventory")
    private List<WebElement> itemButtons;

    private ArrayList<InventoryItem> inventoryItems;

    ProductsPage(WebDriver driver) {
        super(driver);
        super.initElements(driver, this);

        generateInventoryList();
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

    private void generateInventoryList() throws IndexOutOfBoundsException {
        // Check if there are items on the inventory
        if (!inventoryList.isEmpty()) {
            // Set the default product sort
            selectProductSort(DEFAULT_SORT);
            //Go thru the list and add items
            inventoryItems = new ArrayList<InventoryItem>();
            for (int i = 0; i < inventoryList.size(); i++) {
                String itemURL;
                String itemImage;
                String itemName;
                String itemDescription;
                String itemPrice;
                WebElement itemButton;

                itemURL = itemURLs.get(i).getAttribute("href");
                itemImage = itemImages.get(i).getAttribute("src");
                itemName = itemNames.get(i).getAttribute("innerText");
                itemDescription = itemDescriptions.get(i).getAttribute("innerText");
                itemPrice = itemPrices.get(i).getAttribute("innerText");
                itemButton = itemButtons.get(i);
                inventoryItems.add(new InventoryItem(itemURL, itemImage,
                        itemName, itemDescription, itemPrice, itemButton));
            }
        } else {
            throw new IndexOutOfBoundsException("Could not load inventory list! ");
        }
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

    public void selectProductSort(String sortValue) throws NoSuchElementException {
        if (isElementVisible(productSortSelect)) {
            getProductSortSelect(productSortSelect).selectByValue(sortValue);
        } else {
            throw new NoSuchElementException("Could not find sort field!");
        }
    }
}

