package com.atherys.misc;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.vehicle.Boat;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.RideEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.Root;

public class BoatListener {
    @Listener
    public void onBoatDismount(RideEntityEvent.Dismount event, @Root Player player, @Getter("getTargetEntity") Boat boat) {
        if (boat.getPassengers().get(0) == player) {
            boat.remove();
        }
    }
}
