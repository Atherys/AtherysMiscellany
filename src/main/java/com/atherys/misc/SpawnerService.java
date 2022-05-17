package com.atherys.misc;

import com.atherys.misc.MiscellanyConfig.NpcConfig;
import com.atherys.rpg.AtherysRPG;
import com.atherys.misc.SpawnersConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.minecraft.world.WorldServer;
import noppes.npcs.api.IWorld;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.IEntity;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.CauseStackManager;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Singleton
public class SpawnerService {

    private List<Spawner> spawners;
    private Task spawnerTask;

    private static Random rand = new Random();

    public void init() {
        spawners = AtherysMiscellany.getSpawnersConfig().SPAWNERS.stream().map(spawnerConfig -> {
            World world = Sponge.getServer().getWorld(spawnerConfig.WORLD).orElseThrow(() -> {
                return new IllegalArgumentException("World " + spawnerConfig.WORLD + " is not a valid world.");
            });

            NpcConfig npcConfig = AtherysMiscellany.getConfig().NPCS.get(spawnerConfig.MOB);

            return new Spawner(
                    world.getLocation(spawnerConfig.POSITION),
                    npcConfig,
                    spawnerConfig.RADIUS,
                    spawnerConfig.SURFACE_ONLY,
                    spawnerConfig.SPAWN_INTERVAL,
                    spawnerConfig.MAXIMUM,
                    spawnerConfig.WAVE
            );
        })
        .collect(Collectors.toList());

        Task.builder()
                .execute(__ -> spawnMobs())
                .interval(1, TimeUnit.SECONDS)
                .submit(AtherysMiscellany.getInstance());
    }

    private void spawnMobs() {
        try (CauseStackManager.StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
            frame.pushCause(AtherysRPG.getInstance());
            frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.MOB_SPAWNER);

            for (Spawner spawner : spawners) {
                int spawned = spawner.currentlySpawned.size();

                if (spawner.secondsSinceLastSpawn >= spawner.spawnIntervalSeconds && spawned < spawner.maximum) {
                    spawner.secondsSinceLastSpawn = 0;
                    int toSpawn = Math.min(spawner.maximumWaveSize, spawner.maximum - spawned);
                    Location<World> location = spawner.location;

                    for (int i = 0; i < toSpawn; i++) {
                        double x = location.getX() + ThreadLocalRandom.current().nextDouble(-spawner.radius, spawner.radius);
                        double z = location.getZ() + ThreadLocalRandom.current().nextDouble(-spawner.radius, spawner.radius);
                        double y = location.getExtent().getHighestYAt((int) x, (int) z);

                        spawnMob(spawner, x, y, z);
                    }
                }
                spawner.secondsSinceLastSpawn++;
            }
        }
    }

    private void spawnMob(Spawner spawner, double x, double y, double z) {
        IWorld world = NpcAPI.Instance().getIWorld((WorldServer) spawner.location.getExtent());
        IEntity mob = NpcAPI.Instance().getClones().spawn(x, y, z, spawner.mob.getTab(), spawner.mob.getName(), world);
        spawner.currentlySpawned.add(mob.getMCEntity().getUniqueID());
    }

    public void removeMob(Living mob) {
        for (Spawner spawner : spawners) {
            spawner.currentlySpawned.remove(mob.getUniqueId());
        }
    }

    private static class Spawner {
        private final Location<World> location;
        private final NpcConfig mob;
        private final double radius;
        private final boolean surfaceOnly;
        private final long spawnIntervalSeconds;
        private final int maximum;
        private final int maximumWaveSize;

        private Set<UUID> currentlySpawned;
        private long secondsSinceLastSpawn;

        private Spawner(Location<World> location, NpcConfig npc, double radius, boolean surfaceOnly, Duration spawnInterval, int maximum, int maximumWaveSize) {
            this.location = location;
            this.mob = npc;
            this.radius = radius;
            this.surfaceOnly = surfaceOnly;
            this.spawnIntervalSeconds = spawnInterval.getSeconds();
            this.maximum = maximum;
            this.maximumWaveSize = maximumWaveSize;

            this.currentlySpawned = new HashSet<>();
        }
    }
}
