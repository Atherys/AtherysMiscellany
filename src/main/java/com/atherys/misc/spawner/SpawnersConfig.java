package com.atherys.misc.spawner;

import com.atherys.core.utils.PluginConfig;
import com.google.inject.Singleton;
import ninja.leaping.configurate.objectmapping.Setting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class SpawnersConfig extends PluginConfig {
    @Setting("spawners")
    public List<SpawnerConfig> SPAWNERS = new ArrayList<>();

    {
        SPAWNERS.add(new SpawnerConfig());
    }

    public SpawnersConfig() throws IOException {
        super("config/atherysmiscellany", "spawners.conf");
        init();
    }
}
