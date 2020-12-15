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

    public static final String BUTTON_OPEN_SESSION_PREFIX = "Open Session";

    public static final double SSH_EDITOR_LABEL_PREF_WIDTH = 200;
    public static final double SSH_EDITOR_TEXT_FIELD_PREF_WIDTH = 300;
    public static final String SSH_EDITOR_TITLE = "SSH Configuration";
    public static final String SSH_EDITOR_LABEL_NAME = "SSH Config Name:";
    public static final String SSH_EDITOR_LABEL_IP = "SSH Config IP:";
    public static final String SSH_EDITOR_LABEL_PORT = "SSH Config Port:";
    public static final String SSH_EDITOR_LABEL_USERNAME = "SSH Config Username:";
    public static final String SSH_EDITOR_LABEL_PASSWORD = "SSH Config Password:";
    public static final String SSH_EDITOR_LABEL_OPEN_ON_STARTUP = "Open on startup ";
    public static final String SSH_EDITOR_LABEL_LOGIN_ACTIONS = "SSH Config Login Actions:";
    public static final String SSH_EDITOR_LABEL_SAVE_DIRECTORY = "Config Path:";
    public static final String SSH_EDITOR_BUTTON_SAVE = "Save";
    public static final String SSH_EDITOR_BUTTON_SAVE_AS = "Save As";
    public static final String SSH_EDITOR_BUTTON_CANCEL = "Cancel";
}
