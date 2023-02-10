package tests;

import client.OrdersAPIClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Получение списка заказов")
public class OrdersListGettingTests {
    private OrdersAPIClient ordersAPIClient;

    @Before
    public void setUp() {
        ordersAPIClient = new OrdersAPIClient();
    }

    @Test
    @DisplayName("Получение полного списка заказов")
    public void positiveFullOrdersListGettingTest() {
        Response response = ordersAPIClient.getFullOrdersList();
        ordersAPIClient.checkPositiveFullOrdersListGetting(response);
    }
}
