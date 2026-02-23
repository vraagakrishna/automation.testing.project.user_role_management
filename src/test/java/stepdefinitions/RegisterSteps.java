package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.User;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.RegisterPage;
import utils.DriverManager;
import utils.UserTestData;

import java.util.Map;
import java.util.Objects;

public class RegisterSteps {

    // <editor-fold desc="Class Fields / Constants">
    private final RegisterPage registerPage;

    private final LoginPage loginPage;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public RegisterSteps() {
        WebDriver driver = DriverManager.getDriver();
        this.registerPage = new RegisterPage(driver);
        this.loginPage = new LoginPage(driver);
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    @Given("I attempt to register with the following user data:")
    public void registerUser(DataTable table) {
        Map<String, String> userData = table.asMaps(String.class, String.class)
                                            .get(0);

        User<Object> user = new User<>();
        UserTestData testData = new UserTestData();

        // Replace placeholders with real values
        userData.forEach((k, v) -> {
            if (Objects.equals(k, "first name")) {
                if (Objects.equals(v, "empty")) user.setFirstName("");
                if (Objects.equals(v, "valid")) user.setFirstName(testData.getFirstName());
            } else if (Objects.equals(k, "last name")) {
                if (Objects.equals(v, "empty")) user.setLastName("");
                if (Objects.equals(v, "valid")) user.setLastName(testData.getLastName());
            } else if (Objects.equals(k, "email")) {
                if (Objects.equals(v, "empty")) user.setEmail("");
                if (Objects.equals(v, "valid")) user.setEmail(testData.getEmail());
                if (Objects.equals(v, "invalid")) user.setEmail(testData.getLastName());
            } else if (Objects.equals(k, "password")) {
                if (Objects.equals(v, "empty")) user.setPassword("");
                if (Objects.equals(v, "valid")) user.setPassword(testData.getPassword());
                if (Objects.equals(v, "weak")) user.setPassword(testData.getWeakPassword());
            } else if (Objects.equals(k, "confirm password")) {
                if (Objects.equals(v, "empty")) user.setConfirmPassword("");
                if (Objects.equals(v, "valid")) user.setConfirmPassword(testData.getPassword());
                if (Objects.equals(v, "weak")) user.setConfirmPassword(testData.getWeakPassword());
                if (Objects.equals(v, "mismatch")) user.setConfirmPassword(testData.getLastName());
            } else if (Objects.equals(k, "group")) {
                if (Objects.equals(v, "valid")) user.setGroup(1);
                if (Objects.equals(v, "invalid")) user.setGroup(0);
            }
        });

        testData.setUser(user);

        this.registerPage.registerUser(user);
    }

    @Then("I should see an error message {string}")
    public void validateErrorMessage(String errorMessage) {
        this.registerPage.verifyErrorMessage(errorMessage);
    }

    @Given("I have valid user data")
    public void getValidUserData() {
        User<Object> user = new User<>();
        UserTestData testData = new UserTestData();

        user.setFirstName(testData.getFirstName());
        user.setLastName(testData.getLastName());
        user.setEmail(testData.getEmail());
        user.setPassword(testData.getPassword());
        user.setConfirmPassword(testData.getPassword());
        user.setGroup(1);

        testData.setUser(user);
    }

    @When("I register the user")
    public void registerValidUser() {
        this.registerPage.registerUser(UserTestData.user);
    }

    @Then("registration should be successful")
    public void validateSuccessRegistration() {
        String successMessage = "Registration submitted successfully. Your account is pending admin approval.";
        this.registerPage.verifyErrorMessage(successMessage);

        this.loginPage.verifyLoginPageIsDisplayed();

        this.loginPage.validateEmailAddress(UserTestData.user.getEmail());
    }
    // </editor-fold>

}
