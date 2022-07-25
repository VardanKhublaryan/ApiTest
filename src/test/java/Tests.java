

import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;


import java.util.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static service.BaseService.*;
import static service.Configuration.*;


public class Tests {

    @Test
    public void aa() {
        String json = gets(BASE_URL, USERS).asString();
        List<String> users = JsonPath.from(json).get("id");
        assertThat(users.size(), greaterThan(0));
    }

    @Test
    public void bb() {
        gets(BASE_URL, USERS)
                .then()
                .statusCode(200)
                .body("id", everyItem(allOf(isA(Integer.class),greaterThanOrEqualTo(0))),
                        "email", everyItem(allOf(containsString("@"))));//to do
    }

    @Test
    public void generic() {
        List<Map<String, Object>> users = gets(BASE_URL, USERS).as(new TypeRef<List<Map<String, Object>>>() {
        });
        assertThat(users.get(0).get("id"), equalTo(4102));
        assertThat(users.get(1).get("email"), equalTo("aarya_dutta@haag.io"));
        assertThat(users.get(2).get("gender"), equalTo("male"));
    }


    @Test
    public void post() {
        String jsonString = "{\"id\":\"41055156139\",\"name\":\"Vardan\",\"email\":\"vasrsv@gmail.com\"}";
        posts(BASE_URL,USERS,jsonString);
    }
}
