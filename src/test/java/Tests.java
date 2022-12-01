
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.testng.annotations.*;
import util.MyListener;
import util.UsersData;


import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static service.BaseService.*;
import static service.Configuration.*;

@Listeners(MyListener.class)
public class Tests {

    @Test
    public void aa() {
        List<UsersData> users = Get(USERS)
                .then().log().all()
                .extract().body().jsonPath().getList("data", UsersData.class);

        users.forEach(x->assertThat(x.getId(),isA(Integer.class)));




    }
}