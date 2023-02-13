package tests;

import client.OrdersAPIClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Получение заказа")
public class OrderGettingTests {
    private OrdersAPIClient ordersAPIClient;
    private int trackNumber;

    @Before
    public void setUp() {
        ordersAPIClient = new OrdersAPIClient();
        OrderModel orderModel = OrderModel.getRandomOrderModel();
        Response response = ordersAPIClient.createOrderByModel(orderModel);
        trackNumber = ordersAPIClient.getTrackNumberFromResponse(response);
    }

    @Test
    @DisplayName("Получение заказа по трэк-номеру")
    public void positiveOrderGettingTest() {
        Response response = ordersAPIClient.getOrder(trackNumber);
        ordersAPIClient.checkPositiveOrderGetting(response, trackNumber);
    }

    @Test
    @DisplayName("Получение заказа без трэк-номера")
    public void noTrackNumberOrderGettingTest() {
        Response response = ordersAPIClient.getOrderWithoutId();
        ordersAPIClient.checkNegativeOrderGettingWithoutTrackNumber(response);
    }

    @Test
    @DisplayName("Получение заказа с несуществующим трэк-номерои")
    public void wrongTrackNumberOrderGettingTest() {
        int wrongTrackNumber = Integer.parseInt(RandomStringUtils.randomNumeric(9));
        Response response = ordersAPIClient.getOrder(wrongTrackNumber);
        ordersAPIClient.checkNegativeOrderGettingWithWrongTrackNumber(response);
    }
}