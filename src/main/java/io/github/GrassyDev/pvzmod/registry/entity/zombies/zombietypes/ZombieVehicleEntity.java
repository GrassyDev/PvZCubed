package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class ZombieVehicleEntity extends MachinePvZombieEntity{

	protected ZombieVehicleEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);

	}

	@Override
	public void tick() {
		GeneralPvZombieEntity zombiePassenger = null;
		for (Entity entity : this.getPassengerList()){
			if (entity instanceof GeneralPvZombieEntity){
				zombiePassenger = (GeneralPvZombieEntity) entity;
			}
		}
		if (zombiePassenger == null){
			Vec3d vec3d2 = new Vec3d((double) 0.08, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			this.setVelocity(vec3d2);
		}
		else {
			if (zombiePassenger.getTarget() != null) {
				this.setTarget(zombiePassenger.getTarget());
			}
			if (zombiePassenger.getHypno()) {
				this.setHypno(IsHypno.TRUE);
			}
			else {
				this.setHypno(IsHypno.FALSE);
			}
		}
		super.tick();
	}


	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}
}
