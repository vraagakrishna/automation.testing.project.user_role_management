package utils;

import com.github.javafaker.Faker;
import com.mailslurp.models.InboxDto;
import model.User;
import services.MailSlurpEmailService;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class UserTestData {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = LoggerManager.getLogger(UserTestData.class.getName());

    private static final Faker faker = new Faker();

    private static final List<String> DOMAINS = List.of(
            "gmail.com", "yahoo.com", "outlook.com", "hotmail.com", "icloud.com"
    );

    public static User<Object> user;

    public static String resetLink;

    public static MailSlurpEmailService emailService;

    public static InboxDto inbox;

    public String weakPassword = faker.internet()
                                      .password(1, 5, true, true, true);

    private String password = generateFakePassword();

    private String firstName = generateFakeFirstName();

    private String lastName = generateFakeLastName();

    private String email = generateFakeEmail();

    private String realEmail;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public UserTestData() {
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    public String getWeakPassword() {
        return weakPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }

    public String getRealEmail() {
        return realEmail;
    }

    public void setRealEmail(String realEmail) {
        this.realEmail = realEmail;
    }

    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public void setUser(User<Object> user) {
        this.user = user;
    }

    public void setResetLink(String resetLink) {
        this.resetLink = resetLink;
    }

    public void generateRealEmail() throws Exception {
        emailService = new MailSlurpEmailService(ConfigManager.getMailSlurpApiKey());
        inbox = emailService.createInbox();
        logger.info("Generated Email: " + inbox.getEmailAddress());
        logger.info("Associated Inbox ID: " + inbox.getId());
        realEmail = inbox.getEmailAddress();
    }

    public void generateNewPassword() {
        password = generateFakePassword();
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private String sanitize(String input) {
        return input.replaceAll("[^A-Za-z0-9]", "");
    }

    private String randomDomain() {
        int idx = ThreadLocalRandom.current()
                                   .nextInt(DOMAINS.size());
        return DOMAINS.get(idx);
    }

    private String generateFakeFirstName() {
        return sanitize(faker.name()
                             .firstName());
    }

    private String generateFakeLastName() {
        return sanitize(faker.name()
                             .lastName());
    }

    private String generateFakeEmail() {
        return lastName + "." + firstName + "." + faker.number()
                                                       .numberBetween(0, 10000) + "@" + randomDomain();
    }

    private String generateFakePassword() {
        String newPassword;

        do {
            newPassword = faker.internet()
                               .password(8, 16, true, true, true);
        } while (!newPassword.matches(".*[!@#$%^&*()_+\\-={}\\[\\]|:;\"'<>,.?/].*") || newPassword.length() < 8);

        return newPassword;
    }
    // </editor-fold>

}
