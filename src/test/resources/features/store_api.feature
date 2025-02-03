Feature: Operaciones del API de Store
  Como usuario del API de PetStore
  Quiero poder crear y recuperar pedidos
  Para poder gestionar los pedidos de la tienda de manera efectiva

  Scenario Outline: Crear un nuevo pedido en la tienda
    Given I have the order details with petId "<petId>", quantity "<quantity>", shipDate "<shipDate>", status "<status>", and complete "<complete>"
    When I send a POST request to "/store/order"
    Then the response status code should be 200
    And the response body should contain the order details

    Examples:
      | petId | quantity | shipDate          | status   | complete |
      | 400     | 5        | 2023-10-01T00:00Z | placed   | true     |
      | 200     | 3        | 2023-10-02T00:00Z | approved | false    |

  Scenario Outline: Recuperar un pedido existente por orderId
    Given I have an existing order with orderId "<orderId>"
    When I send a GET request to "/store/order/<orderId>"
    Then the response status code should be 200
    And the response body should contain the order details

    Examples:
      | orderId |
      | 1       |
      | 2       |