package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import pages.RegisterPage;
import utils.DriverManager;
import utils.LoggerManager;
import utils.ScreenshotUtils;

import java.util.logging.Logger;

public class Hooks {

    private static final Logger logger = LoggerManager.getLogger(RegisterPage.class.getName());

    @Before("@ui")
    public void setUp(Scenario scenario) {
        String scenarioName = scenario.getName();

        if (scenarioName == null || scenarioName.isEmpty())
            return;

        logger.info("========================================");
        logger.info(">> Feature : " + getFeatureName(scenario));
        logger.info(">> Scenario: " + scenarioName);
        logger.info(">> Tags    : " + scenario.getSourceTagNames());
        logger.info("========================================");

        DriverManager.initDriver(scenario);
    }

    @After("@ui")
    public void afterStep(Scenario scenario) {
        if (scenario.getName() == null || scenario.getName()
                                                  .isEmpty())
            return;

        logger.info("---------- Scenario Result ----------");
        logger.info(">> Scenario: " + scenario.getName());

        if (scenario.isFailed()) {
            logger.info(">> Status  : FAILED");
            logger.info(">> Error(s):");
            logger.info(getErrorMessage(scenario));

            ScreenshotUtils screenshotUtils = new ScreenshotUtils();
            screenshotUtils.captureAndAttach(
                    DriverManager.getDriver(),
                    scenario,
                    "Failed test - " + scenario.getName()
            );
        } else {
            logger.info(">> Status  : PASSED");
        }

        logger.info("-------------------------------------");

        DriverManager.quitDriver();
    }

    private String getFeatureName(Scenario scenario) {
        String uri = scenario.getUri()
                             .toString();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    private String getErrorMessage(Scenario scenario) {
        return scenario.isFailed() ? "See stacktrace in report." : "";
    }

}
