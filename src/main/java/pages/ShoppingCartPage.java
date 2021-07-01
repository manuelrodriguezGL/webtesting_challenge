package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.CommonUtils;

import java.util.List;
import java.util.NoSuchElementException;

public class ShoppingCartPage extends BaseStorePage {

    private static final String URL = "/cart.html";

    @FindBy(id = "cart_contents_container")
    private WebElement cartContentsContainer;

    @FindBy(className = "cart_quantity_label")
    private WebElement cartQuantityLabel;

    @FindBy(className = "cart_desc_label")
    private WebElement cartDescLabel;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItemsList;

    @FindBy(className = "cart_quantity")
    private List<WebElement> cartQuantityList;

    @FindBy(css = ".cart_item_label>a")
    private List<WebElement> cartItemLinkList;

    @FindBy(css = ".cart_item_label>a>.inventory_item_name")
    private List<WebElement> cartItemNameList;

    @FindBy(css = ".cart_item_label>.inventory_item_desc")
    private List<WebElement> cartItemDescList;

    @FindBy(css = ".item_pricebar>.inventory_item_price")
    private List<WebElement> cartItemPricesList;

    @FindBy(className = "inventory_item_price")
    private WebElement itemPrice;

    @FindBy(css = ".btn_secondary.cart_button")
    private List<WebElement> removeFromCartButtonsList;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public ShoppingCartPage(WebDriver driver, String base_url) {
        super(driver, base_url);
    }

    private By getProductNameLocator(String id) {
        return By.cssSelector(CommonUtils.formatLocator("#item_{0}_title_link", id) + ">.inventory_item_name");
    }

    private By getProductDescriptionLocator(String id) {
        return By.cssSelector(CommonUtils.formatLocator("#item_{0}_title_link", id) + "~.inventory_item_desc");
    }

    private By getProductPriceLocator(String id) {
        return By.cssSelector(CommonUtils.formatLocator(
                "#item_{0}_title_link~.item_pricebar>.inventory_item_price", id));
    }

    private By getProductRemoveButton(String id) {
        return By.cssSelector(CommonUtils.formatLocator(
                "#item_{0}_title_link~.item_pricebar>.btn_secondary.cart_button", id));
    }

    @Override
    protected void load() {
        System.out.println("Attempting to load Shopping Cart page...");
        driver.get(base_url + URL);
    }

    @Override
    protected void isLoaded() throws Error {
        if (!isPageLoaded())
            throw new Error("Shopping cart page was not loaded!");
    }

    @Override
    public boolean isPageLoaded() {
        return isElementVisible(cartContentsContainer);
    }

    public String getProductName(String id) {
        return botStyle.waitByLocator((getProductNameLocator(id))).getText();
    }

    public String getProductDescription(String id) {
        return botStyle.waitByLocator((getProductDescriptionLocator(id))).getText();
    }

    public String getProductPrice(String id) {
        return botStyle.waitByLocator(getProductPriceLocator(id)).getText();
    }

    public String getProductRemoveButtonText(String id) {
        return botStyle.waitByLocator(getProductRemoveButton(id)).getText();
    }

    public void clickRemoveButton(String id) {
        botStyle.click(getProductRemoveButton(id));
    }

    public void clickAllRemoveButtons() {
        for (WebElement e : removeFromCartButtonsList) {
            botStyle.click(e);
        }
    }

    public boolean isCartEmpty() throws NoSuchElementException {
        return cartItemsList.size() == 0;
    }

    public ProductsInventoryPage clickContinueShoppingButton() {
        botStyle.click(continueShoppingButton);
        return new ProductsInventoryPage(driver, base_url);
    }

    public CheckoutInformationPage clickCheckoutButton() {
        botStyle.click(checkoutButton);
        return new CheckoutInformationPage(driver, base_url);
    }
}
