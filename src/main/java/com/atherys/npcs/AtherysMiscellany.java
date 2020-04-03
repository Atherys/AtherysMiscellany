package com.atherys.npcs;

import com.atherys.core.AtherysCore;
import com.atherys.core.command.CommandService;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import static com.atherys.npcs.AtherysMiscellany.*;

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

    @Listener
    public void onInit(GameInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new NpcListener());

        Sponge.getEventManager().registerListeners(this, new BoatListener());

        try {
            AtherysCore.getCommandService().register(new BoatCommand(), this);
        } catch (CommandService.AnnotatedCommandException e) {
            e.printStackTrace();
        }
    }
}