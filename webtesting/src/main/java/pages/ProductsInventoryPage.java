package pages;

import constants.GlobalPageConstants;
import constants.InventoryPageConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import utils.CommonUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

public class ProductsInventoryPage extends BaseStorePage {

    private static final String URL = "/inventory.html";
    private static final String DEFAULT_SORT = "az";

    @FindBy(className = "product_label")
    private WebElement pageHeader;

    @FindBy(className = "product_sort_container")
    private WebElement productSortSelect;

    @FindBy(css = ".inventory_list>.inventory_item")
    private List<WebElement> inventoryList;

    @FindBy(css = ".inventory_item_img>a")
    private List<WebElement> itemURLs;

    @FindBy(css = ".inventory_item_img>a[id]")
    private List<WebElement> productUniqueIds;

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

    @FindBy(css = "a.shopping_cart_link.fa-layers.fa-fw")
    private WebElement shoppingCartButton;

    @FindBy(css = ".bm-burger-button>button")
    private WebElement burgerMenu;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutOption;

    public ProductsInventoryPage(WebDriver driver) {
        super(driver);
    }

    public ProductsInventoryPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    private By getInventoryItemLink(String id) {
        return By.cssSelector(CommonUtils.formatLocator("#item_{0}_title_link", id));
    }

    private By getInventoryItemImage(String id) {
        return By.cssSelector(CommonUtils.formatLocator("#item_{0}_img_link", id) + ">img");
    }

    private By getInventoryItemName(String id) {
        return By.cssSelector(CommonUtils.formatLocator("#item_{0}_title_link", id) + ">.inventory_item_name");
    }

    private By getInventoryItemDescription(String id) {
        return By.cssSelector(CommonUtils.formatLocator("#item_{0}_title_link", id) + "~.inventory_item_desc");
    }

    @Override
    protected void load() {
        //TODO
        // This loads can be implemented in the parent class, using "this" in the parent class
        // like "driver.get(BASE_URL + this.URL)", will refer to the
        System.out.println("Attempting to load Inventory page...");
        driver.get(BASE_URL + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        if (!isPageLoaded())
            throw new Error("Inventory page was not loaded!");
    }

    @Override
    public boolean isPageLoaded() {
        return isElementVisible(pageHeader);
    }

    public String assesInventoryItemValues(Object[][] values) {

        String errorMessages = "";

        ArrayList<String> items = new ArrayList<>();
        try {
            for (int row = 0; row < values.length; row++) {
                items.clear();
                items.add(itemURLs.get(row).getAttribute("href"));
                items.add(itemImages.get(row).getAttribute("src"));
                items.add(itemNames.get(row).getAttribute("innerText"));
                items.add(itemDescriptions.get(row).getAttribute("innerText"));
                items.add(itemPrices.get(row).getAttribute("innerText"));

                for (int col = 0; col < values[row].length; col++) {
                    if (itemURLs.get(row).getAttribute("href").contains(values[row][0].toString())) {
                        errorMessages += assesElementTextContains(items.get(col), values[row][col].toString());
                    }
                }

                errorMessages += assesElementTextEquals(itemAddToCartButtons.get(row), GlobalPageConstants.ADD_TO_CART_TXT);
            }

            errorMessages += assesElementTextEquals(pageHeader, InventoryPageConstants.INVENTORY_TITLE);
            errorMessages += assesUIElement(productSortSelect);

        } catch (Exception e) {
            errorMessages = e.getStackTrace().toString();
        }
        return errorMessages;
    }

    public boolean changeProductSort(String sortValue) throws NoSuchElementException {
        /*
            1. Change the sort
            2. Get the current first and last item
            3. Perform the sort action
            4. Compare the old first and last with current first and last
         */
        boolean result = false;
        String firstValue = "";
        String lastValue = "";
        ArrayList<Double> pricesList;

        // A workaround since enums are case sensitive, as well as Select values.
        String switchOption = sortValue.toUpperCase();

        // TODO Considerar collections
        // TODO Refactor para dividir responsabilidades entre metodos
        // TODO Clean code, Uncle Bob
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
            // Remove the $ character from each price
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

        return (quantity == getCartItems());
    }

    public boolean addToCartById(String id) {
        //TODO refactor with new IDs
        int i = 0;
        for (WebElement e : itemURLs) {
            if (e.getAttribute("href").contains(id)) {
                itemAddToCartButtons.get(i).click();
                return (itemRemoveFromCartButtons.get(itemRemoveFromCartButtons.size() - 1)
                        .getAttribute("innerText").equals(GlobalPageConstants.REMOVE_FROM_CART_TXT)
                        && itemRemoveFromCartButtons.size() == getCartItems());
            }
            i++;
        }

        return false;
    }

    public boolean removeFromCartByQuantity(int quantity) throws NoSuchElementException {

        if (quantity > itemRemoveFromCartButtons.size()) {
            throw new IndexOutOfBoundsException("Quantity value can't be greater than number of products added to cart");
        }

        int originalQuantity = getCartItems();
        int i = 1;
        for (WebElement e : itemRemoveFromCartButtons) {
            if (i > quantity) {
                break;
            }
            e.click();
            i++;
        }

        return (getCartItems() == 0 || getCartItems() + quantity == originalQuantity);
    }

    public boolean removeFromCartById(String id) {

        //TODO refactor with new IDs
        if (itemRemoveFromCartButtons.size() == 0) {
            throw new IndexOutOfBoundsException("There are no products added to cart");
        }

        // TODO Naming conventions en los contadores
        int i = 0;
        for (WebElement e : itemURLs) {
            if (e.getAttribute("href").contains(id)) {
                itemAddToCartButtons.get(i).click();
                return (itemAddToCartButtons.get(i).getAttribute("innerText").equals(GlobalPageConstants.ADD_TO_CART_TXT)
                        && (getCartItems() == 0 ||
                        itemRemoveFromCartButtons.size() == getCartItems()));
            }
            i++;
        }

        return false;
    }

    public ProductPage loadProductPageById(String id) throws NoSuchElementException {

        waitByLocator(getInventoryItemLink(id)).click();
        return new ProductPage(driver, id, BASE_URL);

    }

    public ShoppingCartPage loadShoppingCart() throws NoSuchElementException {
        shoppingCartButton.click();
        return new ShoppingCartPage(driver);
    }

    public LoginPage logout() throws NoSuchElementException {
        burgerMenu.click();
        if (isElementVisible(logoutOption))
            logoutOption.click();
        else
            throw new NoSuchElementException("Could not load logout option from menu!");
        return new LoginPage(driver, BASE_URL);
    }
}