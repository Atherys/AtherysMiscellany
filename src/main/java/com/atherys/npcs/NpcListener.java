package com.atherys.npcs;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.constants.EntityType;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IEntity;

public class NpcListener {
    @SubscribeEvent
    public void onNpcSpawn(EntityJoinWorldEvent event) {
        IEntity e = NpcAPI.Instance().getIEntity(event.getEntity());

        if (e.typeOf(EntityType.NPC)) {
            ICustomNpc npc = (ICustomNpc) e;
            event.getEntity().setCustomNameTag(npc.getDisplay().getName());
        }
    }
}
