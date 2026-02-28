package utils;

import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {

    // <editor-fold desc="Public Methods">
    public void captureAndAttach(WebDriver driver, Scenario scenario, String label) {
        byte[] screenshotBytes = this.takeScreenshot(driver);

        scenario.attach(
                screenshotBytes,
                "image/png",
                "Image: " + label
        );
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private byte[] takeScreenshot(WebDriver driver) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        return takesScreenshot.getScreenshotAs(OutputType.BYTES);
    }
    // </editor-fold>

}
