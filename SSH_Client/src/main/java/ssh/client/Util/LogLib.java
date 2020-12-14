package ssh.client.Util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogLib {

    /**
     * Flag for printing & logging DEBUG logs.
     */
    private static boolean enableDebugLogging = true;
    /**
     * Flag for separate error log file.
     * If true, errors will be written in stderr.log
     */
    private static boolean separateErrorLogging = false;
    /**
     * Flag for printing logs.
     * If true, logs will also be shown in CLI.
     */
    private static boolean printLogsInCLI = true;

    public enum TAG {
        INFO("INFO"),
        DEBUG("DEBUG"),
        WARNING("WARNING"),
        ERROR("ERROR");
        private final String str;

        TAG(String str) {
            this.str = str;
        }

        public String getTagName() {
            return str;
        }
    }

    private static final String dateFormat = "yyyy/MM/dd HH:mm:ss.sss";
    public static final String LOG_FILE = "stdout.log";
    public static final String ERR_LOG_FILE = "stderr.log";

    private static void writeLog(String logFile, TAG tag, String text) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.append("[" + currentDate + "] - [" + tag.getTagName() + "] - " + text);
            if (printLogsInCLI) {
                System.out.println("[" + currentDate + "] - [" + tag.getTagName() + "] - " + text);
            }
            writer.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void writeDebugLog(String text) {
        if (enableDebugLogging) {
            writeLog(LOG_FILE, TAG.DEBUG, text);
        }
    }

    public static void writeInfoLog(String text) {
        writeLog(LOG_FILE, TAG.INFO, text);
    }

    public static void writeWarningLog(String text) {
        String logFile = LOG_FILE;
        if (separateErrorLogging) {
            logFile = ERR_LOG_FILE;
        }
        if (!UtilLib.isEmptySafe(text)) {
            writeLog(logFile, TAG.WARNING, text);
        }
    }

    public static void writeErrorLog(String text, Throwable t) {
        String logFile = LOG_FILE;
        if (separateErrorLogging) {
            logFile = ERR_LOG_FILE;
        }
        if (!UtilLib.isEmptySafe(text)) {
            writeLog(logFile, TAG.ERROR, text);
        }
        if (!UtilLib.isEmptySafe(t)) {
            writeLog(logFile, TAG.ERROR, getStackTraceStr(t));
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
