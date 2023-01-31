package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

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
			if (vehicle instanceof DuckyTubeEntity duckyTube) {
				duckyTube.addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 60, 1)));
				duckyTube.addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 60, 1)));
			}
		}
		else if (!this.hasStatusEffect(PvZCubed.ICE) && vehicle != null){
			if (vehicle instanceof DuckyTubeEntity duckyTube) {
				duckyTube.removeStatusEffect(PvZCubed.ICE);
			}
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN) && vehicle instanceof DuckyTubeEntity && !(this instanceof GargantuarEntity)){
			vehicle.discard();
			this.discard();
		}
		else if (this.hasStatusEffect(PvZCubed.FROZEN) && vehicle instanceof DuckyTubeEntity duckyTube && this instanceof GargantuarEntity){
			duckyTube.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, 200, 5)));
			duckyTube.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, 200, 5)));
		}
		if (!this.hasStatusEffect(PvZCubed.FROZEN) && vehicle instanceof DuckyTubeEntity duckyTube){
			duckyTube.removeStatusEffect(PvZCubed.FROZEN);
		}
		if (this.getRandom().nextFloat() < 0.8F && (this.isTouchingWater() || this.isInLava())) {
			this.getJumpControl().setActive();
			this.setSwimming(true);
		}
		else if (!this.isTouchingWater() || !this.isInLava()){
			this.setSwimming(false);
		}
		if (this.isInsideWaterOrBubbleColumn()){
			this.despawnDucky = 0;
		}
		if (!this.isSubmergedInWater() && !this.hasVehicle()){
			if (this.isTouchingWater()) {
				++this.spawnDucky;
				if (this.spawnDucky == 1){
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
		if (!this.isInsideWaterOrBubbleColumn()){
			++this.despawnDucky;
			if (this.despawnDucky == 10) {
				this.spawnDucky = 0;
				if (vehicle != null && vehicle.age > 9 && vehicle instanceof DuckyTubeEntity) {
					dismountVehicle();
				}
			}
			if (this.despawnDucky > 10){
				this.despawnDucky = 0;
			}
		}
		if (vehicle instanceof DuckyTubeEntity duckyTube){
			duckyTube.setTarget(this.getTarget());
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
