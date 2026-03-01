package pages.navigation;

import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.UIActions;

public class DesktopNavigation extends UIActions implements INavigation {

    // <editor-fold desc="Class Fields / Constants">
    private final By loginBtn = By.xpath("//button[@class='user-pill'][contains(., 'Login')]");

    private final By profileBtn = By.xpath("//button[contains(@class, 'user-pill')]");

    private final By adminPanelBtn = By.xpath(
            "//button[contains(@class, 'nav-dropdown-item') and contains(., 'Admin Panel')]");

    private final By approvalsNavBtn = By.xpath("//nav//button[contains(., 'Approvals')]");

    private final By usersNavBtn = By.xpath("//nav//button[contains(text(), 'Users')]");

    private final By backToWebsiteBtn = By.xpath(
            "//div[contains(@class, 'admin-sidebar-footer')]//button[contains(., 'Back to Website')]");

    private final By logoutBtn = By.xpath("//button[contains(@class, 'nav-dropdown-item') and contains(., 'Logout')]");
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public DesktopNavigation(WebDriver driver, Scenario scenario) {
        super(driver, scenario);
    }
    // </editor-fold>

    // <editor-fold desc="Overrides">
    @Override
    public void goToLoginPage() {
        this.clickButton(loginBtn);
    }

    @Override
    public void openAdminPanel() {
        this.clickButton(profileBtn);
        this.clickButton(adminPanelBtn);
    }

    @Override
    public void openApprovalsPage() {
        this.clickButton(approvalsNavBtn);
    }

    @Override
    public void openUsersPage() {
        this.clickButton(usersNavBtn);
    }

    @Override
    public void clickBackToWebsiteBtn() {
        this.clickButton(backToWebsiteBtn);
    }

    @Override
    public void logout() {
        this.clickButton(profileBtn);
        this.clickButton(logoutBtn);
    }
    // </editor-fold>

}
