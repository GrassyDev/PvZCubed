package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
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
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.*;
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

public class IcebergLettuceEntity extends PlantEntity implements IAnimatable {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final TrackedData<Integer> FUSE_SPEED;
    private static final TrackedData<Boolean> CHARGED;
    private static final TrackedData<Boolean> IGNITED;
    private int currentFuseTime;
    private int fuseTime = 16;
    private int explosionRadius = 1;
	private String controllerName = "bombcontroller";

    public IcebergLettuceEntity(EntityType<? extends IcebergLettuceEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FUSE_SPEED, -1);
		this.dataTracker.startTracking(CHARGED, false);
		this.dataTracker.startTracking(IGNITED, false);
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, false);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if ((Boolean)this.dataTracker.get(CHARGED)) {
			nbt.putBoolean("powered", true);
		}

		nbt.putShort("Fuse", (short)this.fuseTime);
		nbt.putByte("ExplosionRadius", (byte)this.explosionRadius);
		nbt.putBoolean("ignited", this.getIgnited());
		nbt.putBoolean("Permanent", this.getPuffshroomPermanency());
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
		this.dataTracker.set(DATA_ID_TYPE_COUNT, nbt.getBoolean("Permanent"));

	}

	static {
		FUSE_SPEED = DataTracker.registerData(IcebergLettuceEntity.class, TrackedDataHandlerRegistry.INTEGER);
		CHARGED = DataTracker.registerData(IcebergLettuceEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IGNITED = DataTracker.registerData(IcebergLettuceEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 6) {
			for(int i = 0; i < 16; ++i) {
				double d = this.random.nextDouble() / 6 * (this.random.range(-1, 1) * 1.5);
				double e = this.random.nextDouble() / 6 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 6 * (this.random.range(-1, 1) * 1.5);
				this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 16; ++i) {
				double d = this.random.nextDouble() / 6 * (this.random.range(-1, 1) * 1.5);
				double e = this.random.nextDouble() / 6 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 6 * (this.random.range(-1, 1) * 1.5);
				this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 16; ++i) {
				double d = this.random.nextDouble() / 6 * (this.random.range(-1, 1) * 1.5);
				double e = this.random.nextDouble() / 6 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 6 * (this.random.range(-1, 1) * 1.5);
				this.world.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(IcebergLettuceEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum PuffPermanency {
		DEFAULT(false),
		PERMANENT(true);

		PuffPermanency(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	private Boolean getPuffshroomPermanency() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setPuffshroomPermanency(IcebergLettuceEntity.PuffPermanency puffshroomPermanency) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, puffshroomPermanency.getId());
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
        if (i > 0) {
            event.getController().setAnimation(new AnimationBuilder().playOnce("small.seedling.bomb"));
        } else {
            event.getController().setAnimation(new AnimationBuilder().loop("small.idle"));
        }
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(2, new IcebergIgniteGoal(this));
		this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0D, false));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity));
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity);
		}));
	}

	public boolean tryAttack(Entity target) {
		return true;
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
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(3));
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
			} while (this.squaredDistanceTo(livingEntity) > 4);

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
				float damage = 4;
				if (((livingEntity instanceof Monster &&
						!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()) &&
						!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
								&& (generalPvZombieEntity.getHypno()))) && checkList != null && !checkList.contains(livingEntity))) {ZombiePropEntity zombiePropEntity2 = null;
					for (Entity entity1 : livingEntity.getPassengerList()) {
						if (entity1 instanceof ZombiePropEntity zpe) {
							zombiePropEntity2 = zpe;
						}
					}
					if (damage > livingEntity.getHealth() &&
							!(livingEntity instanceof ZombieShieldEntity) &&
							livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - livingEntity.getHealth();
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this), damage2);
						checkList.add(livingEntity);
						checkList.add(generalPvZombieEntity);
					} else if (livingEntity instanceof ZombieShieldEntity zombieShieldEntity && zombieShieldEntity.getVehicle() != null){
						zombieShieldEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						checkList.add((LivingEntity) zombieShieldEntity.getVehicle());
						checkList.add(zombieShieldEntity);
					}
					else if (livingEntity.getVehicle() instanceof ZombieShieldEntity zombieShieldEntity) {
						zombieShieldEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						checkList.add(livingEntity);
						checkList.add(zombieShieldEntity);
					}
					else {
						if (livingEntity instanceof ZombiePropEntity zombiePropEntity && livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
							livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
							checkList.add(livingEntity);
							checkList.add(generalPvZombieEntity);
						}
						else if (zombiePropEntity2 == null && !checkList.contains(livingEntity)) {
							livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
							checkList.add(livingEntity);
						}
					}
					if (!(livingEntity instanceof ZombieShieldEntity) && !(zombiePropEntity2 instanceof ZombieShieldEntity)) {
						livingEntity.removeStatusEffect(PvZCubed.FROZEN);
						livingEntity.removeStatusEffect(PvZCubed.ICE);
						if (livingEntity.hasStatusEffect(PvZCubed.WARM) || livingEntity.isOnFire()) {
							livingEntity.removeStatusEffect(PvZCubed.WARM);
							livingEntity.extinguish();
						}
						livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, 200, 5)));
					}
				}
			}
		}
	}

	private void spawnEffectsCloud() {
		AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
		areaEffectCloudEntity.setParticleType(ParticleTypes.SNOWFLAKE);
		areaEffectCloudEntity.setRadius(1.5F);
		areaEffectCloudEntity.setRadiusOnUse(-0.5F);
		areaEffectCloudEntity.setWaitTime(1);
		areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 40);
		areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
		this.world.spawnEntity(areaEffectCloudEntity);
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
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.ICEBERGLETTUCE_SEED_PACKET);
				}
				this.kill();
			}

		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/


	public void tick() {
		super.tick();
		LivingEntity target = this.getTarget();
		if (target instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()){
			this.setTarget(null);
		}
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		if (this.isAlive()) {
			if (this.getIgnited()) {
				this.setFuseSpeed(1);
			}

			int i = this.getFuseSpeed();
			if (i > 0 && this.currentFuseTime == 0) {
				this.addStatusEffect((new StatusEffectInstance(StatusEffects.RESISTANCE, 999999999, 999999999)));
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
				this.playSound(PvZCubed.ICEBERGEXPLOSIONEVENT, 1F, 1F);
				this.spawnEffectsCloud();
				this.dead = true;
				this.remove(RemovalReason.DISCARDED);
			}
		}
		if (this.age >= 600 && !this.getPuffshroomPermanency()) {
			this.discard();
		}
		float time = 200 / this.world.getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
		if (this.age <= time && !this.getPuffshroomPermanency() && !this.hasStatusEffect(StatusEffects.GLOWING)) {
			this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, (int) Math.floor(time), 1)));
		}
	}


	public void tickMovement() {
        super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.clearStatusEffects();
            this.kill();
        }
    }


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.ICEBERGLETTUCE_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createIcebergLettuceAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 2D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4);
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
		return null	;
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
		}
		else {
			this.raycastExplode();
			this.removeStatusEffect(StatusEffects.RESISTANCE);
			this.world.sendEntityStatus(this, (byte) 6);
			this.playSound(PvZCubed.ICEBERGEXPLOSIONEVENT, 1F, 1F);
			this.spawnEffectsCloud();
			this.dead = true;
			this.remove(RemovalReason.DISCARDED);
			return true;
		}
	}

	@Override
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		LivingEntity attacker = (LivingEntity) source.getAttacker();
		if (attacker instanceof GargantuarEntity && attacker.isAlive()){
			attacker.damage(DamageSource.thrownProjectile(this, this), 4);
			attacker.removeStatusEffect(PvZCubed.FROZEN);
			attacker.removeStatusEffect(PvZCubed.ICE);
			if (attacker.hasStatusEffect(PvZCubed.WARM) || attacker.isOnFire()) {
				attacker.removeStatusEffect(PvZCubed.WARM);
				attacker.extinguish();
			}
			attacker.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, 200, 5)));
			attacker.removeStatusEffect(PvZCubed.FROZEN);
			attacker.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, 200, 5)));
		}
		if (!(attacker instanceof PlayerEntity)) {
			this.raycastExplode();
			this.removeStatusEffect(StatusEffects.RESISTANCE);
			this.world.sendEntityStatus(this, (byte) 80);
			this.playSound(PvZCubed.ICEBERGEXPLOSIONEVENT, 1F, 1F);
			this.spawnEffectsCloud();
			this.dead = true;
			this.remove(RemovalReason.DISCARDED);
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


	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canIcebergLettuceSpawn(EntityType<? extends IcebergLettuceEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		return world.getAmbientDarkness() < 4 &&
				world.getLightLevel(LightType.SKY, pos) > 10;
	}
}
