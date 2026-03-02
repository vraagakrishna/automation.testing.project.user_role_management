package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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

        dashboardPage.validateUserLoggedIn();
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

    @When("I modify the user role to {string} in storage")
    public void manipulateTokenRole(String userRole) {
        dashboardPage.manipulateTokenRole(userRole);
        dashboardPage.manipulateUserRole(userRole);
    }

    @When("I expire the JWT token in storage")
    public void expireToken() {
        dashboardPage.expireToken();
    }

    @When("I replace the JWT token with an invalid value")
    public void setInvalidToken() {
        dashboardPage.setInvalidToken();
    }

    @When("I remove the JWT signature in the storage")
    public void removeTokenSignature() {
        dashboardPage.removeTokenSignature();
    }

    @When("I modify the JWT algorithm to {string}")
    public void manipulateTokenAlgorithm(String newAlgo) {
        dashboardPage.manipulateTokenAlgorithm(newAlgo);
    }

    @When("I open a new browser tab")
    public void openNewBrowser() {
        dashboardPage.openNewBrowser();
    }

    @And("I switch to tab {int}")
    @When("I switch back to tab {int}")
    public void switchToTab(int tabNumber) {
        dashboardPage.switchToTab(tabNumber);
    }
    // </editor-fold>

}
