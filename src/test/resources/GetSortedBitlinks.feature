Feature: feature

  Background: background
    Given I want to connect to an API

  Scenario Outline: I make a Request to Sort the Bitlinks of a given Group with Query Parameters
    Given I have a 'Authorization' Header with value 'CORRECT_AUTHORIZATION'
    And I have a 'Content-Type' Header with value 'application/json'
    And I have a '<query>' Query with Parameter '<parameter>'
    When I make a Get Request to '/groups/MY_GROUP/bitlinks/clicks'
    Then the Response Status Code is 200
    And the Response conforms to the Schema 'bitlinks-sorted-schema.json'
    And the Sorted Links are Sorted by Clicks
    And the number of Links is equal to number of Sorted Links
    Examples:
      | query      | parameter |
      |            |           |
      | unit       | week      |
      | units      | 10        |
      | unit,units | week,10   |

  Scenario: I make a Request to Sort the Bitlinks of a given Group, with an invalid Sort Type
    Given I have a 'Authorization' Header with value 'CORRECT_AUTHORIZATION'
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Get Request to '/groups/MY_GROUP/bitlinks/null'
    Then the Response Status Code is 400
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'INVALID_ARG_SORT'
    And the 'description' parameter is equal to 'The value provided is invalid.'

  Scenario: I make a Request to Sort the Bitlinks of a given Group, with no Authorization
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Get Request to '/groups/MY_GROUP/bitlinks/clicks'
    Then the Response Status Code is 403
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'FORBIDDEN'

  Scenario: I make a Request to Sort the Bitlinks of a given Group, with incorrect Authorization
    And I have a 'Authorization' Header with value 'null'
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Get Request to '/groups/MY_GROUP/bitlinks/clicks'
    Then the Response Status Code is 403
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'FORBIDDEN'

  Scenario: I make a Request to Sort the Bitlinks of a Group which does not exist; for Security, 403 is expected
    Given I have a 'Authorization' Header with value 'CORRECT_AUTHORIZATION'
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Get Request to '/groups/null/bitlinks/clicks'
    Then the Response Status Code is 403
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'FORBIDDEN'