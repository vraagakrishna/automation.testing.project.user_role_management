Feature: User Login
  As a registered user
  I want to login
  So that I can access my account
  
  Background:
    Given I am on the login page
  
  @ui
  Scenario Outline: Negative login cases: <variant>
    Given I attempt to login with the following user data:
      | email   | password   |
      | <email> | <password> |
    Then I should see a login error message "<message>"
    
    Examples:
      | variant         | email   | password | message                                |
      | missingEmail    | empty   | valid    | Please enter both email and password   |
      | missingPassword | valid   | empty    | Please enter both email and password   |
      | invalidEmail    | invalid | valid    | Invalid credentials. Please try again. |
  
  @ui
  Scenario: Registered user is unable to login until approved
    Given a newly registered user exists
    When I attempt to login with the user
    Then login should fail
  
  @ui
  Scenario: Successful login
    Given a newly registered user exists
    And the admin approves the user
    When I attempt to login with the user
    Then I should see the Dashboard
