
import org.hamcrest.Matcher;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Tests {

    @Test
    public void aa() {
        given().
                when()
                .get("https://gorest.co.in/public-api/users\n")
                .then()
                .statusCode(200)
                .assertThat()
                .body("data[0].id", equalTo(3301))
                .body("data[0].name",equalTo("Advaya Somayaji"));

    }
}
