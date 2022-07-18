package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    public static final String BASE_URL = getPropValues("base_url");


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
            inputStream.close();
        }
        return props;
    }


    public static String getPropValues(String key) {
        if (System.getProperty(key).isEmpty() || System.getProperty(key) == null) {
            try {
                return getPropValues().getProperty(key);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return System.getProperty(key);
        }
    }
}


