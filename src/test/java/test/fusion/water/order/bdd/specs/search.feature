#Author: araf.karsh@metamagic.in
#Keywords Summary :
#Feature: By Product from Amazon
@tag
Feature: Amazon Search Result

  Scenario Outline: Select Product and Add to Cart
    Given I start a new browser
    And  I am on Amazon search page
    When I do a search for "<keyword>"
    And I click the search button
    And the first title should be "<title>"

    Examples: 
      | keyword     | title                                |
      | iPhone 12   | Apple iPhone 12 (128GB)              |
      | Pixel 5     | Google Pixel 5 5G 128GB - Just Black |
