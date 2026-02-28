package pages;

import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LoggerManager;

import java.util.logging.Logger;

public class HomePage extends BasePage {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = LoggerManager.getLogger(HomePage.class.getName());

    private final By homePageTitle = By.id("overview-hero");

    private final By loginBtn = By.xpath("//button[@class='user-pill'][contains(., 'Login')]");
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public HomePage(WebDriver driver, Scenario scenario) {
        super(driver, scenario);
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public void verifyHomePageIsDisplayed() {
        logger.info("Waiting for Home Page to be visible...");
        String expectedHeading = "Master Test Automation";

        // wait until the element is visible
        WebElement element = this.getElement(homePageTitle);

        String heading = element.findElement(By.tagName("h1"))
                                .getText();
        logger.info(String.format("Heading found: %s", heading));

        Assert.assertEquals(heading, expectedHeading, "Heading does not match");
    }

    public void clickLogin() {
        logger.info("Clicking Login button");
        this.clickButton(loginBtn);
    }
    // </editor-fold>

}
