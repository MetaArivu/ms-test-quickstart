#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: As a Consumer
  I want to make the payent 
  So that I can buy products

  Scenario: Save Payment
    Given The request is authenticated
    When Input contains login as "userloginid", order as "orderid", and payment in "payment details"
    Then The System Validates the credit card 123456789 and the expirydate "Expiry Date" and the cardname "John Doe" and the cvv 123 Must NOT be Null
