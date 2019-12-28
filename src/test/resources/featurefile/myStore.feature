Feature: Update first Name of user
This feature verifies updation of personal details of the user
 
Scenario: Check that user can update his/her first name
Given I login to My Store
When I update my first name to "SnehaNew"
Then I verify that my first name is updated to "SnehaNew"

Scenario: Check that user can place an order
Given I login to My Store
When I place an order
Then I verify that order is seen in account order history

