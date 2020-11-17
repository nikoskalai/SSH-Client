package ssh.client.config;

import ssh.client.Util.LogLib;
import ssh.client.Util.UtilLib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SSHConfigurator {

    private static final String extension = ".sshc";

    private static List<SSHConfig> configs = null;

    public static List<SSHConfig> getConfigs() {
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

    public static void readConfigFiles() {
        configs = new ArrayList<>();
        File currentDir = new File(System.getProperty("user.dir"));
        for (File f: currentDir.listFiles()) {
            if (f.isFile() && f.getName().endsWith(extension)) {
                processConfigFile(f);
            }
        }
//        System.out.println(configs);
    }

    private static void processConfigFile(File f) {
        if (UtilLib.isEmptySafe(f)) {
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line=null;
            SSHConfig sshConfig = new SSHConfig();
//            sshConfig.setName(f.getName());
            while ((line=reader.readLine()) != null) {
                if (line.startsWith("name=")) {
                    sshConfig.setIpAddress(line.replaceFirst("name=",""));
                } else if (line.startsWith("ip=")) {
                    sshConfig.setIpAddress(line.replaceFirst("ip=",""));
                } else if (line.startsWith("username=")) {
                    sshConfig.setUsername(line.replaceFirst("username=",""));
                } else if (line.startsWith("password=")) {
                    sshConfig.setPassword(line.replaceFirst("password=",""));
                } else if (line.startsWith("port=")) {
                    sshConfig.setPort(line.replaceFirst("port=",""));
                } else if (line.startsWith("login_actions=")) {
                    sshConfig.setLoginActions(line.replaceFirst("login_actions=",""));
                } else if (line.startsWith("theme=")) {
                    String theme = line.replaceFirst("theme=","");
                    if (theme.equalsIgnoreCase("Dark")) {
                        sshConfig.setTheme(Themes.getDarkTheme());
                    } else if (theme.equalsIgnoreCase("Cygwin")) {
                        sshConfig.setTheme(Themes.getCygwinTheme());
                    } else {
                        sshConfig.setTheme(Themes.getDefaultTheme());
                    }
                } else if (line.startsWith("passwordDelay=")) {
                    sshConfig.setPasswordDelay(line.replaceFirst("passwordDelay=",""));
                } else if (line.startsWith("loginActionsDelay=")) {
                    sshConfig.setLoginActionsDelay(line.replaceFirst("loginActionsDelay=",""));
                } else if (line.startsWith("openOnStartup=")) {
                    sshConfig.setOpenOnStartup(line.replaceFirst("openOnStartup=",""));
                }
            }
            if (!sshConfig.isEmpty()) {
                configs.add(sshConfig);
            }
        } catch (Exception e) {
            LogLib.writeErrorLog(e);
        }
    }
}
