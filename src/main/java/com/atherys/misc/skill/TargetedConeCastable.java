package com.atherys.misc.skill;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.TargetedCastable;
import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.vector.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.api.entity.living.Living;

import java.util.Collection;
import java.util.stream.Collectors;

public interface TargetedConeCastable extends TargetedCastable {
    @Override
    default CastResult cast(Living user, long timestamp, String... args) throws CastException {
        Collection<Living> nearby = user.getNearbyEntities(getRange(user)).stream()
                .filter(entity -> entity instanceof Living)
                .map(entity -> (Living) entity)
                .collect(Collectors.toList());

        if (nearby.isEmpty()) throw CastErrors.noTarget();

        final Vector3d rotation = user.getHeadRotation();
        final Vector3d axis = Quaterniond.fromAxesAnglesDeg(rotation.getX(), -rotation.getY(), rotation.getZ()).getDirection();
        final Vector3d userPosition = user.getLocation().getPosition();

        Living finalTarget = null;
        double differenceSquared = Double.MAX_VALUE;

        double angleCos = Math.cos(Math.toRadians(getAngle(user)));
        for (Living target : nearby) {
            if (target == user) continue;

            Vector3d targetPosition = target.getLocation().getPosition();
            Vector3d between = targetPosition.sub(userPosition);

            double dot = axis.dot(between.normalize());

            AtherysSkills.getInstance().getLogger().info("Dot product: {}", dot);
            if (dot > angleCos && ((EntityLiving) user).canEntityBeSeen((Entity) target)) {
                double lengthSquared = between.lengthSquared();
                if (finalTarget == null || lengthSquared < differenceSquared) {
                    differenceSquared = lengthSquared;
                    finalTarget = target;
                }
            }
        }

        if (finalTarget == null) {
            throw CastErrors.noTarget();
        }

        return cast(user, finalTarget, timestamp, args);
    }

    default double getAngle(Living user) {
        return 15;
    }
}