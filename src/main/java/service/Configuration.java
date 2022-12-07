package service;

import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    public static final String BASE_URL = getPropValues("base_url");
    public static final String USERS = getPropValues("users");
    public static final String SUCCESS_REGISTER =getPropValues("success_register");
    public static final String CREATE = getPropValues("create");
    public static final String LOGIN = getPropValues("login");
    public static final String UPDATE_USER = getPropValues("update_user");


    private Configuration() {
    }


    private static Properties getPropValues() throws IOException {
        InputStream inputStream = null;
        Properties props = new Properties();

        try {

            String propFileName = "config.properties";
            inputStream = Configuration.class.getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                props.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            assert inputStream != null;
            inputStream.close();
        }
        return props;
    }


    public static String getPropValues(String key) {
        if (System.getProperty(key) == null || System.getProperty(key).isEmpty()) {
            try {
                return getPropValues().getProperty(key);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return System.getProperty(key);
    }

    public static void showDetails() {
        ITestResult result = Reporter.getCurrentTestResult();
        String s = result.getTestClass().getName() + "." + result.getMethod().getMethodName() + "()  on Thread #"
                + Thread.currentThread().getId();
        Reporter.log(s + " ran.", true);
    }
}


