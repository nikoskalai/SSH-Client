package com.nikoskalai.ssh_client;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import com.kodedu.terminalfx.config.TabNameGenerator;
import com.kodedu.terminalfx.config.TerminalConfig;
import com.nikoskalai.ssh_client.config.TabNameGen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML
    private TabPane tabbedPane;
    private TreeView treeView;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TerminalConfig darkConfig = new TerminalConfig();
        darkConfig.setBackgroundColor(Color.rgb(16, 16, 16));
        darkConfig.setForegroundColor(Color.rgb(240, 240, 240));
        darkConfig.setCursorColor(Color.rgb(255, 0, 0, 0.5));

        TerminalBuilder terminalBuilder = new TerminalBuilder(darkConfig);

        terminalBuilder.setNameGenerator(TabNameGen.getTabNameGen());
        TerminalTab terminal = terminalBuilder.newTerminal();
//        terminal.onTerminalFxReady(() -> {
//            terminal.getTerminal().command("java -version\r");
//        });
//        terminal.setText("Test");
        tabbedPane.getTabs().add(terminal);
    }


}
