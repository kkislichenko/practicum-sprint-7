package order.response;

import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;

public class OrderCheckResponse {
    public int makeOrderSuccessfully(ValidatableResponse response){
        return response.assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("track");
    }
    public void cancelOrderSuccessfully(ValidatableResponse response){
        response.assertThat().body("ok", is(true))
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }
    public void getOrderListSuccessfully(ValidatableResponse response){
        response.assertThat().body("orders", notNullValue())
                .body("orders", is(not(empty())))
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }
    public void successfulAcceptOrder(ValidatableResponse response){
        response.assertThat().body("ok", is(true))
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }
    public void acceptOrderWithoutCourierIdOrOrderID(ValidatableResponse response){
        response.assertThat().body("message", equalTo("Недостаточно данных для поиска"))
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST);
    }
    public void acceptOrderWithInvalidOrderID(ValidatableResponse response){
        response.assertThat().body("message", equalTo("Заказа с таким id не существует"))
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND);
    }
    public void acceptOrderWithInvalidCourierId(ValidatableResponse response){
        response.assertThat().body("message", equalTo("Курьера с таким id не существует"))
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND);
    }
    public int successfulGetOrder(ValidatableResponse response){
        return response.assertThat().body("order", notNullValue())
                .body("order", is(not(empty())))
                .and()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("order.id");
    }
    public void getOrderWithoutTrackNumber(ValidatableResponse response){
        response.assertThat().body("message", equalTo("Недостаточно данных для поиска"))
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST);
    }
    public void getOrderWithInvalidTrackNumber(ValidatableResponse response){
        response.assertThat().body("message", equalTo("Заказ не найден"))
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND);
    }
    public void successfulFinishOrder(ValidatableResponse response){
        response.assertThat().body("ok", is(true))
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }
}
