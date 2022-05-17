package com.atherys.misc.npcs;

import com.atherys.misc.AtherysMiscellany;
import com.atherys.rpg.AtherysRPG;
import net.minecraft.entity.Entity;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.constants.EntityType;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IEntity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.Getter;

public class NpcListener {
    @Listener
    public void onNpcSpawn(SpawnEntityEvent event) {
        event.getEntities().forEach(entity -> {
            IEntity e = NpcAPI.Instance().getIEntity((Entity) entity);

            if (e.typeOf(EntityType.NPC)) {
                ICustomNpc npc = (ICustomNpc) e;
                npc.getMCEntity().setCustomNameTag(npc.getDisplay().getName());
            }
        });
    }

    @Listener
    public void onEntityDestruction(DestructEntityEvent event, @Getter("getTargetEntity") Living target) {
        AtherysMiscellany.getSpawnerService().removeMob(target);
        AtherysRPG.getInstance().getLogger().info("UUID removed {}", target.getUniqueId());
    }
}
