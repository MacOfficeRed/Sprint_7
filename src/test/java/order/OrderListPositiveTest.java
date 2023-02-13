package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class OrderListPositiveTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @Description("Make an order and check status code + size of list. It should be non-empty")
    @DisplayName("getOrderList method should return non-empty orderList")
    public void getOrderListShouldReturnedOrderList() {
        ValidatableResponse responseOrderList = orderClient.getOrderList();
        ArrayList actualList = responseOrderList.extract().path("orders");
        boolean isListSizeNonEmpty = actualList.size() > 0;
        int actualStatusCode = responseOrderList.extract().statusCode();
        assertEquals("We expected " + SC_OK + ", but found " + actualStatusCode,
                SC_OK, actualStatusCode);
        assertEquals("We expected that size is grater than 0, but found size " + actualList.size(),
                true, isListSizeNonEmpty);
    }
}
