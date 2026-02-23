package pages;

import model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.logging.Logger;

public class RegisterPage extends BasePage {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = Logger.getLogger(RegisterPage.class.getName());

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
    public RegisterPage(WebDriver driver) {
        super(driver);
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public void verifyRegisterPageIsDisplayed() {
        String expectedHeading = "Create Your Account";
        logger.info("Waiting for Register Page to be visible");

        WebElement element = this.getElement(registrationHeading);

        String heading = element.getText();
        logger.info(String.format("Heading found: %s", heading));

        Assert.assertEquals(heading, expectedHeading, "Heading does not match");
    }

    public boolean isRegisterFormVisible() {
        try {
            WebElement element = this.getElement(registrationHeading);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void registerUser(User<Object> user) {
        logger.info("Registering user");
        this.clearRegisterForm();

        this.enterFirstName(user.getFirstName());
        this.enterLastName(user.getLastName());
        this.enterEmailAddress(user.getEmail());
        this.enterPassword(user.getPassword());
        this.enterConfirmPassword(user.getConfirmPassword());
        this.selectGroup((Integer) user.getGroup());

        this.clickRegisterButton();
    }

    public void verifyErrorMessage(String expectedMessage) {
        this.alertUtils.verifyIfAlertMessageIsCorrect(expectedMessage);
    }

    public void clickLoginButton() {
        WebElement element = this.getElement(loginButton);
        element.click();
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
        WebElement element = this.getElement(firstNameField);
        element.clear();
        element.sendKeys((CharSequence) firstName);
    }

    private void enterLastName(Object lastName) {
        WebElement element = this.getElement(lastNameField);
        element.clear();
        element.sendKeys((CharSequence) lastName);
    }

    private void enterEmailAddress(Object emailAddress) {
        WebElement element = this.getElement(emailField);
        element.clear();
        element.sendKeys((CharSequence) emailAddress);
    }

    private void enterPassword(Object password) {
        WebElement element = this.getElement(passwordField);
        element.clear();
        element.sendKeys((CharSequence) password);
    }

    private void enterConfirmPassword(Object confirmPassword) {
        WebElement element = this.getElement(confirmPasswordField);
        element.clear();
        element.sendKeys((CharSequence) confirmPassword);
    }

    private void selectGroup(int groupIndex) {
        WebElement element = this.getElement(groupField);
        new Select(element).selectByIndex(groupIndex);
    }

    private void clickRegisterButton() {
        WebElement element = this.getElement(registerButton);
        element.click();
    }
    // </editor-fold>


}
