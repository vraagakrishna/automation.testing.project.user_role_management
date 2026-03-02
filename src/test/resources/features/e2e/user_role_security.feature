Feature: User Role Security - Authorization Protected
  As a system
  I want to prevent privilege escalation
  So that users cannot gain unauthorized access
  
  Background:
    Given I am on the login page
    
    Given a newly registered user exists
    And the admin approves the user
    
    When I attempt to login with the user
    Then I should see the Dashboard
    And the user role should be "User"
  
  @ui
  Scenario: JWT manipulation should not grant admin access
    When I modify the user role to "Admin" in storage
    Then I refresh the page
    And the user role should be "User"
    
    When I navigate to the Admin Panel
    Then I should be redirected to the login page
  
  @ui
  Scenario: Role downgrade while logged in should remove privileges
    And I logout as the user
    
    Given the admin changes the user's role to "Admin"
    And I attempt to login with the user
    Then the user role should be "Admin"
    
    When the admin changes the user's role to "User"
    And I refresh the page
    Then the user role should be "User"
  
  @ui
  Scenario: Expired JWT token should log the user out
    When I expire the JWT token in storage
    And I refresh the page
    Then I should be redirected to the login page
  
  @ui
  Scenario: Invalid JWT token should log the user out
    When I replace the JWT token with an invalid value
    And I refresh the page
    Then I should be redirected to the login page
  
  @ui
  Scenario: JWT without signature should be rejected
    When I remove the JWT signature in the storage
    And I refresh the page
    Then I should be redirected to the login page
  
  @ui
  Scenario: JWT with algorithm set to none should be rejected
    When I modify the JWT algorithm to "none"
    And I refresh the page
    Then I should be redirected to the login page
  
  @ui
  Scenario: Logout in one tab should invalidate session in another tab
    When I open a new browser tab
    And I switch to tab 2
    When I should see the Dashboard
    
    When I switch to tab 1
    And I logout as the user
    
    When I switch back to tab 2
    And I refresh the page
    Then I should be redirected to the login page