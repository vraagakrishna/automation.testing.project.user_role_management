package services;

import com.mailslurp.models.Email;
import junit.framework.Assert;
import utils.FileUtils;
import utils.LoggerManager;
import utils.UserTestData;

import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailService {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = LoggerManager.getLogger(EmailService.class.getName());
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public EmailService() {
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public Email waitForEmail() throws Exception {
        logger.info("Inbox ID: " + UserTestData.inbox.getId());

        int retries = 20;
        Email email = null;
        for (int attempt = 0; attempt < retries; attempt++) {
            try {
                logger.info("Attempt " + (attempt + 1));
                email = UserTestData.emailService.waitForEmail(
                        UserTestData.inbox.getId(), 6_000L
                );
                break; // email received
            } catch (Exception e) {
                Thread.sleep(5000); // wait 5s and retry
            }
        }
        if (email == null) throw new RuntimeException("Email never arrived");

        logger.info("Message body: " + email.getBody());
        LoggerManager.logToReport("Message body: " + email.getBody());

        FileUtils.saveAndAttachHtml(
                email.getBody(),
                email.getSubject()
        );

        return email;
    }

    public String validateAndGetResetLink(Email email) {
        logger.info("Validating reset email");
        String expectedSubject = "Password Reset Request";

        // validate subject
        Assert.assertTrue(
                "Reset password email subject is incorrect",
                Objects.requireNonNull(email.getSubject())
                       .contains(expectedSubject)
        );

        // validate email body
        Assert.assertTrue(
                "Reset email text is missing",
                Objects.requireNonNull(email.getBody())
                       .contains("Password Reset Request")
        );

        // validate the expiry time
        Assert.assertTrue(
                "1 hour",
                Objects.requireNonNull(email.getBody())
                       .contains("Password Reset Request")
        );

        return extractLink(email.getBody());
    }

    public void validateSuccessfulPasswordReset(Email email) {
        logger.info("Validating successful password reset email");
        String expectedSubject = "Password Changed Successfully";

        // validate subject
        Assert.assertTrue(
                "Successful password reset email subject is incorrect",
                Objects.requireNonNull(email.getSubject())
                       .contains(expectedSubject)
        );

        // validate email body
        Assert.assertTrue(
                "Reset email text is missing",
                Objects.requireNonNull(email.getBody())
                       .contains("Password Changed Successfully")
        );
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private String extractLink(String body) {
        logger.info("Validating reset link");
        Pattern pattern = Pattern.compile("https?://[^\\s\"]+");

        Matcher matcher = pattern.matcher(body);

        if (matcher.find())
            return matcher.group();

        throw new RuntimeException("link not found in email");
    }
    // </editor-fold>

}
