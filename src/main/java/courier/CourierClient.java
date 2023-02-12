package courier;

import config.Config;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Config {
    private static final String COURIER_CREATE = "/api/v1/courier/";
    private static final String COURIER_LOGIN = "/api/v1/courier/login/";
    private static final String COURIER_DELETE = "/api/v1/courier/";

    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpecification())
                .body(courier)
                .when()
                .post(COURIER_CREATE)
                .then();
    }

    public ValidatableResponse login(Credentials credentials) {
        return given()
                .spec(getSpecification())
                .body(credentials)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getSpecification())
                .when()
                .delete(COURIER_DELETE + courierId)
                .then();
    }
}
