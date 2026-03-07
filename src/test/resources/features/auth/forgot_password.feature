Feature: Forgot Password
  As a registered user who forgot password
  I want to reset my password
  So that I can access my account again
  
  Background:
    Given I am on the login page
    
    Given a newly registered user exists with real email
    And the admin approves the user
    
    When I attempt to login with the user
    Then I should see the Dashboard
    
    And I logout as the user
    
    Given I am on the login page
  
  @email
  Scenario: Registered user should get email to reset password
    When I request a password reset
    Then a password reset email should be sent to the user
    
    And I open the password reset link from the email
    And I set a new password
    
    Then a password changed confirmation email should be sent to the user
    
    When I attempt to login with the old password
    Then login should fail
    
    When I attempt to reuse the password reset link
    Then I should see a reset token expired or invalid message
    
    When I login with the new password
    Then I should see the Dashboard
  