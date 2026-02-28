package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeSuite;
import utils.ConfigManager;
import utils.LoggerManager;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        dryRun = false,
        monochrome = false,
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        tags = "@ui"
)
public class UiTestRunner extends AbstractTestNGCucumberTests {

    @BeforeSuite
    public void beforeSuite() {
        // Set system properties using ConfigManager defaults if not already set
        System.setProperty("systeminfo.browser", ConfigManager.getBrowser());
        System.setProperty("systeminfo.os", ConfigManager.getOS());

        LoggerManager.initializeFileLogging();
    }

}
