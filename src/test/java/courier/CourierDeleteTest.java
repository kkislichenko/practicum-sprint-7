package courier;

import couier.CourierLogin;
import random.RandomData;
import couier.CourierRegistration;
import couier.response.CourierCheckResponse;
import couier.response.CourierTakeResponse;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

public class CourierDeleteTest {
    CourierTakeResponse courierTakeResponse = new CourierTakeResponse();
    CourierCheckResponse courierCheckResponse = new CourierCheckResponse();
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void deleteCourier(){
        CourierRegistration randomCourier = RandomData.randomCourierRegistration();
        courierCheckResponse.successfulRegistration(courierTakeResponse.createCourierResponse(randomCourier));
        CourierLogin loginCourierData = CourierLogin.from(randomCourier);
        int courierId = courierCheckResponse.successfulLogin(courierTakeResponse.loginCourierResponse(loginCourierData));
        courierCheckResponse.successfulDelete(courierTakeResponse.deleteCourierResponse(courierId));
    }

    @Test
    public void deleteWithoutID(){
        courierCheckResponse.deleteWithoutID(courierTakeResponse.deleteCourierResponse());
    }
    @Test
    public void deleteWithInvalidID() {
        int courierId = RandomData.randomInt();
        courierCheckResponse.deleteWithInvalidID(courierTakeResponse.deleteCourierResponse(courierId));
    }

}
