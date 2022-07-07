
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.List;

import static io.restassured.RestAssured.*;


public class Tests {
    public final String URL = "https://reqres.in/";

    @Test
    public void aa() {
        List<Users> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", Users.class);
        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
    }
}
