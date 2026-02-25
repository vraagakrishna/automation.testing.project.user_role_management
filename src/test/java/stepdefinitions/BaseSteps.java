package stepdefinitions;

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
        this.homePage = new HomePage(driver);
        this.registerPage = new RegisterPage(driver);
        this.loginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
    }
    // </editor-fold>

}
