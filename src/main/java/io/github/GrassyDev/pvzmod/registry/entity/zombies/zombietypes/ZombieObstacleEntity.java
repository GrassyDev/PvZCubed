package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;

import java.util.List;

public abstract class ZombieObstacleEntity extends ZombieShieldEntity{

	public boolean beingEaten = false;
	protected ZombieObstacleEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.noClip = false;
	}

	@Override
	public void tick() {
		super.tick();
		List<GravebusterEntity> list = world.getNonSpectatingEntities(GravebusterEntity.class, entityBox.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()));
		this.beingEaten = !list.isEmpty();
		if (!this.hasVehicle() && this.getHypno()){
			this.setHypno(IsHypno.FALSE);
		}
	}


	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}
}
