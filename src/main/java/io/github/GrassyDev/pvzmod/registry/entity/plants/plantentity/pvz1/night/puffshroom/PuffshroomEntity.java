package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spore.SporeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
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
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.Optional;

public class PuffshroomEntity extends PlantEntity implements IAnimatable, RangedAttackMob {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private String controllerName = "puffcontroller";
    public boolean isTired;

	public boolean isFiring;

    public PuffshroomEntity(EntityType<? extends PuffshroomEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, false);
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Permanent", this.getPuffshroomPermanency());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Permanent"));
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2){
			super.handleStatus(status);
		}
		if (status == 113) {
			this.isTired = true;
			this.isFiring = false;
		} else if (status == 112) {
			this.isTired = false;
		}
		if (status == 111) {
			this.isFiring = true;
		} else if (status == 110) {
			this.isFiring = false;
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(PuffshroomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

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

	public void setPuffshroomPermanency(PuffshroomEntity.PuffPermanency puffshroomPermanency) {
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
		if (this.isTired) {
			event.getController().setAnimation(new AnimationBuilder().loop("puffshroom.asleep"));
		} else if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().playOnce("puffshroom.shoot"));
		} else {
			event.getController().setAnimation(new AnimationBuilder().loop("puffshroom.idle"));
		}
		return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new PuffshroomEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(25) + 20, 6.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!(generalPvZombieEntity.isFlying());
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity);
		}));
		snorkelGoal();
	}
	protected void snorkelGoal() {
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel() && !(snorkelEntity.getHypno());
		}));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {

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
					this.dropItem(ModItems.PUFFSHROOM_SEED_PACKET);
				}
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
		LivingEntity target = this.getTarget();
		if (target != null){
			if (target instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
				this.setTarget(null);
				snorkelGoal();
			}
			else if (target instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()){
				this.setTarget(null);
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
			this.kill();
		}
	}

	boolean sleepSwitch = false;
	boolean awakeSwitch = false;

	protected void mobTick() {
		if ((this.world.getAmbientDarkness() >= 2 ||
				this.world.getLightLevel(LightType.SKY, this.getBlockPos()) < 2 ||
				this.world.getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS)))
				&& !awakeSwitch) {
			this.world.sendEntityStatus(this, (byte) 112);
			this.initGoals();
			sleepSwitch = false;
			awakeSwitch = true;
		}
		else if (this.world.getAmbientDarkness() < 2 &&
				this.world.getLightLevel(LightType.SKY, this.getBlockPos()) >= 2 &&
				!this.world.getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS))
				&& !sleepSwitch) {
			this.world.sendEntityStatus(this, (byte) 113);
			this.clearGoalsAndTasks();
			sleepSwitch = true;
			awakeSwitch = false;
		}
		super.mobTick();
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.PUFFSHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/


	public static DefaultAttributeContainer.Builder createPuffshroomAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
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
		return PvZCubed.SILENCEVENET;
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

	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canPuffshroomSpawn(EntityType<? extends PuffshroomEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		return world.getAmbientDarkness() >= 2 ||
				world.getLightLevel(LightType.SKY, pos) < 2 ||
				world.getBiome(blockPos).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS));
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final PuffshroomEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(PuffshroomEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
			this.plantEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.plantEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.plantEntity.setTarget((LivingEntity) null);
			} else {
				this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
						SporeEntity proj = new SporeEntity(PvZEntity.SPORE, this.plantEntity.world);
						double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 36) ? 50 : 1;
						Vec3d targetPos = livingEntity.getPos();
						Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
						double d = this.plantEntity.squaredDistanceTo(predictedPos);
						float df = (float)d;
						double e = predictedPos.getX() - this.plantEntity.getX();
						double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? -0.07500000111758709 : livingEntity.getY() - this.plantEntity.getY();
						double g = predictedPos.getZ() - this.plantEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.sporeAge = 20;
						proj.setVelocity(e * (double)h, f * (double)h, g * (double)h, 0.33F, 0F);
						proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.25D, this.plantEntity.getZ());
						proj.setOwner(this.plantEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -7;
							this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
							this.plantEntity.playSound(PvZCubed.MUSHROOMSHOOTEVENT, 0.3F, 1);
							this.plantEntity.world.spawnEntity(proj);
						}
					}
				}
				else if (this.animationTicks >= 0)
				{
					this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
