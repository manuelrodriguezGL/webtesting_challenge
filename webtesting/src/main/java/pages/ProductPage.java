package pages;

import constants.GlobalPageConstants;
import constants.ProductPageConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductPage extends BaseProductPage {

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

    public ProductPage(WebDriver driver, String productId) {
        super(driver);
        this.url = url + productId;
        super.initElements(driver, this);
    }


    @Override
    protected void load() {
        System.out.println("Attempting to load Product detail page...");
        driver.get(BASE_URL + this.url);
    }

    @Override
    protected void isLoaded() throws Error {
        if(!isPageLoaded())
            throw new Error("Product detail page was not loaded!");
    }

    @Override
    public boolean isPageLoaded() {
        return isElementVisible(productName);
    }

    public ProductsInventoryPage goBack() {
        if (isElementVisible(backButton)) {
            backButton.click();
            return new ProductsInventoryPage(driver);
        }
        return null;
    }

    //TODO Usar listas en lugar de una concatenacion
    public String assesProductValues(String imageUrl, String name, String description, String price) {

        String errorMessages = "";

        try {
            errorMessages += assesElementTextContains(productImage.getAttribute("src"), imageUrl);
            errorMessages += assesElementTextEquals(productName, name);
            errorMessages += assesElementTextEquals(productDescription, description);
            errorMessages += assesElementTextEquals(productPrice, price);

            errorMessages += assesElementTextEquals(productAddToCartButton, GlobalPageConstants.ADD_TO_CART_TXT);
            errorMessages += assesElementTextEquals(backButton, ProductPageConstants.PRODUCT_BACK_BUTTON_TXT);

        } catch (Exception e) {
            errorMessages = e.getStackTrace().toString();
        }
        return errorMessages;
    }

    public boolean clickAddToCartButton() {
        int originalQuantity = headerContainer.getCartItems();
        if (isElementVisible(productAddToCartButton)) {
            productAddToCartButton.click();
            return (productRemoveFromCartButton.getAttribute("innerText").equals(GlobalPageConstants.REMOVE_FROM_CART_TXT)
                    && (headerContainer.getCartItems() == originalQuantity + 1));
        }
        return false;
    }

    public boolean clickRemoveButton() {
        int originalQuantity = headerContainer.getCartItems();
        if (isElementVisible(productRemoveFromCartButton)) {
            productRemoveFromCartButton.click();
            return (productAddToCartButton.getAttribute("innerText").equals(GlobalPageConstants.ADD_TO_CART_TXT)
                    && (headerContainer.getCartItems() == originalQuantity - 1));
        }
        return false;
    }


}
