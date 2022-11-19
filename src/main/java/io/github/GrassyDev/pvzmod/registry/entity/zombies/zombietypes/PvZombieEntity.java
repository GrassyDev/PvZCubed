package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import com.google.common.collect.ImmutableList;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.peashooter.PeashooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.polevaulting.PoleVaultingEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import net.minecraft.entity.ai.brain.task.WardenLookAtEntityTask;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.warden.WardenEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public abstract class PvZombieEntity extends HostileEntity {
	private int despawnDucky;
	private int spawnDucky;

	/** For Hypnotized Zombies **/
	protected PvZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	public void tick() {
		super.tick();
		Entity vehicle = this.getVehicle();
		if (this.hasStatusEffect(PvZCubed.ICE) && vehicle != null){
			((DuckyTubeEntity) vehicle).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 60, 1)));
			((DuckyTubeEntity) vehicle).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 60, 1)));
		}
		else if (!this.hasStatusEffect(PvZCubed.ICE) && vehicle != null){
			((DuckyTubeEntity) vehicle).removeStatusEffect(PvZCubed.ICE);
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN) && vehicle instanceof DuckyTubeEntity && !(this instanceof GargantuarEntity)){
			vehicle.discard();
			this.discard();
		}
		else if (this.hasStatusEffect(PvZCubed.FROZEN) && vehicle instanceof DuckyTubeEntity && this instanceof GargantuarEntity){
			((DuckyTubeEntity) vehicle).addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, 200, 5)));
			((DuckyTubeEntity) vehicle).addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, 200, 5)));
		}
		if (!this.hasStatusEffect(PvZCubed.FROZEN) && vehicle instanceof DuckyTubeEntity){
			((DuckyTubeEntity) vehicle).removeStatusEffect(PvZCubed.FROZEN);
		}
		if (this.isInsideWaterOrBubbleColumn()){
			this.despawnDucky = 0;
		}
		if (this.getRandom().nextFloat() < 0.8F && (this.isTouchingWater() || this.isInLava())) {
			this.getJumpControl().setActive();
			this.setSwimming(true);
		}
		else if (!this.isTouchingWater() || !this.isInLava()){
			this.setSwimming(false);
			this.spawnDucky = 0;
		}
		if (!this.isSubmergedInWater() && !this.hasVehicle()){
			if (this.isTouchingWater() && ++this.spawnDucky >= 1) {
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

	@Override
	public void dismountVehicle() {
		super.dismountVehicle();
	}

	@Override
	protected void updateDespawnCounter() {
	}
}
