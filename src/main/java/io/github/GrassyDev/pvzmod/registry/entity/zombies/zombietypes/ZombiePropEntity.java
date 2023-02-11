package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.world.World;

import java.util.Objects;

public abstract class ZombiePropEntity extends GeneralPvZombieEntity implements Monster {

	/** For Hypnotized Zombies **/
	protected ZombiePropEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	public EntityGroup getGroup() {
		return EntityGroup.DEFAULT;
	}

	public void tick() {
		super.tick();
		LivingEntity vehicle = (LivingEntity) this.getVehicle();
		if (this.hasStatusEffect(PvZCubed.ICE) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, Objects.requireNonNull(this.getStatusEffect(PvZCubed.ICE)).getDuration(), 1)));
		}
		else if (this.hasStatusEffect(PvZCubed.ICE) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.ICE) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.ICE);
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, Objects.requireNonNull(this.getStatusEffect(PvZCubed.FROZEN)).getDuration(), 1)));
		}
		else if (this.hasStatusEffect(PvZCubed.FROZEN) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.FROZEN) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.FROZEN);
		}
		if (this.hasStatusEffect(PvZCubed.WARM) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, Objects.requireNonNull(this.getStatusEffect(PvZCubed.WARM)).getDuration(), 1)));
		}
		else if (this.hasStatusEffect(PvZCubed.WARM) && vehicle != null && !vehicle.hasStatusEffect(PvZCubed.WARM) && !(this instanceof ZombieShieldEntity)){
			this.removeStatusEffect(PvZCubed.WARM);
		}
		if (this.hasStatusEffect(PvZCubed.PVZPOISON) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.PVZPOISON, Objects.requireNonNull(this.getStatusEffect(PvZCubed.PVZPOISON)).getDuration(), 1)));
		}
		else if (!this.hasStatusEffect(PvZCubed.PVZPOISON) && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.removeStatusEffect(PvZCubed.PVZPOISON);
		}
	}
}
