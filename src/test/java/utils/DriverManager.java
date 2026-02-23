package utils;

import factory.BrowserFactory;
import org.openqa.selenium.WebDriver;

public class DriverManager {

    // <editor-fold desc="Class Fields / Constants">
    public static final String WEBSITE_URL = "https://ndosisimplifiedautomation.vercel.app/";

    private static WebDriver driver;
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public static void initDriver() {
        driver = new BrowserFactory().startBrowser("chrome", true, WEBSITE_URL);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null)
            driver.quit();
    }
    // </editor-fold>

}
