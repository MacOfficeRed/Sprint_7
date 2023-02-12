package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierAuthorizationNegativeParamTest {
    private CourierClient courierClient;
    private Courier courier;
    private int expectedStatusCode;
    private String expectedMessage;

    public CourierAuthorizationNegativeParamTest(Courier courier, int expectedStatusCode, String expectedMessage) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters(name = "{index}, {1}, {2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {CourierTestGen.getNonExist(), SC_NOT_FOUND, "Учетная запись не найдена"},
                {CourierTestGen.getWithoutLogin(), SC_BAD_REQUEST, "Недостаточно данных для входа"},
                {CourierTestGen.getWithoutPassword(), SC_BAD_REQUEST, "Недостаточно данных для входа"}
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @Description("Make an negative authorization with 3 cases:\n" +
            "1. With a non-exist user\n" +
            "2. Without a login field\n" +
            "3. Without a password field")
    @DisplayName("Negative courier authorization")
    public void negativeCourierAuthorizationParamsTest() {
        ValidatableResponse responseLogin = courierClient.login(Credentials.getCredentialsUsing(courier));
        int actualStatusCode = responseLogin.extract().statusCode();
        String actualMessage = responseLogin.extract().path("message");
        assertEquals("We expected " + expectedMessage + ", but found " + actualMessage,
                expectedMessage, actualMessage);
        assertEquals("We expected " + expectedStatusCode + ", but found " + actualStatusCode,
                expectedStatusCode, actualStatusCode);
    }
}
