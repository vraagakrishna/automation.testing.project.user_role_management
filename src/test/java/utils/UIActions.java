package utils;

import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class UIActions {

    // <editor-fold desc="Class Fields / Constants">
    protected final WebDriver driver;

    protected final Scenario scenario;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public UIActions(WebDriver driver, Scenario scenario) {
        this.driver = driver;
        this.scenario = scenario;
    }
    // </editor-fold>

    // <editor-fold desc="Protected Methods">
    protected WebElement getElement(By by) {
        return new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(visibilityOfElementLocated(by));
    }

    protected void clickButton(By by) {
        this.getElement(by)
            .click();
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

    protected WebElement waitForDropdownToHaveOptions(By by) {
        WebElement dropdown = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(elementToBeClickable(by));

        new WebDriverWait(driver, Duration.ofSeconds(20)).until(d ->
                new Select(dropdown).getOptions()
                                    .size() > 1);

        return dropdown;
    }
    // </editor-fold>

}
