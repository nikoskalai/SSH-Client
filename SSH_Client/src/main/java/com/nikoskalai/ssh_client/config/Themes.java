package com.nikoskalai.ssh_client.config;

import com.kodedu.terminalfx.config.TerminalConfig;
import javafx.scene.paint.Color;

public class Themes {
    //        Dark Config
    private static TerminalConfig darkConfig = null;
    //        CygWin Config
    private static TerminalConfig cygwinConfig = null;
    //        Default Config
    private static TerminalConfig defaultConfig = null;
    static {
        initConfigs();
    }

    private static void initConfigs() {
        darkConfig = new TerminalConfig();
        darkConfig.setBackgroundColor(Color.rgb(16, 16, 16));
        darkConfig.setForegroundColor(Color.rgb(240, 240, 240));
        darkConfig.setCursorColor(Color.rgb(255, 0, 0, 0.5));
        cygwinConfig = new TerminalConfig();
//        cygwinConfig.setWindowsTerminalStarter("C:\\cygwin64\\bin\\bash -i");
        cygwinConfig.setFontSize(14);
        defaultConfig = new TerminalConfig();
        defaultConfig.setFontSize(16);
//        defaultConfig.setFontFamily("\"Ubuntu Bold\"");

    }

    public static TerminalConfig getDarkTheme() {
        return darkConfig;
    }

    public static TerminalConfig getCygwinTheme() {
        return cygwinConfig;
    }

    public static TerminalConfig getDefaultTheme() {
        if (defaultConfig == null) {
            initConfigs();
        }
        return defaultConfig;
    }
}
