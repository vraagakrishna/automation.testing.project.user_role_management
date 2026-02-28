Feature: End-to-end User Role Management
  As a system administrator
  I want to promote users to admin role
  So that they can manage the system
  
  Background:
    Given I am on the login page
  
  @ui
  Scenario: Promote a standard user to admin
    Given a newly registered user exists
    And the admin approves the user
    
    When I attempt to login with the user
    Then I should see the Dashboard
    And the user role should be "User"
    
    When the admin changes the user's role to "Admin"
    And I login as the new user
    Then I should see the Dashboard
    And the user role should be "Admin"
    
    When the admin deletes the user
    And I attempt to login with the user
    Then login should fail
