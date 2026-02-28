package pages;

import io.cucumber.java.Scenario;
import model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.LoggerManager;

import java.util.logging.Logger;

public class RegisterPage extends BasePage {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = LoggerManager.getLogger(RegisterPage.class.getName());

    private final By registrationHeading = By.id("registration-heading");

    private final By firstNameField = By.id("register-firstName");

    private final By lastNameField = By.id("register-lastName");

    private final By emailField = By.id("register-email");

    private final By passwordField = By.id("register-password");

    private final By confirmPasswordField = By.id("register-confirmPassword");

    private final By groupField = By.id("register-group");

    private final By registerButton = By.id("register-submit");

    private final By loginButton = By.id("login-toggle");
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public RegisterPage(WebDriver driver, Scenario scenario) {
        super(driver, scenario);
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public void verifyRegisterPageIsDisplayed() {
        logger.info("Waiting for Register Page to be visible");
        String expectedHeading = "Create Your Account";
        this.verifyIfTextDisplayed(registrationHeading, expectedHeading);
    }

    public void registerUser(User<Object> user) {
        logger.info("Registering user " + user.toString());
        this.clearRegisterForm();

        this.enterFirstName(user.getFirstName());
        this.enterLastName(user.getLastName());
        this.enterEmailAddress(user.getEmail());
        this.enterPassword(user.getPassword());
        this.enterConfirmPassword(user.getConfirmPassword());
        this.selectGroup((Integer) user.getGroup());

        user.setGroup(this.getSelectedGroup());

        this.clickRegisterButton();

        LoggerManager.logToReport("Registered user " + user);
    }

    public void verifyErrorMessage(String expectedMessage) {
        this.alertUtils.verifyIfAlertMessageIsCorrect(expectedMessage);
    }

    public void clearRegisterForm() {
        this.enterFirstName("");
        this.enterLastName("");
        this.enterEmailAddress("");
        this.enterPassword("");
        this.enterConfirmPassword("");
        this.selectGroup(0);
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private void enterFirstName(Object firstName) {
        this.enterKeys(firstNameField, firstName);
    }

    private void enterLastName(Object lastName) {
        this.enterKeys(lastNameField, lastName);
    }

    private void enterEmailAddress(Object emailAddress) {
        this.enterKeys(emailField, emailAddress);
    }

    private void enterPassword(Object password) {
        this.enterKeys(passwordField, password);
    }

    private void enterConfirmPassword(Object confirmPassword) {
        this.enterKeys(confirmPasswordField, confirmPassword);
    }

    private void selectGroup(int groupIndex) {
        WebElement element = this.getElement(groupField);
        new Select(element).selectByIndex(groupIndex);
    }

    private String getSelectedGroup() {
        WebElement element = this.getElement(groupField);
        String elementText = new Select(element).getFirstSelectedOption()
                                                .getText();  // group dropdown has year in it
        return elementText.replaceAll("\\s*\\(\\d{4}\\)$", "");  // removing the year
    }

    private void clickRegisterButton() {
        this.clickButton(registerButton);
    }
    // </editor-fold>

}
