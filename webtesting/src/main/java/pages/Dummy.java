package pages;

import selenium.SeleniumBae;

public class Dummy {
    public static void main(String[] args) {
        SeleniumBae baseDriver = new SeleniumBae();
        LoginPage login = new LoginPage(baseDriver.setup("Chrome", false));

        try {
            login.get();
        } catch (Exception e) {
            System.out.println("Could not load page! - " + e.getMessage());
        } finally {
            baseDriver.quit();
        }
    }
}
