package stepdefinitions;

import io.cucumber.java.en.Given;

public class HomeSteps extends BaseSteps {

    // <editor-fold desc="Ctor">
    public HomeSteps() {
        super();
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    @Given("I am on the registration page")
    public void openRegisterPage() {
        homePage.verifyHomePageIsDisplayed();

        homePage.clickLogin();

        loginPage.verifyLoginPageIsDisplayed();

        loginPage.clickRegisterButton();

        registerPage.verifyRegisterPageIsDisplayed();
    }

    @Given("I am on the login page")
    public void openLoginPage() {
        homePage.verifyHomePageIsDisplayed();

        homePage.clickLogin();

        loginPage.verifyLoginPageIsDisplayed();
    }
    // </editor-fold>

}
