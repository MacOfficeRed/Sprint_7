package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourierCreatingPositiveTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierTestGen.getRandomCourier();
    }

    @After
    public void cleanup() {
        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @Description("Login with courier and check status code and id")
    @DisplayName("A courier should be able to login")
    public void courierShouldBeAbleToLogin() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.getCredentialsUsing(courier));
        courierId = responseLogin.extract().path("id");
        int actualStatusCode = responseLogin.extract().statusCode();
        assertEquals("We expected " + SC_OK + ", but found " + actualStatusCode,
                SC_OK, actualStatusCode);
        assertNotNull("courierId return null", courierId);
    }

    @Test
    @Description("Make a positive courier creation. Create a courier and check a message")
    @DisplayName("Positive courier creation")
    public void courierShouldBeCreated() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.getCredentialsUsing(courier));
        courierId = responseLogin.extract().path("id");
        int actualStatusCode = responseCreate.extract().statusCode();
        boolean isOk = responseCreate.extract().path("ok");
        assertEquals("We expected true, but found " + isOk, true, isOk);
        assertEquals("We expected " + SC_CREATED + ", but found " + actualStatusCode,
                SC_CREATED, actualStatusCode);
    }
}
