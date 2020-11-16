open module ssh_client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.kodedu.terminalfx;
    requires java.datatransfer;
    requires java.desktop;

    exports ssh.client;
}