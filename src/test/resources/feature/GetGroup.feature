Feature: Test the Get Group Bitly API
  The Get Group API returns the details of a Group for a supplied Group GUID.
  It requires correct Authorization and Content-Type Headers.


  Background: As a User, I need to connect to one of the Bitly APIs
    Given I want to connect to an API

  Scenario: I make a Request to Get a Group by it's GUID
    Given I have a 'Authorization' Header with value 'CORRECT_AUTHORIZATION'
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Get Request to '/groups/MY_GROUP'
    Then the Response Status Code is 200
    And the Response conforms to the Schema 'groups-schema.json'
    And the 'guid' parameter is equal to 'MY_GROUP'
    And the 'is_active' parameter is equal to 'true'

  Scenario: I make a Request to Get a Group by it's GUID with no Authorization
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Get Request to '/groups/MY_GROUP'
    Then the Response Status Code is 403
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'FORBIDDEN'

  Scenario: I make a Request to Get a Group by it's GUID with incorrect Authorization
    And I have a 'Authorization' Header with value 'null'
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Get Request to '/groups/MY_GROUP'
    Then the Response Status Code is 403
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'FORBIDDEN'

  Scenario: I make a Request to Get a Group by it's GUID, with a wrong GUID; for Security, 403 is expected
    Given I have a 'Authorization' Header with value 'CORRECT_AUTHORIZATION'
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Get Request to '/groups/null'
    Then the Response Status Code is 403
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'FORBIDDEN'