package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public abstract class ZombieObstacleEntity extends ZombieShieldEntity{
	protected ZombieObstacleEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.noClip = false;
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.hasVehicle() && this.getHypno()){
			this.setHypno(IsHypno.FALSE);
		}
	}
}
