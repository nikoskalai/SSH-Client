package com.nikoskalai.ssh_client;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import com.nikoskalai.ssh_client.Util.Constants;
import com.nikoskalai.ssh_client.Util.LogLib;
import com.nikoskalai.ssh_client.config.SSHConfig;
import com.nikoskalai.ssh_client.config.SSHConfigurator;
import com.nikoskalai.ssh_client.config.TabNameGen;
import com.nikoskalai.ssh_client.config.Themes;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;

import java.io.BufferedReader;
import java.io.Reader;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    int terminals = 0;

    @FXML
    private TabPane tabbedPane;
    private TreeView treeView;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SSHConfigurator.readConfigFiles();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (SSHConfig sshConfig: SSHConfigurator.getConfigs()) {
                    openTerminal(sshConfig);
                }
                if (TabNameGen.activeInstances <= 0) {
                    initTerminal();
                }
            }
        });
    }

    private void openTerminal(final SSHConfig sshConfig) {
        TerminalBuilder terminalBuilder = new TerminalBuilder(Themes.getDefaultTheme());

        terminalBuilder.setNameGenerator(TabNameGen.getTabNameGen());
        final TerminalTab terminal = terminalBuilder.newTerminal();
        terminal.setClosable(true);
        terminal.setText(sshConfig.getName());
        terminal.onTerminalFxReady(new Runnable() {
            @Override
            public void run() {
                try {
                    terminal.getTerminal().command(sshConfig.getSSHCommand() + Constants.LINE_SEPARATOR);
                    terminal.getTerminal().command(sshConfig.getPassword() + Constants.LINE_SEPARATOR);
                } catch (Exception e) {
                    LogLib.writeErrorLog(e);
                }
//                for (String command: sshConfig.getLoginActions()) {
//                    terminal.getTerminal().command(command + System.getProperty("line.separator"));
//                }

            }
        });
        tabbedPane.getTabs().add(terminal);
    }

    private void initTerminal() {
        TerminalBuilder terminalBuilder = new TerminalBuilder(Themes.getDefaultTheme());

        terminalBuilder.setNameGenerator(TabNameGen.getTabNameGen());
        final TerminalTab terminal = terminalBuilder.newTerminal();
        terminal.setClosable(true);
        terminal.onTerminalFxReady(new Runnable() {
            @Override
            public void run() {
                terminal.getTerminal().command("java -version\r");
                terminal.getTerminal().command("dir\r");

            }
        });
        tabbedPane.getTabs().add(terminal);
    }


}
