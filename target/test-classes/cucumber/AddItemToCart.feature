Feature: Login to a portal and search for the an expensive item and add to cart

  @addItemToCart
  Scenario: Login as a valid user and add an expensive dress to the cart and logout
    Given I login to the application using the below credentials
      | username                   | password    |
      | automationguru92@gmail.com | password123 |
    When I search for the most expensive "Dresses"
    And I add the "max" price item to the cart
    Then The item should be added to the cart
    And I should log out from the application


  @verifyItemPresentInCart
  Scenario: Login as a valid user and verify if the previously added item is still in the cart
    Given I login to the application using the below credentials
      | username                   | password    |
      | automationguru92@gmail.com | password123 |
    When I navigate to cart
    Then I should not see the item in the cart
    And I should log out from the application


  @deleteItemFromCart
  Scenario: Login as a valid user and add an expensive dress to the cart and logout
    Given I login to the application using the below credentials
      | username                   | password    |
      | automationguru92@gmail.com | password123 |
    When I search for the most expensive "Women"
    And I add the "min" price item to the cart
    Then The item should be added to the cart
    When I delete the item from the cart
    Then I should not see the item in the cart
    And I should log out from the application