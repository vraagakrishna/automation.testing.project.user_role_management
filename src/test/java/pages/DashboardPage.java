package pages;

import io.cucumber.java.Scenario;
import model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utils.UserTestData;

import java.util.List;
import java.util.logging.Logger;

public class DashboardPage extends BasePage {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = Logger.getLogger(DashboardPage.class.getName());

    private final By welcomeBackHeading = By.xpath(
            "//*[@id='app-main-content']//*[self::h2 and contains(., 'Welcome back')]");

    private final By adminDashboard = By.xpath("//section[@class='dashboard-section']/div/p");

    private final By profileBtn = By.xpath("//button[contains(@class, 'user-pill')]");

    private final By adminPanelBtn = By.xpath(
            "//button[contains(@class, 'nav-dropdown-item') and contains(., 'Admin Panel')]");

    private final By approvalsNavBtn = By.xpath("//nav//button[contains(., 'Approvals')]");

    private final By usersNavBtn = By.xpath("//nav//button[contains(text(), 'Users')]");

    private final By groupDropdown = By.xpath(
            "//div[contains(@class, 'admin-main-content')]//select[./option[contains(text(), 'All Groups')]]");

    private final By approvalSearchInput = By.xpath(
            "//input[@type='text' and @placeholder='Search by name or email...']");

    private final By usersSearchInput = By.xpath("//input[@type='text' and contains(@placeholder, 'Search users...')]");

    private final By userApprovalsTbl = By.xpath("//table[contains(., 'Registered')]");

    private final By userManagementTbl = By.xpath("//table[contains(., 'Role')]");

    private final By approveBtn = By.xpath(".//button[contains(text(),'Approve')]");

    private final By approveSuccessMsg = By.xpath("//div[text()='User approved successfully!']");

    private final By roleDropdown = By.xpath("//td[4]/select");

    private final By deleteButton = By.xpath("//td[7]//button[2]");

    private final By backToWebsiteBtn = By.xpath(
            "//div[contains(@class, 'admin-sidebar-footer')]//button[contains(., 'Back to Website')]");

    private final By logoutBtn = By.xpath(
            "//button[contains(@class, 'nav-dropdown-item') and contains(., 'Logout')]");
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public DashboardPage(WebDriver driver, Scenario scenario) {
        super(driver, scenario);
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public void verifyDashboardIsDisplayed() {
        logger.info("Waiting for Dashboard to be visible");
        String expectedHeading = "Welcome back, " + UserTestData.user.getFirstName() + "!";
        this.verifyIfTextDisplayed(welcomeBackHeading, expectedHeading);
    }

    public boolean validateNonUserDashboardIsDisplayed() {
        if (!isElementVisible(adminDashboard))
            return false;

        String expectedHeading = "Here's who's working today";
        WebElement element = this.getElement(adminDashboard);
        Assert.assertEquals(element.getText(), expectedHeading, "Non User Dashboard is not displayed");

        return true;
    }

    public void validateUserDashboardIsDisplayed() {
        Assert.assertTrue(isElementVisible(adminDashboard), "User Dashboard is not found");

        String expectedHeading = "Here's an overview of your learning journey";
        WebElement element = this.getElement(adminDashboard);
        Assert.assertEquals(element.getText(), expectedHeading, "User Dashboard is not displayed");
    }

    public void approveUser(User<Object> user) {
        this.openAdminPanel();
        this.openApprovalsPage();

        this.selectGroup(user.getGroup());
        this.searchEmail(approvalSearchInput, user.getEmail());

        WebElement tableRow = this.getTableRow(userApprovalsTbl, By.xpath("//td[3]"), user.getEmail());

        Assert.assertNotNull(tableRow, "Table row is not found");

        this.clickApproval();

        this.verifyApprovalSuccessMsg();

        this.clickBackToWebsiteBtn();
    }

    public void changeUserRole(User<Object> user, String userRole) {
        this.openUserManagementAndPerformAction(user, () ->
                this.changeUserRole(userRole)
        );
    }

    public void deleteUser(User<Object> user) {
        this.openUserManagementAndPerformAction(user, this::deleteUser);
    }

    public void logout() {
        this.clickButton(profileBtn);
        this.clickButton(logoutBtn);

        this.alertUtils.verifyIfConfirmationAlertMessageIsCorrect("Are you sure you want to logout?", true);
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private void openAdminPanel() {
        this.clickButton(profileBtn);
        this.clickButton(adminPanelBtn);
    }

    private void openApprovalsPage() {
        this.clickButton(approvalsNavBtn);
    }

    private void openUsersPage() {
        this.clickButton(usersNavBtn);
    }

    private void selectGroup(Object group) {
        WebElement element = this.getElement(groupDropdown);
        new Select(element).selectByContainsVisibleText((String) group);
    }

    private void searchEmail(By searchInput, Object email) {
        this.enterKeys(searchInput, email);
    }

    private WebElement getTableRow(By tbl, By elementBy, Object searchEmail) {
        List<WebElement> tableRows = driver.findElements(tbl);

        for (WebElement row : tableRows) {
            String email = row.findElement(elementBy)
                              .getText();

            if (searchEmail.equals(email))
                return row;
        }

        return null;
    }

    private void clickApproval() {
        this.clickButton(approveBtn);
    }

    private void verifyApprovalSuccessMsg() {
        WebElement element = this.getElement(approveSuccessMsg);

        Assert.assertNotNull(element, "Approval success message is not displayed");
    }

    private void clickBackToWebsiteBtn() {
        this.clickButton(backToWebsiteBtn);
    }

    private void openUserManagementAndPerformAction(User<Object> user, Runnable action) {
        this.openAdminPanel();
        this.openUsersPage();

        this.selectGroup(user.getGroup());
        this.searchEmail(usersSearchInput, user.getEmail());

        WebElement tableRow = this.getTableRow(userManagementTbl, By.xpath("//td[2]"), user.getEmail());

        Assert.assertNotNull(tableRow, "Table row is not found");

        action.run();

        this.clickBackToWebsiteBtn();
    }

    private void changeUserRole(String userRole) {
        WebElement roleDropdownElement = this.getElement(roleDropdown);
        new Select(roleDropdownElement).selectByContainsVisibleText(userRole);

        this.alertUtils.verifyIfConfirmationAlertMessageIsCorrect(
                "Are you sure you want to change this user's role to \"" + userRole.toLowerCase() + "\"?",
                true
        );

        this.alertUtils.verifyIfAlertMessageIsCorrect(
                "User role updated to \"" + userRole.toLowerCase() + "\" successfully!");

        String roleElementText = new Select(roleDropdownElement).getFirstSelectedOption()
                                                                .getText();

        Assert.assertTrue(roleElementText.contains(userRole), "Role is incorrect");
    }

    private void deleteUser() {
        this.clickButton(deleteButton);

        this.alertUtils.verifyIfConfirmationAlertMessageIsCorrect(
                "Are you sure you want to delete this user?",
                true
        );

        this.alertUtils.verifyIfAlertMessageIsCorrect("User deleted successfully!");
    }
    // </editor-fold>

}
