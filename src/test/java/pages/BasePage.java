package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AlertUtils;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class BasePage {

    // <editor-fold desc="Class Fields / Constants">
    protected final WebDriver driver;

    protected final AlertUtils alertUtils;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.alertUtils = new AlertUtils(driver);
    }
    // </editor-fold>

    // <editor-fold desc="Protected Methods">
    protected WebElement getElement(By by) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(visibilityOfElementLocated(by));
    }
    // </editor-fold>

}
