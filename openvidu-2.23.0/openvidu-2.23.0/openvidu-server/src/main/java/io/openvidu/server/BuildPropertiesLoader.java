package io.openvidu.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BuildPropertiesLoader {
    public static Properties loadBuildProperties() {
        Properties properties = new Properties();
        try (InputStream input = BuildPropertiesLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find build-info.properties");
                return properties;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }
}
