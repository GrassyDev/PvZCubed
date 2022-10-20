package io.github.GrassyDev.pvzmod.registry.plants.plantentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.FumeEntity;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.ShootingFlamingPeaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;

public class FumeshroomEntity extends GolemEntity implements IAnimatable, RangedAttackMob {
	public AnimationFactory factory = new AnimationFactory(this);
	private static final TrackedData<Byte> SNOW_GOLEM_FLAGS;
	protected static final TrackedData<Optional<BlockPos>> ATTACHED_BLOCK;
	private String controllerName = "puffcontroller";
	public boolean isAsleep;
	public boolean isTired;
	public int healingTime;
	private static final TrackedData<Integer> FUMESHROOM_TARGET_ID;

	private int beamTicks;

	private LivingEntity cachedBeamTarget;

	public FumeshroomEntity(EntityType<? extends FumeshroomEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
		this.healingTime = 6000;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (this.isTired) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("fumeshroom.asleep", true));
		} else {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("fumeshroom.idle", true));
		}
		return PlayState.CONTINUE;
	}

	public void calculateDimensions() {
		double d = this.getX();
		double e = this.getY();
		double f = this.getZ();
		super.calculateDimensions();
		this.updatePosition(d, e, f);
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		if (tag.contains("APX")) {
			int i = tag.getInt("APX");
			int j = tag.getInt("APY");
			int k = tag.getInt("APZ");
			this.dataTracker.set(ATTACHED_BLOCK, Optional.of(new BlockPos(i, j, k)));
		} else {
			this.dataTracker.set(ATTACHED_BLOCK, Optional.empty());
		}
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		BlockPos blockPos = this.getAttachedBlock();
		if (blockPos != null) {
			tag.putInt("APX", blockPos.getX());
			tag.putInt("APY", blockPos.getY());
			tag.putInt("APZ", blockPos.getZ());
		}
	}

	public void tick() {
		super.tick();
		BlockPos blockPos = (BlockPos) ((Optional) this.dataTracker.get(ATTACHED_BLOCK)).orElse((Object) null);
		if (blockPos == null && !this.world.isClient) {
			blockPos = this.getBlockPos();
			this.dataTracker.set(ATTACHED_BLOCK, Optional.of(blockPos));
		}

		if (blockPos != null) {
			this.setPosition((double) blockPos.getX() + 0.5D, (double) blockPos.getY(), (double) blockPos.getZ() + 0.5D);
		}
	}

	public void updatePosition(double x, double y, double z) {
		super.updatePosition(x, y, z);
		if (this.dataTracker != null && this.age != 0) {
			Optional<BlockPos> optional = (Optional) this.dataTracker.get(ATTACHED_BLOCK);
			Optional<BlockPos> optional2 = Optional.of(new BlockPos(x, y, z));
			if (!optional2.equals(optional)) {
				this.dataTracker.set(ATTACHED_BLOCK, optional2);
				this.velocityDirty = true;
			}

		}
	}

	public void onTrackedDataSet(TrackedData<?> data) {
		if (ATTACHED_BLOCK.equals(data) && this.world.isClient && !this.hasVehicle()) {
			BlockPos blockPos = this.getAttachedBlock();
			if (blockPos != null) {
				this.setPosition((double) blockPos.getX() + 0.5D, (double) blockPos.getY(), (double) blockPos.getZ() + 0.5D);
			}
		}
		if (FUMESHROOM_TARGET_ID.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget = null;
		}

		super.onTrackedDataSet(data);
	}

	private void setFumeTarget(int entityId) {
		this.dataTracker.set(FUMESHROOM_TARGET_ID, entityId);
	}

	public boolean hasFumeTarget() {
		return (Integer) this.dataTracker.get(FUMESHROOM_TARGET_ID) != 0;
	}


	@Nullable
	public BlockPos getAttachedBlock() {
		return (BlockPos) ((Optional) this.dataTracker.get(ATTACHED_BLOCK)).orElse((Object) null);
	}

	static {
		ATTACHED_BLOCK = DataTracker.registerData(FumeshroomEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
		FUMESHROOM_TARGET_ID = DataTracker.registerData(HypnoshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	public void move(MovementType type, Vec3d movement) {
		if (type == MovementType.SHULKER_BOX) {
			this.damage(DamageSource.GENERIC, 9999);
		} else {
			super.move(type, movement);
		}

	}

	public boolean collides() {
		return true;
	}

	public boolean handleAttack(Entity attacker) {
		if (attacker instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) attacker;
			return this.damage(DamageSource.player(playerEntity), 9999.0F);
		} else {
			return false;
		}
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.damage(DamageSource.GENERIC, 9999);
		}
		this.playBlockFallSound();
		return true;
	}

	protected boolean canClimb() {
		return false;
	}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
	}

	protected void initGoals() {
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(25) + 20, 6.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
					!(livingEntity instanceof HypnoFlagzombieEntity);
		}));
	}

	public static DefaultAttributeContainer.Builder createFumeshroomAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 14.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 6D);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ATTACHED_BLOCK, Optional.empty());
		this.dataTracker.startTracking(FUMESHROOM_TARGET_ID, 0);
	}

	public int getWarmupTime() {
		return 60;
	}

	public boolean hurtByWater() {
		return false;
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {
	}

	@Nullable
	public LivingEntity getFumeTarget() {
		if (!this.hasFumeTarget()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget != null) {
				return this.cachedBeamTarget;
			} else {
				Entity entity = this.world.getEntityById((Integer) this.dataTracker.get(FUMESHROOM_TARGET_ID));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget = (LivingEntity) entity;
					return this.cachedBeamTarget;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	public float getFumeProgress(float tickDelta) {
		return ((float) this.beamTicks + tickDelta) / (float) this.getWarmupTime();
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && --this.healingTime <= 0 && !this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.heal(1.0F);
			this.healingTime = 6000;
		}

		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.damage(DamageSource.GENERIC, 9999);
		}
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 13) {
			this.isTired = true;
		} else if (status == 12) {
			this.isTired = false;
		}
	}

	protected void mobTick() {
		float f = this.getLightLevelDependentValue();
		if (f > 0.5f) {
			this.isAsleep = true;
			this.world.sendEntityStatus(this, (byte) 13);
		} else {
			this.isAsleep = false;
			this.world.sendEntityStatus(this, (byte) 12);
		}
		super.mobTick();
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.5F;
	}

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZCubed.ZOMBIEBITEEVENT;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return PvZCubed.PLANTPLANTEDEVENT;
	}

	@Environment(EnvType.CLIENT)
	public Vec3d method_29919() {
		return new Vec3d(0.0D, (double) (0.75F * this.getStandingEyeHeight()), (double) (this.getWidth() * 0.4F));
	}

	public static boolean isSpawnDark(ServerWorldAccess serverWorldAccess, BlockPos pos, Random random) {
		if (serverWorldAccess.getLightLevel(LightType.SKY, pos) > random.nextInt(32)) {
			return false;
		} else {
			int i = serverWorldAccess.toServerWorld().isThundering() ? serverWorldAccess.getLightLevel(pos, 10) : serverWorldAccess.getLightLevel(pos);
			return i <= random.nextInt(11);
		}
	}

	public static boolean canFumeshroomSpawn(EntityType<FumeshroomEntity> entity, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos pos, Random random) {
		return pos.getY() > 1 && isSpawnDark(serverWorldAccess, pos, random);
	}

	@Override
	public boolean canSpawn(WorldView worldreader) {
		return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
	}

	static {
		SNOW_GOLEM_FLAGS = DataTracker.registerData(FumeshroomEntity.class, TrackedDataHandlerRegistry.BYTE);
	}

	static class FireBeamGoal extends Goal {
		private final FumeshroomEntity fumeshroomEntity;
		private int beamTicks;

		public FireBeamGoal(FumeshroomEntity fumeshroomEntity) {
			this.fumeshroomEntity = fumeshroomEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.fumeshroomEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive() && !fumeshroomEntity.isAsleep && !fumeshroomEntity.isInsideWaterOrBubbleColumn();
		}

		public boolean shouldContinue() {
			return super.shouldContinue() && (this.fumeshroomEntity.squaredDistanceTo(this.fumeshroomEntity.getTarget()) > 9.0D);
		}

		public void start() {
			this.beamTicks = -10;
			this.fumeshroomEntity.getNavigation().stop();
			this.fumeshroomEntity.getLookControl().lookAt(this.fumeshroomEntity.getTarget(), 90.0F, 90.0F);
			this.fumeshroomEntity.velocityDirty = true;
		}

		public void stop() {
			this.fumeshroomEntity.setFumeTarget(0);
			this.fumeshroomEntity.setTarget((LivingEntity) null);
		}

		public void tick() {
			LivingEntity livingEntity = this.fumeshroomEntity.getTarget();
			this.fumeshroomEntity.getNavigation().stop();
			this.fumeshroomEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if (!this.fumeshroomEntity.canSee(livingEntity) || this.fumeshroomEntity.isAsleep) {
				this.fumeshroomEntity.setTarget((LivingEntity) null);
			} else {
				++this.beamTicks;
				if (this.beamTicks == 0) {
					this.fumeshroomEntity.setFumeTarget(this.fumeshroomEntity.getTarget().getId());
					if (!this.fumeshroomEntity.isSilent()) {
						this.fumeshroomEntity.world.sendEntityStatus(this.fumeshroomEntity, (byte) 21);
					}
				} else if (this.beamTicks >= this.fumeshroomEntity.getWarmupTime()) {
					FumeEntity fumeEntity = new FumeEntity(this.fumeshroomEntity.world, this.fumeshroomEntity);
					double d = livingEntity.getX() - this.fumeshroomEntity.getX();
					double e = livingEntity.getBodyY(0.3333333333333333) - fumeEntity.getY();
					double f = livingEntity.getZ() - this.fumeshroomEntity.getZ();
					double g = Math.sqrt(d * d + f * f);
					fumeEntity.setVelocity(d, e + g * 0.20000000298023224, f, 0.85F, 0);
					fumeEntity.updatePosition(fumeEntity.getX(), this.fumeshroomEntity.getY() + 1D, fumeEntity.getZ());
					if (livingEntity.isAlive()) {
						this.fumeshroomEntity.playSound(PvZCubed.FUMESHROOMSHOOTEVENT, 0.3F, 1);
						this.fumeshroomEntity.world.spawnEntity(fumeEntity);
					}
					super.tick();
				}
			}
		}
	}
}
