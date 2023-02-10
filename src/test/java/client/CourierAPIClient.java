package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.CourierModel;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierAPIClient extends BaseClient {

    @Step("Создание курьера со всеми полями")
    public Response createCourier(String login, String password, String firstName) {
        CourierModel courierModel = new CourierModel(login, password, firstName);
        return doPostRequest("/api/v1/courier", courierModel);
    }

    @Step("Создание курьера по модели")
    public Response createCourierByModel(CourierModel courierModel) {
        return doPostRequest("/api/v1/courier", courierModel);
    }

    @Step("Проверка успешности создания курьера")
    public void checkPositiveCourierCreating(Response response) {
        response.then().assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    @Step("Проверка неуспешности создания курьера с дублирующимся логином")
    public void checkNegativeCourierDuplicateCreating(Response response) {
        response.then().assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Step("Создание курьера без логина")
    public Response createNoLoginCourier (String password, String firstName) {
        CourierModel courierModel = new CourierModel(true, password, firstName);
        return doPostRequest("/api/v1/courier", courierModel);
    }

    @Step("Создание курьера без пароля")
    public Response createNoPasswordCourier (String login, String firstName) {
        CourierModel courierModel = new CourierModel(false, login, firstName);
        return doPostRequest("/api/v1/courier", courierModel);
    }

    @Step("Создание курьера без имени")
    public Response createNoFirstNameCourier (String login, String password) {
        CourierModel courierModel = new CourierModel(login, password);
        return doPostRequest("/api/v1/courier", courierModel);
    }

    @Step("Проверка неуспешности создания курьера без обязательного параметра")
    public void checkNegativeCourierWithoutParamCreating(Response response) {
        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Авторизация курьера")
    public Response authorizationCourier(String login, String password) {
        CourierModel courierModel = new CourierModel(login, password);
        return doPostRequest("/api/v1/courier/login", courierModel);
    }

    @Step("Проверка успешности авторизации курьера")
    public void checkPositiveCourierLogin(Response response) {
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Step("Авторизация курьера без логина")
    public Response authorizationNoLoginCourier(String password) {
        CourierModel courierModel = new CourierModel(true, password);
        return doPostRequest("/api/v1/courier/login", courierModel);
    }

    @Step("Авторизация курьера без пароля")
    public Response authorizationNoPasswordCourier(String login) {
        CourierModel courierModel = new CourierModel(false, login);
        return doPostRequest("/api/v1/courier/login", courierModel);
    }

    @Step("Проверка неуспешности авторизации без обязательных полей")
    public void checkNegativeCourierWithoutParamAuthorization(Response response) {
        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Проверка неуспешности авторизации c неверными полями")
    public void checkNegativeCourierWithWrongParamAuthorization(Response response) {
        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Получение ID курьера по логину и паролю")
    public int getCourierID(String login, String password) {
        CourierModel courierModel = new CourierModel(login, password);
        return doPostRequest("/api/v1/courier/login", courierModel)
                .then().extract().path("id");
    }

    @Step("Получение ID курьера по модели")
    public int getCourierIdByModel(CourierModel courierModel) {
        return doPostRequest("/api/v1/courier/login", courierModel)
                .then().extract().path("id");
    }

    @Step("Удаление курьера")
    public Response deleteCourierById(int id) {
        CourierModel courierModel = new CourierModel(id);
        return doDeleteRequest("/api/v1/courier/" + id, courierModel);
    }

    @Step("Проверка успешности удаления курьера")
    public void checkPositiveCourierDeleting(Response response) {
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("ok", equalTo(true));
    }

    @Step("Удаление курьера без ID")
    public Response deleteCourierWithoutId() {
        return doDeleteRequest("/api/v1/courier");
    }

    @Step("Проверка неуспешности удаления курьера без ID")
    public void checkNegativeCourierDeletingWithoutID (Response response) {
        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для удаления курьера"));
    }

    @Step("Проверка неуспешности удаления курьера с неверным ID")
    public void checkNegativeCourierDeletingWithWrongID (Response response) {
        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Курьера с таким id нет"));
    }
}

