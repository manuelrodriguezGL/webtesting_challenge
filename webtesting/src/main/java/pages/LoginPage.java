package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPage extends BasePage {

    @FindBy(tagName = "title")
    private WebElement title;

    public LoginPage(WebDriver driver)
    {
        super(driver);
        super.initElements(driver, this);
    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {
        if(!title.isDisplayed()){
            throw new Error("Page not loaded!");
        }
    }
}
