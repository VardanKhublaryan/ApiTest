package service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static service.Configuration.*;

public class BaseService {


    public  Response Get(String path) {
        return RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .basePath(path)
                .get();
    }

    public  Response Post(String path, Object json) {
        return RestAssured.given().
                baseUri(BASE_URL)
                .basePath(path)
                .contentType(ContentType.JSON)
                .body(json)
                .post();
    }

    public  Response Put(String path, Object json) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .basePath(path)
                .body(json)
                .put();
    }

    public  Response Delete(String path) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(path)
                .contentType(ContentType.JSON)
                .delete();
    }

    public  void GetTime(String path) {
        System.out.print(Get(path).getTime() + " mls ");
    }

    public  void PostTime(String path,Object json) {
        System.out.print(Post(path, json).getTime() + " mls ");
    }

    public  void PutTime(String path,Object json) {
        System.out.print(Put(path, json).getTime() + " mls ");
    }
}
