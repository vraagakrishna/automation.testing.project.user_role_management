package stepdefinitions;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import utils.DriverManager;

public class HomeSteps {

    // <editor-fold desc="Class Fields / Constants">
    private final HomePage homePage;

    private final LoginPage loginPage;

    private final RegisterPage registerPage;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public HomeSteps() {
        WebDriver driver = DriverManager.getDriver();
        this.homePage = new HomePage(driver);
        this.loginPage = new LoginPage(driver);
        this.registerPage = new RegisterPage(driver);
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
    // </editor-fold>

}
