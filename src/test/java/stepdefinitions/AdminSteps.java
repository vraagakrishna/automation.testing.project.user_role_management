package stepdefinitions;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.User;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import pages.HomePage;
import pages.LoginPage;
import utils.DriverManager;
import utils.ScreenshotUtils;
import utils.UserTestData;

import java.util.function.Consumer;

public class AdminSteps extends BaseSteps {

    // <editor-fold desc="Ctor">
    public AdminSteps() {
        super();
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    @And("the admin approves the user")
    public void approveUser() {
        this.loginAdminAndPerformAction(adminDashboardPage ->
                adminDashboardPage.approveUser(UserTestData.user)
        );
    }

    @When("the admin changes the user's role to {string}")
    public void changeUserRole(String userRole) {
        this.loginAdminAndPerformAction(adminDashboardPage ->
                adminDashboardPage.changeUserRole(UserTestData.user, userRole)
        );
    }

    @When("the admin deletes the user")
    public void deleteUser() {
        this.loginAdminAndPerformAction(adminDashboardPage ->
                adminDashboardPage.deleteUser(UserTestData.user)
        );
    }

    @When("the admin deactivates the user")
    public void deactivateUser() {
        this.loginAdminAndPerformAction(adminDashboardPage ->
                adminDashboardPage.deactivateUser(UserTestData.user)
        );
    }

    @Then("I navigate to the Admin Panel")
    public void openAdminPanel() {
        dashboardPage.openAdminPanel();
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private void loginAdminAndPerformAction(Consumer<DashboardPage> action) {
        Object[] tempDriver = DriverManager.createTempDriver();
        WebDriver adminDriver = (WebDriver) tempDriver[0];
        Scenario scenario = (Scenario) tempDriver[1];

        try {
            LoginPage adminLoginPage = new LoginPage(adminDriver, scenario);
            HomePage adminHomePage = new HomePage(adminDriver, scenario);
            DashboardPage adminDashboardPage = new DashboardPage(adminDriver, scenario);

            User<Object> adminUser = new User<Object>(
                    System.getProperty("ADMIN_EMAIL", System.getenv("ADMIN_EMAIL")),
                    System.getProperty("ADMIN_PASSWORD", System.getenv("ADMIN_PASSWORD"))
            );

            adminHomePage.verifyHomePageIsDisplayed();

            adminHomePage.clickLogin();

            adminLoginPage.verifyLoginPageIsDisplayed();

            adminLoginPage.loginUser(adminUser.getEmail(), adminUser.getPassword());

            adminDashboardPage.validateNonUserDashboardIsDisplayed();

            action.accept(adminDashboardPage);

            if (adminDashboardPage.validateNonUserDashboardIsDisplayed())
                adminDashboardPage.logout();
        } catch (Throwable t) {
            // Capture screenshot BEFORE killing driver
            ScreenshotUtils screenshotUtils = new ScreenshotUtils();
            screenshotUtils.captureAndAttach(
                    adminDriver,
                    scenario,
                    "Admin session failure"
            );

            throw t; // rethrow so scenario still fails
        } finally {
            // clean up the temp admin session
            DriverManager.quitDriver(adminDriver);
        }
    }
    // </editor-fold>

}
