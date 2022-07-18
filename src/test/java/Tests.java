
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.*;
import org.testng.annotations.Test;
import service.BaseService;
import util.URI;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static service.BaseService.gets;
import static service.BaseService.posts;


public class Tests {
    public final String URL = "https://reqres.in/";

    @Test
    public void aa() {
        String json = gets(URI.BASE, URI.USERS).asString();
        List<String> users = JsonPath.from(json).get("id");
        assertThat(users.size(), greaterThan(0));
    }

    @Test
    public void bb() {
        gets(URI.BASE, URI.USERS)
                .then()
                .statusCode(200)
                .body("id", everyItem(allOf(isA(Integer.class),greaterThanOrEqualTo(0))),
                        "email", everyItem(allOf(containsString("@"))));//to do
    }

    @Test
    public void generic() {
        List<Map<String, Object>> users = gets(URI.BASE, URI.USERS).as(new TypeRef<List<Map<String, Object>>>() {
        });
        assertThat(users.get(0).get("id"), equalTo(4102));
        assertThat(users.get(1).get("email"), equalTo("aarya_dutta@haag.io"));
        assertThat(users.get(2).get("gender"), equalTo("male"));
    }


    @Test
    public void post() {
        String jsonString = "{\"id\":\"41055156139\",\"name\":\"Vardan\",\"email\":\"vasrsv@gmail.com\"}";
        posts(URI.BASE,URI.USERS,jsonString);
    }
}
