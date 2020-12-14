package ssh.client.Util;

import java.io.File;

public class Constants {
    public static final String WINDOW_TITLE = "window.title";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = File.separator;

    public static final String CONFIG_DIR = System.getProperty("user.dir") + Constants.FILE_SEPARATOR + "configs";
    public static final String SSH_CLIENT_CONFIGURATION_FILE_SUFFIX = ".sshc";

    public static final int DEFAULT_PASSWORD_DELAY = 500;
    public static final int DEFAULT_LOGIN_ACTIONS_DELAY = 1000;

    public static final double SPLIT_PANE_DIVIDER_POSITION_INITIAL = 0.30;
    public static final double SPLIT_PANE_DIVIDER_POSITION_MAX = 0.50;
}
