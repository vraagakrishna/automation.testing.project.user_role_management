package factory;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import pages.navigation.DesktopNavigation;
import pages.navigation.INavigation;
import pages.navigation.MobileNavigation;
import utils.ConfigManager;

public class NavigationFactory {

    public static INavigation create(WebDriver driver, Scenario scenario) {
        String screenType = ConfigManager.getScreenType();

        if (screenType.equalsIgnoreCase("desktop"))
            return new DesktopNavigation(driver, scenario);
        
        return new MobileNavigation(driver, scenario);
    }

}
