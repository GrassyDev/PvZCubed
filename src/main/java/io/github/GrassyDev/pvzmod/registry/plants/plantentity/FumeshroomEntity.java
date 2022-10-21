package io.github.GrassyDev.pvzmod.registry.plants.plantentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.FumeEntity;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.ShootingFlamingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.ShootingPeaEntity;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
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
	public boolean isFiring;

	public FumeshroomEntity(EntityType<? extends FumeshroomEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
		this.healingTime = 6000;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (this.isTired) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("fumeshroom.asleep", true));
		} else if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("fumeshroom.attack", false));
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
		super.onTrackedDataSet(data);
	}

	@Nullable
	public BlockPos getAttachedBlock() {
		return (BlockPos) ((Optional) this.dataTracker.get(ATTACHED_BLOCK)).orElse((Object) null);
	}

	static {
		ATTACHED_BLOCK = DataTracker.registerData(FumeshroomEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);
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
		this.goalSelector.add(1, new FumeshroomEntity.FireBeamGoal(this));
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
		this.dataTracker.startTracking(SNOW_GOLEM_FLAGS, (byte) 16);
		this.dataTracker.startTracking(ATTACHED_BLOCK, Optional.empty());
	}

	public int getWarmupTime() {
		return 0;
	}

	public boolean hurtByWater() {
		return false;
	}

	//@Override
	//public void attack(LivingEntity target, float pullProgress) {
	//	if (!this.isAsleep) {
	//		if (!this.isInsideWaterOrBubbleColumn()) {
	//			FumeEntity fumeEntity = new FumeEntity(this.world, this);
	//			double d = target.getX() - this.getX();
	//			double e = target.getBodyY(0.3333333333333333) - fumeEntity.getY();
	//			double f = target.getZ() - this.getZ();
	//			double g = Math.sqrt(d * d + f * f);
	//			fumeEntity.setVelocity(d, e + g * 0.20000000298023224, f, 0.85F, 0);
	//			fumeEntity.updatePosition(fumeEntity.getX(), this.getY() + 1D, fumeEntity.getZ());
	//			if (target.isAlive()) {
	//				this.playSound(PvZCubed.FUMESHROOMSHOOTEVENT, 0.3F, 1);
	//				this.world.spawnEntity(fumeEntity);
	//			}
	//		}
	//	}
	//}

	@Override
	public void attack(LivingEntity target, float pullProgress) {

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
			this.isFiring = false;
		} else if (status == 12) {
			this.isTired = false;
		}
		if (status == 11) {
			this.isFiring = true;
		} else if (status == 10) {
			this.isFiring = false;
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
		private int animationTicks;

		public FireBeamGoal(FumeshroomEntity fumeshroomEntity) {
			this.fumeshroomEntity = fumeshroomEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.fumeshroomEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive() && !this.fumeshroomEntity.isAsleep;
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -17;
			this.animationTicks = -32;
			this.fumeshroomEntity.getNavigation().stop();
			this.fumeshroomEntity.getLookControl().lookAt(this.fumeshroomEntity.getTarget(), 90.0F, 90.0F);
			this.fumeshroomEntity.velocityDirty = true;
		}

		public void stop() {
			this.fumeshroomEntity.world.sendEntityStatus(this.fumeshroomEntity, (byte) 10);
			this.fumeshroomEntity.setTarget((LivingEntity) null);
		}

		public void tick() {
			ServerWorld serverWorld = this.fumeshroomEntity.world.getServer().getWorld(this.fumeshroomEntity.world.getRegistryKey());
			LivingEntity livingEntity = this.fumeshroomEntity.getTarget();
			this.fumeshroomEntity.getNavigation().stop();
			this.fumeshroomEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.fumeshroomEntity.canSee(livingEntity) || this.fumeshroomEntity.isAsleep) &&
					this.animationTicks >= 0) {
				this.fumeshroomEntity.setTarget((LivingEntity) null);
			} else {
				this.fumeshroomEntity.world.sendEntityStatus(this.fumeshroomEntity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					FumeEntity proj = new FumeEntity(PvZEntity.FUME, this.fumeshroomEntity.world);
					double d = this.fumeshroomEntity.squaredDistanceTo(livingEntity);
					float df = (float) d;
					double e = livingEntity.getX() - this.fumeshroomEntity.getX();
					double f = livingEntity.getBodyY(0.5D) - this.fumeshroomEntity.getBodyY(0.5D);
					double g = livingEntity.getZ() - this.fumeshroomEntity.getZ();
					float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
					proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.85F, 0F);
					proj.updatePosition(this.fumeshroomEntity.getX(), this.fumeshroomEntity.getY() + 0.5D, this.fumeshroomEntity.getZ());
					if (livingEntity.isAlive()) {
						this.beamTicks = -2;
						this.fumeshroomEntity.playSound(PvZCubed.FUMESHROOMSHOOTEVENT, 0.3F, 1);
						this.fumeshroomEntity.world.spawnEntity(proj);
						Vec3d vec3d = fumeshroomEntity.getPos().add(0.0, 0.500000023841858, 0.0);
						Vec3d vec3d2 = livingEntity.getEyePos().subtract(vec3d);
						Vec3d vec3d3 = vec3d2.normalize();
						for (int i = 1; i < MathHelper.floor(vec3d2.length()) + 3; ++i) {
							Vec3d vec3d4 = vec3d.add(vec3d3.multiply((double) i));
							serverWorld.spawnParticles(ParticleTypes.BUBBLE, vec3d4.x, vec3d4.y - 0.5, vec3d4.z, 3, 0.25, 0.25, 0.25, 0.05);
						}
					}
				}
				if (this.animationTicks >= 0) {
					this.fumeshroomEntity.world.sendEntityStatus(this.fumeshroomEntity, (byte) 10);
					this.beamTicks = -17;
					this.animationTicks = -32;
				}
				super.tick();
			}
		}
	}
}
