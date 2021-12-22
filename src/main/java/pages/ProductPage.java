package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BaseStorePage {

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

    // In this case, the URL is not a constant, since it changes based on product id
    private String url = "/inventory-item.html?id=";

    public ProductPage(WebDriver driver, String productId, String baseUrl) {
        super(driver, baseUrl);
        this.url = url + productId;
    }

    @Override
    protected void load() {
        logger.getLogger().info("Attempting to load Product detail page...");
        driver.get(baseUrl + this.url);
    }

    @Override
    protected void isLoaded() throws Error {
        if (!isPageLoaded())
            throw new Error("Product detail page was not loaded!");
    }

    @Override
    public boolean isPageLoaded() {
        return isElementVisible(productName);
    }

    public ProductsInventoryPage goBack() throws NoSuchElementException {
        botStyle.click(backButton);
        return new ProductsInventoryPage(driver, baseUrl);
    }

    public String getProductImageUrl() throws NoSuchElementException {
        return productImage.getAttribute("src");
    }

    public String getProductName() throws NoSuchElementException {
        return productName.getText();
    }

    public String getProductDescription() throws NoSuchElementException {
        return productDescription.getText();
    }

    public String getProductPrice() throws NoSuchElementException {
        return productPrice.getText();
    }

    public void clickAddToCartButton() throws NoSuchElementException {
        botStyle.click(productAddToCartButton);
    }

    public void clickRemoveButton() throws NoSuchElementException {
        botStyle.click(productRemoveFromCartButton);
    }


}
