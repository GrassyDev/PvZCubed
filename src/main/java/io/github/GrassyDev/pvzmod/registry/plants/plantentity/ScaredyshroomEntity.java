package io.github.GrassyDev.pvzmod.registry.plants.plantentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.SporeEntity;
import io.github.GrassyDev.pvzmod.registry.variants.plants.ScaredyshroomVariants;
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

public class ScaredyshroomEntity extends GolemEntity implements IAnimatable, RangedAttackMob {

	public AnimationFactory factory = new AnimationFactory(this);

	private String controllerName = "scaredycontroller";

	private int healingTime;

	private boolean isTired;

	private boolean isFiring;

	private boolean isAfraid;

	private int animationScare;

    public ScaredyshroomEntity(EntityType<? extends ScaredyshroomEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 6000;
        this.animationScare = 30;
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
		if (status == 14) {
			this.isAfraid = false;
			this.animationScare = 30;
		}
		if (status == 4) {
			this.isAfraid = true;
			this.isFiring = false;
		}
		if (status == 13) {
			this.isTired = true;
			this.isFiring = false;
		}
		else if (status == 12) {
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
			DataTracker.registerData(ScaredyshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		ScaredyshroomVariants variant = Util.getRandom(ScaredyshroomVariants.values(), this.random);
		setVariant(variant);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public ScaredyshroomVariants getVariant() {
		return ScaredyshroomVariants.byId(this.getTypeVariant() & 255);
	}

	private void setVariant(ScaredyshroomVariants variant) {
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
			event.getController().setAnimation(new AnimationBuilder().addAnimation("scaredyshroom.asleep", true));
		}
		else if (this.animationScare <= 0 && this.isAfraid){
			event.getController().setAnimation(new AnimationBuilder().addAnimation("scaredyshroom.afraid", true));
		}
		else if (this.isAfraid){
			event.getController().setAnimation(new AnimationBuilder().addAnimation("scaredyshroom.hiding", false));
		}
		else if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("scaredyshroom.attack", false));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("scaredyshroom.idle", true));
		}
        return PlayState.CONTINUE;
    }


	//~*~//~AI~//~*~//

	protected void initGoals() {
		this.goalSelector.add(1, new ScaredyshroomEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(45) + 40, 30.0F));
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

		if (this.animationScare > 0 && this.isAfraid) {
			--this.animationScare;
		}
	}

	protected void mobTick() {
		float f = this.getLightLevelDependentValue();
		if (f > 0.5f) {
			this.isTired = true;
			this.world.sendEntityStatus(this, (byte) 13);
		} else {
			this.isTired = false;
			this.world.sendEntityStatus(this, (byte) 12);
		}
		super.mobTick();
	}


	//~*~//~ATTRIBUTES~//~*~//

	public static DefaultAttributeContainer.Builder createScaredyshroomAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 3.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30.0D);
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


	//~*~//~GOALS~//~*~//

	static class FireBeamGoal extends Goal {
		private final ScaredyshroomEntity scaredyshroomEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(ScaredyshroomEntity scaredyshroomEntity) {
			this.scaredyshroomEntity = scaredyshroomEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.scaredyshroomEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive() && !this.scaredyshroomEntity.isTired;
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.scaredyshroomEntity.getNavigation().stop();
			this.scaredyshroomEntity.getLookControl().lookAt(this.scaredyshroomEntity.getTarget(), 90.0F, 90.0F);
			this.scaredyshroomEntity.velocityDirty = true;
		}

		public void stop() {
			this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 10);
			this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 14);
			this.scaredyshroomEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.scaredyshroomEntity.getTarget();
			this.scaredyshroomEntity.getNavigation().stop();
			this.scaredyshroomEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.scaredyshroomEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 14);
				this.scaredyshroomEntity.setTarget((LivingEntity) null);
			}
			else {
				if (!this.scaredyshroomEntity.isTired && !this.scaredyshroomEntity.isAfraid) {
					this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 11);
					++this.animationTicks;
					++this.beamTicks;
					if (this.scaredyshroomEntity.squaredDistanceTo(livingEntity) < 36) {
						this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 4);
					} else if (this.scaredyshroomEntity.squaredDistanceTo(livingEntity) >= 36 && !this.scaredyshroomEntity.isAfraid)  {
						if (this.beamTicks >= 0 && this.animationTicks >=-7) {
							if (!this.scaredyshroomEntity.isInsideWaterOrBubbleColumn()) {
								this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 14);
								SporeEntity proj = new SporeEntity(PvZEntity.SPORE, this.scaredyshroomEntity.world);
								double d = this.scaredyshroomEntity.squaredDistanceTo(livingEntity);
								float df = (float) d;
								double e = livingEntity.getX() - this.scaredyshroomEntity.getX();
								double f = livingEntity.getBodyY(0.5D) - this.scaredyshroomEntity.getBodyY(0.5D);
								double g = livingEntity.getZ() - this.scaredyshroomEntity.getZ();
								float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
								proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 3.5F, 0F);
								proj.updatePosition(this.scaredyshroomEntity.getX(), this.scaredyshroomEntity.getY() + 0.75D, this.scaredyshroomEntity.getZ());
								proj.setOwner(this.scaredyshroomEntity);
								if (livingEntity.isAlive()) {
									this.beamTicks = -13;
									this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 11);
									this.scaredyshroomEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
									this.scaredyshroomEntity.world.spawnEntity(proj);
								}
							}
						}
						else if (this.animationTicks >= 0)
						{
							this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 10);
							this.beamTicks = -7;
							this.animationTicks = -16;
						}
					}
				}
				super.tick();
			}
		}
	}
}
