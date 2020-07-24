package com.atherys.misc;

import com.atherys.core.utils.PluginConfig;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MiscellanyConfig extends PluginConfig {

    @ConfigSerializable
    public static class NpcConfig {
        private String npc;
        private int x;
        private int y;
        private int z;

        public NpcConfig(String npc, int x, int y, int z) {
            this.npc = npc;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public String getNpc() {
            return npc;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }

    public List<NpcConfig> NPCS = new ArrayList<>();

    protected MiscellanyConfig() throws IOException {
        super("config/" + AtherysMiscellany.ID, "npcs.conf");
        init();
    }

}
