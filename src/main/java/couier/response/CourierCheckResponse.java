package couier.response;

import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CourierCheckResponse {

    public void successfulRegistration(ValidatableResponse response){
        response.assertThat().body("ok", is(true))
                .and()
                .statusCode(HttpURLConnection.HTTP_CREATED);
    }

    public void registrationWithoutLoginOrPassword(ValidatableResponse response){
        response.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST);
    }
    public void registrationOfAlreadyRegistered(ValidatableResponse response){
         response.assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(HttpURLConnection.HTTP_CONFLICT);
    }
    public int successfulLogin(ValidatableResponse response){
        return response.assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id");
    }
    public void loginWithoutLoginOrPassword(ValidatableResponse response){
        response.assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST);
    }
    public void loginWithInvalidLoginOrPassword(ValidatableResponse response){
        response.assertThat().body("message", equalTo("Учетная запись не найдена"))
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND);
    }

    public void successfulDelete(ValidatableResponse response){
        response.assertThat().body("ok", is(true))
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    public void deleteWithoutID(ValidatableResponse response){
        response.assertThat().body("message", equalTo("Недостаточно данных для удаления курьера"))
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST);
    }
    public void deleteWithInvalidID(ValidatableResponse response){
        response.assertThat().body("message", equalTo("Курьера с таким id нет."))
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND);
    }

}
