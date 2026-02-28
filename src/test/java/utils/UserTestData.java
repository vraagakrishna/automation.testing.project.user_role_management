package utils;

import com.github.javafaker.Faker;
import model.User;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UserTestData {

    // <editor-fold desc="Class Fields">
    private static final Faker faker = new Faker();

    private static final List<String> DOMAINS = List.of(
            "gmail.com", "yahoo.com", "outlook.com", "hotmail.com", "icloud.com"
    );

    public static User<Object> user;

    public String weakPassword = faker.internet()
                                      .password(1, 5, true, true, true);
    private String password = generateFakePassword();

    private String firstName = generateFakeFirstName();

    private String lastName = generateFakeLastName();

    private String email = generateFakeEmail();
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
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public void setUser(User<Object> user) {
        this.user = user;
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
        } while (!newPassword.matches(".*[!@#$%^&*()_+\\-={}\\[\\]|:;\"'<>,.?/].*") && newPassword.length() < 8);

        return newPassword;
    }
    // </editor-fold>

}
