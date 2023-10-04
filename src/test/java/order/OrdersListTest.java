package order;

import io.restassured.RestAssured;
import order.response.OrderCheckResponse;
import order.response.OrderTakeResponse;
import org.junit.Before;
import org.junit.Test;

public class OrdersListTest {
    OrderTakeResponse orderTakeResponse = new OrderTakeResponse();
    OrderCheckResponse orderCheckResponse = new OrderCheckResponse();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void getOrdersList(){
        orderCheckResponse.getOrderListSuccessfully(orderTakeResponse.orderListResponse());
    }

}
