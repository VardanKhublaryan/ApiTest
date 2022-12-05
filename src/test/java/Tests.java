
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pojoClases.*;
import util.MyListener;


import java.time.Clock;
import java.util.*;


import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static service.BaseService.*;
import static service.Configuration.*;

@Listeners(MyListener.class)
public class Tests {

    @Test
    public void idAndEmail() {
        List<UsersData> users = Get(USERS)
                .then().statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("data", UsersData.class);

        SoftAssert softAssert = new SoftAssert();
        users.forEach(x -> softAssert.assertNotNull(x.getFirst_name()));
        users.forEach(x -> softAssert.assertNotNull(x.getLast_name()));
        users.forEach(x -> softAssert.assertNotNull(x.getId()));
        users.forEach(x -> softAssert.assertNotNull(x.getAvatar()));
        softAssert.assertAll();

        users.forEach(x -> assertThat(x.getId(), allOf(isA(Integer.class), greaterThanOrEqualTo(0))));
        users.forEach(x -> assertThat(x.getEmail(), allOf(isA(String.class), containsString("@reqres.in"))));

    }

    @Test
    public void checkId() {
        List<Integer> ids = Get(USERS)
                .then().log().all()
                .extract().body().jsonPath().getList("data.id", Integer.class);
        for (int i = 1; i < ids.size(); i++) {
            Assert.assertEquals(ids.get(i - 1) + 1, (int) ids.get(i));
        }
    }

    @Test
    public void checkAvatar() {
        List<UsersData> users = Get(USERS)
                .then().log().all()
                .extract().body().jsonPath().getList("data", UsersData.class);
        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        users.forEach(x -> Assert.assertTrue(x.getAvatar().endsWith("image.jpg")));

    }

    @Test
    public void validRegister() {
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");

        SuccessRegister successRegister = Post(SUCCESS_REGISTER, user)
                .then().statusCode(200)
                .log().all()
                .extract().as(SuccessRegister.class);
        Assert.assertNotNull(successRegister.getId());
        Assert.assertNotNull(successRegister.getToken());

        Assert.assertEquals(id, successRegister.getId());
        Assert.assertEquals(token, successRegister.getToken());
    }

    @Test
    public void unSuccessRegister() {
        String error = "Missing password";

        Register user = new Register("sydney@fife", "");
        UnSuccessRegister unSuccessRegister = Post(SUCCESS_REGISTER, user)
                .then().statusCode(400)
                .log().all()
                .extract().as(UnSuccessRegister.class);

        Assert.assertNotNull(unSuccessRegister.getError());
        Assert.assertEquals(error, unSuccessRegister.getError());
    }

    @Test
    public void create() {
        String name = "morpheus";
        String job = "leader";
        Create create = new Create(name, job);

        CreateResponse createResponse = Post(CREATE, create)
                .then().statusCode(201)
                .log().all()
                .extract().as(CreateResponse.class);

        Assert.assertNotNull(createResponse);
        Assert.assertEquals(name, createResponse.getName());
        Assert.assertEquals(job, createResponse.getJob());
        assertThat(createResponse.getId(), isA(Integer.class));

        String regex = "(.{7})$";
        String regex1 = "(.{13})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex1, "");
        Assert.assertEquals(currentTime, createResponse.getCreatedAt().replaceAll(regex, ""));
    }

    @Test
    public void deleteUser() {
        Delete("/api/users/2")
                .then().statusCode(204);
    }

    @Test
    public void successLogin() {
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "cityslicka");

        SuccessRegister login = Post(LOGIN, user).
                then().statusCode(200)
                .log().all()
                .extract().as(SuccessRegister.class);
        Assert.assertNotNull(login.getToken());
        Assert.assertEquals(token, login.getToken());
    }

    @Test
    public void unSuccessLogin() {
        String error = "Missing password";

        Register user = new Register("peter@klaven", "");
        UnSuccessRegister unSuccessReg = Post(LOGIN, user)
                .then().statusCode(400)
                .log().all()
                .extract().as(UnSuccessRegister.class);
        Assert.assertEquals(error, unSuccessReg.getError());
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
}