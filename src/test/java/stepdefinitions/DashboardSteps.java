package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class DashboardSteps extends BaseSteps {

    // <editor-fold desc="Ctor">
    public DashboardSteps() {
        super();
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    @Then("I should see the Dashboard")
    public void validateDashboard() {
        dashboardPage.verifyDashboardIsDisplayed();
    }

    @And("the user role should be {string}")
    public void validateUserRole(String userRole) {
        switch (userRole) {
            case "User" -> dashboardPage.validateUserDashboardIsDisplayed();
            case "Admin" -> dashboardPage.validateNonUserDashboardIsDisplayed();
            default -> throw new IllegalArgumentException("Unknown User role " + userRole);
        }
    }

    @And("I logout as the user")
    public void logoutUser() {
        dashboardPage.logout();
    }

    @And("I refresh the page")
    public void refreshPage() {
        dashboardPage.refreshPage();
    }
    // </editor-fold>

}
