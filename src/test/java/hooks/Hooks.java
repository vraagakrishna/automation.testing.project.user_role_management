package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;
import pages.RegisterPage;
import utils.*;

import java.util.logging.Logger;

public class Hooks {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = LoggerManager.getLogger(RegisterPage.class.getName());
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    @Before("@ui")
    public void setUp(Scenario scenario) {
        this.logBeforeScenario(scenario);

        DriverManager.initDriver(scenario);
    }

    @Before("@email")
    public void setUpEmailTests(Scenario scenario) {
        this.logBeforeScenario(scenario);

        String browser = ConfigManager.getBrowser();
        String screen = ConfigManager.getScreenType();

        boolean allowed = ("chrome".equals(browser) && "desktop".equals(screen)) ||
                ("chrome".equals(browser) && "mobile".equals(screen));

        if (!allowed) {
            String skipMsg = "Skipping email test for " + browser + " + " + screen;
            logger.info(skipMsg);
            throw new SkipException(skipMsg);
        }

        DriverManager.initDriver(scenario);
    }

    @After("@ui or @email")
    public void afterStep(Scenario scenario) {
        SoftAssert softAssert = SoftAssertManager.getSoftAssert();
        AssertionError softAssertionError = null;

        try {
            softAssert.assertAll();  // will throw if any soft assertions failed
        } catch (AssertionError ex) {
            softAssertionError = ex;
        } finally {
            SoftAssertManager.remove();  // clean up thread-local
        }

        if (scenario.getName() == null || scenario.getName()
                                                  .isEmpty())
            return;

        logger.info("---------- Scenario Result ----------");
        logger.info(">> Scenario: " + scenario.getName());

        switch (scenario.getStatus()) {
            case FAILED:
                logger.info(">> Status  : FAILED");
                logger.info(">> Error(s):");
                logger.info(getErrorMessage(scenario));

                ScreenshotUtils screenshotUtils = new ScreenshotUtils();
                screenshotUtils.captureAndAttach(
                        DriverManager.getDriver(),
                        scenario,
                        "Failed test - " + scenario.getName()
                );
                break;

            case PASSED:
                logger.info(">> Status  : PASSED");
                break;

            case SKIPPED:
                logger.info(">> Status  : SKIPPED");
                break;

            default:
                logger.info(">> Status  : UNKNOWN");
                break;
        }

        logger.info("-------------------------------------");

        DriverManager.quitDriver();

        // fail scenario if any soft assertions failed
        if (softAssertionError != null)
            throw softAssertionError;
    }

    @After("@email")
    public void afterEmailTests() {
        if (UserTestData.inbox != null)
            UserTestData.emailService.deleteInbox(UserTestData.inbox.getId());
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private String getFeatureName(Scenario scenario) {
        String uri = scenario.getUri()
                             .toString();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    private String getErrorMessage(Scenario scenario) {
        return scenario.isFailed() ? "See stacktrace in report." : "";
    }

    private void logBeforeScenario(Scenario scenario) {
        String scenarioName = scenario.getName();

        if (scenarioName == null || scenarioName.isEmpty())
            return;

        logger.info("========================================");
        logger.info(">> Feature : " + getFeatureName(scenario));
        logger.info(">> Scenario: " + scenarioName);
        logger.info(">> Tags    : " + scenario.getSourceTagNames());
        logger.info("========================================");
    }
    // </editor-fold>

}
