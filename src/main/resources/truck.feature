Feature: Gestión de Camiones

  Scenario: Agregar dos camiones, buscar uno y marcarlo como en ruta
    Given I access the add truck page
    When I enter the plate and driver of the first truck
    And I click on add truck button
    Then The first truck should appear in the list
    When I enter the plate and driver of the second truck
    And I click on add truck button
    Then The second truck should appear in the list
    When I search for the first truck by plate
    Then The first truck should be found in the list
    When I mark the first truck as on route
    Then The first truck should be marked as on route
