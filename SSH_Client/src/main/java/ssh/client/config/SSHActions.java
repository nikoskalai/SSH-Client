package ssh.client.config;

import com.kodedu.terminalfx.TerminalTab;
import ssh.client.Util.Constants;
import ssh.client.Util.LogLib;
import ssh.client.Util.UtilLib;
import ssh.client.config.SSHConfig;

public class SSHActions {

    public static void login(TerminalTab terminal, SSHConfig sshConfig) throws InterruptedException {
        try {
            terminal.getTerminal().command(UtilLib.getCommandString(sshConfig.getSSHCommand()));
            LogLib.writeInfoLog("Connected to " + sshConfig.getIpAddress());
            if (!UtilLib.isEmptySafe(sshConfig.getPassword())) {
                LogLib.writeInfoLog("Sleeping for password with delay:" + sshConfig.getPasswordDelay());
                Thread.sleep(sshConfig.getPasswordDelay());
                terminal.getTerminal().command(UtilLib.getCommandString(sshConfig.getPassword()));
            }
            int i=0;
            for (String command : sshConfig.getLoginActions()) {
                LogLib.writeInfoLog("Sleeping for login action (" + i++ + ") with delay:" + sshConfig.getPasswordDelay());
                Thread.sleep(sshConfig.getLoginActionsDelay());
                terminal.getTerminal().command(UtilLib.getCommandString(command));
            }
        } catch (InterruptedException e) {
            LogLib.writeErrorLog(e);
        }
    }
}
