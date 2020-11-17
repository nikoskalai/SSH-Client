package ssh.client.config;

import com.kodedu.terminalfx.config.TerminalConfig;
import ssh.client.Util.Constants;
import ssh.client.Util.LogLib;
import ssh.client.Util.UtilLib;

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

    @Override
    public String toString() {
        return "SSHConfig{" +
                "name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", port='" + port + '\'' +
                ", loginActions=" + loginActions +
                ", openOnStartup=" + openOnStartup +
                ", passwordDelay=" + passwordDelay +
                ", loginActionsDelay=" + loginActionsDelay +
                ", theme=" + theme +
                '}';
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
}
