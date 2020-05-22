package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductPage extends BaseProductPage {

    private static final String ADD_TO_CART_TXT = "ADD TO CART";
    private static final String REMOVE_FROM_CART_TXT = "REMOVE";

    @FindBy(className = "inventory_details_back_button")
    private WebElement backButton;

    @FindBy(className = "inventory_details_img")
    private WebElement productImage;

    @FindBy(className = "inventory_details_name")
    private WebElement productName;

    @FindBy(className = "inventory_details_desc")
    private WebElement productDescription;

    @FindBy(className = "inventory_details_price")
    private WebElement productPrice;

    @FindBy(css = ".btn_primary.btn_inventory")
    private WebElement productAddToCartButton;

    @FindBy(css = ".btn_secondary.btn_inventory")
    private WebElement productRemoveFromCartButton;

    private String url = "/inventory-item.html?id=";

    //TODO: Add a separate class for the top bar. It should be invoked only when needed.

    public ProductPage(WebDriver driver, String productId) {
        super(driver);
        this.url = url + productId;
        super.initElements(driver, this);
    }


    @Override
    protected void load() {
        driver.get(BASE_URL + this.url);
    }

    @Override
    protected void isLoaded() throws Error {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith(this.url), "The page could not be loaded! Found URL: " + currentUrl);
    }


}
