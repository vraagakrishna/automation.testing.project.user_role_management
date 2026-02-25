package stepdefinitions;

import io.cucumber.java.en.And;
import model.User;
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
        User<Object> adminUser = new User<Object>("admin@gmail.com", "@12345678");

        loginPage.loginUser(adminUser);

        dashboardPage.validateAdminDashboardIsDisplayed();
        dashboardPage.approveUser(UserTestData.user);

        dashboardPage.logout();
    }
    // </editor-fold>

}
