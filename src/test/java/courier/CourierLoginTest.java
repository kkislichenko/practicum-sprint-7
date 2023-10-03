package courier;

import couier.CourierLogin;
import random.RandomData;
import couier.CourierRegistration;
import couier.response.CourierCheckResponse;
import couier.response.CourierTakeResponse;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourierLoginTest {
    CourierTakeResponse courierTakeResponse = new CourierTakeResponse();
    CourierCheckResponse courierCheckResponse = new CourierCheckResponse();
    protected int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }
    @Test
    public void loginCourier(){
        CourierRegistration randomCourier = RandomData.randomCourierRegistration();
        courierCheckResponse.successfulRegistration(courierTakeResponse.createCourierResponse(randomCourier));
        CourierLogin loginCourierData = CourierLogin.from(randomCourier);
        courierId = courierCheckResponse.successfulLogin(courierTakeResponse.loginCourierResponse(loginCourierData));
    }

    @Test
    public void loginWithoutLogin(){
        CourierLogin courierWithoutLogin = new CourierLogin("","withoutLogin");
        courierCheckResponse.loginWithoutLoginOrPassword(courierTakeResponse.loginCourierResponse(courierWithoutLogin));

    }

    @Test
    public void loginWithoutPassword(){
        CourierLogin courierWithoutLogin = new CourierLogin("withoutPassword","");
        courierCheckResponse.loginWithoutLoginOrPassword(courierTakeResponse.loginCourierResponse(courierWithoutLogin));
    }
    @Test
    public void loginNonexistentCourier(){
        CourierLogin nonexistentCourier = RandomData.randomCourierLogin();
        courierCheckResponse.loginWithInvalidLoginOrPassword(courierTakeResponse.loginCourierResponse(nonexistentCourier));
    }

    @After
    public void courierDelete(){
        if(courierId != 0){
            courierCheckResponse.successfulDelete(courierTakeResponse.deleteCourierResponse(courierId));
        }
    }
}
