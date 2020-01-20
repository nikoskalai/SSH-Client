package com.nikoskalai.ssh_client.config;

import com.kodedu.terminalfx.config.TabNameGenerator;

public class TabNameGen implements TabNameGenerator {

    private String prefix = "SSH Session";
    private static TabNameGen tabNameGen;
    public static int activeInstances = 0;

    @Override
    public String next() {
        return prefix + "_" + getNextInstance();
    }

    public static TabNameGen getTabNameGen() {
        if (tabNameGen == null) {
            tabNameGen = new TabNameGen();
        }
        return tabNameGen;
    }

    public static int getNextInstance() {
        return ++activeInstances;
    }

}
