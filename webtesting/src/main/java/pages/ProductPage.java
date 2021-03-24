package pages;

import constants.GlobalPageConstants;
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
        System.out.println("Attempting to load Product detail page...");
        driver.get(BASE_URL + this.url);
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

    public ProductsInventoryPage goBack() {
        if (isElementVisible(backButton)) {
            backButton.click();
            return new ProductsInventoryPage(driver, BASE_URL);
        }
        return null;
    }

    public String getProductImageUrl() {
        return productImage.getAttribute("src");
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getProductDescription() {
        return productDescription.getText();
    }

    public String getProductPrice() {
        return productPrice.getText();
    }

    public boolean clickAddToCartButton() {
        int originalQuantity = getCartItems();
        if (isElementVisible(productAddToCartButton)) {
            productAddToCartButton.click();
            return (productRemoveFromCartButton.getAttribute("innerText").equals(GlobalPageConstants.REMOVE_FROM_CART_TXT)
                    && (getCartItems() == originalQuantity + 1));
        }
        return false;
    }

    public boolean clickRemoveButton() {
        //TODO
        // I think this method is to click on the Remove button, and other methods should be created to get the
        // productAddToCartButton.getAttribute("innerText") and headerContainer.getCartItems(), and do the validation
        // on the test case. It would apply on several PageObjects in the project
        int originalQuantity = getCartItems();
        if (isElementVisible(productRemoveFromCartButton)) {
            productRemoveFromCartButton.click();
            return (productAddToCartButton.getAttribute("innerText").equals(GlobalPageConstants.ADD_TO_CART_TXT)
                    && (getCartItems() == originalQuantity - 1));
        }
        return false;
    }


}
