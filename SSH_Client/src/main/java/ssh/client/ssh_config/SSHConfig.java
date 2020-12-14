package ssh.client.ssh_config;

import com.kodedu.terminalfx.config.TerminalConfig;
import ssh.client.Util.Constants;
import ssh.client.Util.LogLib;
import ssh.client.Util.UtilLib;
import ssh.client.config.Themes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

public class SSHConfig {

    private static final String LOGIN_ACTIONS_SEPARATOR = "---";
    private String name;
    private String ipAddress;
    private String username;
    private String password;
    private String port = "22";
    private ArrayList<String> loginActions;
    private boolean openOnStartup;
    private int passwordDelay = 500;
    private int loginActionsDelay = 500;
    //TODO local terminal path
    private TerminalConfig theme = Themes.getDefaultTheme();
    private boolean isFolder = false;
    private boolean isRootFolder = false;
    private File file;

    public SSHConfig() {
        super();
    }

    public static SSHConfig setupFolder(String path) {
        SSHConfig sshConfig = new SSHConfig(path);
        SSHConfigStore.addSSHConfigInList(sshConfig);
        return sshConfig;
    }

    public static SSHConfig setupRootFolder(String path) {
        SSHConfig sshConfig = new SSHConfig(path);
        sshConfig.setIsRootFolder(true);
        SSHConfigStore.addSSHConfigInList(sshConfig);
        return sshConfig;
    }

    public SSHConfig(String path) {
        setFile(new File(path));
        SSHConfigStore.addSSHConfigInList(this);
    }

    public SSHConfig(File f) {
        if (UtilLib.isEmptySafe(f)) {
            return;
        }
        setFile(f);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("name=") && !line.startsWith("username=")) {
                    setName(line.replaceFirst("name=", ""));
                } else if (line.startsWith("ip=")) {
                    setIpAddress(line.replaceFirst("ip=", ""));
                } else if (line.startsWith("username=")) {
                    setUsername(line.replaceFirst("username=", ""));
                } else if (line.startsWith("password=")) {
                    setPassword(line.replaceFirst("password=", ""));
                } else if (line.startsWith("port=")) {
                    setPort(line.replaceFirst("port=", ""));
                } else if (line.startsWith("login_actions=")) {
                    setLoginActions(line.replaceFirst("login_actions=", ""));
                } else if (line.startsWith("theme=")) {
                    String theme = line.replaceFirst("theme=", "");
                    if (theme.equalsIgnoreCase("Dark")) {
                        setTheme(Themes.getDarkTheme());
                    } else if (theme.equalsIgnoreCase("Cygwin")) {
                        setTheme(Themes.getCygwinTheme());
                    } else {
                        setTheme(Themes.getDefaultTheme());
                    }
                } else if (line.startsWith("passwordDelay=")) {
                    setPasswordDelay(line.replaceFirst("passwordDelay=", ""));
                } else if (line.startsWith("loginActionsDelay=")) {
                    setLoginActionsDelay(line.replaceFirst("loginActionsDelay=", ""));
                } else if (line.startsWith("openOnStartup=")) {
                    setOpenOnStartup(line.replaceFirst("openOnStartup=", ""));
                }
            }
        } catch (Exception e) {
            LogLib.writeErrorLog(e);
        }
        SSHConfigStore.addSSHConfigInList(this);
    }

    public String getName() {
        if (UtilLib.isEmptySafe(name)) {
            return getUsername() + "@" + getIpAddress();
        } else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordMasked() {
        return "********";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getPasswordDelay() {
        return passwordDelay;
    }

    public void setPasswordDelay(String passwordDelay) {
        try {
            this.passwordDelay = Integer.valueOf(passwordDelay);
        } catch (Exception e) {
            this.passwordDelay = Constants.DEFAULT_PASSWORD_DELAY;
            LogLib.writeErrorLog("Could not set password delay from " + passwordDelay + ". Setting default " + Constants.DEFAULT_PASSWORD_DELAY + "ms.", e);
        }
    }

    public int getLoginActionsDelay() {
        return loginActionsDelay;
    }

    public void setLoginActionsDelay(String loginActionsDelay) {
        try {
            this.loginActionsDelay = Integer.valueOf(loginActionsDelay);
        } catch (Exception e) {
            this.loginActionsDelay = Constants.DEFAULT_LOGIN_ACTIONS_DELAY;
            LogLib.writeErrorLog("Could not set login actions delay from " + loginActionsDelay + ". Setting default " + Constants.DEFAULT_LOGIN_ACTIONS_DELAY + "ms.", e);
        }
    }

    public ArrayList<String> getLoginActions() {
        return loginActions;
    }

    public void setLoginActions(String loginActionsStr) {
        loginActions = new ArrayList<>();
        for (String loginAction : loginActionsStr.split(LOGIN_ACTIONS_SEPARATOR)) {
            loginActions.add(loginAction);
        }
    }

    public boolean isOpenOnStartup() {
        return openOnStartup;
    }

    public void setOpenOnStartup(String openOnStartup) {
        this.openOnStartup = false;
        if (!UtilLib.isEmptySafe(openOnStartup) && (openOnStartup.equals("1") || openOnStartup.equalsIgnoreCase("TRUE"))) {
            this.openOnStartup = true;
        }
    }

    public TerminalConfig getTheme() {
        return theme;
    }

    public void setTheme(TerminalConfig theme) {
        this.theme = theme;
    }

    public String objectToString() {
        return "SSHConfig{" +
                "name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", username='" + username + '\'' +
                ", password='" + getPasswordMasked() + '\'' +
                ", port='" + port + '\'' +
                ", loginActions=" + loginActions +
                ", openOnStartup=" + openOnStartup +
                ", passwordDelay=" + passwordDelay +
                ", loginActionsDelay=" + loginActionsDelay +
                ", theme=" + theme +
                ", isFolder=" + isFolder +
                ", isRootFolder=" + isRootFolder +
                ", file=" + file +
                '}';
    }

    @Override
    public String toString() {
        if (isRootFolder())
            return getAbsolutePath();
        else if (isFolder())
            return getFileName();
        else
            return "" + getFileName() + " [\"" + name + "\" - " + username + "@" + ipAddress + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SSHConfig sshConfig = (SSHConfig) o;
        return isOpenOnStartup() ==
                sshConfig.getName().equals(sshConfig.getName()) &&
                sshConfig.isOpenOnStartup() &&
                getIpAddress().equals(sshConfig.getIpAddress()) &&
                getUsername().equals(sshConfig.getUsername()) &&
                getPassword().equals(sshConfig.getPassword()) &&
                getPort().equals(sshConfig.getPort()) &&
                Objects.equals(getLoginActions(), sshConfig.getLoginActions()) &&
                getTheme().equals(sshConfig.getTheme());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIpAddress(), getUsername(), getPassword(), getPort(), getLoginActions(), isOpenOnStartup(), getTheme(), getName());
    }

    public boolean isEmpty() {
        return UtilLib.isEmptySafe(ipAddress) &&
                UtilLib.isEmptySafe(username) &&
                UtilLib.isEmptySafe(password) &&
                UtilLib.isEmptySafe(port) &&
                UtilLib.isEmptySafe(loginActions) &&
                UtilLib.isEmptySafe(theme) &&
                UtilLib.isEmptySafe(name);
    }

    public String getSSHCommand() {
        return "ssh " + getUsername() + "@" + getIpAddress() + " -p " + getPort();
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setIsFolder(boolean isFolder) {
        this.isFolder = isFolder;
    }

    public void setFile(File file) {
        this.file = file;
        if (file.isDirectory()) {
            setIsFolder(true);
        }
    }

    public boolean isRootFolder() {
        return isRootFolder;
    }

    public void setIsRootFolder(boolean rootFolder) {
        isRootFolder = rootFolder;
        setIsFolder(true);
    }

    public String getAbsolutePath() {
        return file.getPath();
    }

    public String getFileName() {
        return file.getName();
    }

    public File getFile() {
        return file;
    }
}
