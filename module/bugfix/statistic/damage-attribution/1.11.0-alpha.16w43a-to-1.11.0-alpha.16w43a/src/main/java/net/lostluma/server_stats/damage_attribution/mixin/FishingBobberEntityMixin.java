package net.lostluma.server_stats.damage_attribution.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.FishingBobberEntity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends Entity {
	public FishingBobberEntityMixin(World world) {
		super(world);
	}

	@Shadow
	public Entity caughtEntity;

	@Shadow
	private PlayerEntity f_0358231;

	@Inject(method = "m_6658044", at = @At("RETURN"))
	public void setDamageSource(CallbackInfo ci) {
		if (this.caughtEntity instanceof LivingEntity) {
			LivingEntity caughtEntity = (LivingEntity) this.caughtEntity;

			caughtEntity.setAttacker(this.f_0358231);
		}
	}
}
