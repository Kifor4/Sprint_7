package tests;

import client.CourierAPIClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Создание курьера")
public class CourierCreatingTests {
    private CourierAPIClient courierAPIClient;
    private String login;
    private String password;
    private String firstName;
    private int id;

    @Before
    public void setUp() {
        courierAPIClient = new CourierAPIClient();
        login = RandomStringUtils.randomAlphanumeric(5, 10);
        password = RandomStringUtils.randomAlphanumeric(5, 10);
        firstName = RandomStringUtils.randomAlphabetic(5, 10);
    }


    @Test
    @DisplayName("Создание курьера")
    public void positiveCourierCreatingTest() {
        Response response = courierAPIClient.createCourier(login, password, firstName);
        courierAPIClient.checkPositiveCourierCreating(response);
    }

    @Test
    @DisplayName("Неудачное создание дубликата курьера")
    public void DuplicateCourierCreatingTest() {
        Response response = courierAPIClient.createCourier(login, password, firstName);
        courierAPIClient.checkPositiveCourierCreating(response);
        response = courierAPIClient.createCourier(login, password, firstName);
        courierAPIClient.checkNegativeCourierDuplicateCreating(response);
    }

    @Test
    @DisplayName("Неудачное создание курьера без логина")
    public void noLoginCourierCreatingTest() {
        Response response = courierAPIClient.createNoLoginCourier(password, firstName);
        courierAPIClient.checkNegativeCourierWithoutParamCreating(response);
    }

    @Test
    @DisplayName("Неудачное создание курьера без пароля")
    public void noPasswordCourierCreatingTest() {
        Response response = courierAPIClient.createNoPasswordCourier(login, firstName);
        courierAPIClient.checkNegativeCourierWithoutParamCreating(response);
    }

    @Test
    @DisplayName("Неудачное cоздание курьера без имени")
    public void noFirstNameCourierCreatingTest() {
        Response response = courierAPIClient.createNoFirstNameCourier(login, password);
        courierAPIClient.checkNegativeCourierWithoutParamCreating(response);
    }

    @After
    public void tearDown() {
        try {
            id = courierAPIClient.getCourierID(login, password);
        } catch (Exception e) {

        }
        courierAPIClient.deleteCourierById(id);
    }
}
