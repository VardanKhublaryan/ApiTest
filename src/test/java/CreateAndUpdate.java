import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojoClases.Create;
import pojoClases.CreateResponse;
import pojoClases.TimeResponse;

import java.time.Clock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;
import static service.BaseService.*;
import static service.Configuration.CREATE;
import static service.Configuration.UPDATE_USER;

public class CreateAndUpdate {
    SoftAssert softAssert = new SoftAssert();
    @Test
    public void create() {
        String name = "morpheus";
        String job = "leader";
        Create create = new Create(name, job);

        CreateResponse createResponse = Post(CREATE, create)
                .then().statusCode(201)
                .log().all()
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
    }

    @Test
    public void updateUser() {
        String name = "morpheus";
        String job = "zion resident";
        Create create = new TimeResponse(name, job);

        TimeResponse updateUser = Put(UPDATE_USER, create)
                .then()
                .log().all()
                .extract().as(TimeResponse.class);

        String regex = "(.{7})$";
        String regex1 = "(.{13})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex1, "");
        Assert.assertEquals(currentTime, updateUser.getUpdatedAt().replaceAll(regex, ""));
    }

    @Test
    public void deleteUser() {
        Delete("/api/users/2")
                .then().statusCode(204);
    }
}
