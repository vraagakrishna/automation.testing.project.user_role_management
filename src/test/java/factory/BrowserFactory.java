package factory;

import driver.IBrowserCreator;
import driver.impl.ChromeCreator;
import driver.impl.EdgeCreator;
import driver.impl.FirefoxCreator;
import org.openqa.selenium.WebDriver;
import utils.LoggerManager;

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
    public static IBrowserCreator getBrowser(String browserName) {
        return switch (browserName.toLowerCase()) {
            case "chrome" -> new ChromeCreator();
            case "firefox" -> new FirefoxCreator();
            case "edge" -> new EdgeCreator();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        };
    }

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

        IBrowserCreator creator = BrowserFactory.getBrowser(browserName);

        WebDriver driver = creator.createDriver(width, height, headless);
        driver.get(url);
        return driver;
    }
    // </editor-fold>

}
