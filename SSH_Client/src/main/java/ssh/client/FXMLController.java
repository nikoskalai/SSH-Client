package ssh.client;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import ssh.client.Util.LogLib;
import ssh.client.Util.UtilLib;
import ssh.client.config.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

        Platform.runLater(() -> {
            for (SSHConfig sshConfig: SSHConfigurator.getOpenOnStartupConfigs()) {
                openTerminal(sshConfig);
            }
            if (TabNameGen.activeInstances <= 0) {
                initTerminal();
            }
        });
    }

    private void openTerminal(final SSHConfig sshConfig) {
        TerminalBuilder terminalBuilder = new TerminalBuilder(Themes.getDarkTheme());

        terminalBuilder.setNameGenerator(TabNameGen.getTabNameGen(sshConfig.getName()));
        final TerminalTab terminal = terminalBuilder.newTerminal();
        terminal.setClosable(true);
        terminal.onTerminalFxReady(() -> {
            try {
                SSHActions.login(terminal, sshConfig);
            } catch (Exception e) {
                LogLib.writeErrorLog(e);
            }
        });
        tabbedPane.getTabs().add(terminal);
    }

    private void initTerminal() {
        TerminalBuilder terminalBuilder = new TerminalBuilder(Themes.getDarkTheme());

        terminalBuilder.setNameGenerator(TabNameGen.getTabNameGen());
        final TerminalTab terminal = terminalBuilder.newTerminal();
        terminal.setClosable(true);
        terminal.onTerminalFxReady(() -> {
            terminal.getTerminal().command(UtilLib.getCommandString("java -version"));
        });
        tabbedPane.getTabs().add(terminal);
    }


}
