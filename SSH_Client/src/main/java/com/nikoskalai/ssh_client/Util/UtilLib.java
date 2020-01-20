package com.nikoskalai.ssh_client.Util;

public class UtilLib {
    public static boolean isEmptySafe(Object obj) {
        if (obj instanceof String) {
            return isEmptySafe((String) obj);
        } else {
            return obj == null;
        }
    }

    private static boolean isEmptySafe(String t) {
        if (t != null && !t.trim().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}