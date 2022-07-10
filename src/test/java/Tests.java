
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.response.ValidatableResponse;


public class Tests {
    public final String URL = "https://reqres.in/";

    @Test
    public void aa() {
        String json = get("https://gorest.co.in/public/v2/users").asString();
        List<String> users = JsonPath.from(json).get("data.id");
    }

    @Test
    public void bb() {
        get("https://gorest.co.in/public/v2/users")
                .then()
                .statusCode(200)
                .body("[0].id", equalTo(4108))
                .body("[0].email", equalTo("mrs_shakti_sharma@wintheiser-kerluke.org"));
    }

    @Test
    public void post() {
        String jsonString = "{\"id\":\"41055156139\",\"name\":\"Vardan\",\"email\":\"vasrsv@gmail.com\"}";

        RestAssured.baseURI = "https://gorest.co.in/public/v2/users";


        requestSpecification = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(jsonString);

        Response response = requestSpecification.post();
        String responseString = response.prettyPrint();

//        ValidatableResponse validatableResponse = response
//                .then()
//                .statusCode(200)
//                .statusLine("HTTP/1.1 200 OK");

    }
}
