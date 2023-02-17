package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.doomshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.BombardEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Iterator;
import java.util.List;

public class DoomshroomEntity extends BombardEntity implements IAnimatable {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final TrackedData<Integer> FUSE_SPEED;
    private static final TrackedData<Boolean> CHARGED;
    private static final TrackedData<Boolean> IGNITED;
    private int lastFuseTime;
    private int currentFuseTime;
    private int fuseTime = 40;
    private int explosionRadius = 1;
    public boolean isTired;
	public boolean isAsleep;
	private String controllerName = "doomcontroller";

    public DoomshroomEntity(EntityType<? extends DoomshroomEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FUSE_SPEED, -1);
		this.dataTracker.startTracking(CHARGED, false);
		this.dataTracker.startTracking(IGNITED, false);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if ((Boolean)this.dataTracker.get(CHARGED)) {
			nbt.putBoolean("powered", true);
		}

		nbt.putShort("Fuse", (short)this.fuseTime);
		nbt.putByte("ExplosionRadius", (byte)this.explosionRadius);
		nbt.putBoolean("ignited", this.getIgnited());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(CHARGED, nbt.getBoolean("powered"));
		if (nbt.contains("Fuse", 99)) {
			this.fuseTime = nbt.getShort("Fuse");
		}

		if (nbt.contains("ExplosionRadius", 99)) {
			this.explosionRadius = nbt.getByte("ExplosionRadius");
		}

		if (nbt.getBoolean("ignited")) {
			this.ignite();
		}

	}

	static {
		FUSE_SPEED = DataTracker.registerData(DoomshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);
		CHARGED = DataTracker.registerData(DoomshroomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IGNITED = DataTracker.registerData(DoomshroomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}


	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		RandomGenerator randomGenerator = this.getRandom();
		if (status == 13) {
			this.isTired = true;
		}
		else if (status == 12) {
			this.isTired = false;
		}
		if (status == 6) {
			for(int i = 0; i < 256; ++i) {
				double d = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 1.5);
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 1.5);
				this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 256; ++i) {
				double d = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 1.5);
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 1.5);
				this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 256; ++i) {
				double d = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 1.5);
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 1.5);
				this.world.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 128; ++i) {
				double e = (double)MathHelper.nextBetween(randomGenerator, 0.04F, 0.06F);
				this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getX() +  (double)MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + (double)MathHelper.nextBetween(randomGenerator, 0F, 4.5F),
						this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						0, e, 0);
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double)MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + (double)MathHelper.nextBetween(randomGenerator, 0F, 4.5F),
						this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						0, e, 0);
			}
			for (int i = 0; i < 128; ++i){
				double e = (double)MathHelper.nextBetween(randomGenerator, 0.04F, 0.06F);
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double)MathHelper.nextBetween(randomGenerator, -3F, 3F),
						this.getY() + 5,
						this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -3F, 3F),
						0, e, 0);
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double)MathHelper.nextBetween(randomGenerator, -4F, 4F),
						this.getY() + (this.random.range(3, 7)),
						this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -4F, 4F),
						0, e, 0);
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double)MathHelper.nextBetween(randomGenerator, -5F, 5F),
						this.getY() + 5,
						this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -5F, 5F),
						0, e, 0);
			}
			for(int i = 0; i < 128; ++i) {
				double d = this.random.nextDouble() / 2 * 0.75;
				double f = this.random.nextDouble() / 2 * 0.75;
				double d1 = this.random.nextDouble() / 2 * 0.75;
				double f1 = this.random.nextDouble() / 2 * 0.75;
				double d2 = this.random.nextDouble() / 2 * 0.75;
				double f2 = this.random.nextDouble() / 2 * 0.75;
				double d3 = this.random.nextDouble() / 2 * 0.75;
				double f3 = this.random.nextDouble() / 2 * 0.75;
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						this.getY() + MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						d, 0, f);
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						this.getY() + MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						d1, 0, f1 * -1);
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						this.getY() + MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						d2 * -1, 0, f2 * -1);
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						this.getY() + MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						d3 * -1, 0, f3);
			}
		}
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        int i = this.getFuseSpeed();
        if (this.isTired){
            event.getController().setAnimation(new AnimationBuilder().loop("doomshroom.asleep"));
        }
        else if (i > 0) {
            event.getController().setAnimation(new AnimationBuilder().playOnce("doomshroom.explode"));
        } else {
            event.getController().setAnimation(new AnimationBuilder().loop("doomshroom.idle"));
        }
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(2, new DoomIgniteGoal(this));
		this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0D, false));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof ZombiePropEntity);
		}));
	}

	public boolean tryAttack(Entity target) {
		return true;
	}

	@Environment(EnvType.CLIENT)
	public float getClientFuseTime(float timeDelta) {
		return MathHelper.lerp(timeDelta, (float)this.lastFuseTime, (float)this.currentFuseTime) / (float)(this.fuseTime - 2);
	}

	public int getFuseSpeed() {
		return (Integer)this.dataTracker.get(FUSE_SPEED);
	}

	public void setFuseSpeed(int fuseSpeed) {
		this.dataTracker.set(FUSE_SPEED, fuseSpeed);
	}

	public boolean getIgnited() {
		return (Boolean)this.dataTracker.get(IGNITED);
	}

	public void ignite() {
		this.dataTracker.set(IGNITED, true);
	}
	List<LivingEntity> checkList = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().shrink(0.5, 0, 0));

	private void raycastExplode() {
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(10));
		Iterator var9 = list.iterator();
		while (true) {
			LivingEntity livingEntity;
			do {
				do {
					if (!var9.hasNext()) {
						return;
					}

					livingEntity = (LivingEntity) var9.next();
				} while (livingEntity == this);
			} while (this.squaredDistanceTo(livingEntity) > 81);

			boolean bl = false;

			for (int i = 0; i < 2; ++i) {
				Vec3d vec3d2 = new Vec3d(livingEntity.getX(), livingEntity.getBodyY(0.5 * (double) i), livingEntity.getZ());
				HitResult hitResult = this.world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
				if (hitResult.getType() == HitResult.Type.MISS) {
					bl = true;
					break;
				}
			}

			if (bl) {
				float damage = 180;
				if (livingEntity instanceof Monster && checkList != null && !checkList.contains(livingEntity)) {
					if (damage > livingEntity.getHealth() &&
							!(livingEntity instanceof ZombieShieldEntity) &&
							livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity) {
						float damage2 = damage - livingEntity.getHealth();
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this), damage2);
						checkList.add(livingEntity);
						checkList.add(generalPvZombieEntity);
					} else if (livingEntity instanceof ZombieShieldEntity zombieShieldEntity && zombieShieldEntity.getVehicle() != null){
						String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(zombieShieldEntity.getType()).orElse("flesh");
						if ("paper".equals(zombieMaterial)) {
							zombieShieldEntity.damage(DamageSource.thrownProjectile(this, this), 99999);
						} else {
							zombieShieldEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						}
						checkList.add((LivingEntity) zombieShieldEntity.getVehicle());
						checkList.add(zombieShieldEntity);
					}
					else if (livingEntity.getVehicle() instanceof ZombieShieldEntity zombieShieldEntity) {
						String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(zombieShieldEntity.getType()).orElse("flesh");
						if ("paper".equals(zombieMaterial)) {
							zombieShieldEntity.damage(DamageSource.thrownProjectile(this, this), 99999);
						} else {
							zombieShieldEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						}
						checkList.add(livingEntity);
						checkList.add(zombieShieldEntity);
					}
					else {
						if (livingEntity instanceof ZombiePropEntity zombiePropEntity && livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity) {
							String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(zombiePropEntity.getType()).orElse("flesh");
							if ("paper".equals(zombieMaterial)) {
								livingEntity.damage(DamageSource.thrownProjectile(this, this), 99999);
							} else {
								livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
							}
							checkList.add(livingEntity);
							checkList.add(generalPvZombieEntity);
						}
						else if (!(livingEntity.getFirstPassenger() instanceof ZombiePropEntity) && !checkList.contains(livingEntity)) {
							livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
							checkList.add(livingEntity);
						}
					}
				}
			}
		}
	}

	private void spawnEffectsCloud() {
		AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
		areaEffectCloudEntity.setParticleType(ParticleTypes.SMOKE);
		areaEffectCloudEntity.setRadius(8F);
		areaEffectCloudEntity.setRadiusOnUse(-0.5F);
		areaEffectCloudEntity.setWaitTime(10);
		areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration());
		areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
		this.world.spawnEntity(areaEffectCloudEntity);
		AreaEffectCloudEntity areaEffectCloudEntity2 = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
		areaEffectCloudEntity2.setColor(0x5F316E);
		areaEffectCloudEntity2.setRadius(6F);
		areaEffectCloudEntity2.setRadiusOnUse(-0.5F);
		areaEffectCloudEntity2.setWaitTime(5);
		areaEffectCloudEntity2.setDuration(areaEffectCloudEntity2.getDuration() / 6);
		areaEffectCloudEntity2.setRadiusGrowth(-areaEffectCloudEntity2.getRadius() / (float)areaEffectCloudEntity2.getDuration());
		this.world.spawnEntity(areaEffectCloudEntity2);
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age != 0) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				this.kill();
			}

		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/


	public void tick() {
		super.tick();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		if (this.isAlive() && !this.isAsleep) {
			this.lastFuseTime = this.currentFuseTime;
			if (this.getIgnited()) {
				this.setFuseSpeed(1);
			}

			int i = this.getFuseSpeed();
			if (i > 0 && this.currentFuseTime == 0) {
				this.addStatusEffect((new StatusEffectInstance(StatusEffects.RESISTANCE, 999999999, 999999999)));
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
			}

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
				removeStatusEffect(StatusEffects.RESISTANCE);
			}

			if (this.currentFuseTime >= this.fuseTime) {
				this.currentFuseTime = this.fuseTime;
				this.raycastExplode();
				this.world.sendEntityStatus(this, (byte) 6);
				this.playSound(PvZCubed.DOOMSHROOMEXPLOSIONEVENT, 1F, 1F);
				this.spawnEffectsCloud();
				this.dead = true;
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

    public void tickMovement() {
        super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.clearStatusEffects();
            this.kill();
        }
    }

	protected void mobTick() {
		float f = getLightLevelDependentValue();
		if (f > 0.5f) {
			isAsleep = true;
			this.world.sendEntityStatus(this, (byte) 13);
			this.clearGoalsAndTasks();
			removeStatusEffect(StatusEffects.RESISTANCE);
		}
		else {
			this.world.sendEntityStatus(this, (byte) 12);
			isAsleep = false;
			this.initGoals();
		}
		super.mobTick();
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.DOOMSHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createDoomshroomAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 5D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 180);
	}

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZCubed.ZOMBIEBITEEVENT;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return PvZCubed.PLANTPLANTEDEVENT;
	}

	public boolean hurtByWater() {
		return false;
	}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
	}

	public boolean startRiding(Entity entity, boolean force) {
		return super.startRiding(entity, force);
	}

	public void stopRiding() {
		super.stopRiding();
		this.prevBodyYaw = 0.0F;
		this.bodyYaw = 0.0F;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	public boolean handleAttack(Entity attacker) {
		if (attacker instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) attacker;
			this.clearStatusEffects();
			return this.damage(DamageSource.player(playerEntity), 9999.0F);
		} else {
			return false;
		}
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.kill();
		}
		this.playBlockFallSound();
		return true;
	}
}
