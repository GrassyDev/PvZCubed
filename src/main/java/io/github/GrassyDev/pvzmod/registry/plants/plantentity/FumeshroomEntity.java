package io.github.GrassyDev.pvzmod.registry.plants.plantentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.FumeEntity;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.FumeEntityVariants.FumeEntity_G;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.FumeEntityVariants.FumeEntity_T;
import io.github.GrassyDev.pvzmod.registry.variants.plants.FumeshroomVariants;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;
import java.util.Random;

public class FumeshroomEntity extends GolemEntity implements IAnimatable, RangedAttackMob {

	public AnimationFactory factory = new AnimationFactory(this);

	private String controllerName = "puffcontroller";

	private int healingTime;

	private boolean isAsleep;

	private boolean isTired;

	private boolean isFiring;


	public FumeshroomEntity(EntityType<? extends FumeshroomEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
		this.healingTime = 6000;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
	}

	static {
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


	//~*~//~VARIANTS~//~*~//

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(FumeshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		FumeshroomVariants variant = Util.getRandom(FumeshroomVariants.values(), this.random);
		setVariant(variant);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public FumeshroomVariants getVariant() {
		return FumeshroomVariants.byId(this.getTypeVariant() & 255);
	}

	private void setVariant(FumeshroomVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}


	//~*~//~GECKOLIB ANIMATION~//~*~//

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
		if (this.isTired) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("fumeshroom.asleep", true));
		} else if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("fumeshroom.attack", false));
		} else {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("fumeshroom.idle", true));
		}
		return PlayState.CONTINUE;
	}


	//~*~//~AI~//~*~//

	protected void initGoals() {
		this.goalSelector.add(1, new FumeshroomEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(25) + 20, 6.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
					!(livingEntity instanceof HypnoFlagzombieEntity);
		}));
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {

	}


	//~*~//~POSITION~//~*~//

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age != 0) {
			BlockPos blockPos2 = this.getBlockPos();
			if (!blockPos2.equals(blockPos)) {
				this.kill();
			}

		}
	}


	//~*~//~TICKING~//~*~//

	public void tick() {
		super.tick();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
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


	//~*~//~ATTRIBUTES~//~*~//


	public static DefaultAttributeContainer.Builder createFumeshroomAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 14.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 6D);
	}

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.5F;
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


	//~*~//~DAMAGE HANDLER~//~*~//

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


	//~*~//~SPAWNING~//~*~//


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


	//~*~//~GOALS~//~*~//

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
			if ((!this.fumeshroomEntity.canSee(livingEntity) && this.animationTicks >= 0) || this.fumeshroomEntity.isAsleep){
				this.fumeshroomEntity.setTarget((LivingEntity) null);
			} else {
				this.fumeshroomEntity.world.sendEntityStatus(this.fumeshroomEntity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					/// DEFAULT VARIANT ///
					if (this.fumeshroomEntity.getVariant().equals(FumeshroomVariants.DEFAULT))
					{
						FumeEntity proj = new FumeEntity(PvZEntity.FUME, this.fumeshroomEntity.world);
						double d = this.fumeshroomEntity.squaredDistanceTo(livingEntity);
						float df = (float) d;
						double e = livingEntity.getX() - this.fumeshroomEntity.getX();
						double f = livingEntity.getBodyY(0.5D) - this.fumeshroomEntity.getBodyY(0.5D);
						double g = livingEntity.getZ() - this.fumeshroomEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.85F, 0F);
						proj.updatePosition(this.fumeshroomEntity.getX(), this.fumeshroomEntity.getY() + 0.5D, this.fumeshroomEntity.getZ());
						proj.setOwner(this.fumeshroomEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -2;
							this.fumeshroomEntity.playSound(PvZCubed.FUMESHROOMSHOOTEVENT, 0.3F, 1);
							this.fumeshroomEntity.world.spawnEntity(proj);
						}
					}
					/// GAY VARIANT ///
					if (this.fumeshroomEntity.getVariant().equals(FumeshroomVariants.GAY))
					{
						FumeEntity_G proj = new FumeEntity_G(PvZEntity.FUME_G, this.fumeshroomEntity.world);
						double d = this.fumeshroomEntity.squaredDistanceTo(livingEntity);
						float df = (float) d;
						double e = livingEntity.getX() - this.fumeshroomEntity.getX();
						double f = livingEntity.getBodyY(0.5D) - this.fumeshroomEntity.getBodyY(0.5D);
						double g = livingEntity.getZ() - this.fumeshroomEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.85F, 0F);
						proj.updatePosition(this.fumeshroomEntity.getX(), this.fumeshroomEntity.getY() + 0.5D, this.fumeshroomEntity.getZ());
						proj.setOwner(this.fumeshroomEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -2;
							this.fumeshroomEntity.playSound(PvZCubed.FUMESHROOMSHOOTEVENT, 0.3F, 1);
							this.fumeshroomEntity.world.spawnEntity(proj);
						}
					}
					/// TRANS VARIANT ///
					if (this.fumeshroomEntity.getVariant().equals(FumeshroomVariants.TRANS))
					{
						FumeEntity_T proj = new FumeEntity_T(PvZEntity.FUME_T, this.fumeshroomEntity.world);
						double d = this.fumeshroomEntity.squaredDistanceTo(livingEntity);
						float df = (float) d;
						double e = livingEntity.getX() - this.fumeshroomEntity.getX();
						double f = livingEntity.getBodyY(0.5D) - this.fumeshroomEntity.getBodyY(0.5D);
						double g = livingEntity.getZ() - this.fumeshroomEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.85F, 0F);
						proj.updatePosition(this.fumeshroomEntity.getX(), this.fumeshroomEntity.getY() + 0.5D, this.fumeshroomEntity.getZ());
						proj.setOwner(this.fumeshroomEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -2;
							this.fumeshroomEntity.playSound(PvZCubed.FUMESHROOMSHOOTEVENT, 0.3F, 1);
							this.fumeshroomEntity.world.spawnEntity(proj);
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
