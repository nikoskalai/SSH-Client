package com.nikoskalai.ssh_client.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    static {
        readProperties();
    }

    private static Properties properties = null;

    private static final String STRING_PROPERTIES = "properties/string.properties";

    public static String getProperty(String property) {
        if (UtilLib.isEmptySafe(property)) {
            return "";
        }
        System.out.println(getPropertiesInstance().getProperty(property));
        return properties.getProperty(property);
    }

    private static Properties getPropertiesInstance() {
        if (properties == null) {
            readProperties();
        }
        return properties;
    }

    private static void readProperties() {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(STRING_PROPERTIES)) {

            properties = new Properties();

            // load a properties file
            properties.load(input);
            System.out.println(properties);
        } catch (IOException ex) {
            ex.printStackTrace();
            LogLib.writeErrorLog(ex);
        }
    }
}
