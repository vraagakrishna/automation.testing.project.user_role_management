package driver.impl;

import driver.IBrowserCreator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxCreator implements IBrowserCreator {

    @Override
    public WebDriver createDriver(int width, int height, boolean headless) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        if (headless)
            firefoxOptions.addArguments("--headless");
        firefoxOptions.addArguments("--width=" + width);
        firefoxOptions.addArguments("--height=" + height);

        return new FirefoxDriver(firefoxOptions);
    }

}
