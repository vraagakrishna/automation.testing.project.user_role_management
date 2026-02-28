package pages;

import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.AlertUtils;
import utils.ScreenshotUtils;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class BasePage {

    // <editor-fold desc="Class Fields / Constants">
    protected final WebDriver driver;

    protected final Scenario scenario;

    protected final AlertUtils alertUtils;

    protected final ScreenshotUtils screenshotUtils;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public BasePage(WebDriver driver, Scenario scenario) {
        this.driver = driver;
        this.scenario = scenario;
        this.alertUtils = new AlertUtils(driver);
        this.screenshotUtils = new ScreenshotUtils();
    }
    // </editor-fold>

    // <editor-fold desc="Protected Methods">
    protected WebElement getElement(By by) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(visibilityOfElementLocated(by));
    }

    protected void clickButton(By by) {
        WebElement btnElement = this.getElement(by);
        btnElement.click();
    }

    protected void verifyIfTextDisplayed(By by, String expectedMessage) {
        WebElement element = this.getElement(by);
        String actualMessage = element.getText();

        Assert.assertTrue(actualMessage.contains(expectedMessage), "Text does not match");
    }

    protected void enterKeys(By by, Object keys) {
        WebElement element = this.getElement(by);
        element.clear();
        element.sendKeys((CharSequence) keys);
    }

    protected boolean isElementVisible(By by) {
        List<WebElement> elements = driver.findElements(by);
        return !elements.isEmpty() && elements.get(0)
                                              .isDisplayed();
    }
    // </editor-fold>

}
