package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.LoggerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class BrowserFactory {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = LoggerManager.getLogger(BrowserFactory.class.getName());
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public BrowserFactory() {
    }
    // </editor-fold>

    // <editor-fold desc="Public methods">
    public WebDriver startBrowser(String browserName, String screenType, boolean headless, String url) {
        logger.info("Starting browser " + browserName + " in screen type " + screenType);

        int width, height;
        switch (screenType.toLowerCase()) {
            case "mobile" -> {
                width = 372;
                height = 812;
            }
            case "tablet" -> {
                width = 768;
                height = 1024;
            }
            case "desktop" -> {
                width = 1920;
                height = 1080;
            }
            default -> throw new IllegalArgumentException("Unsupported screenType: " + screenType);
        }

        WebDriver driver;
        switch (browserName.toLowerCase()) {
            case "chrome" -> {
                ChromeOptions chromeOptions = new ChromeOptions();

                // Disable password manager popups
                chromeOptions.addArguments("--disable-notifications");

                if (headless)
                    chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--window-size=" + width + "," + height);

                driver = new ChromeDriver(chromeOptions);
            }
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (headless)
                    firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--width=" + width);
                firefoxOptions.addArguments("--height=" + height);

                driver = new FirefoxDriver(firefoxOptions);
            }
            case "edge" -> {
                EdgeOptions edgeOptions = new EdgeOptions();

                Map<String, Object> edgePrefs = new HashMap<>();
                edgePrefs.put("safebrowsing.enabled", true);

                edgeOptions.setExperimentalOption("prefs", edgePrefs);

                if (headless)
                    edgeOptions.addArguments("--headless=new");
                edgeOptions.addArguments("--window-size=" + width + "," + height);

                driver = new EdgeDriver(edgeOptions);
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        // removed because of window-size added to Options
        //driver.manage().window().maximize();
        driver.get(url);
        return driver;
    }
    // </editor-fold>

}
