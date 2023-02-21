package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Objects;

public abstract class ZombiePropEntity extends GeneralPvZombieEntity implements Monster {

	/** For Hypnotized Zombies **/
	protected ZombiePropEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.noClip = true;
	}

	public EntityGroup getGroup() {
		return EntityGroup.DEFAULT;
	}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return (this.getHypno()) ? PvZCubed.ZOMBIEBITEEVENT : PvZCubed.SILENCEVENET;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return PvZCubed.SILENCEVENET;
	}

	public void tick() {
		super.tick();
		LivingEntity vehicle = (LivingEntity) this.getVehicle();
		if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE && !(this instanceof ZombieShieldEntity) &&
		vehicle instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())){
			this.setHypno(IsHypno.TRUE);
			vehicle.damage(PvZCubed.HYPNO_DAMAGE, 0);
		}
		if (vehicle instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()){
			this.setHypno(IsHypno.TRUE);
		}
		if (vehicle != null && this.getCustomName() != vehicle.getCustomName()){
			vehicle.setCustomName(this.getCustomName());
			((HostileEntity) vehicle).setPersistent();
		}
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
			this.removeStatusEffect(PvZCubed.PVZPOISON);
		}
		if (vehicle != null && vehicle.isOnFire() && !(this instanceof ZombieShieldEntity)){
			vehicle.setOnFire(false);
		}
		if (this.getHealth() <= 0 && this.isOnFire() && vehicle != null && !(this instanceof ZombieShieldEntity)){
			vehicle.setOnFireFor(this.getFireTicks());
		}
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (this.getHypno() && this.getVehicle() instanceof GargantuarEntity gargantuarEntity){
			player.startRiding(gargantuarEntity, true);
			return ActionResult.success(this.world.isClient);
		}
		else {
			return ActionResult.FAIL;
		}
	}
}
