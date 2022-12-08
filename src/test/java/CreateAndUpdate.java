import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojoClases.Create;
import pojoClases.CreateResponse;
import pojoClases.TimeResponse;
import service.BaseService;
import service.Configuration;

import java.lang.reflect.Method;
import java.time.Clock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static service.BaseService.*;
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
}
