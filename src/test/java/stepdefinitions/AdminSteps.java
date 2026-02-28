package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import model.User;
import org.openqa.selenium.TimeoutException;
import utils.UserTestData;

public class AdminSteps extends BaseSteps {

    // <editor-fold desc="Ctor">
    public AdminSteps() {
        super();
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    @And("the admin approves the user")
    public void approveUser() {
        this.loginAdminAndPerformAction(() ->
                dashboardPage.approveUser(UserTestData.user)
        );
    }

    @When("the admin changes the user's role to {string}")
    public void changeUserRole(String userRole) {
        this.loginAdminAndPerformAction(() ->
                dashboardPage.changeUserRole(UserTestData.user, userRole)
        );
    }

    @When("the admin deletes the user")
    public void deleteUser() {
        this.loginAdminAndPerformAction(() ->
                dashboardPage.deleteUser(UserTestData.user)
        );
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private void loginAdminAndPerformAction(Runnable action) {
        User<Object> adminUser = new User<Object>("admin@gmail.com", "@12345678");

        // ensuring the user is on the login page
        try {
            loginPage.verifyLoginPageIsDisplayed();
        } catch (TimeoutException ex) {
            homePage.verifyHomePageIsDisplayed();

            homePage.clickLogin();

            loginPage.verifyLoginPageIsDisplayed();
        }

        loginPage.loginUser(adminUser);

        dashboardPage.validateNonUserDashboardIsDisplayed();

        action.run();

        if (dashboardPage.validateNonUserDashboardIsDisplayed())
            dashboardPage.logout();
    }
    // </editor-fold>

}
