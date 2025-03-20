Feature: Book Search in Online Library

  As a user of the online bookstore
  I want to search for books by title
  So that I can find the book I want to buy

  Scenario: Successful search for an existing book
    Given I am on the bookstore homepage
    When I search for "Harry Potter"
    Then I should see a book with title "Harry Potter and the Sorcerer's Stone"
    And the author should be "J.K. Rowling"

  Scenario: Searching for a book that does not exist
    Given I am on the bookstore homepage
    When I search for "Nonexistent Book"
    Then I should see a message indicating no books are found