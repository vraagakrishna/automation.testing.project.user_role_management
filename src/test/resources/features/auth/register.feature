Feature: User Registration
  As a user
  I want to register in the system
  So that I can login after approval
  
  Background:
    Given I am on the registration page
  
  @ui
  Scenario Outline: Negative registration cases: <variant>
    Given I attempt to register with the following user data:
      | first name   | last name   | email   | password   | confirm password   | group   |
      | <first_name> | <last_name> | <email> | <password> | <confirm_password> | <group> |
    Then I should see a register error message "<message>"
    
    Examples:
      | variant          | first_name | last_name | email   | password | confirm_password | group   | message                                     |
      | missingFirstName | empty      | valid     | valid   | valid    | valid            | valid   | Please fill in all fields                   |
      | missingLastName  | valid      | empty     | valid   | valid    | valid            | valid   | Please fill in all fields                   |
      | missingEmail     | valid      | valid     | empty   | valid    | valid            | valid   | Please fill in all fields                   |
      | invalidEmail     | valid      | valid     | invalid | valid    | valid            | valid   | Please enter a valid email address          |
      | missingPassword  | valid      | valid     | valid   | empty    | valid            | valid   | Passwords do not match!                     |
      | weakPassword     | valid      | valid     | valid   | weak     | weak             | valid   | Password must be at least 8 characters long |
      | mismatchPassword | valid      | valid     | valid   | valid    | mismatch         | valid   | Passwords do not match!                     |
      | emptyGroup       | valid      | valid     | valid   | valid    | valid            | invalid | Please select a group                       |
  
  @ui
  Scenario: Positive registration
    Given I have valid user data
    When I register the user
    Then registration should be successful
