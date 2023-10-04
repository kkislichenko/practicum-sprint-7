package order;

import io.restassured.RestAssured;
import order.response.OrderCheckResponse;
import order.response.OrderTakeResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import random.RandomData;

import java.util.ArrayList;
import java.util.List;

public class GetOrderTest {
    OrderTakeResponse orderTakeResponse = new OrderTakeResponse();
    OrderCheckResponse orderCheckResponse = new OrderCheckResponse();
    protected int trackNumber;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void getOrder(){
        OrderCreate order = new OrderCreate("Auto", "Test", "860321, Курская область, город Балашиха, спуск Космонавтов, 33", "Беговая", "79099196767", 3, "2023-11-06", "Комментарий к заказу", new ArrayList<>(List.of("BLACK")));
        trackNumber = orderCheckResponse.makeOrderSuccessfully(orderTakeResponse.createOrderResponse(order));
        orderCheckResponse.successfulGetOrder(orderTakeResponse.getOrderResponse(trackNumber));
    }
    @Test
    public void getWithoutTrackNumber(){
        orderCheckResponse.getOrderWithoutTrackNumber(orderTakeResponse.getOrderResponse());
    }
    @Test
    public void getWithInvalidTrackNumber(){
        int trackNumber = RandomData.randomInt();
        orderCheckResponse.getOrderWithInvalidTrackNumber(orderTakeResponse.getOrderResponse(trackNumber));
    }

    @After
    public void cancelCreatedOrder(){
        if(trackNumber != 0){
            orderCheckResponse.cancelOrderSuccessfully(orderTakeResponse.cancelOrderResponse(trackNumber));
        }
    }
}
