package com.atherys.misc;

import com.atherys.core.AtherysCore;
import com.atherys.core.command.CommandService;
import com.atherys.misc.npcs.NpcListener;
import com.atherys.misc.spawner.CreateSpawnerCommand;
import com.atherys.misc.spawner.SpawnerService;
import com.atherys.misc.spawner.SpawnersConfig;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import java.io.IOException;

import static com.atherys.misc.AtherysMiscellany.*;

@Plugin(
        id = ID,
        name = NAME,
        version = VERSION,
        dependencies = {
                @Dependency(id = "atheryscore"),
                @Dependency(id = "atherysrpg"),
                @Dependency(id = "customnpcs")
        }
)
public class AtherysMiscellany {
    static final String ID = "atherysmiscellany";
    static final String NAME = "CustomNPCs fix and others";
    static final String VERSION = "1.0.0";

    @Inject
    private Logger logger;

    private static AtherysMiscellany instance;

    private MiscellanyConfig config;

    private SpawnersConfig spawnersConfig;

    private SpawnerService spawnerService;

    @Listener
    public void onInit(GameInitializationEvent event) {
        instance = this;

        try {
            config = new MiscellanyConfig();
            spawnersConfig = new SpawnersConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sponge.getEventManager().registerListeners(this, new BoatListener());
        Sponge.getEventManager().registerListeners(this, new NpcListener());

        spawnerService = new SpawnerService();

        try {
            AtherysCore.getCommandService().register(new BoatCommand(), this);
            AtherysCore.getCommandService().register(new CreateSpawnerCommand(), this);
        } catch (CommandService.AnnotatedCommandException e) {
            e.printStackTrace();
        }
    }

    @Listener
    public void onStart(GameStartedServerEvent event) {
        spawnerService.init();
    }

    @Listener
    public void onPlayerSpawn(ClientConnectionEvent.Join event, @Getter("getTargetEntity") Player source) {
        if (!source.hasPlayedBefore()) {
            source.offer(
                    Keys.POTION_EFFECTS,
                    Lists.newArrayList(
                            PotionEffect.of(PotionEffectTypes.MINING_FATIGUE, 7, 20),
                            PotionEffect.of(PotionEffectTypes.BLINDNESS, 3, 40),
                            PotionEffect.of(PotionEffectTypes.NAUSEA, 7, 40)
                    )
            );
        }
    }

    public static AtherysMiscellany getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return getInstance().logger;
    }

    public static MiscellanyConfig getConfig() {
        return getInstance().config;
    }

    public static SpawnersConfig getSpawnersConfig() {
        return getInstance().spawnersConfig;
    }

    public static SpawnerService getSpawnerService() {
        return getInstance().spawnerService;
    }
}
