Feature: DemoBlaze Run feature

  Scenario: Login into App
    Given User is on Launch Page
    When User login
    Then Should display Home Page
    
   Scenario Outline: Add items to cart
   
    When Add item "<data>" to cart
    Then Item must be added to cart
    
   Examples: 
    | data |
    | HTC One M9 |
    | Samsung galaxy s6 |
    
    Scenario: Delete an item
    When Delete an item from cart
    Then Item should be deleted
    
    Scenario: Purchase
    When Place Order
    Then Item should be purchased
    
    