package pages;

import org.openqa.selenium.By;
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
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public void verifyLoginPageIsDisplayed() {
        String expectedHeading = "Login to Access Learning Materials";
        logger.info("Waiting for Login Page to be visible");

        WebElement element = this.getElement(loginHeading);

        String heading = element.getText();
        logger.info(String.format("Heading found: %s", heading));

        Assert.assertEquals(heading, expectedHeading, "Heading does not match");

        //screenshotUtils.captureAndAttach(driver, "Verifying login page is displayed");
    }

    public void clickRegisterButton() {
        WebElement element = this.getElement(registerButton);
        element.click();
    }

    public void clearLoginForm() {
        this.enterEmailAddress("");
        this.enterPassword("");
    }

    public void validateEmailAddress(Object expectedEmailAddress) {
        String heading = this.getEmailAddress();

        Assert.assertEquals(heading, expectedEmailAddress, "Email address does not match");
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private void enterEmailAddress(String emailAddress) {
        WebElement element = this.getElement(emailField);
        element.clear();
        element.sendKeys(emailAddress);
    }

    private void enterPassword(String password) {
        WebElement element = this.getElement(passwordField);
        element.clear();
        element.sendKeys(password);
    }

    private void clickLoginButton() {
        WebElement element = this.getElement(loginButton);
        element.click();
    }

    public String getEmailAddress() {
        WebElement element = this.getElement(emailField);
        return element.getDomProperty("value");
    }
    // </editor-fold>

}
