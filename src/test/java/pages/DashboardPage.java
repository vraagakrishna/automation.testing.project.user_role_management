package pages;

import io.cucumber.java.Scenario;
import model.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utils.DriverManager;
import utils.JwtUtils;
import utils.LoggerManager;
import utils.UserTestData;

import java.util.List;
import java.util.logging.Logger;

public class DashboardPage extends BasePage {

    // <editor-fold desc="Class Fields / Constants">
    private static final Logger logger = LoggerManager.getLogger(DashboardPage.class.getName());

    private final By welcomeBackHeading = By.xpath(
            "//*[@id='app-main-content']//*[self::h2 and contains(., 'Welcome back')]");

    private final By dashboardSection = By.xpath("//section[@class='dashboard-section']/div/p");

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

    private final By statusChangeButton = By.xpath("//td[5]//button");
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
        String expectedHeading = "Here's who's working today";
        WebElement element = this.getElement(dashboardSection);
        softAssert.assertEquals(element.getText(), expectedHeading, "Non User Dashboard is not displayed");

        return true;
    }

    public void validateUserDashboardIsDisplayed() {
        String expectedHeading = "Here's an overview of your learning journey";
        WebElement element = this.getElement(dashboardSection);
        softAssert.assertEquals(element.getText(), expectedHeading, "User Dashboard is not displayed");
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

        screenshotUtils.captureAndAttach(
                DriverManager.getDriver(),
                scenario,
                "User is approved"
        );

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

    public void deactivateUser(User<Object> user) {
        this.openUserManagementAndPerformAction(user, this::deactivateUser);
    }

    public void logout() {
        logger.info("Logging out...");
        this.navigation.logout();

        this.alertUtils.verifyIfConfirmationAlertMessageIsCorrect("Are you sure you want to logout?", true);
        screenshotUtils.captureAndAttach(driver, scenario, "After clicking Logout");
    }

    public void refreshPage() {
        logger.info("Refreshing the page...");

        driver.navigate()
              .refresh();

        screenshotUtils.captureAndAttach(
                DriverManager.getDriver(),
                scenario,
                "Verifying page is refreshed"
        );
    }

    public void validateUserLoggedIn() {
        logger.info("Checking if token exists in local storage");
        String token = javascriptExecutorUtils.getLocalStorageItem("authToken");
        LoggerManager.logToReport("Token from localStorage: " + token);

        logger.info("Token from localStorage " + token);
        JwtUtils.decodeJwt(token);

        UserTestData.user.setToken(token);

        Assert.assertNotNull(token, "Auth token is not present");
    }

    public void manipulateTokenRole(String userRole) {
        String newToken = JwtUtils.manipulateJwtRole(
                UserTestData.user.getToken(),
                userRole
        );

        javascriptExecutorUtils.setLocalStorageItem("authToken", newToken);

        String token = javascriptExecutorUtils.getLocalStorageItem("authToken");
        LoggerManager.logToReport("Modified token from localStorage: " + token);

        logger.info("Modified token from localStorage " + token);
    }

    public void manipulateUserRole(String userRole) {
        String user = javascriptExecutorUtils.getLocalStorageItem("user");
        JSONObject jsonUser;

        try {
            JSONParser parser = new JSONParser();
            jsonUser = (JSONObject) parser.parse(user);
            System.out.println("Original user: " + user);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse JSON payload: " + e.getMessage());
        }

        // manipulate usr role
        jsonUser.put("role", userRole.toLowerCase());

        javascriptExecutorUtils.setLocalStorageItem("user", jsonUser.toJSONString());

        String modifiedUser = javascriptExecutorUtils.getLocalStorageItem("user");
        logger.info("Modified user " + modifiedUser);
    }

    public void openAdminPanel() {
        logger.info("Opening Admin Panel");
        this.navigation.openAdminPanel();
    }

    public void expireToken() {
        String newToken = JwtUtils.expireToken(UserTestData.user.getToken());

        javascriptExecutorUtils.setLocalStorageItem("authToken", newToken);

        String token = javascriptExecutorUtils.getLocalStorageItem("authToken");
        LoggerManager.logToReport("ExpiredExpired token from localStorage: " + token);

        logger.info("Modified token from localStorage " + token);
    }

    public void invalidateToken() {
        String newToken = (String) UserTestData.user.getFirstName();

        javascriptExecutorUtils.setLocalStorageItem("authToken", newToken);

        String token = javascriptExecutorUtils.getLocalStorageItem("authToken");
        LoggerManager.logToReport("Invalid token from localStorage: " + token);

        logger.info("Invalid token from localStorage " + token);
    }
    // </editor-fold>

    // <editor-fold desc="Private Methods">
    private void openApprovalsPage() {
        logger.info("Opening Approvals page");
        this.navigation.openApprovalsPage();
    }

    private void openUsersPage() {
        logger.info("Opening Users page");
        this.navigation.openUsersPage();
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
        logger.info("Going back to Website");
        this.navigation.clickBackToWebsiteBtn();
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

    private void deactivateUser() {
        this.clickButton(statusChangeButton);

        this.alertUtils.verifyIfConfirmationAlertMessageIsCorrect(
                "Are you sure you want to deactivate this user?",
                true
        );

        this.alertUtils.verifyIfAlertMessageIsCorrect("User deactivated successfully!");
    }
    // </editor-fold>

}
