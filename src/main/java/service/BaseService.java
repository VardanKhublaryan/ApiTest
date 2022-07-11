
package service;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseService {
    RequestSpecification requestSpecification = RestAssured.given();

//    todo wrap methods: get, post, put, delete
}
