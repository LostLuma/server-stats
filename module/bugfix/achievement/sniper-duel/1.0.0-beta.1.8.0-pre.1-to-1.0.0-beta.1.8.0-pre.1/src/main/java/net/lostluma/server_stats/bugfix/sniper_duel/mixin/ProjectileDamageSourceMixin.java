package net.lostluma.server_stats.bugfix.sniper_duel.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.living.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileDamageSource.class)
public class ProjectileDamageSourceMixin extends EntityDamageSource {
	@Unique
	private Entity projectile;

	public ProjectileDamageSourceMixin(String name, Entity entity) {
		super(name, entity);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(String name, Entity projectile, LivingEntity shooter, CallbackInfo callbackInfo) {
		this.projectile = projectile;
	}

	@Override
	public Entity getSource() {
		return this.projectile;
	}
}
