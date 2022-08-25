
import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.*;
import util.MyListener;


import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static service.BaseService.*;
import static service.Configuration.*;
import static service.UserService.*;

@Listeners(MyListener.class)
public class Tests {

    @Test
    public void aa() {
        String json = getUsers();
        List<String> users = JsonPath.from(json).get("id");
        assertThat(users.size(), greaterThan(0));
    }

    @Test
    public void bb() {
        Get(USERS)
                .then()
                .statusCode(200)
                .body("id", everyItem(allOf(isA(Integer.class), greaterThanOrEqualTo(0))),
                        "email", everyItem(allOf(containsString("@"))));
    }

    @Test
    public void generic() {
        List<Map<String, Object>> users = Get(USERS).as(new TypeRef<>() {
        });
        assertThat(users.get(0).get("id"), equalTo(4102));
        assertThat(users.get(1).get("email"), equalTo("aarya_dutta@haag.io"));
        assertThat(users.get(2).get("gender"), equalTo("male"));
    }


    @Test
    public void post() {
        String jsonString = "{\"id\":\"41055156139\",\"name\":\"Vardan\",\"email\":\"vasrsv@gmail.com\"}";
        Post(USERS, jsonString).then().statusCode(401);
    }
}
