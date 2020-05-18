package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

enum SortValues {
    AZ("az"),
    ZA("za"),
    LOHI("lohi"),
    HILO("hilo");

    private final String sortCode;

    SortValues(String sortCode) {
        this.sortCode = sortCode.toLowerCase();
    }

    public String getSortCode() {
        return this.sortCode.toLowerCase();
    }
}

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

    private ArrayList<InventoryItem> generateInventoryList() throws IndexOutOfBoundsException {
        // Check if there are items on the inventory
        inventoryItems = new ArrayList<InventoryItem>();

        if (!inventoryList.isEmpty()) {
            // Set the default product sort
            changeValueProductSortSelect(DEFAULT_SORT);
            //Go thru the list and add items

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

        return inventoryItems;
    }

    public boolean changeProductSort(String sortValue) throws NoSuchElementException {
        /*
            1. Change the sort
            2. Get the current first an last item
            3. Perform the sort action
            4. Compare the old first and last with current first and last
         */
        boolean result = false;
        String firstValue = "";
        String lastValue = "";
        // A workaround since enums are case sensitive, as well as Select values.
        String switchOption = sortValue.toUpperCase();

        try {
            switch (SortValues.valueOf(switchOption)) {
                case AZ:
                    changeValueProductSortSelect(SortValues.ZA.getSortCode());
                    firstValue = itemNames.get(0).getAttribute("innerText");
                    lastValue = itemNames.get(itemNames.size() - 1).getAttribute("innerText");
                    changeValueProductSortSelect(sortValue.toLowerCase());
                    result = (itemNames.get(0).getAttribute("innerText").equals(lastValue)
                            && itemNames.get(itemNames.size() - 1).getAttribute("innerText").equals(firstValue));
                    break;
                case ZA:
                    changeValueProductSortSelect(SortValues.AZ.getSortCode());
                    firstValue = itemNames.get(0).getAttribute("innerText");
                    lastValue = itemNames.get(itemNames.size() - 1).getAttribute("innerText");
                    changeValueProductSortSelect(sortValue.toLowerCase());
                    result = (itemNames.get(0).getAttribute("innerText").equals(lastValue)
                            && itemNames.get(itemNames.size() - 1).getAttribute("innerText").equals(firstValue));
                    break;
                case LOHI:
                    changeValueProductSortSelect(SortValues.HILO.getSortCode());
                    firstValue = itemPrices.get(0).getAttribute("innerText");
                    lastValue = itemPrices.get(itemPrices.size() - 1).getAttribute("innerText");
                    changeValueProductSortSelect(sortValue.toLowerCase());
                    result = (itemPrices.get(0).getAttribute("innerText").equals(lastValue)
                            && itemPrices.get(itemPrices.size() - 1).getAttribute("innerText").equals(firstValue));
                    break;
                case HILO:
                    changeValueProductSortSelect(SortValues.LOHI.getSortCode());
                    firstValue = itemPrices.get(0).getAttribute("innerText");
                    lastValue = itemPrices.get(itemPrices.size() - 1).getAttribute("innerText");
                    changeValueProductSortSelect(sortValue.toLowerCase());
                    result = (itemPrices.get(0).getAttribute("innerText").equals(lastValue)
                            && itemPrices.get(itemPrices.size() - 1).getAttribute("innerText").equals(firstValue));
                    break;
                default:
                    result = false;
            }
        } catch (Exception e) {
            throw e;
        }

        return result;
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

    public void changeValueProductSortSelect(String sortValue) throws NoSuchElementException {
        if (isElementVisible(productSortSelect)) {
            getProductSortSelect(productSortSelect).selectByValue(sortValue);
        } else {
            throw new NoSuchElementException("Could not find sort field!");
        }
    }
}