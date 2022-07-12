
package service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseService {

    public static RequestSpecification requestSpecification = RestAssured.given();

    public static Response gets(String url, String path) {
        requestSpecification.baseUri(url)
                .basePath(path);
        return requestSpecification.get();
    }
//    todo wrap methods: get, post, put, delete
}
