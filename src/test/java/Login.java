import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojoClases.Register;
import pojoClases.SuccessRegister;
import pojoClases.UnSuccessRegister;

import static service.BaseService.Post;
import static service.Configuration.LOGIN;

public class Login {
    SoftAssert softAssert = new SoftAssert();
    @Test
    public void successLogin() {
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "cityslicka");

        SuccessRegister login = Post(LOGIN, user).
                then().statusCode(200)
                .log().all()
                .extract().as(SuccessRegister.class);
        softAssert.assertNotNull(login.getToken());
        softAssert.assertEquals(token, login.getToken());
        softAssert.assertAll();
    }

    @Test
    public void unSuccessLogin() {
        String error = "Missing password";

        Register user = new Register("peter@klaven", "");
        UnSuccessRegister unSuccessReg = Post(LOGIN, user)
                .then().statusCode(400)
                .log().all()
                .extract().as(UnSuccessRegister.class);
        softAssert.assertNotNull(unSuccessReg);
        softAssert.assertEquals(error, unSuccessReg.getError());
    }
}
