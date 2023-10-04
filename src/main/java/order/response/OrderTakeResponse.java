package order.response;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import order.OrderCreate;

import static io.restassured.RestAssured.given;

public class OrderTakeResponse {
    public ValidatableResponse createOrderResponse(OrderCreate order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().log().all();
    }
    public ValidatableResponse cancelOrderResponse(int trackNumber) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .when()
                .queryParam("track", Integer.toString(trackNumber))
                .put("/api/v1/orders/cancel")
                .then().log().all();
    }
    public ValidatableResponse orderListResponse() {
        return given().log().all()
                .get("/api/v1/orders")
                .then().log().all();
    }
    public ValidatableResponse acceptOrderResponse(int courierId, int orderId) {
        return given().log().all()
                .queryParam("courierId", Integer.toString(courierId))
                .put(String.format("/api/v1/orders/accept/%d", orderId))
                .then().log().all();
    }
    public ValidatableResponse acceptOrderWithoutCourierIdResponse(int orderId) {
        return given().log().all()
                .put(String.format("/api/v1/orders/accept/%d", orderId))
                .then().log().all();
    }
    public ValidatableResponse acceptOrderWithoutOrderIdResponse(int courierId) {
        return given().log().all()
                .queryParam("courierId", Integer.toString(courierId))
                .put("/api/v1/orders/accept/")
                .then().log().all();
    }

    public ValidatableResponse getOrderResponse(int trackNumber) {
        return given().log().all()
                .queryParam("t", Integer.toString(trackNumber))
                .get("/api/v1/orders/track")
                .then().log().all();
    }
    public ValidatableResponse getOrderResponse() {
        return given().log().all()
                .get("/api/v1/orders/track")
                .then().log().all();
    }
    public ValidatableResponse finishOrderResponse(int orderId) {
        return given().log().all()
                .put(String.format("/api/v1/orders/finish/%d", orderId))
                .then().log().all();
    }
}
