package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.junit.Assert.assertEquals;

public class CourierCreatingIdenticalNegativeTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    private static final String MESSAGE_409 = "Этот логин уже используется. Попробуйте другой.";

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierTestGen.getRandomCourier();
        ValidatableResponse responseCreate = courierClient.create(courier);
    }

    @After
    public void cleanup() {
        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @Description("The system should return an error with 409 code when a user tries to create identical couriers")
    @DisplayName("Creating identical couriers should be impossible")
    public void createIdenticalCouriersTest() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.getCredentialsUsing(courier));
        courierId = responseLogin.extract().path("id");
        int actualStatusCode = responseCreate.extract().path("code");
        String actualMessage = responseCreate.extract().path("message");
        assertEquals("We expected " + SC_CONFLICT + ", but found " + actualStatusCode,
                SC_CONFLICT, actualStatusCode);
        assertEquals(MESSAGE_409, actualMessage);
    }
}
