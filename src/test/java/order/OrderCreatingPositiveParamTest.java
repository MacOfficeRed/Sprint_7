package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreatingPositiveParamTest {
    private OrderClient orderClient;
    private Order order;
    private int expectedStatusCode;

    public OrderCreatingPositiveParamTest(Order order, int expectedStatusCode) {
        this.order = order;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {OrderTestGen.getBlackColor(), SC_CREATED},
                {OrderTestGen.getGreyColor(), SC_CREATED},
                {OrderTestGen.getWithoutColours(), SC_CREATED},
                {OrderTestGen.getWithTwoColours(), SC_CREATED}
        };
    }

    @Before
    public void setup() {
        orderClient = new OrderClient();
    }

    @Test
    @Description("Make orders with 4 cases:\n" +
            "1. With a black color\n" +
            "2. With a gray color\n" +
            "3. Without any colors\n" +
            "4. With two colors")
    @DisplayName("Make orders positive tests")
    public void orderShouldBeCreated() {
        ValidatableResponse responseCreate = orderClient.create(order);
        int actualStatusCode = responseCreate.extract().statusCode();
        int track = responseCreate.extract().path("track");
        assertEquals("We expected " + expectedStatusCode + ", but found " + actualStatusCode,
                expectedStatusCode, actualStatusCode);
        assertNotNull("Track return null", track);
    }
}
