package io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.PvZombieEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public abstract class HypnoZombieEntity extends GolemEntity {
	private int despawnDucky;
	/** For Hypnotized Zombies **/
	protected HypnoZombieEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	public EntityGroup getGroup() {
		return EntityGroup.DEFAULT;
	}

	public void tick() {
		super.tick();
		Entity vehicle = this.getVehicle();
		if (this.isInsideWaterOrBubbleColumn()){
			this.despawnDucky = 0;
		}
		if (this.getRandom().nextFloat() < 0.8F && (this.isTouchingWater() || this.isInLava())) {
			this.getJumpControl().setActive();
			this.setSwimming(true);
		}
		else if (!this.isTouchingWater() || !this.isInLava()){
			this.setSwimming(false);
		}
		if (!this.isSubmergedInWater() && !this.hasVehicle()){
			if (this.isTouchingWater()) {
				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					DuckyTubeEntity duckyTube = new DuckyTubeEntity(PvZEntity.DUCKYTUBE, this.world);
					duckyTube.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
					world.spawnEntity(duckyTube);
					this.startRiding(duckyTube);
					world.playSound((PlayerEntity) null, duckyTube.getX(), duckyTube.getY(), duckyTube.getZ(), SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.BLOCKS, 0.33f, 0.8F);
				}
			}
		}
		if (!this.isInsideWaterOrBubbleColumn() && vehicle != null && ++this.despawnDucky > 10){
			if (vehicle.isOnGround()){
				vehicle.discard();
			}
		}
		if (vehicle instanceof DuckyTubeEntity){
			((DuckyTubeEntity) vehicle).setTarget(this.getTarget());
		}
	}

	protected class AttackGoal extends MeleeAttackGoal {
		public AttackGoal() {
			super(HypnoZombieEntity.this, 1.0, true);
		}

		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			float f = HypnoZombieEntity.this.getWidth() - 0.1F;
			return (double)(f * 4.0F * f * 4.0F + entity.getWidth());
		}
	}
}
