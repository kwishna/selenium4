package org.example.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    private static Properties configProp;
    private final String fileName;
    private Properties properties;

    public PropertyReader(String fileName) {
        this.fileName = fileName;
    }

    public static String getConfig(String key) {
        try {
            if (configProp == null) {
                configProp = new Properties();
                configProp.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return configProp.getProperty(key);
    }

    public String getValue(String key) {
        try {
            if (properties == null) {
                properties = new Properties();
                properties.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/" + fileName));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(key);
    }

}
