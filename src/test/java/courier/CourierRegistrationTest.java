package courier;

import couier.CourierLogin;
import random.RandomData;
import couier.CourierRegistration;
import couier.response.CourierCheckResponse;
import couier.response.CourierTakeResponse;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;


public class CourierRegistrationTest {
    CourierTakeResponse courierTakeResponse = new CourierTakeResponse();
    CourierCheckResponse courierCheckResponse = new CourierCheckResponse();
    protected int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createCourier() {
        CourierRegistration randomCourier = RandomData.randomCourierRegistration();
        courierCheckResponse.successfulRegistration(courierTakeResponse.createCourierResponse(randomCourier));
        CourierLogin loginCourierData = CourierLogin.from(randomCourier);
        courierId = courierCheckResponse.successfulLogin(courierTakeResponse.loginCourierResponse(loginCourierData));
    }

    @Test
    public void duplicateCourier() {
        CourierRegistration courier = RandomData.randomCourierRegistration();
        courierCheckResponse.successfulRegistration(courierTakeResponse.createCourierResponse(courier));
        courierCheckResponse.registrationOfAlreadyRegistered(courierTakeResponse.createCourierResponse(courier));
        CourierLogin login = CourierLogin.from(courier);
        courierId = courierCheckResponse.successfulLogin(courierTakeResponse.loginCourierResponse(login));
    }

    @Test
    public void createWithoutLogin(){
        CourierRegistration courier = new CourierRegistration("", "NewCourier", "withoutLogin");
        courierCheckResponse.registrationWithoutLoginOrPassword(courierTakeResponse.createCourierResponse(courier));
    }

    @Test
    public void createWithoutPassword(){
        CourierRegistration courier = new CourierRegistration("NewCourier", "", "withoutPassword");
        courierCheckResponse.registrationWithoutLoginOrPassword(courierTakeResponse.createCourierResponse(courier));
    }
    @After
    public void courierDelete(){
        if(courierId != 0){
        courierCheckResponse.successfulDelete(courierTakeResponse.deleteCourierResponse(courierId));
        }
    }

}
