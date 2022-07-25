
package service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseService {

    public static RequestSpecification requestSpecification = RestAssured.given();

    public static Response gets(String url, String path) {
        requestSpecification.baseUri(url)
                .basePath(path);
        return requestSpecification.get();
    }

    public static void posts(String url, String path, String json) {
        requestSpecification.baseUri(url)
                .basePath(path)
                .contentType(ContentType.JSON)
                .body(json);
        requestSpecification.post();
    }
//    todo wrap methods: get, post, put, delete
}
