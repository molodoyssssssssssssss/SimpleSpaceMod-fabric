package org.molodoyss.simplespacemod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class SimpleSpaceMod implements ModInitializer {
    @Override
    public void onInitialize() {
        DamageSources[] damageSource = new DamageSources[1];
        DamageSource[] drown = new DamageSource[1];
        ServerLifecycleEvents.SERVER_STARTING.register(minecraftServer -> {
            damageSource[0] = new DamageSources(minecraftServer.getRegistryManager());
            drown[0] = damageSource[0].create(DamageTypes.DROWN);
        });
        ServerTickEvents.END_WORLD_TICK.register(serverWorld -> {
            if (!serverWorld.getServer().getPlayerManager().getPlayerList().isEmpty()) {
                for (ServerPlayerEntity player : serverWorld.getServer().getPlayerManager().getPlayerList()) {
                    if (player.getY() >= 500 && (!player.hasStatusEffect(StatusEffects.WATER_BREATHING))) {
                        player.damage(serverWorld, drown[0], (float)(player.getY() / 1000F));
                    }
                }
            }
        });
    }
}
