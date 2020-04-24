package com.atherys.misc;

import com.atherys.core.AtherysCore;
import com.atherys.core.command.CommandService;
import com.google.inject.Inject;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import static com.atherys.misc.AtherysMiscellany.*;

@Plugin(
        id = ID,
        name = NAME,
        version = VERSION,
        dependencies = {
                @Dependency(id = "atheryscore")
        }
)
public class AtherysMiscellany {
    static final String ID = "atherysmiscellany";
    static final String NAME = "CustomNPCs fix and others";
    static final String VERSION = "1.0.0";

    @Inject
    private Logger logger;

    private static AtherysMiscellany instance;

    @Listener
    public void onInit(GameInitializationEvent event) {
        instance = this;
        //MinecraftForge.EVENT_BUS.register(new NpcListener());


        Sponge.getEventManager().registerListeners(this, new BoatListener());
        Sponge.getEventManager().registerListeners(this, new NpcListener());

        try {
            AtherysCore.getCommandService().register(new BoatCommand(), this);
        } catch (CommandService.AnnotatedCommandException e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger() {
        return instance.logger;
    }
}