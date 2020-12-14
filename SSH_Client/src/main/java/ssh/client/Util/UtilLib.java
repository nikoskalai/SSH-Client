package ssh.client.Util;

import java.io.File;
import java.util.ArrayList;

public class UtilLib {
    public static boolean isEmptySafe(Object obj) {
        if (obj instanceof String) {
            return isEmptySafe((String) obj);
        } else {
            return obj == null;
        }
    }

    private static boolean isEmptySafe(String t) {
        return t == null || t.trim().isEmpty();
    }

    public static String getCommandString(String command) {
        return command + Constants.LINE_SEPARATOR;
    }

    public static void sleepSafe(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            LogLib.writeErrorLog(e);
        }
    }
}
