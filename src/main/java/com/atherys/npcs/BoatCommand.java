package com.atherys.npcs;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.vehicle.Boat;
import org.spongepowered.api.text.Text;

@Aliases("boat")
@Permission("atherysmiscellany.boat")
@Description("Spawns a boat and puts the player in it at the given location.")
public class BoatCommand implements ParameterizedCommand {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = args.<Player>getOne("player").get();
        Vector3d location = args.<Vector3d>getOne("location").get();
        Boat boat = (Boat) player.getWorld().createEntity(EntityTypes.BOAT, location);
        boat.offer(Keys.INVULNERABLE, true);
        boat.setRotation(player.getRotation());

        player.getWorld().spawnEntity(boat);
        player.setVehicle(boat);
        return CommandResult.success();
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                GenericArguments.player(Text.of("player")),
                GenericArguments.vector3d(Text.of("location"))
        };
    }
}
