package couier.response;

import couier.CourierLogin;
import couier.CourierRegistration;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierTakeResponse {

    public ValidatableResponse createCourierResponse(CourierRegistration courier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then().log().all();
    }

    public ValidatableResponse loginCourierResponse(CourierLogin courier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then().log().all();
    }
    public ValidatableResponse deleteCourierResponse(int courierID) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(String.format("/api/v1/courier/%d", courierID))
                .then().log().all();
    }

    public ValidatableResponse deleteCourierResponse() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .delete(String.format("/api/v1/courier/"))
                .then().log().all();
    }
}
