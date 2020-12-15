package ssh.client.config;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ssh.client.Util.Constants;
import ssh.client.Util.LogLib;
import ssh.client.Util.PropertyLoader;
import ssh.client.Util.UtilLib;
import ssh.client.ssh_config.SSHActions;
import ssh.client.ssh_config.SSHConfig;
import ssh.client.ssh_config.SSHConfigStore;
import ssh.client.view.SSHConfigEditor;
import ssh.client.view.SSHConfigTreeCluster;
import ssh.client.view.TabNameGen;


public class AppConfig {

    private static Button openButton;
    private static TreeView<SSHConfig> treeView;
    private static TabPane tabPane;
    private static SSHConfig selectedConfig;

    static AppConfig appConfig = null;

    public static AppConfig getInstance() {
        if (appConfig == null) {
            appConfig = new AppConfig();
        }
        return appConfig;
    }

    public void initApp(Stage stage) {
        Scene scene = initView();

        stage.setTitle(getWindowTitle());
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            exitApp();
        });

        getFonts();

        Platform.runLater(() -> {
            for (SSHConfig sshConfig : SSHConfigStore.getOpenOnStartupConfigs()) {
                openTerminal(sshConfig);
                SSHConfigEditor.configureSSH(sshConfig);
            }
            if (TabNameGen.activeInstances <= 0) {
                initTerminal();
            }
        });
    }

    private Scene initView() {
        VBox menus = initTop();
        SplitPane splitPane = initSplitPane();

        BorderPane root = new BorderPane();
        root.setTop(menus);
        root.setCenter(splitPane);
        Scene scene = new Scene(root, 1000, 500);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<>() {
            final KeyCombination[] ctrlCombinations = {new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN),
                    new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_DOWN),
                    new KeyCodeCombination(KeyCode.DIGIT3, KeyCombination.CONTROL_DOWN),
                    new KeyCodeCombination(KeyCode.DIGIT4, KeyCombination.CONTROL_DOWN),
                    new KeyCodeCombination(KeyCode.DIGIT5, KeyCombination.CONTROL_DOWN),
                    new KeyCodeCombination(KeyCode.DIGIT6, KeyCombination.CONTROL_DOWN),
                    new KeyCodeCombination(KeyCode.DIGIT7, KeyCombination.CONTROL_DOWN),
                    new KeyCodeCombination(KeyCode.DIGIT8, KeyCombination.CONTROL_DOWN),
                    new KeyCodeCombination(KeyCode.DIGIT9, KeyCombination.CONTROL_DOWN),
                    new KeyCodeCombination(KeyCode.DIGIT0, KeyCombination.CONTROL_DOWN)};

            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.isControlDown()) {
                    for (int i = 0; i < ctrlCombinations.length; i++) {
                        KeyCombination kcc = ctrlCombinations[i];
                        if (kcc.match(keyEvent)) {
                            int target=i+1;
                            tabPane.getSelectionModel().select(target);
                            System.out.println("Switching to tab " + target);
                            break;
                        }
                    }
                }
            }
        });
        scene.getStylesheets().add("/css_styles/Styles.css");
        return scene;
    }

    private SplitPane initSplitPane() {
        initTreeView();
        tabPane = new TabPane();
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().add(treeView);
        splitPane.getItems().add(tabPane);
        splitPane.setDividerPositions(Constants.SPLIT_PANE_DIVIDER_POSITION_INITIAL);
        treeView.maxWidthProperty().bind(splitPane.widthProperty().multiply(Constants.SPLIT_PANE_DIVIDER_POSITION_MAX));
        return splitPane;
    }

    private void initTreeView() {
        ContextMenu contextMenu = initContextMenu();

        SSHConfigTreeCluster treeItem = new SSHConfigTreeCluster(SSHConfig.setupRootFolder(SSHConfigStore.getConfigDir()));
        treeItem.setExpanded(true);
        treeView = new TreeView<>(treeItem);
        treeView.setContextMenu(contextMenu);
        treeView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.getValue().isFolder()) {
                selectedConfig = newValue.getValue();
                LogLib.writeDebugLog("Selected config:" + selectedConfig.objectToString());
                openButton.setDisable(false);
                openButton.setText(Constants.BUTTON_OPEN_SESSION_PREFIX + " (" + selectedConfig.getName() + ")");
            } else {
                selectedConfig = null;
                openButton.setDisable(true);
                openButton.setText(Constants.BUTTON_OPEN_SESSION_PREFIX);
            }
        });
    }

    private ContextMenu initContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem openSession = new MenuItem("Open Session in new tab.");
        openSession.setOnAction(actionEvent -> {
            if (selectedConfig != null) {
                openTerminal(selectedConfig);
            }
        });
        MenuItem editSession = new MenuItem("Edit Session");
        editSession.setOnAction(actionEvent -> {
            SSHConfigEditor.configureSSH(selectedConfig);
        });
        contextMenu.getItems().addAll(openSession, editSession);
        return contextMenu;
    }

    private VBox initTop() {
        MenuBar menuBar = initMenu();
        ToolBar tb = initToolBar();
        VBox menus = new VBox();
        menus.getChildren().addAll(menuBar, tb);
        return menus;
    }

    private ToolBar initToolBar() {
        openButton = new Button();
        openButton.setDisable(true);
        openButton.setText(Constants.BUTTON_OPEN_SESSION_PREFIX);
        openButton.setOnAction(actionEvent -> {
            openTerminal(selectedConfig);
        });
        ToolBar tools = new ToolBar(openButton);
        return tools;
    }

    private MenuBar initMenu() {
        MenuBar menu = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuHelp = new Menu("Help");
        MenuItem about = new MenuItem("About");
        about.setOnAction(actionEvent -> {
            LogLib.writeDebugLog("About");
            //TODO
        });
        menuHelp.getItems().add(about);
        menu.getMenus().addAll(menuFile, menuHelp);
        return menu;
    }

    private void getFonts() {
        java.awt.GraphicsEnvironment e = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.awt.Font[] fonts = e.getAllFonts();
        for (java.awt.Font f : fonts) {
            if (f.getFamily().contains("Ubuntu")) {
                System.out.println(f);
            }
        }
    }

    private String getWindowTitle() {
        String windowTitle = PropertyLoader.getProperty("window.title") + PropertyLoader.getProperty("application.version");
        return windowTitle;
    }

    private static void exitApp() {
        LogLib.writeInfoLog("Exiting...");
        System.exit(0);
    }


    private void openTerminal(final SSHConfig sshConfig) {
        TerminalBuilder terminalBuilder = new TerminalBuilder(Themes.getDarkTheme());

        terminalBuilder.setNameGenerator(TabNameGen.getTabNameGen(sshConfig.getName()));
        final TerminalTab terminal = terminalBuilder.newTerminal();
        terminal.setClosable(true);
        terminal.onTerminalFxReady(() -> {
            try {
                SSHActions.login(terminal, sshConfig);
                selectedConfig = sshConfig;
            } catch (Exception e) {
                LogLib.writeErrorLog(e);
            }
        });
        tabPane.getTabs().add(terminal);
    }

    private void initTerminal() {
        TerminalBuilder terminalBuilder = new TerminalBuilder(Themes.getDarkTheme());

        terminalBuilder.setNameGenerator(TabNameGen.getTabNameGen());
        final TerminalTab terminal = terminalBuilder.newTerminal();
        terminal.setClosable(true);
        terminal.onTerminalFxReady(() -> {
            terminal.getTerminal().command(UtilLib.getCommandString("java -version"));
        });
        tabPane.getTabs().add(terminal);
    }
}
