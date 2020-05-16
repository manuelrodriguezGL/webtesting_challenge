package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryItem extends BasePage {

    private static final String URL = "/inventory.html";

    @FindBy(css = ".inventory_item_img>a")
    private WebElement itemImage;

    @FindBy(className = "inventory_item_label")
    private WebElement itemLabel;

    @FindBy(className = "inventory_item_name")
    private WebElement itemName;

    @FindBy(className = "inventory_item_desc")
    private WebElement itemDescription;

    @FindBy(className = "inventory_item_price")
    private WebElement itemPrice;

    @FindBy(className = "btn_primary btn_inventory")
    private WebElement itemButton;

    public InventoryItem(WebDriver driver) {
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

    public String getItemImage() {
        return itemImage.getAttribute("href");
    }

    public WebElement getItemLabel() {
        return itemLabel;
    }

    public String getItemName() {
        return itemName.getAttribute("innerText");
    }

    public String getItemDescription() {
        return itemDescription.getAttribute("innerText");
    }

    public String getItemPrice() {
        return itemPrice.getAttribute("innerText");
    }

    public WebElement getItemButton() {
        return itemButton;
    }
}
