package driver.impl;

import driver.IBrowserCreator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeCreator implements IBrowserCreator {

    @Override
    public WebDriver createDriver(int width, int height, boolean headless) {
        ChromeOptions chromeOptions = new ChromeOptions();

        // Disable password manager popups
        chromeOptions.addArguments("--disable-notifications");

        if (headless)
            chromeOptions.addArguments("--headless=new");
        chromeOptions.addArguments("--window-size=" + width + "," + height);

        return new ChromeDriver(chromeOptions);
    }

}
