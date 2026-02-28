package stepdefinitions;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import utils.DriverManager;

public class BaseSteps {

    // <editor-fold desc="Class Fields / Constants">
    protected final HomePage homePage;

    protected final RegisterPage registerPage;

    protected final LoginPage loginPage;

    protected final DashboardPage dashboardPage;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public BaseSteps() {
        WebDriver driver = DriverManager.getDriver();
        Scenario scenario = DriverManager.getScenario();
        this.homePage = new HomePage(driver, scenario);
        this.registerPage = new RegisterPage(driver, scenario);
        this.loginPage = new LoginPage(driver, scenario);
        this.dashboardPage = new DashboardPage(driver, scenario);
    }
    // </editor-fold>

}
