import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojoClases.Create;
import pojoClases.CreateResponse;
import pojoClases.TimeResponse;
import service.BaseService;

import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.isA;
import static service.Configuration.*;

public class CreateAndUpdate {

    BaseService baseService = new BaseService();
    SoftAssert softAssert = new SoftAssert();

//    @DataProvider(name = "hardCodedBrowsers", parallel = true)
//    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
//        return new Object[][]{
//                new Object[]{"chrome", "41", "Windows XP"},
//        };
//    }


    @Test
    public void create() {
        String name = "morpheus";
        String job = "leader";
        Create create = new Create(name, job);
        CreateResponse createResponse = baseService.Post(CREATE, create)
                .then().statusCode(201)
                .extract().as(CreateResponse.class);

        softAssert.assertNotNull(createResponse);
        softAssert.assertEquals(name, createResponse.getName());
        softAssert.assertEquals(job, createResponse.getJob());
        assertThat(createResponse.getId(), isA(Integer.class));

        String regex = "(.{7})$";
        String regex1 = "(.{13})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex1, "");
        softAssert.assertEquals(currentTime, createResponse.getCreatedAt().replaceAll(regex, ""));
        softAssert.assertAll();
        baseService.PostTime(CREATE, create);
        showDetails();
    }

    @Test
    public void updateUser() {
        String name = "morpheus";
        String job = "zion resident";
        Create create = new TimeResponse(name, job);

        TimeResponse updateUser = baseService.Put(UPDATE_USER, create)
                .then()
                .extract().as(TimeResponse.class);

        String regex = "(.{7})$";
        String regex1 = "(.{13})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex1, "");
        Assert.assertEquals(currentTime, updateUser.getUpdatedAt().replaceAll(regex, ""));
        baseService.PutTime(CREATE, create);
        showDetails();
    }

    @Test
    public void deleteUser() {
        baseService.Delete("/api/users/2")
                .then().statusCode(204);
        showDetails();
    }

    @Test
    public void aaa() {
        RequestSpecification requestSpecification = given()
                .baseUri("http://habesha.energaming.systems/api/v2/multi")
                .contentType(ContentType.JSON);
        String d = """
                        [
                            {
                                "module": "auth",
                                "method": "sign_in",
                                "options": {
                                    "login": "275390",
                                    "password": "habesha789",
                                    "client": "desktop"
                                }
                            }
                        ]
                """;
        requestSpecification.body(d);
        requestSpecification.post("http://habesha.energaming.systems/api/v2/multi")
                .then().log().all()
                .statusCode(200);

    }
}
