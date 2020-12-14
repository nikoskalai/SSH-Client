package ssh.client.view;

import com.kodedu.terminalfx.config.TabNameGenerator;
import ssh.client.Util.UtilLib;

public class TabNameGen implements TabNameGenerator {

    private String prefix = "SSH Session";
    private String name = null;
    public static int activeInstances = 0;

    @Override
    public String next() {
        if (UtilLib.isEmptySafe(name)) {
            return prefix + "_" + getNextInstance();
        } else {
            if (activeInstances == 0) {
                getNextInstance();
                return name;
            } else {
                return name + "_" + getNextInstance();
            }
        }
    }

    public static TabNameGen getTabNameGen() {
        return new TabNameGen();
    }

    public static TabNameGen getTabNameGen(String name) {
        TabNameGen tabNameGen = new TabNameGen();
        tabNameGen.name = name;
        return tabNameGen;
    }

    public static int getNextInstance() {
        return ++activeInstances;
    }

}