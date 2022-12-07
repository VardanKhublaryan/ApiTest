import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojoClases.UsersData;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.isA;
import static service.BaseService.Get;
import static service.Configuration.USERS;

public class DataFields {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void email() {
        List<UsersData> users = Get(USERS)
                .then().statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("data", UsersData.class);
        users.forEach(x -> softAssert.assertNotNull(x.getEmail()));
        softAssert.assertAll();
        users.forEach(x -> assertThat(x.getEmail(), allOf(isA(String.class), containsString("@reqres.in"))));

    }

    @Test
    public void checkId() {
        List<Integer> ids = Get(USERS)
                .then().statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("data.id", Integer.class);
        for (int i = 1; i < ids.size(); i++) {
            softAssert.assertEquals(ids.get(i - 1) + 1, (int) ids.get(i));
            softAssert.assertNotNull(ids.get(i));
            assertThat(ids.get(i),allOf(isA(Integer.class), greaterThanOrEqualTo(0)));
        }
        softAssert.assertAll();
    }

    @Test
    public void checkAvatar() {
        List<UsersData> users = Get(USERS)
                .then().statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("data", UsersData.class);
        users.forEach(x-> softAssert.assertNotNull(x.getAvatar()));
        users.forEach(x -> softAssert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        users.forEach(x -> assertThat((x.getAvatar()),allOf(endsWith("image.jpg"), startsWith("https://reqres.in/img/faces/"))));
        softAssert.assertAll();
    }

    @Test
    public void firstNameAndLastName() {
        List<UsersData> users = Get(USERS)
                .then().statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList("data", UsersData.class);

        String regEx = "[A-Z]\\w*";
        users.forEach(x -> assertThat(x.getFirst_name(), isA(String.class)));
        users.forEach(x -> assertThat(x.getLast_name(), isA(String.class)));
        users.forEach(x -> softAssert.assertTrue(x.getFirst_name().matches(regEx)));
        users.forEach(x -> softAssert.assertTrue(x.getLast_name().matches(regEx)));
        softAssert.assertAll();
    }
}
