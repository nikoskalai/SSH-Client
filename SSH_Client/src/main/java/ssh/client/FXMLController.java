package ssh.client;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import ssh.client.Util.Constants;
import ssh.client.Util.LogLib;
import ssh.client.config.SSHConfig;
import ssh.client.config.SSHConfigurator;
import ssh.client.config.TabNameGen;
import ssh.client.config.Themes;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;

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
