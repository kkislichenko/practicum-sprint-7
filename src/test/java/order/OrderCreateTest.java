package order;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import order.response.OrderCheckResponse;
import order.response.OrderTakeResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import java.util.List;


@RunWith(Parameterized.class)
public class OrderCreateTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private ArrayList<String> color;
    OrderTakeResponse orderTakeResponse = new OrderTakeResponse();
    OrderCheckResponse orderCheckResponse = new OrderCheckResponse();
    protected int trackNumber;
    public OrderCreateTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, ArrayList<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object[] getOrderData() {
        return new Object[][] {
                {"Auto", "Test-1", "860321, Курская область, город Балашиха, спуск Космонавтов, 33", "Беговая", "79099196767", 3, "2023-11-06", "Комментарий к заказу", new ArrayList<>(List.of("BLACK"))},
                {"Auto", "Test-2", "860321, Курская область, город Балашиха, спуск Космонавтов, 33", "Беговая", "79099196767", 3, "2023-11-06", "Комментарий к заказу", new ArrayList<>(List.of("BLACK", "GREY"))},
                {"Auto", "Test-3", "860321, Курская область, город Балашиха, спуск Космонавтов, 33", "Беговая", "79099196767", 3, "2023-11-06", "Комментарий к заказу", new ArrayList<>()},

        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void orderCreateTest(){
        OrderCreate order = new OrderCreate(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse response = orderTakeResponse.createOrderResponse(order);
        trackNumber = orderCheckResponse.makeOrderSuccessfully(response);
    }

    @After
    public void cancelOrder(){
        if(trackNumber != 0){
            orderCheckResponse.cancelOrderSuccessfully(orderTakeResponse.cancelOrderResponse(trackNumber));
        }
    }
}
