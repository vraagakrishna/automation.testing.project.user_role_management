package stepdefinitions;

import io.cucumber.java.en.Then;

public class DashboardSteps extends BaseSteps {

    // <editor-fold desc="Ctor">
    public DashboardSteps() {
        super();
    }
    // </editor-fold>

    // <editor-fold desc="Ctor">
    @Then("I should see the Dashboard")
    public void validateDashboard() {
        dashboardPage.verifyDashboardIsDisplayed();
    }
    // </editor-fold>

}
