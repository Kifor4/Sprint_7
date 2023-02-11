package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.OrderModel;
import model.OrdersListModel;
import model.PageInfoModel;
import model.StationModel;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class OrdersAPIClient extends BaseClient {
    private final String ORDERS_URI = "/api/v1/orders";
    private final String TRACK_URI = "/api/v1/orders/track";
    private final String ACCEPT_URI = "/api/v1/orders/accept";

    @Step("Создание заказа")
    public Response createOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String... color) {
        OrderModel orderModel = new OrderModel(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        return doPostRequest(ORDERS_URI, orderModel);
    }

    @Step("Создание заказа по модели")
    public Response createOrderByModel(OrderModel orderModel) {
        return doPostRequest(ORDERS_URI, orderModel);
    }


    @Step("Проверка успешности создания заказа")
    public void checkPositiveOrderCreating(Response response) {
        response.then().assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @Step("Получение трэк-номера заказа из ответа сервера")
    public int getTrackNumberFromResponse(Response response) {
        OrderModel orderModel = response.body().as(OrderModel.class);
        return orderModel.getTrack();
    }

    @Step("Получение полного списка заказов")
    public Response getFullOrdersList() {
        return doGetRequest(ORDERS_URI);
    }

    @Step("Проверка, что список заказов не пустой")
    public void checkPositiveFullOrdersListGetting(Response response) {
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body(matchesJsonSchemaInClasspath("ordersListSchema.json"));
        OrdersListModel ordersListModel = response.body().as(OrdersListModel.class);
        OrderModel[] orders = ordersListModel.getOrders();
        PageInfoModel pageInfoModel = ordersListModel.getPageInfo();
        StationModel[] stationModels = ordersListModel.getAvailableStations();
        Assert.assertTrue("Список заказов пуст", orders.length > 0);
        Assert.assertNotNull("Отсутствует pageInfo", pageInfoModel);
        Assert.assertTrue("Список станций пуст", stationModels.length > 0);
    }

    @Step("Получение заказа по его номеру")
    public Response getOrder(int trackNumber) {
        Map<String, Integer> params = new HashMap<>();
        params.put("t", trackNumber);
        return doGetRequest(TRACK_URI, params);
    }

    @Step("Проверка успешности получения заказа")
    public void checkPositiveOrderGetting(Response response, int trackNumber) {
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("order.track", equalTo(trackNumber));
    }

    @Step("Получение ID заказа из ответа сервера")
    public int getOrderIdFromResponse(Response response) {
        return response.then().extract().path("order.id");
    }

    @Step("Получение заказа без трэк-номера")
    public Response getOrderWithoutId() {
        return doGetRequest(TRACK_URI);
    }

    @Step("Проверка неуспешности получения заказа без трэк-номера")
    public void checkNegativeOrderGettingWithoutTrackNumber(Response response) {
        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Step("Проверка неуспешности получения заказа с несуществующим трэк-номерои")
    public void checkNegativeOrderGettingWithWrongTrackNumber(Response response) {
        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Заказ не найден"));
    }

    @Step("Принятие заказа")
    public Response acceptOrder(int id, int courierId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("courierId", courierId);
        return doPutRequest(ACCEPT_URI + "/" + id, params);
    }


    @Step("Проверка успешности принятия заказа")
    public void checkPositiveOrderAccepting(Response response) {
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("ok", equalTo(true));
    }

    @Step("Принятие заказа без ID курьера")
    public Response acceptOrderWithoutCourierId(int id) {
        return doPutRequest(ACCEPT_URI + "/" + id);
    }

    @Step("Принятие заказа без ID заказа")
    public Response acceptOrderWithoutOrderId(int courierId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("courierId", courierId);
        return doPutRequest(ACCEPT_URI, params);
    }


    @Step("Проверка неуспешности принятия заказа без id")
    public void checkNegativeOrderAcceptingWithoutId(Response response) {
        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Step("Проверка неуспешности принятия заказа c несуществующим id курьера")
    public void checkNegativeOrderAcceptingWithWrongCourierId(Response response) {
        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Курьера с таким id не существует"));
    }

    @Step("Проверка неуспешности принятия заказа c несуществующим id заказа")
    public void checkNegativeOrderAcceptingWithWrongOrderId(Response response) {
        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Заказа с таким id не существует"));
    }
}
