Feature: Curitiba em Movimento

  @bookVolleyball
  Scenario: Book a volleyball court
    Given I am on the Login Page
    When I click Entrar com CPF
    And I use the credentials "11341204944" "87817725"
    Then the user successfully logs in
    When I select volleyball court data
    And I select the suitable day and time
    Then I return a list of available date and time
    And I filter available spots for preferred schedule
    Then I notify the group about new matching spots



  Scenario: Send daily availability summary
    When I check available dates
    Then I send daily availability summary