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
    private static final String VERSION_PROPERTIES = "properties/version.properties";

    public static String getProperty(String property) {
        if (UtilLib.isEmptySafe(property)) {
            return "";
        }
        return getPropertiesInstance().getProperty(property);
    }

    private static Properties getPropertiesInstance() {
        if (properties == null) {
            readProperties();
        }
        return properties;
    }

    private static void readProperties() {
        try {
            if (properties == null) {
                properties = new Properties();
            } else {
                properties.clear();
            }
            // load a properties file
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(STRING_PROPERTIES);
            properties.load(input);
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream(VERSION_PROPERTIES);
            properties.load(input);
            input.close();
            System.out.println(properties);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogLib.writeErrorLog(ex);
        }
    }
}
