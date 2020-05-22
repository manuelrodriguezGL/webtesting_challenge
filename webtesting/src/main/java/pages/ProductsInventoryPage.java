package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Comparator;
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

public class ProductsInventoryPage extends BaseProductPage {

    private static final String URL = "/inventory.html";
    private static final String DEFAULT_SORT = "az";
    private static final String ADD_TO_CART_TXT = "ADD TO CART";
    private static final String REMOVE_FROM_CART_TXT = "REMOVE";

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
    private List<WebElement> itemAddToCartButtons;

    @FindBy(css = ".btn_secondary.btn_inventory")
    private List<WebElement> itemRemoveFromCartButtons;

    @FindBy(xpath = "//div[@class='inventory_item']/div[@class='pricebar']/div[@class='inventory_item_price']/following-sibling::button")
    private List<WebElement> itemButtonList;

    @FindBy(css = "[data-icon=\"shopping-cart\"]")
    private WebElement shoppingCartIcon;

    @FindBy(css = ".fa-layers-counter.shopping_cart_badge")
    private WebElement cartItemsIcon;

    private ArrayList<InventoryItem> inventoryItems;

    ProductsInventoryPage(WebDriver driver) {
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
                itemButton = itemAddToCartButtons.get(i);
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
        ArrayList<Double> pricesList;

        // A workaround since enums are case sensitive, as well as Select values.
        String switchOption = sortValue.toUpperCase();

        switch (SortValues.valueOf(switchOption)) {
            case AZ:
                itemNames.sort(Comparator.comparing(item -> item.getAttribute("innerText")));
                // Gotta save the values, since itemNames changes dynamically
                firstValue = itemNames.get(0).getAttribute("innerText");
                lastValue = itemNames.get(itemNames.size() - 1).getAttribute("innerText");
                changeValueProductSortSelect(sortValue.toLowerCase());
                result = (itemNames.get(0).getAttribute("innerText").equals(firstValue)
                        && itemNames.get(itemNames.size() - 1).getAttribute("innerText").equals(lastValue));
                break;
            case ZA:
                itemNames.sort(Comparator.comparing(item -> item.getAttribute("innerText")));
                // Gotta save the values, since itemNames changes dynamically
                firstValue = itemNames.get(0).getAttribute("innerText");
                lastValue = itemNames.get(itemNames.size() - 1).getAttribute("innerText");
                changeValueProductSortSelect(sortValue.toLowerCase());
                result = (itemNames.get(0).getAttribute("innerText").equals(lastValue)
                        && itemNames.get(itemNames.size() - 1).getAttribute("innerText").equals(firstValue));
                break;
            case LOHI:
                // Get a list of prices as Doubles
                pricesList = getPricesList(itemPrices);
                pricesList.sort(Comparator.naturalOrder());
                // Gotta save the values, since itemPrices changes dynamically
                firstValue = "$" + pricesList.get(0).toString();
                lastValue = "$" + pricesList.get(pricesList.size() - 1).toString();
                changeValueProductSortSelect(sortValue.toLowerCase());
                result = (itemPrices.get(0).getAttribute("innerText").equals(firstValue)
                        && itemPrices.get(itemPrices.size() - 1).getAttribute("innerText").equals(lastValue));
                break;
            case HILO:
                // Get a list of prices as Doubles
                pricesList = getPricesList(itemPrices);
                pricesList.sort(Comparator.naturalOrder());
                // Gotta save the values, since itemPrices changes dynamically
                firstValue = "$" + pricesList.get(0).toString();
                lastValue = "$" + pricesList.get(pricesList.size() - 1).toString();
                changeValueProductSortSelect(sortValue.toLowerCase());
                result = (itemPrices.get(0).getAttribute("innerText").equals(lastValue)
                        && itemPrices.get(itemPrices.size() - 1).getAttribute("innerText").equals(firstValue));
                break;
            default:
                result = false;
        }

        return result;
    }

    private ArrayList<Double> getPricesList(List<WebElement> list) {
        ArrayList<Double> pricesList = new ArrayList<>();
        for (WebElement e : list) {
            pricesList.add(Double.parseDouble(e.getAttribute("innerText").substring(1)));
        }
        return pricesList;
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

    public boolean addToCartByQuantity(int quantity) throws NoSuchElementException {

        if (quantity > inventoryList.size()) {
            throw new IndexOutOfBoundsException("Quantity value can't be greater than number of products");
        }

        int i = 1;
        for (WebElement e : itemAddToCartButtons) {
            if (i > quantity) {
                break;
            }
            e.click();
            i++;
        }

        return (quantity == headerContainer.getCartItems());
    }

    public boolean addToCartById(int id) {

        String numberToFind = String.valueOf(id);

        int i = 0;
        for (WebElement e : itemURLs) {
            if (e.getAttribute("href").contains(numberToFind)) {
                itemAddToCartButtons.get(i).click();
                return (itemRemoveFromCartButtons.get(itemRemoveFromCartButtons.size() - 1)
                        .getAttribute("innerText").equals(REMOVE_FROM_CART_TXT)
                        && itemRemoveFromCartButtons.size() == headerContainer.getCartItems());
            }
            i++;
        }

        return false;
    }

    public boolean removeFromCartByQuantity(int quantity) throws NoSuchElementException {

        if (quantity > itemRemoveFromCartButtons.size()) {
            throw new IndexOutOfBoundsException("Quantity value can't be greater than number of products added to cart");
        }

        int originalQuantity = headerContainer.getCartItems();
        int i = 1;
        for (WebElement e : itemRemoveFromCartButtons) {
            if (i > quantity) {
                break;
            }
            e.click();
            i++;
        }

        return (headerContainer.getCartItems() == 0 || headerContainer.getCartItems() + quantity == originalQuantity);
    }

    public boolean removeFromCartById(int id) {

        if (itemRemoveFromCartButtons.size() == 0) {
            throw new IndexOutOfBoundsException("There are no products added to cart");
        }

        String numberToFind = String.valueOf(id);
        int i = 0;
        for (WebElement e : itemURLs) {

            if (e.getAttribute("href").contains(numberToFind)) {
                itemButtonList.get(i).click();
                return (itemAddToCartButtons.get(i).getAttribute("innerText").equals(ADD_TO_CART_TXT)
                        && (headerContainer.getCartItems() == 0 ||
                        itemRemoveFromCartButtons.size() == headerContainer.getCartItems()));
            }
            i++;
        }

        return false;
    }

    public int getCartItems() throws NoSuchElementException {
        try {
            return (Integer.parseInt(cartItemsIcon.getAttribute("innerText")));
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    public ProductPage loadProductPageById(int id) throws NoSuchElementException {
        String numberToFind = String.valueOf(id);
        for (WebElement e : itemURLs) {
            String productId = e.getAttribute("href");
            if (productId.contains(numberToFind)) {
                e.click();
                return new ProductPage(driver, productId);
            }
        }
        return null;
    }
}