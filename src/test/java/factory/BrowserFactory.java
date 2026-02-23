package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class BrowserFactory {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = Logger.getLogger(BrowserFactory.class.getName());
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public BrowserFactory() {
    }
    // </editor-fold>

    // <editor-fold desc="Public methods">
    public WebDriver startBrowser(String browserName, boolean headless, String url) {
        logger.info(String.format("Starting browser %s", browserName));

        WebDriver driver;
        switch (browserName.toLowerCase()) {
            case "chrome" -> {
                ChromeOptions chromeOptions = new ChromeOptions();

                // Disable password manager popups
                chromeOptions.addArguments("--disable-notifications");

                if (headless)
                    chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--window-size=1920,1080");

                driver = new ChromeDriver(chromeOptions);
            }
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (headless)
                    firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--window-size=1920,1080");

                driver = new FirefoxDriver(firefoxOptions);
            }
            case "edge" -> {
                EdgeOptions edgeOptions = new EdgeOptions();

                Map<String, Object> edgePrefs = new HashMap<>();
                edgePrefs.put("safebrowsing.enabled", true);

                edgeOptions.setExperimentalOption("prefs", edgePrefs);

                if (headless)
                    edgeOptions.addArguments("--headless=new");
                edgeOptions.addArguments("--window-size=1920,1080");

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
