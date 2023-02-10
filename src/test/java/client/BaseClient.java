package client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Model;

import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class BaseClient {
    private final String BASE_URL = "http://qa-scooter.praktikum-services.ru";

    private RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }

    protected final Response doGetRequest(String uri) {
        return given()
                .spec(baseSpec())
                .get(uri);
    }

    protected final Response doGetRequest(String uri, Map queryParams) {
        return given()
                .spec(baseSpec())
                .queryParams(queryParams)
                .get(uri);
    }


    protected final Response doPostRequest(String uri, Model body) {
        return given()
                .spec(baseSpec())
                .body(body)
                .post(uri);
    }

    protected final Response doPostRequest(String uri, Model body, Map queryParams) {
        return given()
                .spec(baseSpec())
                .queryParams(queryParams)
                .body(body)
                .post(uri);
    }

    protected final Response doPostRequest(String uri) {
        return given()
                .spec(baseSpec())
                .post(uri);
    }

    protected final Response doPostRequest(String uri, Map queryParams) {
        return given()
                .spec(baseSpec())
                .queryParams(queryParams)
                .post(uri);
    }

    protected final Response doPutRequest(String uri, Model body) {
        return given()
                .spec(baseSpec())
                .body(body)
                .put(uri);
    }

    protected final Response doPutRequest(String uri, Model body, Map queryParams) {
        return given()
                .spec(baseSpec())
                .queryParams(queryParams)
                .body(body)
                .put(uri);
    }

    protected final Response doPutRequest(String uri) {
        return given()
                .spec(baseSpec())
                .put(uri);
    }

    protected final Response doPutRequest(String uri, Map queryParams) {
        return given()
                .spec(baseSpec())
                .queryParams(queryParams)
                .put(uri);
    }

    protected final Response doDeleteRequest(String uri, Model body) {
        return given()
                .spec(baseSpec())
                .body(body)
                .delete(uri);
    }

    protected final Response doDeleteRequest(String uri, Model body, Map queryParams) {
        return given()
                .spec(baseSpec())
                .queryParams(queryParams)
                .body(body)
                .delete(uri);
    }

    protected final Response doDeleteRequest(String uri) {
        return given()
                .spec(baseSpec())
                .delete(uri);
    }

    protected final Response doDeleteRequest(String uri, Map queryParams) {
        return given()
                .spec(baseSpec())
                .queryParams(queryParams)
                .delete(uri);
    }


}
