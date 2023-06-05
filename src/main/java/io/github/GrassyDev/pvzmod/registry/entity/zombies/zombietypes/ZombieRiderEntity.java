package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;

public abstract class ZombieRiderEntity extends ZombieObstacleEntity{

	public boolean beingEaten = false;
	protected ZombieRiderEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.noClip = false;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.hasVehicle()){
			this.setFlying(Flying.TRUE);
		}
		else {
			this.setFlying(Flying.FALSE);
		}
	}


	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}
}
