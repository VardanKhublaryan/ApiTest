import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojoClases.Register;
import pojoClases.SuccessRegister;
import pojoClases.UnSuccessRegister;

import static service.BaseService.Post;
import static service.Configuration.SUCCESS_REGISTER;

public class UserRegister {
    SoftAssert softAssert = new SoftAssert();
    @Test
    public void validRegister() {
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");

        SuccessRegister successRegister = Post(SUCCESS_REGISTER, user)
                .then().statusCode(200)
                .log().all()
                .extract().as(SuccessRegister.class);
        softAssert.assertNotNull(successRegister.getId());
        softAssert.assertNotNull(successRegister.getToken());
        softAssert.assertEquals(id, successRegister.getId());
        softAssert.assertEquals(token, successRegister.getToken());
        softAssert.assertAll();
    }

    @Test
    public void unSuccessRegister() {
        String error = "Missing password";

        Register user = new Register("sydney@fife", "");
        UnSuccessRegister unSuccessRegister = Post(SUCCESS_REGISTER, user)
                .then().statusCode(400)
                .log().all()
                .extract().as(UnSuccessRegister.class);

        softAssert.assertNotNull(unSuccessRegister.getError());
        softAssert.assertEquals(error, unSuccessRegister.getError());
        softAssert.assertAll();
    }
}
