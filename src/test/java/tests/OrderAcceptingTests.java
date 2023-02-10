package tests;

import client.CourierAPIClient;
import client.OrdersAPIClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierModel;
import model.OrderModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@DisplayName("Принятие заказа")
public class OrderAcceptingTests {
    private CourierAPIClient courierAPIClient;
    private OrdersAPIClient ordersAPIClient;

    private int courierId;
    private int orderID;


    @Before
    public void setUp() {
        courierAPIClient = new CourierAPIClient();
        ordersAPIClient = new OrdersAPIClient();

        CourierModel courierModel = CourierModel.getRandomCourierModel();
        courierAPIClient.createCourierByModel(courierModel);
        courierId = courierAPIClient.getCourierIdByModel(courierModel);

        OrderModel orderModel = OrderModel.getRandomOrderModel();
        Response response = ordersAPIClient.createOrderByModel(orderModel);
        int trackNumber = ordersAPIClient.getTrackNumberFromResponse(response);
        response = ordersAPIClient.getOrder(trackNumber);
        orderID = ordersAPIClient.getOrderIdFromResponse(response);
    }

    @Test
    @DisplayName("Принятие заказа")
    public void positiveOrderAcceptingTest() {
        Response response = ordersAPIClient.acceptOrder(orderID, courierId);
        ordersAPIClient.checkPositiveOrderAccepting(response);
    }

    @Test
    @DisplayName("Принятие заказа без ID курьера")
    public void noCourierIdOrderAcceptingTest() {
        Response response = ordersAPIClient.acceptOrderWithoutCourierId(orderID);
        ordersAPIClient.checkNegativeOrderAcceptingWithoutId(response);
    }

    @Test
    @DisplayName("Принятие заказа без ID заказа")
    public void noOrderIdOrderAcceptingTest() {
        Response response = ordersAPIClient.acceptOrderWithoutOrderId(courierId);
        ordersAPIClient.checkNegativeOrderAcceptingWithoutId(response);
    }

    @Test
    @DisplayName("Принятие заказа c несуществующим ID курьера")
    public void wrongCourierIdOrderAcceptingTest() {
        int wrongCourierId = Integer.parseInt(RandomStringUtils.randomNumeric(9));
        Response response = ordersAPIClient.acceptOrder(orderID, wrongCourierId);
        ordersAPIClient.checkNegativeOrderAcceptingWithWrongCourierId(response);
    }

    @Test
    @DisplayName("Принятие заказа c несуществующим ID заказа")
    public void wrongOrderIdOrderAcceptingTest() {
        int wrongOrderId = Integer.parseInt(RandomStringUtils.randomNumeric(9));
        Response response = ordersAPIClient.acceptOrder(wrongOrderId, courierId);
        ordersAPIClient.checkNegativeOrderAcceptingWithWrongOrderId(response);
    }


    @After
    public void tearDown() {
        courierAPIClient.deleteCourierById(courierId);
    }
}
