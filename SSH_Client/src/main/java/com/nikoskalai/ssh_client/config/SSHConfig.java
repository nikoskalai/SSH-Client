package com.nikoskalai.ssh_client.config;

import com.kodedu.terminalfx.config.TerminalConfig;
import com.nikoskalai.ssh_client.Util.UtilLib;

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
    private TerminalConfig theme = Themes.getDefaultTheme();

    public String getName() {
        return name;
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

    public ArrayList<String> getLoginActions() {
        return loginActions;
    }

    public void setLoginActions(ArrayList<String> loginActions) {
        this.loginActions = loginActions;
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

    public void setOpenOnStartup(boolean openOnStartup) {
        this.openOnStartup = openOnStartup;
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
                "name='" + name + "\'" +
                ", ipAddress='" + ipAddress + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", port='" + port + '\'' +
                ", loginActions=" + loginActions +
                ", openOnStartup=" + openOnStartup +
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
