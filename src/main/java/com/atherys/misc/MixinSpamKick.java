package com.atherys.misc;

import net.minecraft.network.NetHandlerPlayServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayServer.class)
public abstract class MixinSpamKick {

    @Shadow
    private int chatSpamThresholdCount;

    @Inject(method = "processChatMessage", at = @At("RETURN"))
    private void checkMessageCount(CallbackInfo info) {
        this.chatSpamThresholdCount -= 10;
    }

}
