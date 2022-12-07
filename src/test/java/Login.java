import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojoClases.Register;
import pojoClases.SuccessRegister;
import pojoClases.UnSuccessRegister;

import java.lang.reflect.Method;

import static service.BaseService.Post;
import static service.Configuration.LOGIN;
import static service.Configuration.showDetails;

public class Login {


    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"chrome", "41", "Windows XP"},
        };
    }

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void successLogin() {
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "cityslicka");

        SuccessRegister login = Post(LOGIN, user).
                then().statusCode(200)
                .extract().as(SuccessRegister.class);
        softAssert.assertNotNull(login.getToken());
        softAssert.assertEquals(token, login.getToken());
        softAssert.assertAll();
        showDetails();
    }

    @Test
    public void unSuccessLogin() {
        String error = "Missing password";

        Register user = new Register("peter@klaven", "");
        UnSuccessRegister unSuccessReg = Post(LOGIN, user)
                .then().statusCode(400)
                .extract().as(UnSuccessRegister.class);
        softAssert.assertNotNull(unSuccessReg);
        softAssert.assertEquals(error, unSuccessReg.getError());
        showDetails();
    }
}
