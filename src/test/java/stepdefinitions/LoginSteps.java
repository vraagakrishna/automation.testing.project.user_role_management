package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.User;
import org.openqa.selenium.TimeoutException;
import utils.UserTestData;

import java.util.Map;
import java.util.Objects;

public class LoginSteps extends BaseSteps {

    // <editor-fold desc="Ctor">
    public LoginSteps() {
        super();
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    @Given("I attempt to login with the following user data:")
    public void registerUser(DataTable table) {
        Map<String, String> userData = table.asMaps(String.class, String.class)
                                            .get(0);

        User<Object> user = new User<>();
        UserTestData testData = new UserTestData();

        // Replace placeholders with real values
        userData.forEach((k, v) -> {
            if (Objects.equals(k, "email")) {
                if (Objects.equals(v, "empty")) user.setEmail("");
                if (Objects.equals(v, "valid")) user.setEmail(testData.getEmail());
                if (Objects.equals(v, "invalid")) user.setEmail(testData.getLastName());
            } else if (Objects.equals(k, "password")) {
                if (Objects.equals(v, "empty")) user.setPassword("");
                if (Objects.equals(v, "valid")) user.setPassword(testData.getPassword());
            }
        });

        testData.setUser(user);

        loginPage.loginUser(user);
    }

    @Then("I should see a login error message {string}")
    public void validateErrorMessage(String errorMessage) {
        loginPage.verifyErrorMessage(errorMessage);
    }

    @When("I attempt to login with the user")
    @And("I login as the new user")
    public void loginNewUser() {
        try {
            loginPage.verifyLoginPageIsDisplayed();
        } catch (TimeoutException ex) {
            // if error, then login page is not displayed
            homePage.clickLogin();
            loginPage.verifyLoginPageIsDisplayed();
        }
        loginPage.loginUser(UserTestData.user);
    }

    @Then("login should fail")
    public void newlyLoginFailed() {
        loginPage.verifyErrorMessage("Invalid credentials. Please try again.");
    }

    // </editor-fold>

}
