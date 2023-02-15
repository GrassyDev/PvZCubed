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

	public float colliderOffset = 0.4F;

	public EntityType<? extends HostileEntity> entityBox = PvZEntity.BROWNCOAT;

	@Override
	public boolean canBeRiddenInWater() {
		return true;
	}

	public PlantEntity CollidesWithPlant(){
		Vec3d vec3d = new Vec3d((double)colliderOffset, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<PlantEntity> list = world.getNonSpectatingEntities(PlantEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
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
		List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
		if (!list.isEmpty()){
			return list.get(0);
		}
		else {
			return null;
		}
	}

	public void tick() {
		super.tick();
	}
}
