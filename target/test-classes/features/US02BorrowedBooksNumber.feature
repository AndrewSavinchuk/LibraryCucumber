@us02
Feature: As a librarian, I want to know who borrowed books
@db @ui
  Scenario: verify the amount of borrowed books
    Given I am in the homepage of library app
    When I take borrowed books number
    Then borrowed books number information must match with DB
