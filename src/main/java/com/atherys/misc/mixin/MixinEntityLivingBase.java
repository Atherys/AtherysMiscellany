package com.atherys.misc.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {

    @Final
    @Shadow
    private static Logger LOGGER;

    public MixinEntityLivingBase(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "attackEntityFrom", at = @At(value = "HEAD"))
    private void onAttackEntityFrom(DamageSource source, float damageAmount, CallbackInfoReturnable<Boolean> cir) {
        this.hurtResistantTime = 0;
    }
}
