package com.atherys.misc.spawner;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.core.utils.TimeUtils;
import com.atherys.misc.AtherysMiscellany;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;

@Aliases("create-spawner")
@Permission("atherysmiscellany.spawner.create")
@Description("Adds a spawner to the config.")
public class CreateSpawnerCommand implements ParameterizedCommand, PlayerCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                GenericArguments.string(Text.of("mob")),
                GenericArguments.doubleNum(Text.of("radius")),
                GenericArguments.integer(Text.of("maximum")),
                GenericArguments.integer(Text.of("wave")),
                GenericArguments.remainingJoinedStrings(Text.of("interval"))
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        SpawnerConfig newSpawner = new SpawnerConfig();
        newSpawner.MOB = args.<String>getOne("mob").get();
        newSpawner.RADIUS = args.<Double>getOne("radius").get();
        newSpawner.MAXIMUM = args.<Integer>getOne("maximum").get();
        newSpawner.WAVE = args.<Integer>getOne("wave").get();
        newSpawner.SPAWN_INTERVAL = TimeUtils.stringToDuration(args.<String>getOne("interval").get());
        newSpawner.WORLD = source.getLocation().getExtent().getName();
        newSpawner.POSITION = source.getPosition();

        AtherysMiscellany.getSpawnersConfig().SPAWNERS.add(newSpawner);
        AtherysMiscellany.getSpawnersConfig().save();
        return CommandResult.success();
    }
}
