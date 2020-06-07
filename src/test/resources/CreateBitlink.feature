Feature: feature

  Background: background
    Given I want to connect to an API

  Scenario Outline: I make a Request to generate a BitLink with a Long URL
    Given I have a Request
    And my Request has a 'long_url' of '<longUrl>'
    And I have a 'Authorization' Header with value 'CORRECT_AUTHORIZATION'
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Post Request to '/bitlinks'
    Then the Response Status Code is 200
    And the Response conforms to the Schema 'bitlinks-schema.json'
    And the ID in the Response matches the Link
    And the Link correctly redirects to the expected URL
    And the 'long_url' parameter is equal to '<longUrl>'
    Examples:
      | longUrl                                           |
      | https://www.elsevier.com/en-gb/research-platforms |
      | https://www.google.com                            |
      | https://www.example.com                           |

  Scenario: I make a Request to generate a BitLink with no Content
    Given I have a Request
    And I have a 'Authorization' Header with value 'CORRECT_AUTHORIZATION'
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Post Request to '/bitlinks'
    Then the Response Status Code is 400
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'INVALID_ARG_LONG_URL'
    And the 'description' parameter is equal to 'The value provided is invalid.'

  Scenario: I make a Request to generate a BitLink with Unprocessable Content
    Given I have a 'Authorization' Header with value 'CORRECT_AUTHORIZATION'
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Post Request to '/bitlinks' with Raw Content 'Bad Content'
    Then the Response Status Code is 422
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'UNPROCESSABLE_ENTITY'
    And the 'description' parameter is equal to 'The JSON value provided is invalid.'

  Scenario: I make a Request to generate a BitLink with no Authorization
    Given I have a Request
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Post Request to '/bitlinks'
    Then the Response Status Code is 403
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'FORBIDDEN'

  Scenario: I make a Request to generate a BitLink with incorrect Authorization
    Given I have a Request
    And I have a 'Authorization' Header with value 'null'
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Post Request to '/bitlinks'
    Then the Response Status Code is 403
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'FORBIDDEN'

  Scenario: I make a Request to generate a BitLink with no Content-Type Header
    Given I have a Request
    And I have a 'Authorization' Header with value 'CORRECT_AUTHORIZATION'
    When I make a Post Request to '/bitlinks'
    Then the Response Status Code is 406
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'INVALID_CONTENT_TYPE_HEADER'

  Scenario: I make a Request to generate a BitLink with incorrect Content-Type Header
    Given I have a Request
    And I have a 'Authorization' Header with value 'CORRECT_AUTHORIZATION'
    And I have a 'Content-Type' Header with value 'application/xml'
    When I make a Post Request to '/bitlinks'
    Then the Response Status Code is 406
    And the Response conforms to the Schema 'error-schema.json'
    And the 'message' parameter is equal to 'INVALID_CONTENT_TYPE_HEADER'

  Scenario: I make a Get Request to the Create BitLink API
    Given I have a 'Authorization' Header with value 'CORRECT_AUTHORIZATION'
    And I have a 'Content-Type' Header with value 'application/json'
    When I make a Get Request to '/bitlinks'
    Then the Response Status Code is 405
    And the Response Content is equal to 'Method Not Allowed'