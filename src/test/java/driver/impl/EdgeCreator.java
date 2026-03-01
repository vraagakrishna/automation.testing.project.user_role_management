package driver.impl;

import driver.IBrowserCreator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.HashMap;
import java.util.Map;

public class EdgeCreator implements IBrowserCreator {

    @Override
    public WebDriver createDriver(int width, int height, boolean headless) {
        EdgeOptions edgeOptions = new EdgeOptions();

        Map<String, Object> edgePrefs = new HashMap<>();
        edgePrefs.put("safebrowsing.enabled", true);

        edgeOptions.setExperimentalOption("prefs", edgePrefs);

        if (headless)
            edgeOptions.addArguments("--headless=new");
        edgeOptions.addArguments("--window-size=" + width + "," + height);

        return new EdgeDriver(edgeOptions);
    }
    
}
