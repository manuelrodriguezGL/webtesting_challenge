package pages;

import selenium.SeleniumBae;

public class Dummy {
    public static void main(String[] args) {

        SeleniumBae baseDriver = new SeleniumBae();
        LoginPage login = new LoginPage(baseDriver.setup("Chrome", false));

        try {
            login.get();
            ProductsInventoryPage page = login.login("standard_user", "secret_sauce");
            page.addToCartByQuantity(4);
            //page.addToCartById(4);
            if(page.removeFromCartByQuantity(3))
                System.out.println("Success!");
            Thread.sleep(2000); // Just a small pause to see the page
        } catch (Exception e) {
            System.out.println("Could not load page! - " + e.getMessage());
        } finally {
            baseDriver.quit();
        }
    }
}
