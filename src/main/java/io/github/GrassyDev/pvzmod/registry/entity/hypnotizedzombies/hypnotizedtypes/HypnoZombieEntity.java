package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public abstract class HypnoZombieEntity extends GolemEntity {
	private int despawnDucky;
	private int spawnDucky;

	/**
	 * For Hypnotized Zombies
	 **/
	protected HypnoZombieEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	public EntityGroup getGroup() {
		return EntityGroup.DEFAULT;
	}

	public void tick() {
		super.tick();
		Entity vehicle = this.getVehicle();
		if (this.hasStatusEffect(PvZCubed.ICE) && vehicle != null) {
			((DuckyTubeEntity) vehicle).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 60, 1)));
			((DuckyTubeEntity) vehicle).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 60, 1)));
		} else if (!this.hasStatusEffect(PvZCubed.ICE) && vehicle != null) {
			((DuckyTubeEntity) vehicle).removeStatusEffect(PvZCubed.ICE);
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN) && this.isInsideWaterOrBubbleColumn()) {
			this.discard();
		}

		if (this.getRandom().nextFloat() < 0.8F && (this.isTouchingWater() || this.isInLava())) {
			this.getJumpControl().setActive();
			this.setSwimming(true);
		} else if (!this.isTouchingWater() || !this.isInLava()) {
			this.setSwimming(false);
		}
		if (this.isInsideWaterOrBubbleColumn()) {
			this.despawnDucky = 0;
		}
		if (!this.isSubmergedInWater() && !this.hasVehicle()) {
			if (this.isTouchingWater()) {
				++this.spawnDucky;
				if (this.spawnDucky == 1) {
					if (world instanceof ServerWorld) {
						DuckyTubeEntity duckyTube = new DuckyTubeEntity(PvZEntity.DUCKYTUBE, this.world);
						duckyTube.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
						world.spawnEntity(duckyTube);
						this.startRiding(duckyTube);
						world.playSound(null, duckyTube.getX(), duckyTube.getY(), duckyTube.getZ(), SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.BLOCKS, 0.33f, 0.8F);
					}
				}
			}
		}
		if (!this.isInsideWaterOrBubbleColumn()) {
			++this.despawnDucky;
			if (this.despawnDucky == 10) {
				this.spawnDucky = 0;
			}
			if (this.despawnDucky > 10) {
				this.despawnDucky = 0;
			}
		}
	}

	protected class AttackGoal extends MeleeAttackGoal {

		public AttackGoal(PathAwareEntity pvzombie) {
			super(HypnoZombieEntity.this, 1.0, true);
		}

		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			float f = HypnoZombieEntity.this.getWidth() - 0.1F;
			return (double) (f * 4.0F * f * 4.0F + entity.getWidth());
		}
	}
}
