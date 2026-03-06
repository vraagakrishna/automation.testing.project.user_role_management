package utils;

import factory.BrowserFactory;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

import static common.Constants.DEV_URL;

public class DriverManager {

    // <editor-fold desc="Class Fields / Constants">
    private static WebDriver driver;

    private static Scenario scenario;
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public static void initDriver(Scenario _scenario) {
        driver = new BrowserFactory().startBrowser(
                ConfigManager.getBrowser(),
                ConfigManager.getScreenType(),
                ConfigManager.isHeadless(),
                DEV_URL
        );
        scenario = _scenario;
    }

    public static Object[] createTempDriver() {
        WebDriver tempDriver = new BrowserFactory().startBrowser(
                ConfigManager.getBrowser(),
                ConfigManager.getScreenType(),
                ConfigManager.isHeadless(),
                DEV_URL
        );
        return new Object[]{tempDriver, scenario};
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static Scenario getScenario() {
        return scenario;
    }

    public static void quitDriver() {
        if (driver != null)
            driver.quit();
    }

    public static void quitDriver(WebDriver tempDriver) {
        if (tempDriver != null)
            tempDriver.quit();
    }
    // </editor-fold>

}
