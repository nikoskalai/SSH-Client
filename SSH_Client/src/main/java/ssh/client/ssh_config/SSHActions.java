package ssh.client.ssh_config;

import com.kodedu.terminalfx.TerminalTab;
import ssh.client.Util.LogLib;
import ssh.client.Util.UtilLib;

public class SSHActions {

    public static void login(TerminalTab terminal, SSHConfig sshConfig) {
        System.out.println(sshConfig.toString());
        terminal.getTerminal().command(UtilLib.getCommandString(sshConfig.getSSHCommand()));
        LogLib.writeInfoLog("Connected to " + sshConfig.getIpAddress());
        if (!UtilLib.isEmptySafe(sshConfig.getPassword())) {
            LogLib.writeDebugLog("Sleeping for password with delay:" + sshConfig.getPasswordDelay());
            UtilLib.sleepSafe(sshConfig.getPasswordDelay());
            terminal.getTerminal().command(UtilLib.getCommandString(sshConfig.getPassword()));
        }
        int i = 0;
        for (String command : sshConfig.getLoginActions()) {
            LogLib.writeDebugLog("Sleeping for login action (" + i++ + ") with delay:" + sshConfig.getPasswordDelay());
            UtilLib.sleepSafe(sshConfig.getLoginActionsDelay());
            terminal.getTerminal().command(UtilLib.getCommandString(command));
        }
    }
}
