package order;

import config.Config;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Config {
    private static final String ORDER_CREATE = "/api/v1/orders/";

    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpecification())
                .body(order)
                .when()
                .post(ORDER_CREATE)
                .then();
    }

    public ValidatableResponse getOrderList() {
        return given()
                .spec(getSpecification())
                .when()
                .get(ORDER_CREATE)
                .then();
    }
}
