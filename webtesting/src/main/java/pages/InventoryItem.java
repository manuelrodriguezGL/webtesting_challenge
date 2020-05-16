package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryItem  {

    private String itemURL;
    private String itemImage;
    private String itemName;
    private String itemDescription;
    private String itemPrice;
    private WebElement itemButton;

    public InventoryItem(String itemURL, String itemImage, String itemName, String itemDescription, String itemPrice, WebElement itemButton) {
        this.itemURL = itemURL;
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemButton = itemButton;
    }

    public String getItemURL() {
        return itemURL;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public WebElement getItemButton() {
        return itemButton;
    }

    public void setItemButton(WebElement itemButton) {
        this.itemButton = itemButton;
    }
}
