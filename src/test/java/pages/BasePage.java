package pages;

import factory.NavigationFactory;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import pages.navigation.INavigation;
import utils.AlertUtils;
import utils.ScreenshotUtils;
import utils.UIActions;

public class BasePage extends UIActions {

    // <editor-fold desc="Class Fields / Constants">
    protected final AlertUtils alertUtils;

    protected final ScreenshotUtils screenshotUtils;

    protected INavigation navigation;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public BasePage(WebDriver driver, Scenario scenario) {
        super(driver, scenario);
        this.alertUtils = new AlertUtils(driver);
        this.screenshotUtils = new ScreenshotUtils();
        this.navigation = NavigationFactory.create(driver, scenario);
    }
    // </editor-fold>

}
