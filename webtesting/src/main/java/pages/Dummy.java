package pages;

import selenium.SeleniumBase;
import utils.ExcelFileReader;

import java.util.ArrayList;

public class Dummy {
    public static void main(String[] args) {

        SeleniumBase baseDriver = new SeleniumBase();
        LoginPage login = new LoginPage(baseDriver.setup("Chrome", false));

        try {
            login.get();
            ProductsInventoryPage page = login.login("standard_user", "secret_sauce");


            ArrayList<ArrayList<String>> arrayResult = ExcelFileReader.readFile("dataProviders/Inventory.xlsx",
                    "Inventory");

            System.out.println(page.assesInventoryItemValues(arrayResult));


            //page.addToCartByQuantity(4);
//            page.addToCartById(4);
//            ProductPage productPage = page.loadProductPageById(0);
//            productPage.clickAddToCartButton();
//            ShoppingCartPage cart = productPage.headerContainer.clickShoppingCartLink();
//
//            page = cart.clickContinueShoppingButton();
//            cart = page.headerContainer.clickShoppingCartLink();
//
//            CheckoutInformationPage checkoutPage1 = cart.clickCheckoutButton();
//
//            checkoutPage1.enterCustomerData("FirstName", "LastName", "11223");
//            CheckoutOverviewPage checkoutPage2 = checkoutPage1.continueCheckout();
//            boolean validation = checkoutPage2.getTotalAmount() == checkoutPage2.sumTotalAmount();
//
//            if(validation)
//                System.out.println("Success!");
//
////            page = checkoutPage2.cancelCheckout();
//
//            CheckoutFinishedPage finishedPage = checkoutPage2.finishCheckout();

            Thread.sleep(2000); // Just a small pause to see the page
        } catch (Exception e) {
            System.out.println("Could not load page! - " + e.getMessage());
        } finally {
            baseDriver.quit();
        }
    }
}
