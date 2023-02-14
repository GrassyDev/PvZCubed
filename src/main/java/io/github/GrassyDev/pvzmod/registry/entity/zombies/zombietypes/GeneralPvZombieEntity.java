package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.PlantEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public abstract class GeneralPvZombieEntity extends HostileEntity {
	protected GeneralPvZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}
	public int despawnDucky;
	public int spawnDucky;

	public float colliderOffset = 0.4F;

	public PlantEntity CollidesWithPlant(){
		Vec3d vec3d = new Vec3d((double)colliderOffset, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<PlantEntity> list = world.getNonSpectatingEntities(PlantEntity.class, PvZEntity.BROWNCOAT.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
		if (!list.isEmpty()){
			if (list.get(0) instanceof LilyPadEntity lilyPadEntity){
				if (!(lilyPadEntity.hasPassengers())) {
					return lilyPadEntity;
				} else {
					return (PlantEntity) lilyPadEntity.getFirstPassenger();
				}
			}
			else {
				return list.get(0);
			}
		}
		else {
			return null;
		}
	}
	public PlayerEntity CollidesWithPlayer(){
		Vec3d vec3d = new Vec3d((double)colliderOffset, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, PvZEntity.BROWNCOAT.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
		if (!list.isEmpty()){
			return list.get(0);
		}
		else {
			return null;
		}
	}

	public void tick() {
		super.tick();
		/**Entity vehicle = this.getVehicle();
		if(this.isInsideWaterOrBubbleColumn() && this.getFirstPassenger() == null) {
			DuckyTubeEntity duckyTube = new DuckyTubeEntity(PvZEntity.DUCKYTUBE, this.world);
			duckyTube.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			duckyTube.startRiding(this);
			world.spawnEntity(duckyTube);
		}**/
		/**if (!(this instanceof ZombiePropEntity)) {
			if (this.hasStatusEffect(PvZCubed.ICE) && vehicle != null) {
				if (vehicle instanceof DuckyTubeEntity duckyTube) {
					duckyTube.addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, this.getStatusEffect(PvZCubed.ICE).getDuration(), 1)));
				}
			} else if (!this.hasStatusEffect(PvZCubed.ICE) && vehicle != null) {
				if (vehicle instanceof DuckyTubeEntity duckyTube) {
					duckyTube.removeStatusEffect(PvZCubed.ICE);
				}
			}
			if (this.hasStatusEffect(PvZCubed.FROZEN) && vehicle instanceof DuckyTubeEntity && !(this instanceof GargantuarEntity)) {
				vehicle.discard();
				this.discard();
			} else if (this.hasStatusEffect(PvZCubed.FROZEN) && vehicle instanceof DuckyTubeEntity duckyTube && this instanceof GargantuarEntity) {
				duckyTube.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, this.getStatusEffect(PvZCubed.FROZEN).getDuration(), 5)));
				duckyTube.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, this.getStatusEffect(PvZCubed.FROZEN).getDuration(), 5)));
			}
			if (!this.hasStatusEffect(PvZCubed.FROZEN) && vehicle instanceof DuckyTubeEntity duckyTube) {
				duckyTube.removeStatusEffect(PvZCubed.FROZEN);
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
					if (vehicle != null && vehicle.age > 9 && vehicle instanceof DuckyTubeEntity) {
						dismountVehicle();
					}
				}
				if (this.despawnDucky > 10) {
					this.despawnDucky = 0;
				}
			}
			if (vehicle instanceof DuckyTubeEntity duckyTube) {
				duckyTube.setTarget(this.getTarget());
			}*
		}**/
	}
}
