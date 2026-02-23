package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.logging.Logger;

public class HomePage extends BasePage {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = Logger.getLogger(HomePage.class.getName());

    private final By homePageTitle = By.id("overview-hero");

    private final By loginBtn = By.xpath("//button[@class='user-pill'][contains(., 'Login')]");
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public HomePage(WebDriver driver) {
        super(driver);
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public void verifyHomePageIsDisplayed() {
        String expectedHeading = "Master Test Automation";
        logger.info("Waiting for Home Page to be visible...");

        // wait until the element is visible
        WebElement element = this.getElement(homePageTitle);

        String heading = element.findElement(By.tagName("h1"))
                                .getText();
        logger.info(String.format("Heading found: %s", heading));

        Assert.assertEquals(heading, expectedHeading, "Heading does not match");
    }

    public void clickLogin() {
        logger.info("Waiting for Login button to be visible...");

        // wait until the element is visible
        WebElement element = this.getElement(loginBtn);

        logger.info("Element found; Clicking Login button");
        element.click();
    }
    // </editor-fold>

}
