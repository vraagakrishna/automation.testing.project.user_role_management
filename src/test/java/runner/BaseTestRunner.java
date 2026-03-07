package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.BeforeSuite;
import utils.ConfigManager;
import utils.LoggerManager;

public abstract class BaseTestRunner extends AbstractTestNGCucumberTests {

    @BeforeSuite
    public void beforeSuite() {
        // Set system properties using ConfigManager defaults if not already set
        System.setProperty("systeminfo.browser", ConfigManager.getBrowser());
        System.setProperty("systeminfo.os", ConfigManager.getOS());
        System.setProperty("systeminfo.screenType", ConfigManager.getScreenType());

        LoggerManager.initializeFileLogging();
    }

}
