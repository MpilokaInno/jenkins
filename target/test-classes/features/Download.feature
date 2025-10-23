Feature: Takealot Login

  As a Takealot user
  I want to log in with valid credentials
  So that I can access my account dashboard

  Background:
    Given the user is on the Takealot login page

  @valid-login
  Scenario: User logs in with valid credentials
    When the user enters valid username and password
    And clicks the login button
    Then the user should be logged in successfully

  @invalid-login
  Scenario: User attempts to log in with invalid credentials
    When the user enters invalid username or password
    And clicks the login button
    Then the user should see an error message

  @search-add-to-cart
  Scenario: Search a product and add to cart after login
    Given the user is logged in
    When the user searches for "Laptop"
    And opens the first search result
    And adds the product to the cart
    Then the cart should show at least 1 item