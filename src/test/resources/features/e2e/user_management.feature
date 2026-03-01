Feature: User Management - Account Lifecycle & Session Handling
  As an administrator
  I want to manage user accounts
  So that only valid and active users can access the system
  
  Background:
    Given I am on the login page
  
  @ui
  Scenario: Deactivated user should be logged out on refresh
    Given a newly registered user exists
    And the admin approves the user
    
    When I attempt to login with the user
    Then I should see the Dashboard
    And the user role should be "User"
    And I logout as the user
    
    When the admin deactivates the user
    And I refresh the page
    Then I should be redirected to the login page
    And I should see the message "Your account has been deactivated"
  
  @ui
  Scenario: Deleted user should be logged out on refresh
    Given a newly registered user exists
    And the admin approves the user
    
    When I attempt to login with the user
    Then I should see the Dashboard
    And the user role should be "User"
    And I logout as the user
    
    When the admin deletes the user
    And I refresh the page
    Then I should be redirected to the login page
    And I should see the message "User does not exist"
  
  @ui
  Scenario: User cannot login after being deactivated
    Given a newly registered user exists
    And the admin approves the user
    
    When I attempt to login with the user
    Then I should see the Dashboard
    And the user role should be "User"
    And I logout as the user
    
    When the admin deactivates the user
    And I attempt to login with the user
    Then login should fail
  
  @ui
  Scenario: User cannot login after being deleted
    Given a newly registered user exists
    And the admin approves the user
    
    When I attempt to login with the user
    Then I should see the Dashboard
    And the user role should be "User"
    And I logout as the user
    
    When the admin deletes the user
    And I attempt to login with the user
    Then login should fail
    