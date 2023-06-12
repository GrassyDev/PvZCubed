package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.entity.Entity;
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
		if (!this.hasVehicle()){
			if (this.getAttacking() == null && !(this.getHypno())){
				if (this.CollidesWithPlant(1f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)){
					this.setVelocity(0, -0.3, 0);
					this.setTarget(CollidesWithPlant(1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				}
				else if (this.CollidesWithPlayer(1.5f) != null && !this.CollidesWithPlayer(1.5f).isCreative()){
					this.setTarget(CollidesWithPlayer(1.5f));
					this.setStealthTag(Stealth.FALSE);
				}
			}
		}
		if (this.hasVehicle()){
			this.setFlying(Flying.TRUE);
		}
		else {
			this.setFlying(Flying.FALSE);
		}
	}

	@Override
	public boolean tryAttack(Entity target) {
		if (!this.hasVehicle()) {
			return super.tryAttack(target);
		}
		else {
			return false;
		}
	}

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}
}
