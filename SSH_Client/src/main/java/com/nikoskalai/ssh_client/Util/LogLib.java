package com.nikoskalai.ssh_client.Util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogLib {

    public enum TAG {
        INFO("INFO"),
        ERROR("ERROR");

        private String str;

        TAG(String str) {
            this.str = str;
        }

        public String getTagName() {
            return str;
        }
    }

    private static final String dateFormat = "yyyy/MM/dd HH:mm:ss";
    public static final String LOG_FILE = "stdout.log";
    public static final String ERR_LOG_FILE = "stderr.log";

    private static void writeLog(String logFile, TAG tag, String text) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
            writer.append("[" + currentDate + "] - [" + tag.getTagName() + "] - " + text);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void writeInfoLog(String text) {
        writeLog(LOG_FILE, TAG.INFO, text);
    }

    public static void writeErrorLog(String text, Throwable t) {
        if (!UtilLib.isEmptySafe(text)) {
            writeLog(ERR_LOG_FILE, TAG.ERROR, text);
        }
        if (!UtilLib.isEmptySafe(t)) {
            writeLog(ERR_LOG_FILE, TAG.ERROR, getStackTraceStr(t));
            t.printStackTrace();
        }
    }

    public static void writeErrorLog(Throwable t) {
        if (!UtilLib.isEmptySafe(t)) {
            writeLog(ERR_LOG_FILE, TAG.ERROR, t.getMessage());
            writeLog(ERR_LOG_FILE, TAG.ERROR, getStackTraceStr(t));
            t.printStackTrace();
        }
    }

    private static String getStackTraceStr(Throwable t) {
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            t.printStackTrace(printWriter);
            String stackTrace = stringWriter.toString();
            printWriter.close();
            stringWriter.close();
            return stackTrace;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Could not convert stack trace.";
    }

}
