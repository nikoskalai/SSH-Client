package ssh.client.Util;

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
            input.close();
            loadMavenProperties();
//            System.out.println(properties);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogLib.writeErrorLog(ex);
        }
    }

    private static void loadMavenProperties() {
        properties.put("version", PropertyLoader.class.getPackage().getImplementationVersion());
    }
}
