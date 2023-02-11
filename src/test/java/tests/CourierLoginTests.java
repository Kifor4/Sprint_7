package tests;

import client.CourierAPIClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Авторизация курьера")
public class CourierLoginTests {
    private CourierAPIClient courierAPIClient;
    private String login;
    private String password;

    @Before
    public void setUp() {
        courierAPIClient = new CourierAPIClient();
        login = RandomStringUtils.randomAlphanumeric(5, 10);
        password = RandomStringUtils.randomAlphanumeric(5, 10);
        String firstName = RandomStringUtils.randomAlphabetic(5, 10);
        courierAPIClient.createCourier(login, password, firstName);
    }


    @Test
    @DisplayName("Авторизация курьера")
    public void positiveCourierAuthorizationTest() {
        Response response = courierAPIClient.authorizationCourier(login, password);
        courierAPIClient.checkPositiveCourierLogin(response);
    }

    @Test
    @DisplayName("Неудачная авторизация курьера без логина")
    public void noLoginCourierAuthorizationTest() {
        Response response = courierAPIClient.authorizationNoLoginCourier(password);
        courierAPIClient.checkNegativeCourierWithoutParamAuthorization(response);
    }

    @Test
    @DisplayName("Неудачная авторизация курьера без пароля")
    public void noPasswordCourierAuthorizationTest() {
        Response response = courierAPIClient.authorizationNoPasswordCourier(login);
        courierAPIClient.checkNegativeCourierWithoutParamAuthorization(response);
    }

    @Test
    @DisplayName("Неудачная авторизация курьера c неверным логином")
    public void wrongLoginCourierAuthorizationTest() {
        String wrongLogin = RandomStringUtils.randomAlphanumeric(11);
        Response response = courierAPIClient.authorizationCourier(wrongLogin, password);
        courierAPIClient.checkNegativeCourierWithWrongParamAuthorization(response);
    }

    @Test
    @DisplayName("Неудачная авторизация курьера c неверным паролем")
    public void wrongPasswordCourierAuthorizationTest() {
        String wrongPassword = RandomStringUtils.randomAlphanumeric(11);
        Response response = courierAPIClient.authorizationCourier(login, wrongPassword);
        courierAPIClient.checkNegativeCourierWithWrongParamAuthorization(response);
    }


    @After
    public void tearDown() {
        int id = courierAPIClient.getCourierID(login, password);
        courierAPIClient.deleteCourierById(id);
    }
}
