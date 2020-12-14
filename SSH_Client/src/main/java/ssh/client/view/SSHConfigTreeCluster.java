package ssh.client.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import ssh.client.Util.Constants;
import ssh.client.ssh_config.SSHConfig;

import java.io.File;

public class SSHConfigTreeCluster extends TreeItem<SSHConfig> {

    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;
    private boolean isLeaf;

    public SSHConfigTreeCluster(SSHConfig f) {
        super(f);
    }

    @Override
    public ObservableList<TreeItem<SSHConfig>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeaf) {
            isFirstTimeLeaf = false;
            SSHConfig f = getValue();
            isLeaf = !f.isFolder();
        }
        return isLeaf;
    }

    private ObservableList<TreeItem<SSHConfig>> buildChildren(TreeItem<SSHConfig> TreeItem) {
        SSHConfig f = TreeItem.getValue();
        if (f != null && f.isFolder()) {
            File file = f.getFile();
            File[] files = file.listFiles();
            if (files != null) {
                ObservableList<TreeItem<SSHConfig>> children = FXCollections.observableArrayList();
                for (File childFile : files) {
                    if (childFile.isFile() && childFile.getName().endsWith(Constants.SSH_CLIENT_CONFIGURATION_FILE_SUFFIX)) {
                        SSHConfig sshConfig = new SSHConfig(childFile);
                        children.addAll(new SSHConfigTreeCluster(sshConfig));
                    } else if (childFile.isDirectory()) {
                        SSHConfig sshConfig = SSHConfig.setupFolder(childFile.getPath());
                        children.add(new SSHConfigTreeCluster(sshConfig));
                    }
                }
                return children;
            }
        }
        return FXCollections.emptyObservableList();
    }


}
