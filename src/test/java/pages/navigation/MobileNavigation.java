package pages.navigation;

import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.UIActions;

public class MobileNavigation extends UIActions implements INavigation {

    // <editor-fold desc="Class Fields / Constants">
    private final By navBurgerBtn = By.xpath("//button[@class='nav-burger']");

    private final By loginBtn = By.xpath("//button[contains(@class, 'mobile-menu-item') and contains(., 'Login')]");

    private final By adminPanelBtn = By.xpath(
            "//button[contains(@class, 'mobile-menu-item') and contains(., 'Admin Panel')]");

    private final By adminNavBurgerBtn = By.xpath("//button[@class='admin-burger-btn']");

    private final By approvalsNavBtn = By.xpath("//nav//button[contains(., 'Approvals')]");

    private final By usersNavBtn = By.xpath("//nav//button[contains(text(), 'Users')]");

    private final By backToWebsiteBtn = By.xpath(
            "//div[contains(@class, 'admin-sidebar-footer')]//button[contains(., 'Back to Website')]");

    private final By logoutBtn = By.xpath("//button[contains(@class, 'mobile-menu-item') and contains(., 'Logout')]");
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public MobileNavigation(WebDriver driver, Scenario scenario) {
        super(driver, scenario);
    }
    // </editor-fold>

    // <editor-fold desc="Overrides">
    @Override
    public void goToLoginPage() {
        this.clickNavBurger();
        this.clickButton(loginBtn);
    }

    @Override
    public void openAdminPanel() {
        this.clickNavBurger();
        this.clickButton(adminPanelBtn);
    }

    @Override
    public void openApprovalsPage() {
        this.clickAdminNavBurger();
        this.clickButton(approvalsNavBtn);
    }

    @Override
    public void openUsersPage() {
        this.clickAdminNavBurger();
        this.clickButton(usersNavBtn);
    }

    @Override
    public void clickBackToWebsiteBtn() {
        this.clickAdminNavBurger();
        this.clickButton(backToWebsiteBtn);
    }

    @Override
    public void logout() {
        this.clickNavBurger();
        this.clickButton(logoutBtn);
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private void clickNavBurger() {
        this.clickButton(navBurgerBtn);
    }

    private void clickAdminNavBurger() {
        this.clickButton(adminNavBurgerBtn);
    }
    // </editor-fold>

}
