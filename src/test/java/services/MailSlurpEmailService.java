package services;

import com.mailslurp.apis.InboxControllerApi;
import com.mailslurp.apis.WaitForControllerApi;
import com.mailslurp.clients.ApiClient;
import com.mailslurp.clients.Configuration;
import com.mailslurp.models.Email;
import com.mailslurp.models.InboxDto;
import utils.LoggerManager;

import java.util.UUID;
import java.util.logging.Logger;

public class MailSlurpEmailService {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = LoggerManager.getLogger(MailSlurpEmailService.class.getName());

    private final WaitForControllerApi waitForApi;

    private final InboxControllerApi inboxApi;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public MailSlurpEmailService(String apiKey) {
        ApiClient client = Configuration.getDefaultApiClient();
        client.setApiKey(apiKey);

        waitForApi = new WaitForControllerApi(client);
        inboxApi = new InboxControllerApi(client);
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public InboxDto createInbox() throws Exception {
        logger.info("Creating Inbox");
        return inboxApi.createInboxWithDefaults()
                       .execute();
    }

    public Email waitForEmail(UUID inboxId, long timeoutMillis) throws Exception {
        logger.info("Waiting for email...");

        // Create the request manually
        WaitForControllerApi.APIwaitForLatestEmailRequest request = waitForApi
                .waitForLatestEmail()
                .inboxId(inboxId)
                .timeout(timeoutMillis)
                .unreadOnly(true);

        // Execute synchronously
        return request.execute();
    }

    public void deleteInbox(UUID inboxId) {
        logger.info("Deleting inbox: " + inboxId);

        if (inboxId == null)
            return;

        try {
            inboxApi.deleteInbox(inboxId);
            logger.info("Deleted inbox: " + inboxId);
        } catch (Exception e) {
            logger.severe("Failed to delete inbox: " + inboxId);
            throw new RuntimeException(e);
        }
    }
    // </editor-fold>

}