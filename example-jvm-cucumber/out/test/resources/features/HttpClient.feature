
Feature: Restful Booker client
  Scenario: Receive all bookings

    Given I query all my bookings
    Then I get http status 200
    And I receive at least one responses
