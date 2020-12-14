package ssh.client;

import javafx.application.Application;
import javafx.stage.Stage;
import ssh.client.Util.LogLib;
import ssh.client.config.*;


public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        LogLib.writeDebugLog(System.getProperties().toString());
        AppConfig.getInstance().initApp(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
