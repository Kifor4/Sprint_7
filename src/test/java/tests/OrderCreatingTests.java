package tests;

import client.OrdersAPIClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(JUnitParamsRunner.class)
@DisplayName("Создание заказа")
public class OrderCreatingTests {
    private OrdersAPIClient ordersAPIClient;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;


    @Before
    public void setUp() {
        ordersAPIClient = new OrdersAPIClient();
        firstName = RandomStringUtils.randomAlphabetic(5, 10);
        lastName = RandomStringUtils.randomAlphabetic(5, 10);
        address = RandomStringUtils.randomAlphabetic(5, 10);
        metroStation = RandomStringUtils.randomNumeric(1, 2);
        phone = RandomStringUtils.randomNumeric(10);
        rentTime = (int) (Math.random() * 10);
        deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        comment = RandomStringUtils.randomAlphanumeric(5, 20);
    }


    @Test
    @DisplayName("Создание заказа")
    @Parameters({"BLACK", "GREY", "BLACK, GREY", ""})
    public void positiveCourierAuthorizationTest(String... color) {
        Response response = ordersAPIClient.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ordersAPIClient.checkPositiveOrderCreating(response);
    }
}
