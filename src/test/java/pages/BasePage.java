package pages;

import factory.NavigationFactory;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import pages.navigation.INavigation;
import utils.*;

public class BasePage extends UIActions {

    // <editor-fold desc="Class Fields / Constants">
    protected final AlertUtils alertUtils;

    protected final ScreenshotUtils screenshotUtils;

    protected final JavascriptExecutorUtils javascriptExecutorUtils;

    protected final SoftAssert softAssert = SoftAssertManager.getSoftAssert();

    protected INavigation navigation;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public BasePage(WebDriver driver, Scenario scenario) {
        super(driver, scenario);
        this.alertUtils = new AlertUtils(driver);
        this.screenshotUtils = new ScreenshotUtils();
        this.javascriptExecutorUtils = new JavascriptExecutorUtils(driver);
        this.navigation = NavigationFactory.create(driver, scenario);
    }
    // </editor-fold>

}
