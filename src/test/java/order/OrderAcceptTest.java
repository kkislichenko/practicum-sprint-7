package order;

import couier.CourierLogin;
import random.RandomData;
import couier.CourierRegistration;
import couier.response.CourierCheckResponse;
import couier.response.CourierTakeResponse;
import io.restassured.RestAssured;
import order.response.OrderCheckResponse;
import order.response.OrderTakeResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderAcceptTest {
    OrderTakeResponse orderTakeResponse = new OrderTakeResponse();
    OrderCheckResponse orderCheckResponse = new OrderCheckResponse();
    CourierTakeResponse courierTakeResponse = new CourierTakeResponse();
    CourierCheckResponse courierCheckResponse = new CourierCheckResponse();
    protected int courierId;
    protected int trackNumber;
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void acceptOrder() {
        // 1.Создать курьера
        CourierRegistration randomCourier = RandomData.randomCourierRegistration();
        courierCheckResponse.successfulRegistration(courierTakeResponse.createCourierResponse(randomCourier));
        CourierLogin loginCourierData = CourierLogin.from(randomCourier);
        courierId = courierCheckResponse.successfulLogin(courierTakeResponse.loginCourierResponse(loginCourierData));
        // 2. Создать заказ
        OrderCreate order = new OrderCreate("Auto", "Test", "860321, Курская область, город Балашиха, спуск Космонавтов, 33", "Беговая", "79099196767", 3, "2023-11-06", "Комментарий к заказу", new ArrayList<>(List.of("BLACK")));
        int trackNumber = orderCheckResponse.makeOrderSuccessfully(orderTakeResponse.createOrderResponse(order));
        // 3. Вычленить из заказа id
        int orderId = orderCheckResponse.successfulGetOrder(orderTakeResponse.getOrderResponse(trackNumber));
        // 4. Принять заказ
        orderCheckResponse.successfulAcceptOrder(orderTakeResponse.acceptOrderResponse(courierId, orderId));
        // 5. Завершить заказ по id
        orderCheckResponse.successfulFinishOrder(orderTakeResponse.finishOrderResponse(orderId));
    }
    @Test
    public void acceptWithoutCourierID() {
        OrderCreate order = new OrderCreate("Auto", "Test", "860321, Курская область, город Балашиха, спуск Космонавтов, 33", "Беговая", "79099196767", 3, "2023-11-06", "Комментарий к заказу", new ArrayList<>(List.of("BLACK")));
        trackNumber = orderCheckResponse.makeOrderSuccessfully(orderTakeResponse.createOrderResponse(order));
        int orderId = orderCheckResponse.successfulGetOrder(orderTakeResponse.getOrderResponse(trackNumber));
        orderCheckResponse.acceptOrderWithoutCourierIdOrOrderID(orderTakeResponse.acceptOrderWithoutCourierIdResponse(orderId));
    }
    @Test
    public void acceptWithoutOrderID() {
        CourierRegistration randomCourier = RandomData.randomCourierRegistration();
        courierCheckResponse.successfulRegistration(courierTakeResponse.createCourierResponse(randomCourier));
        CourierLogin loginCourierData = CourierLogin.from(randomCourier);
        courierId = courierCheckResponse.successfulLogin(courierTakeResponse.loginCourierResponse(loginCourierData));
        orderCheckResponse.acceptOrderWithoutCourierIdOrOrderID(orderTakeResponse.acceptOrderWithoutOrderIdResponse(courierId));
    }
    @Test
    public void acceptWithInvalidCourierID() {
        Random randomInvalidID = new Random();
        int courierId = randomInvalidID.nextInt(100);
        OrderCreate order = new OrderCreate("Auto", "Test", "860321, Курская область, город Балашиха, спуск Космонавтов, 33", "Беговая", "79099196767", 3, "2023-11-06", "Комментарий к заказу", new ArrayList<>(List.of("BLACK")));
        trackNumber = orderCheckResponse.makeOrderSuccessfully(orderTakeResponse.createOrderResponse(order));
        int orderId = orderCheckResponse.successfulGetOrder(orderTakeResponse.getOrderResponse(trackNumber));
        orderCheckResponse.acceptOrderWithInvalidCourierId(orderTakeResponse.acceptOrderResponse(courierId, orderId));
    }
    @Test
    public void acceptWithInvalidOrderID() {
        CourierRegistration randomCourier = RandomData.randomCourierRegistration();
        courierCheckResponse.successfulRegistration(courierTakeResponse.createCourierResponse(randomCourier));
        CourierLogin loginCourierData = CourierLogin.from(randomCourier);
        courierId = courierCheckResponse.successfulLogin(courierTakeResponse.loginCourierResponse(loginCourierData));
        int randomInvalidOrderId = RandomData.randomInt();
        orderCheckResponse.acceptOrderWithInvalidOrderID(orderTakeResponse.acceptOrderResponse(courierId, randomInvalidOrderId));
    }
    @After
    public void resetData(){
        if(courierId != 0){
            courierCheckResponse.successfulDelete(courierTakeResponse.deleteCourierResponse(courierId));
        }
        if(trackNumber != 0){
            orderCheckResponse.cancelOrderSuccessfully(orderTakeResponse.cancelOrderResponse(trackNumber));
        }
    }
}
