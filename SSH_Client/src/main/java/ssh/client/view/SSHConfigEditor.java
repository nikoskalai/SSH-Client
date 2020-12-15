package ssh.client.view;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ssh.client.Util.Constants;
import ssh.client.Util.LogLib;
import ssh.client.ssh_config.SSHConfig;

public class SSHConfigEditor {

    private static SSHConfig sshConfig = null;
    public static void configureSSH(SSHConfig selectedConfig) {
        sshConfig = selectedConfig;
        if (sshConfig == null) {
            //TODO show warning message
        } else if (sshConfig.isFolder()) {
            //TODO show warning message
        } else {
            openEditor();
        }
    }

    private static Label nameLabel, ipLabel;
    private static void openEditor() {
        try {
            VBox root = new VBox();
            root.getChildren().addAll(initFields(), initOpenOnStartup(), initLoginActions(), initSavePanel());
            root.setPadding(new Insets(10));
            root.setAlignment(Pos.CENTER_LEFT);
            Scene scene = new Scene(root, 600, 400);
            Stage stage = new Stage();
            stage.setTitle(Constants.SSH_EDITOR_TITLE);
            ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> System.out.println("Height: " + stage.getHeight() + " Width: " + stage.getWidth());
            stage.widthProperty().addListener(stageSizeListener);
            stage.heightProperty().addListener(stageSizeListener);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            LogLib.writeErrorLog(e);
        }
    }

    private static Node initFields() {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(initName(), initIP(), initPort(), initUsername(), initPassword());
        vBox.setAlignment(Pos.CENTER_LEFT);
        return vBox;
    }

    private static Node initName() {
        HBox hbox = new HBox();
        Label label = new Label(Constants.SSH_EDITOR_LABEL_NAME);
        editLabelPadding(label);
        TextField field = new TextField(sshConfig.getName());
        editTextFieldPadding(field);
        hbox.getChildren().addAll(label, field);
        hbox.setAlignment(Pos.CENTER_LEFT);
        nameLabel = label;
        editHboxPadding(hbox);
        return hbox;
    }

    private static Node initIP() {
        HBox hbox = new HBox();
        Label label = new Label(Constants.SSH_EDITOR_LABEL_IP);
        editLabelPadding(label);
        TextField field = new TextField(sshConfig.getIpAddress());
        editTextFieldPadding(field);
        hbox.getChildren().addAll(label, field);
        hbox.setAlignment(Pos.CENTER_LEFT);
        ipLabel = label;
        editHboxPadding(hbox);
        return hbox;
    }

    private static Node initPort() {
        HBox hbox = new HBox();
        Label label = new Label(Constants.SSH_EDITOR_LABEL_PORT);
        editLabelPadding(label);
        TextField field = new TextField(sshConfig.getPort());
        editTextFieldPadding(field);
        hbox.getChildren().addAll(label, field);
        hbox.setAlignment(Pos.CENTER_LEFT);
        editHboxPadding(hbox);
        return hbox;
    }

    private static Node initUsername() {
        HBox hboxUsername = new HBox();
        Label labelUsername = new Label(Constants.SSH_EDITOR_LABEL_USERNAME);
        editLabelPadding(labelUsername);
        TextField fieldUsername = new TextField(sshConfig.getUsername());
        editTextFieldPadding(fieldUsername);
        hboxUsername.getChildren().addAll(labelUsername, fieldUsername);
        hboxUsername.setAlignment(Pos.CENTER_LEFT);
        editHboxPadding(hboxUsername);
        return hboxUsername;
    }

    private static Node initPassword() {
        HBox hboxPassword = new HBox();
        Label labelPassword = new Label(Constants.SSH_EDITOR_LABEL_PASSWORD);
        editLabelPadding(labelPassword);
        TextField fieldPassword = new TextField(sshConfig.getPassword());
        editTextFieldPadding(fieldPassword);
        hboxPassword.getChildren().addAll(labelPassword, fieldPassword);
        hboxPassword.setAlignment(Pos.CENTER_LEFT);
        editHboxPadding(hboxPassword);
        return hboxPassword;
    }

    private static Node initOpenOnStartup() {
        CheckBox checkBox = new CheckBox(Constants.SSH_EDITOR_LABEL_OPEN_ON_STARTUP);
        checkBox.setSelected(sshConfig.isOpenOnStartup());
        checkBox.setPadding(new Insets(10, 20, 10, 20));
        return checkBox;
    }

    private static Node initLoginActions() {
        VBox box = new VBox();
        Label label = new Label(Constants.SSH_EDITOR_LABEL_LOGIN_ACTIONS);
        editLabelPadding(label);
        TextArea textArea = new TextArea(sshConfig.getLoginActionsForTextArea());
        textArea.setMaxHeight(100);
        textArea.setPrefHeight(60);
        box.getChildren().addAll(label, textArea);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(10));
        return box;
    }

    private static Node initSavePanel() {
        VBox box = new VBox();
        HBox dirBox = new HBox();
        Label label = new Label(Constants.SSH_EDITOR_LABEL_SAVE_DIRECTORY);
        editLabelPadding(label);
        TextField dirField = new TextField(sshConfig.getFile().getParent());
        dirField.setPrefWidth(400);
        dirBox.getChildren().addAll(label, dirField);
        dirBox.setPadding(new Insets(10));
        HBox buttonBox = new HBox();
        Button saveButton = new Button(Constants.SSH_EDITOR_BUTTON_SAVE);
        Button saveAsButton = new Button(Constants.SSH_EDITOR_BUTTON_SAVE_AS);
        Button cancelButton = new Button(Constants.SSH_EDITOR_BUTTON_CANCEL);
        buttonBox.getChildren().addAll(saveButton, saveAsButton, cancelButton);
        box.getChildren().addAll(dirBox, buttonBox);
        return box;
    }

    private static void editLabelPadding(Label label) {
        label.setPrefWidth(Constants.SSH_EDITOR_LABEL_PREF_WIDTH);
    }

    private static void editTextFieldPadding(TextField textField) {
        textField.setPrefWidth(Constants.SSH_EDITOR_TEXT_FIELD_PREF_WIDTH);
    }

    private static void editHboxPadding(HBox hBox) {
        hBox.setPadding(new Insets(0, 0,10,0));
    }
}
