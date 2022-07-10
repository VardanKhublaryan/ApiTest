
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.*;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
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
                .body("[0].id", equalTo(4102))
                .body("[0].email", equalTo("mrs_shakti_sharma@wintheiser-kerluke.org"));
    }

    @Test
    public void generic(){
        List<Map<String,Object>> users = get("https://gorest.co.in/public/v2/users").as(new TypeRef<List<Map<String, Object>>>() {});
        assertThat(users.get(0).get("id"),equalTo(4102));
        assertThat(users.get(1).get("email"),equalTo("aarya_dutta@haag.io"));
        assertThat(users.get(2).get("gender"),equalTo("male"));
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
