package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierCreatingNegativeParamTest {
    private CourierClient courierClient;
    private Courier courier;
    private int expectedStatusCode;
    private String expectedMessage;

    public CourierCreatingNegativeParamTest(Courier courier, int expectedStatusCode, String expectedMessage) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {CourierTestGen.getWithoutLogin(), SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи"},
                {CourierTestGen.getWithoutPassword(), SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи"}
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @Description("Make an negative courier creation with 2 cases:\n" +
            "1. Creation without a login field\n" +
            "2. Creation without a password field")
    @DisplayName("Negative courier creation")
    public void createCourierWithOutOneParamTest() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        int actualStatusCode = responseCreate.extract().path("code");
        String actualMessage = responseCreate.extract().path("message");
        assertEquals("We expected " + expectedMessage + ", but found " + actualMessage,
                expectedMessage, actualMessage);
        assertEquals("We expected " + expectedStatusCode + ", but found " + actualStatusCode,
                expectedStatusCode, actualStatusCode);
    }
}
