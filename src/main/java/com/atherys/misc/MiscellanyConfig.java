package com.atherys.misc;

import com.atherys.core.utils.PluginConfig;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MiscellanyConfig extends PluginConfig {

    @ConfigSerializable
    public static class NpcConfig {
        @Setting("name")
        private String name;

        @Setting("tab")
        private int tab;

        public String getName() {
            return name;
        }

        public int getTab() {
            return tab;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTab(int tab) {
            this.tab = tab;
        }
    }

    // This defines how much of a count should be applied per chat message
    // After every tick, this number is de-incremented by 1
    // If the total threshold count hits 200, the player is kicked for spam.
    @Setting("message-threshold")
    public int THRESHOLD_COUNT_PER_MESSAGE = 10;

    public float ATTACK_CANCEL_THRESHOLD = 0.75f;

    @Setting("custom-npcs")
    public Map<String, NpcConfig> NPCS = new HashMap<>();

    protected MiscellanyConfig() throws IOException {
        super("config/" + AtherysMiscellany.ID, "misc.conf");
        init();
    }

}
