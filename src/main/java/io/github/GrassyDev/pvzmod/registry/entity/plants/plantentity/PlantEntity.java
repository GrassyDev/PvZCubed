package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public abstract class PlantEntity extends GolemEntity {

	public boolean onWater;

	public boolean naturalSpawn;

	protected boolean dryLand;

	@Override
	public boolean canBeLeashedBy(PlayerEntity player) {
		return false;
	}

	protected PlantEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	public void rideLilyPad(LivingEntity livingEntity){
		this.refreshPositionAndAngles(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.bodyYaw, 0.0F);
		this.startRiding(livingEntity);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_ASLEEP, false);
		this.dataTracker.startTracking(DATA_ID_LOWPROF, false);
		this.dataTracker.startTracking(DATA_ID_FIREIMMUNE, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Asleep", this.getIsAsleep());
		tag.putBoolean("lowProf", this.getLowProfile());
		tag.putBoolean("fireImmune", this.getFireImmune());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_ASLEEP, tag.getBoolean("Asleep"));
		this.dataTracker.set(DATA_ID_LOWPROF, tag.getBoolean("lowProf"));
		this.dataTracker.set(DATA_ID_FIREIMMUNE, tag.getBoolean("fireImmune"));
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/


	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (PLANT_LOCATION.get(this.getType()).orElse("normal").equals("ground")){
			this.setLowprof(LowProf.TRUE);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}


	//Low Profile Tag

	protected static final TrackedData<Boolean> DATA_ID_LOWPROF =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum LowProf {
		FALSE(false),
		TRUE(true);

		LowProf(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getLowProfile() {
		return this.dataTracker.get(DATA_ID_LOWPROF);
	}

	public void setLowprof(PlantEntity.LowProf lowprof) {
		this.dataTracker.set(DATA_ID_LOWPROF, lowprof.getId());
	}

	// Fire Immune

	protected static final TrackedData<Boolean> DATA_ID_FIREIMMUNE =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum FireImmune {
		FALSE(false),
		TRUE(true);

		FireImmune(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getFireImmune() {
		return this.dataTracker.get(DATA_ID_FIREIMMUNE);
	}

	public void setFireImmune(PlantEntity.FireImmune fireImmune) {
		this.dataTracker.set(DATA_ID_FIREIMMUNE, fireImmune.getId());
	}


	protected static final TrackedData<Boolean> DATA_ID_ASLEEP =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum IsAsleep {
		FALSE(false),
		TRUE(true);

		IsAsleep(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getIsAsleep() {
		return this.dataTracker.get(DATA_ID_ASLEEP);
	}

	public void setIsAsleep(PlantEntity.IsAsleep asleep) {
		this.dataTracker.set(DATA_ID_ASLEEP, asleep.getId());
	}

	/** ----------------------------------------------------------------------- **/

	public boolean targetStrength;
	public boolean lobbedTarget;
	public boolean targetPoison;
	public boolean targetIce;
	public boolean targetHelmet;
	public boolean targetNoHelmet;
	public boolean targetChilled;
	public boolean illuminate;
	public boolean targetMedium;
	public boolean targetNotCovered;
	public boolean targetNotObstacle;

	protected void targetZombies(Vec3d pos, int yDiff, boolean canHitSnorkel, boolean canHitFlying, boolean canHitStealth){
		List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getPos()).expand(this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE) + 1));
		int zombieStrength = 0;
		int prioritizedStrength = 0;
		Vec3d prevZombiePosition = Vec3d.ZERO;
		boolean isIced;
		boolean isPoisoned;
		boolean prevIced = false;
		boolean hasHelmet = false;
		boolean hasShield = false;
		boolean prevHelmet = false;
		LivingEntity targeted = null;
		LivingEntity prioritizedTarget = null;
		if (!this.world.isClient()) {
			for (LivingEntity hostileEntity : list) {
				if (hostileEntity instanceof Monster && !(hostileEntity instanceof GraveEntity graveEntity && graveEntity.decorative)) {
					if (illuminate && hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isStealth() && hostileEntity.squaredDistanceTo(this) < 36) {
						if (PLANT_TYPE.get(this.getType()).orElse("appease").equals("pepper") && this.isWet()) {

						} else {
							generalPvZombieEntity.setStealthTag(GeneralPvZombieEntity.Stealth.FALSE);
						}
					}
					if (!(hostileEntity instanceof ZombiePropEntity && !(hostileEntity instanceof ZombieObstacleEntity))) {
						if (hostileEntity.squaredDistanceTo(pos) <= Math.pow(this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE), 2) &&
								(hostileEntity.getY() < (this.getY() + yDiff) && hostileEntity.getY() > (this.getY() - yDiff)) && hostileEntity.isAlive()) {
							if (hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
									!(generalPvZombieEntity.getHypno())) {
								int currentStrength = ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0);
								if (!(!ZOMBIE_SIZE.get(hostileEntity.getType()).orElse("medium").equals("medium") && targetMedium) &&
										!(generalPvZombieEntity.isCovered() && targetNotCovered) &&
										!(generalPvZombieEntity instanceof ZombieObstacleEntity && targetNotObstacle)) {
									isIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
									isPoisoned = hostileEntity.hasStatusEffect(PvZCubed.PVZPOISON);
									if (hasHelmet) {
										prevHelmet = true;
										hasHelmet = false;
									}
									if (hasShield) {
										prevHelmet = true;
										hasShield = false;
									}
									for (Entity zombiePropEntity : hostileEntity.getPassengerList()) {
										hasHelmet = zombiePropEntity instanceof ZombiePropEntity && !(zombiePropEntity instanceof ZombieShieldEntity);
										hasShield = zombiePropEntity instanceof ZombieShieldEntity;
									}
									if (currentStrength > 0) {
										if (zombieStrength < currentStrength && this.targetStrength) {
											if (canHitFlying && generalPvZombieEntity.isFlying()) {
												zombieStrength = currentStrength;
												prevZombiePosition = hostileEntity.getPos();
												targeted = hostileEntity;
												prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
											} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													zombieStrength = currentStrength;
													prevZombiePosition = hostileEntity.getPos();
													targeted = hostileEntity;
													prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity) {
													zombieStrength = currentStrength;
													prevZombiePosition = hostileEntity.getPos();
													targeted = hostileEntity;
													prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													}
												}
											} else if (canHitFlying) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													zombieStrength = currentStrength;
													prevZombiePosition = hostileEntity.getPos();
													targeted = hostileEntity;
													prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity) {
													zombieStrength = currentStrength;
													prevZombiePosition = hostileEntity.getPos();
													targeted = hostileEntity;
													prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													}
												}
											}
										} else if ((zombieStrength == currentStrength || !this.targetStrength) &&
												this.squaredDistanceTo(prevZombiePosition) > this.squaredDistanceTo(hostileEntity.getPos())) {
											if (!(targetChilled && prevIced && !isIced)) {
												if (canHitFlying && generalPvZombieEntity.isFlying()) {
													zombieStrength = currentStrength;
													prevZombiePosition = hostileEntity.getPos();
													targeted = hostileEntity;
													prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
												} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
													if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
															!(generalPvZombieEntity instanceof SnorkelEntity)) {
														if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
																generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
															zombieStrength = currentStrength;
															prevZombiePosition = hostileEntity.getPos();
															targeted = hostileEntity;
															prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
														} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
															zombieStrength = currentStrength;
															prevZombiePosition = hostileEntity.getPos();
															targeted = hostileEntity;
															prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
														} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
															zombieStrength = currentStrength;
															prevZombiePosition = hostileEntity.getPos();
															targeted = hostileEntity;
															prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
														}
													}
												} else if (canHitFlying) {
													if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
															!(generalPvZombieEntity instanceof SnorkelEntity)) {
														if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
																generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
															zombieStrength = currentStrength;
															prevZombiePosition = hostileEntity.getPos();
															targeted = hostileEntity;
															prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
														} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
															zombieStrength = currentStrength;
															prevZombiePosition = hostileEntity.getPos();
															targeted = hostileEntity;
															prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
														} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
															zombieStrength = currentStrength;
															prevZombiePosition = hostileEntity.getPos();
															targeted = hostileEntity;
															prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
														}
													}
												}
											}
										}
										if (prioritizedTarget != null && lobbedTarget && this.squaredDistanceTo(prioritizedTarget) > this.squaredDistanceTo(hostileEntity.getPos())) {
											if (lobbedTarget && hasShield) {
												if (canHitFlying && generalPvZombieEntity.isFlying()) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
													if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
														prioritizedTarget = hostileEntity;
													} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
															!(generalPvZombieEntity instanceof SnorkelEntity)) {
														if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
																generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
															prioritizedTarget = hostileEntity;
														} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
															prioritizedTarget = hostileEntity;
														} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
															prioritizedTarget = hostileEntity;
														}
													}
												} else if (canHitFlying) {
													if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
														prioritizedTarget = hostileEntity;
													} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
															!(generalPvZombieEntity instanceof SnorkelEntity)) {
														if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
																generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
															prioritizedTarget = hostileEntity;
														} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
															prioritizedTarget = hostileEntity;
														} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
															prioritizedTarget = hostileEntity;
														}
													}
												}
											}
										} else if (lobbedTarget && hasShield) {
											if (canHitFlying && generalPvZombieEntity.isFlying()) {
												prioritizedTarget = hostileEntity;
											} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													}
												}
											} else if (canHitFlying) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													}
												}
											}
										}
										if (prioritizedTarget != null && targetIce && this.squaredDistanceTo(prioritizedTarget) > this.squaredDistanceTo(hostileEntity.getPos())) {
											if (targetIce && !isIced) {
												if (canHitFlying && generalPvZombieEntity.isFlying()) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
													if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
														prioritizedTarget = hostileEntity;
													} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
															!(generalPvZombieEntity instanceof SnorkelEntity)) {
														if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
																generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
															prioritizedTarget = hostileEntity;
														} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
															prioritizedTarget = hostileEntity;
														} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
															prioritizedTarget = hostileEntity;
														}
													}
												} else if (canHitFlying) {
													if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
														prioritizedTarget = hostileEntity;
													} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
															!(generalPvZombieEntity instanceof SnorkelEntity)) {
														if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
																generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
															prioritizedTarget = hostileEntity;
														} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
															prioritizedTarget = hostileEntity;
														} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
															prioritizedTarget = hostileEntity;
														}
													}
												}
											}
										} else if (targetIce && !isIced) {
											if (canHitFlying && generalPvZombieEntity.isFlying()) {
												prioritizedTarget = hostileEntity;
											} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													}
												}
											} else if (canHitFlying) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													}
												}
											}
										}
										if (targetPoison && !isPoisoned) {
											if (canHitFlying && generalPvZombieEntity.isFlying()) {
												prioritizedTarget = hostileEntity;
											} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													}
												}
											} else if (canHitFlying) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													}
												}
											}
										}
										if (targetNoHelmet && hasHelmet && prioritizedTarget == hostileEntity) {
											prioritizedTarget = null;
										}
										if (targetNoHelmet && !hasHelmet && prioritizedStrength < currentStrength) {
											if (canHitFlying && generalPvZombieEntity.isFlying()) {
												prioritizedStrength = currentStrength;
												prioritizedTarget = hostileEntity;
											} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													}
												}
											} else if (canHitFlying) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													}
												}
											}
										}
										if (targetHelmet && hasHelmet && prioritizedTarget == hostileEntity) {
											prioritizedTarget = null;
										}
										if (targetHelmet && hasHelmet && prioritizedStrength < currentStrength) {
											if (canHitFlying && generalPvZombieEntity.isFlying()) {
												prioritizedStrength = currentStrength;
												prioritizedTarget = hostileEntity;
											} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													}
												}
											} else if (canHitFlying) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													}
												}
											}
										}
									}
								}
								if (targeted == null && prioritizedTarget == null && !(hostileEntity instanceof GeneralPvZombieEntity)) {
									targeted = hostileEntity;
								}
							} else if (!(hostileEntity instanceof GeneralPvZombieEntity) && targeted == null && !this.naturalSpawn) {
								targeted = hostileEntity;
							}
						}
					}
				}
			}
			if (prioritizedTarget != null){
				this.setTarget(prioritizedTarget);
			}
			else {
				this.setTarget(targeted);
			}
		}
	}

	public void tick() {
		if (this.getFireImmune()){
			this.setFireTicks(0);
		}
		super.tick();
		Entity vehicle = this.getVehicle();
		if (vehicle instanceof LilyPadEntity){
			vehicle.setBodyYaw(this.bodyYaw);
		}
	}

	@Override
	public void onDeath(DamageSource source) {
		if (!PLANT_LOCATION.get(this.getType()).orElse("normal").equals("flying")){
			RandomGenerator randomGenerator = this.getRandom();
			BlockState blockState = this.getLandingBlockState();
			for (int i = 0; i < 4; ++i) {
				double d = this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.4F, 0.4F);
				double e = this.getY() + 0.3;
				double f = this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.4F, 0.4F);
				this.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), d, e, f, 0.0, 0.0, 0.0);
			}
		}
		super.onDeath(source);
		super.discard();
	}

	@Override
	protected void dropLoot(DamageSource source, boolean causedByPlayer) {
		if (this.world.getGameRules().getBoolean(PvZCubed.SHOULD_PLANT_DROP)){
			super.dropLoot(source, causedByPlayer);
		}
	}

	public HitResult amphibiousRaycast(double maxDistance) {
		Vec3d vec3d1 = this.getPos();
		Vec3d vec3d2 = new Vec3d(vec3d1.x, vec3d1.y - maxDistance, vec3d1.z);
		return this.world.raycast(new RaycastContext(vec3d1, vec3d2, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, this));
	}

	public static boolean checkPlant(Vec3d pos, ServerWorldAccess world, EntityType<?> type) {
		List<PlantEntity> list = world.getNonSpectatingEntities(PlantEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(pos).expand(20));
		List<PlantEntity> list1 = new ArrayList<>();
		for (PlantEntity plantEntity : list){
			if (plantEntity.getType() != type){
				list1.add(plantEntity);
			}
		}
		return !list1.isEmpty();
	}

	public static class PlantData implements EntityData {
		public final boolean tryLilyPad;

		public PlantData(boolean tryLilyPad) {
			this.tryLilyPad = tryLilyPad;
		}
	}
}
