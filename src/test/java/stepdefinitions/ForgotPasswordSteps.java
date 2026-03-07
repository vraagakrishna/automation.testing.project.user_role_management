package stepdefinitions;

import com.mailslurp.models.Email;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import services.EmailService;
import utils.LoggerManager;
import utils.UserTestData;

import java.util.logging.Logger;

public class ForgotPasswordSteps extends BaseSteps {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = LoggerManager.getLogger(ForgotPasswordSteps.class.getName());

    private final EmailService emailService;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public ForgotPasswordSteps() {
        super();
        this.emailService = new EmailService();
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    @When("I request a password reset")
    public void requestResetPasswordLink() {
        forgotPasswordPage.clickForgotPassword();
        forgotPasswordPage.forgotPassword((String) UserTestData.user.getEmail());
    }

    @Then("a password reset email should be sent to the user")
    public void receiveEmail() throws Exception {
        forgotPasswordPage.verifySuccessForgotPassword();

        UserTestData testData = new UserTestData();

        Email resetEmail = this.emailService.waitForEmail();

        String resetLink = this.emailService.validateAndGetResetLink(resetEmail);

        logger.info("Reset link from email: " + resetLink);
        LoggerManager.logToReport("Reset link from email: " + resetLink);
        testData.setResetLink(resetLink);

        forgotPasswordPage.validateResetPasswordLink();
    }

    @And("I open the password reset link from the email")
    @When("I attempt to reuse the password reset link")
    public void openResetPasswordLink() {
        forgotPasswordPage.openResetPasswordLink();
    }

    @And("I set a new password")
    public void resetPassword() {
        forgotPasswordPage.updateToValidPassword();
        forgotPasswordPage.resetPassword();
        forgotPasswordPage.validateSuccessResetPassword();
        forgotPasswordPage.navigateToLogin();
    }

    @Then("a password changed confirmation email should be sent to the user")
    public void verifyResetPasswordConfirmationEmail() throws Exception {
        Email resetEmail = this.emailService.waitForEmail();

        this.emailService.validateSuccessfulPasswordReset(resetEmail);
    }

    @Then("I should see a reset token expired or invalid message")
    public void validateResetLinkFailed() {
        forgotPasswordPage.validateResetLinkFailed();
    }
    // </editor-fold>

}
