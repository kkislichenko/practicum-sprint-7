package random;

import couier.CourierLogin;
import couier.CourierRegistration;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class RandomData {

    public static CourierRegistration randomCourierRegistration(){
        return new CourierRegistration((RandomStringUtils.randomAlphanumeric(10, 12)), "CourierAutotest", "CourierAutotest");
    }
    public static CourierLogin randomCourierLogin(){
        return new CourierLogin((RandomStringUtils.randomAlphanumeric(10, 12)), "CourierAutotest");
    }
    public static int randomInt(){
        return RandomUtils.nextInt(80000000, 90000000) ;
    }

}
