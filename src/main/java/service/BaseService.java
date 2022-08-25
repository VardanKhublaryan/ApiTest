package service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static service.Configuration.*;

public class BaseService {


    public static Response Get(String path) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(path).
                get();
    }

    public static Response Post(String path, String json) {
        return RestAssured.given().
                baseUri(BASE_URL)
                .basePath(path)
                .contentType(ContentType.JSON)
                .body(json)
                .post();
    }

    public static Response Put(String path, String json) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(path)
                .contentType(ContentType.JSON)
                .body(json)
                .put();
    }

    public static Response Delete(String path, String json) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(path)
                .contentType(ContentType.JSON)
                .body(json)
                .delete();
    }
}
