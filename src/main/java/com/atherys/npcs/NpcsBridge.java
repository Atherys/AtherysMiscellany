package com.atherys.npcs;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.atherys.npcs.NpcsBridge.*;

@Mod(modid = ID, version = VERSION, name = NAME, acceptableRemoteVersions = "*", serverSideOnly = true)
public class NpcsBridge {
    static final String ID = "npcsrpg";
    static final String NAME = "CustomNPCs bridge for AtherysRPG";
    static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void onInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new NpcListener());
    }
}