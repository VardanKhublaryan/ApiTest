import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojoClases.Register;
import pojoClases.SuccessRegister;
import pojoClases.UnSuccessRegister;
import service.BaseService;

import static service.Configuration.SUCCESS_REGISTER;
import static service.Configuration.showDetails;

public class UserRegister {

    //    @DataProvider(name = "hardCodedBrowsers", parallel = true)
//    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
//        return new Object[][]{
//                new Object[]{"chrome", "41", "Windows XP"},
//        };
//    }
    BaseService baseService = new BaseService();
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void validRegister() {
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");

        SuccessRegister successRegister = baseService.Post(SUCCESS_REGISTER, user)
                .then().statusCode(200)
                .extract().as(SuccessRegister.class);
        softAssert.assertNotNull(successRegister.getId());
        softAssert.assertNotNull(successRegister.getToken());
        softAssert.assertEquals(id, successRegister.getId());
        softAssert.assertEquals(token, successRegister.getToken());
        softAssert.assertAll();
        baseService.PostTime(SUCCESS_REGISTER, user);
        showDetails();
    }

    @Test
    public void unSuccessRegister() {
        String error = "Missing password";

        Register user = new Register("sydney@fife", "");
        UnSuccessRegister unSuccessRegister = baseService.Post(SUCCESS_REGISTER, user)
                .then().statusCode(400)
                .extract().as(UnSuccessRegister.class);

        softAssert.assertNotNull(unSuccessRegister.getError());
        softAssert.assertEquals(error, unSuccessRegister.getError());
        softAssert.assertAll();
        baseService.PostTime(SUCCESS_REGISTER, user);
        showDetails();
    }
}
