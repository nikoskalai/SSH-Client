package ssh.client.ssh_config;

import ssh.client.Util.Constants;
import ssh.client.Util.LogLib;
import ssh.client.Util.UtilLib;
import ssh.client.config.Themes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SSHConfigStore {

    private static List<SSHConfig> configs = null;

    public static List<SSHConfig> getConfigs() {
        if (configs == null) {
            configs = new ArrayList<>();
        }
        return configs;
    }

    public static List<SSHConfig> getOpenOnStartupConfigs() {
        ArrayList<SSHConfig> openOnStartups = new ArrayList<>();
        for(SSHConfig sshConfig:getConfigs()) {
            if (sshConfig.isOpenOnStartup()) {
                openOnStartups.add(sshConfig);
            }
        }
        return openOnStartups;
    }

    public static String getConfigDir() {
        return Constants.CONFIG_DIR;
    }

    private static void processConfigFile(File f) {
        if (UtilLib.isEmptySafe(f)) {
            return;
        }
        try {
            SSHConfig sshConfig = new SSHConfig(f);
            if (!sshConfig.isEmpty()) {
                configs.add(sshConfig);
            }
        } catch (Exception e) {
            LogLib.writeErrorLog(e);
        }
    }

    public static void addSSHConfigInList(SSHConfig sshConfig) {
        if (sshConfig!= null && !sshConfig.isEmpty()) {
            getConfigs().add(sshConfig);
        }
    }
}
