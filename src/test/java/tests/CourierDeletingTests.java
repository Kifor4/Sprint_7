package tests;

import client.CourierAPIClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Удаление курьера")
public class CourierDeletingTests {
    private CourierAPIClient courierAPIClient;
    private String login;
    private String password;
    private String firstName;
    private int id;

    @Before
    public void setUp() {
        courierAPIClient = new CourierAPIClient();
        CourierModel courierModel = CourierModel.getRandomCourierModel();
        courierAPIClient.createCourierByModel(courierModel);
        id = courierAPIClient.getCourierIdByModel(courierModel);
    }


    @Test
    @DisplayName("Удаление курьера")
    public void positiveCourierDeletingTest() {
        Response response = courierAPIClient.deleteCourierById(id);
        courierAPIClient.checkPositiveCourierDeleting(response);
    }

    @Test
    @DisplayName("Удаление курьера без ID")
    public void noIdCourierDeletingTest() {
        Response response = courierAPIClient.deleteCourierWithoutId();
        courierAPIClient.checkNegativeCourierDeletingWithoutID(response);
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим ID")
    public void wrongIdCourierDeletingTest() {
        int wrongId = Integer.parseInt(RandomStringUtils.randomNumeric(9));
        Response response = courierAPIClient.deleteCourierById(wrongId);
        courierAPIClient.checkNegativeCourierDeletingWithWrongID(response);
    }

    @After
    public void tearDown() {
        courierAPIClient.deleteCourierById(id);
    }
}
