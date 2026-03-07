package pages;

import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import utils.LoggerManager;
import utils.UserTestData;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static common.Constants.DEV_URL;
import static common.Constants.PROD_URL;

public class ForgotPasswordPage extends BasePage {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = LoggerManager.getLogger(ForgotPasswordPage.class.getName());

    private final By forgotPasswordLink = By.id("forgot-password-link");

    private final By forgotPasswordEmailField = By.id("forgot-password-email");

    private final By forgotPasswordSubmitBtn = By.id("forgot-password-submit");

    private final By forgotPasswordSuccessMsg = By.xpath("//p[contains(text(), 'Email Sent')]");

    private final By newPasswordField = By.id("new-password");

    private final By confirmPasswordField = By.id("confirm-new-password");

    private final By resetPasswordSubmitBtn = By.id("reset-password-submit");

    private final By resetPasswordSuccessfulMsg = By.xpath("//p[contains(text(), \"Password Reset Successfully\")]");

    private final By loginNowBtn = By.xpath("//button[contains(text(), \"Login Now\")]");

    private final By failedResetLinkMsg = By.xpath(
            "//*[@id=\"reset-password-card\"]//p[contains(., \"Invalid or expired reset token\")]");
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public ForgotPasswordPage(WebDriver driver, Scenario scenario) {
        super(driver, scenario);
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public void clickForgotPassword() {
        this.clickButton(forgotPasswordLink);
    }

    public void forgotPassword(String email) {
        logger.info("User " + email + " requesting reset password link");
        LoggerManager.logToReport("User " + email + " requesting reset password link");
        this.clearForgotPasswordForm();

        this.enterForgotPasswordEmail(email);

        this.clickForgotPasswordSubmit();
    }

    public void verifySuccessForgotPassword() {
        WebElement element = this.getElement(forgotPasswordSuccessMsg);
        Assert.assertNotNull(element, "Forgot Password Success message is not displayed");
    }

    public void validateResetPasswordLink() {
        UserTestData testData = new UserTestData();

        String resetLink = UserTestData.resetLink;
        boolean containsDevBaseUrl = UserTestData.resetLink.contains(DEV_URL);

        softAssert.assertTrue(containsDevBaseUrl, "Reset link " + resetLink + " does not contain DEV Url");

        if (!containsDevBaseUrl)
            resetLink = resetLink.replace(PROD_URL, DEV_URL);

        logger.info("Updating Reset Link to: " + resetLink);
        LoggerManager.logToReport("Updated Reset Link to: " + resetLink);
        testData.setResetLink(resetLink);
    }

    public void openResetPasswordLink() {
        driver.switchTo()
              .newWindow(WindowType.TAB);
        driver.get(UserTestData.resetLink);

        screenshotUtils.captureAndAttach(driver, scenario, "Opened Reset Password Link");
    }

    public void updateToValidPassword() {
        UserTestData userTestData = new UserTestData();
        UserTestData.user.setOldPassword(UserTestData.user.getPassword());
        userTestData.generateNewPassword();

        UserTestData.user.setPassword(userTestData.getPassword());
        UserTestData.user.setConfirmPassword(userTestData.getPassword());

        logger.info("Updated password: " + UserTestData.user.getPassword());
    }

    public void resetPassword() {
        this.clearResetPasswordForm();

        this.enterNewPasswordEmail(UserTestData.user.getPassword());
        this.enterConfirmPasswordEmail(UserTestData.user.getConfirmPassword());

        this.clickResetPasswordSubmit();
    }

    public void validateSuccessResetPassword() {
        this.getElement(resetPasswordSuccessfulMsg);
    }

    public void navigateToLogin() {
        this.clickLoginNowBtn();
    }

    public void validateResetLinkFailed() {
        WebElement element = this.getElement(failedResetLinkMsg);
        Assert.assertNotNull(element, "Invalid or Expired Reset message is not displayed");

        screenshotUtils.captureAndAttach(driver, scenario, "Reset Link Failed");

        // close the tab
        driver.close();

        // switch to any remaining tab
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo()
              .window(tabs.get(tabs.size() - 1));
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private void clearForgotPasswordForm() {
        this.enterForgotPasswordEmail("");
    }

    private void enterForgotPasswordEmail(String emailAddress) {
        this.enterKeys(forgotPasswordEmailField, emailAddress);
    }

    private void clickForgotPasswordSubmit() {
        this.clickButton(forgotPasswordSubmitBtn);
    }

    private void enterNewPasswordEmail(Object password) {
        this.enterKeys(newPasswordField, password);
    }

    private void enterConfirmPasswordEmail(Object confirmPassword) {
        this.enterKeys(confirmPasswordField, confirmPassword);
    }

    private void clickResetPasswordSubmit() {
        this.clickButton(resetPasswordSubmitBtn);
    }

    private void clearResetPasswordForm() {
        this.enterNewPasswordEmail("");
        this.enterConfirmPasswordEmail("");
    }

    private void clickLoginNowBtn() {
        this.clickButton(loginNowBtn);
    }
    // </editor-fold>

}
