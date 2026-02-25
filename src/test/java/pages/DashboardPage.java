package pages;

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

    private final By adminDashboard = By.xpath("//h3[contains(.,'Admin Dashboard')]");

    private final By profileBtn = By.xpath("//button[contains(@class, 'user-pill')]");

    private final By adminPanelBtn = By.xpath(
            "//button[contains(@class, 'nav-dropdown-item') and contains(., 'Admin Panel')]");

    private final By approvalsNavBtn = By.xpath("//button[contains(., 'Approvals')]");

    private final By groupDropdown = By.xpath(
            "//div[contains(@class, 'admin-main-content')]//select[./option[contains(text(), 'All Groups')]]");

    private final By searchInput = By.xpath("//input[@type='text' and @placeholder='Search by email...']");

    private final By userApprovalsTbl = By.xpath("//table[contains(., 'Registered')]");

    private final By approveBtn = By.xpath(".//button[contains(text(),'✓ Approve')]");

    private final By approveSuccessMsg = By.xpath("//div[text()='User approved successfully!']");

    private final By backToWebsiteBtn = By.xpath(
            "//div[contains(@class, 'admin-sidebar-footer')]//button[contains(., 'Back to Website')]");

    private final By logoutBtn = By.xpath(
            "//button[contains(@class, 'nav-dropdown-item') and contains(., 'Logout')]");
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public void verifyDashboardIsDisplayed() {
        logger.info("Waiting for Dashboard to be visible");
        String expectedHeading = "Welcome back, " + UserTestData.user.getFirstName() + "!";
        this.verifyIfTextDisplayed(welcomeBackHeading, expectedHeading);
    }

    public void validateAdminDashboardIsDisplayed() {
        WebElement element = this.getElement(adminDashboard);
        Assert.assertNotNull(element, "Admin Dashboard is not displayed");
    }

    public void approveUser(User<Object> user) {
        this.openApprovalsPage();

        this.selectGroup(user.getGroup());
        this.searchEmail(user.getEmail());

        WebElement tableRow = this.getTableRow(user.getEmail());

        Assert.assertNotNull(tableRow, "Table row is not found");

        this.clickApproval();

        this.verifyApprovalSuccessMsg();

        this.clickBackToWebsiteBtn();
    }

    public void logout() {
        this.clickButton(profileBtn);
        this.clickButton(logoutBtn);

        this.alertUtils.verifyIfConfirmationAlertMessageIsCorrect("Are you sure you want to logout?", true);
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private void openApprovalsPage() {
        this.clickButton(profileBtn);
        this.clickButton(adminPanelBtn);
        this.clickButton(approvalsNavBtn);
    }

    private void selectGroup(Object group) {
        WebElement element = this.getElement(groupDropdown);
        new Select(element).selectByContainsVisibleText((String) group);
    }

    private void searchEmail(Object email) {
        this.enterKeys(searchInput, email);
    }

    private WebElement getTableRow(Object searchEmail) {
        List<WebElement> tableRows = driver.findElements(userApprovalsTbl);

        for (WebElement row : tableRows) {
            String email = row.findElement(By.xpath("//td[3]"))
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
    // </editor-fold>

}
