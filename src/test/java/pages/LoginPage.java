package pages;

import io.cucumber.java.Scenario;
import model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.logging.Logger;

public class LoginPage extends BasePage {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = Logger.getLogger(LoginPage.class.getName());

    private final By loginHeading = By.id("login-heading");

    private final By emailField = By.id("login-email");

    private final By passwordField = By.id("login-password");

    private final By loginButton = By.id("login-submit");

    private final By registerButton = By.id("signup-toggle");
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public LoginPage(WebDriver driver, Scenario scenario) {
        super(driver, scenario);
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public void verifyLoginPageIsDisplayed() {
        if (!isElementVisible(loginHeading))
            throw new TimeoutException("Login Heading not displayed");

        logger.info("Waiting for Login Page to be visible");
        String expectedHeading = "Login to Access Learning Materials";

        this.verifyIfTextDisplayed(loginHeading, expectedHeading);
    }

    public void clickRegisterButton() {
        WebElement element = this.getElement(registerButton);
        element.click();
    }

    public void validateEmailAddress(Object expectedEmailAddress) {
        String heading = this.getEmailAddress();

        Assert.assertEquals(heading, expectedEmailAddress, "Email address does not match");
    }

    public void loginUser(User<Object> user) {
        logger.info("Login user");
        this.clearLoginForm();

        this.enterEmailAddress(user.getEmail());
        this.enterPassword(user.getPassword());

        this.clickLoginButton();
    }

    public void verifyErrorMessage(String expectedMessage) {
        this.alertUtils.verifyIfAlertMessageIsCorrect(expectedMessage);
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private void clearLoginForm() {
        this.enterEmailAddress("");
        this.enterPassword("");
    }

    private void enterEmailAddress(Object emailAddress) {
        this.enterKeys(emailField, emailAddress);
    }

    private void enterPassword(Object password) {
        this.enterKeys(passwordField, password);
    }

    private void clickLoginButton() {
        this.clickButton(loginButton);
    }

    public String getEmailAddress() {
        WebElement element = this.getElement(emailField);
        return element.getDomProperty("value");
    }
    // </editor-fold>

}
