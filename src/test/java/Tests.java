
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


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
                .body("[1].id", equalTo(4107))
                .body("[1].email", equalTo("dhawan_suresh_dr@osinski-hegmann.io"));
    }
}
