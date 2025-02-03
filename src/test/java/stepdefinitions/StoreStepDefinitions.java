package stepdefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import org.hamcrest.Matchers;

public class StoreStepDefinitions {
    private RequestSpecification request;
    private Response response;
    private String baseURI = "https://petstore.swagger.io/v2";

    @Given("I have the order details with petId {string}, quantity {string}, shipDate {string}, status {string}, and complete {string}")
    public void i_have_the_order_details(String petId, String quantity, String shipDate, String status, String complete) {
        try {
            int petIdInt = Integer.parseInt(petId);
            int quantityInt = Integer.parseInt(quantity);
            boolean isComplete = Boolean.parseBoolean(complete);

            String requestBody = String.format(
                    "{\"petId\": %d, \"quantity\": %d, \"shipDate\": \"%s\", \"status\": \"%s\", \"complete\": %b}",
                    petIdInt, quantityInt, shipDate, status, isComplete
            );

            request = given()
                    .baseUri(baseURI)
                    .header("Content-Type", "application/json")
                    .body(requestBody);

            System.out.println("Request Body: " + requestBody);
        } catch (NumberFormatException e) {
            System.err.println("Error: petId and quantity must be valid integers.");
        }
    }

    @When("I send a POST request to {string}")
    public void i_send_a_post_request(String endpoint) {
        response = request.when().post(endpoint);
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Then("the response body should contain the order details")
    public void the_response_body_should_contain_the_order_details() {
        response.then().assertThat().body("petId", Matchers.notNullValue());
        response.then().assertThat().body("quantity", Matchers.notNullValue());
        response.then().assertThat().body("shipDate", Matchers.not(Matchers.isEmptyOrNullString()));
        response.then().assertThat().body("status", Matchers.notNullValue());
        response.then().assertThat().body("complete", Matchers.notNullValue());
    }


    @Given("I have an existing order with orderId {string}")
    public void i_have_an_existing_order_with_orderId(String orderId) {
        request = given().baseUri(baseURI);
    }

    @When("I send a GET request to {string}")
    public void i_send_a_get_request(String endpoint) {
        response = request.when().get(endpoint);
        System.out.println("Response Body: " + response.getBody().asString());
    }
}
