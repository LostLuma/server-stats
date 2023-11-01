package net.lostluma.server_stats.mixin.server.stats;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.lostluma.server_stats.stats.Stats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.mob.passive.animal.PigEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.stat.Stat;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo callbackInfo) {
        var player = (PlayerEntity) (Object) this;
        player.server_stats$incrementStat(Stats.MINUTES_PLAYED, 1);
    }

    @Inject(method = "onKilled", at = @At("HEAD"))
    private void onKilled(DamageSource source, CallbackInfo callbackInfo) {
        var player = (ServerPlayerEntity)(Object)this;

        if (source.getAttacker() != null) {
            var attacker = source.getAttacker();
            player.server_stats$incrementStat(Stats.getKilledByEntityStat(attacker), 1);
        }

        player.server_stats$incrementStat(Stats.DEATHS, 1);
    }

    @Inject(method = "onKillEntity", at = @At("HEAD"))
    private void onKillEntity(Entity entity, int score, CallbackInfo callbackInfo) {
        var player = (PlayerEntity) (Object) this;

        if (!(entity instanceof PlayerEntity)) {
            player.server_stats$incrementStat(Stats.MOBS_KILLED, 1);
        } else {
            player.server_stats$incrementStat(Stats.PLAYERS_KILLED, 1);
        }
    }

    @Inject(method = "dropItem", at = @At("HEAD"))
    private void dropItem(CallbackInfo callbackInfo) {
        var player = (PlayerEntity) (Object) this;
        player.server_stats$incrementStat(Stats.DROPS, 1); // Vanilla 1.7.10 increments this wrong, so we'll keep that
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"))
    private void damage(DamageSource source, int amount, CallbackInfoReturnable<?> callbackInfo) {
        var world = ((PlayerEntity) (Object) this).world;

        if (world.difficulty == 0) {
            amount = 0;
        }

        if (world.difficulty == 1) {
            amount = amount / 2 + 1;
        }

        if (world.difficulty == 3) {
            amount = amount * 3 / 2;
        }

        var player = (PlayerEntity) (Object) this;
        player.server_stats$incrementStat(Stats.DAMAGE_TAKEN, amount);
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void attack(Entity target, CallbackInfo callbackInfo, int damage) {
        var player = (PlayerEntity) (Object) this;
        player.server_stats$incrementStat(Stats.DAMAGE_DEALT, damage);
    }

    @Inject(method = "tickRidingRelatedStats", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void tickRidingRelatedStats(double x, double y, double z, CallbackInfo callbackInfo, int distance) {
        var player = (PlayerEntity)(Object)this;

        if (player.vehicle instanceof PigEntity) {
            player.server_stats$incrementStat(Stats.CM_PIG, distance);
        } else if (player.vehicle instanceof BoatEntity) {
            player.server_stats$incrementStat(Stats.CM_SAILED, distance);
        } else if (player.vehicle instanceof MinecartEntity) {
            player.server_stats$incrementStat(Stats.CM_MINECART, distance);
        }
    }

    @Redirect(method = "tickNonRidingMovementRelatedStats", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"))
    private void tickNonRidingMovementRelatedStats(PlayerEntity player, Stat stat, int amount) {
        var translation = Map.of(
            net.minecraft.stat.Stats.CM_DIVEN, Stats.CM_DIVEN,
            net.minecraft.stat.Stats.CM_SWUM, Stats.CM_SWUM,
            net.minecraft.stat.Stats.CM_CLIMB, Stats.CM_CLIMB,
            net.minecraft.stat.Stats.CM_WALKED, Stats.CM_WALKED,
            net.minecraft.stat.Stats.CM_FLOWN, Stats.CM_FLOWN
        );

        player.incrementStat(stat, amount); // Increase vanilla stat too
        player.server_stats$incrementStat(translation.get(stat), amount);
    }

    @Inject(method = "applyFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;I)V"))
    private void applyFallDamage(float distance, CallbackInfo callbackInfo) {
        var player = (PlayerEntity) (Object) this;
        player.server_stats$incrementStat(Stats.CM_FALLEN, (int) Math.round((double) distance * 100.0));
    }

    @Inject(method = "onKill", at = @At("HEAD"))
    private void onKill(LivingEntity entity, CallbackInfo callbackInfo) {
        var player = (PlayerEntity) (Object) this;
        player.server_stats$incrementStat(Stats.getEntityKillStat(entity), 1);
    }
}
