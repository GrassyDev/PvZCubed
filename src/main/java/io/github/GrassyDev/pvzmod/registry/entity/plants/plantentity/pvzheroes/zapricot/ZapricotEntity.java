package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.zapricot;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
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
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ZapricotEntity extends PlantEntity implements IAnimatable, RangedAttackMob {


	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private int attackTicksLeft;
	private String controllerName = "lightningcontroller";
	public boolean isFiring;

	public LivingEntity targetZombie;


	private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID;
	private static final TrackedData<Integer> ELECTRIC_BEAM_TARGET_ID;
	private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID2;
	private static final TrackedData<Integer> ELECTRIC_BEAM_TARGET_ID2;
	private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID3;
	private static final TrackedData<Integer> ELECTRIC_BEAM_TARGET_ID3;
	private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID4;
	private static final TrackedData<Integer> ELECTRIC_BEAM_TARGET_ID4;
	private static final TrackedData<Integer> SPARK_TARGET;
	private LivingEntity cachedBeamTarget;
	private LivingEntity cachedBeamTarget2;
	private LivingEntity cachedBeamTarget3;
	private LivingEntity cachedBeamTarget4;
	private LivingEntity cachedBeamTarget5;
	private LivingEntity cachedBeamTarget6;
	private LivingEntity cachedBeamTarget7;
	private LivingEntity cachedBeamTarget8;
	private LivingEntity cachedSparkTarget;
	private int beamTicks;

	public ZapricotEntity(EntityType<? extends ZapricotEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;

		this.setNoGravity(true);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, false);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID, 0);
		this.dataTracker.startTracking(ELECTRIC_BEAM_TARGET_ID, 0);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID2, 0);
		this.dataTracker.startTracking(ELECTRIC_BEAM_TARGET_ID2, 0);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID3, 0);
		this.dataTracker.startTracking(ELECTRIC_BEAM_TARGET_ID3, 0);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID4, 0);
		this.dataTracker.startTracking(ELECTRIC_BEAM_TARGET_ID4, 0);
		this.dataTracker.startTracking(SPARK_TARGET, 0);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("Permanent", this.getPuffshroomPermanency());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, nbt.getBoolean("Permanent"));

	}

	public void onTrackedDataSet(TrackedData<?> data) {
		if (HYPNO_BEAM_TARGET_ID.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget = null;
		}
		if (ELECTRIC_BEAM_TARGET_ID.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget2 = null;
		}
		if (HYPNO_BEAM_TARGET_ID2.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget3 = null;
		}
		if (ELECTRIC_BEAM_TARGET_ID2.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget4 = null;
		}
		if (HYPNO_BEAM_TARGET_ID3.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget5 = null;
		}
		if (ELECTRIC_BEAM_TARGET_ID3.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget6 = null;
		}
		if (HYPNO_BEAM_TARGET_ID4.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget7 = null;
		}
		if (ELECTRIC_BEAM_TARGET_ID4.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget8 = null;
		}
		if (SPARK_TARGET.equals(data)) {
			this.beamTicks = 0;
			this.cachedSparkTarget = null;
		}

		super.onTrackedDataSet(data);
	}

	static {
		HYPNO_BEAM_TARGET_ID = DataTracker.registerData(ZapricotEntity.class, TrackedDataHandlerRegistry.INTEGER);
		ELECTRIC_BEAM_TARGET_ID = DataTracker.registerData(ZapricotEntity.class, TrackedDataHandlerRegistry.INTEGER);
		HYPNO_BEAM_TARGET_ID2 = DataTracker.registerData(ZapricotEntity.class, TrackedDataHandlerRegistry.INTEGER);
		ELECTRIC_BEAM_TARGET_ID2 = DataTracker.registerData(ZapricotEntity.class, TrackedDataHandlerRegistry.INTEGER);
		HYPNO_BEAM_TARGET_ID3 = DataTracker.registerData(ZapricotEntity.class, TrackedDataHandlerRegistry.INTEGER);
		ELECTRIC_BEAM_TARGET_ID3 = DataTracker.registerData(ZapricotEntity.class, TrackedDataHandlerRegistry.INTEGER);
		HYPNO_BEAM_TARGET_ID4 = DataTracker.registerData(ZapricotEntity.class, TrackedDataHandlerRegistry.INTEGER);
		ELECTRIC_BEAM_TARGET_ID4 = DataTracker.registerData(ZapricotEntity.class, TrackedDataHandlerRegistry.INTEGER);
		SPARK_TARGET = DataTracker.registerData(ZapricotEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}


	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 106) {
			this.attackTicksLeft = 20;
		} else {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		}
		if (status == 111) {
			this.isFiring = true;
		} else if (status == 110) {
			this.isFiring = false;
		}
		if (status == 121) {
			if (this.getSparkTarget() != null) {
				LivingEntity livingEntity = this.getSparkTarget();
				for (int i = 0; i < 64; ++i) {
					double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
					double e = this.random.nextDouble() / 2 * this.random.range(0, 1);
					double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
					this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, livingEntity.getX() + (this.random.range(-1, 1)), livingEntity.getY() + 0.5 + (this.random.range(-1, 1)), livingEntity.getZ() + (this.random.range(-1, 1)), d, e, f);
				}
			}
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
		if (entityData == null) {
			entityData = new PlantData(true);
		}
		if (entityData instanceof ZapricotEntity.PlantData plantData && !spawnReason.equals(SpawnReason.SPAWN_EGG)) {
			this.naturalSpawn = true;
			if (plantData.tryLilyPad) {
				List<LilyPadEntity> list = world.getEntitiesByClass(
						LilyPadEntity.class, this.getBoundingBox().expand(12.5), EntityPredicates.NOT_MOUNTED
				);
				if (!list.isEmpty()) {
					LilyPadEntity lilyPadEntity = (LilyPadEntity) list.get(0);
					this.startRiding(lilyPadEntity);
				}
			}
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(ZapricotEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

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

	public void setPuffshroomPermanency(ZapricotEntity.PuffPermanency puffshroomPermanency) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, puffshroomPermanency.getId());
	}


	/**
	 * /~*~//~*GECKOLIB ANIMATION~//~*~//
	 **/

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
		if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().loop("zapricot.shoot"));
		} else {
			event.getController().setAnimation(new AnimationBuilder().loop("zapricot.idle"));
		}
		return PlayState.CONTINUE;
	}

	/** /~*~//~AI~//~*~// **/

	protected void initGoals() {
		this.goalSelector.add(1, new ZapricotEntity.FireBeamGoal(this));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity));
		}));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {
	}


	/**
	 * //~*~//~POSITION~//~*~//
	 **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double) MathHelper.floor(y + 0.5), (double) MathHelper.floor(z) + 0.5);
		}

		if (this.age > 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead) {
					this.dropItem(ModItems.ZAPRICOT_SEED_PACKET);
				}
				this.discard();
			}

		}
	}


	/**
	 * //~*~//~TICKING~//~*~//
	 **/

	public void tick() {
		super.tick();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		if (this.age >= 600 && !this.getPuffshroomPermanency()) {
			this.discard();
		}
		float time = 200 / this.world.getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
		if (this.age > 4 && this.age <= time && !this.getPuffshroomPermanency() && !this.hasStatusEffect(StatusEffects.GLOWING)) {
			if (this.world.getGameRules().getBoolean(PvZCubed.PLANTS_GLOW)) {
				this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, (int) Math.floor(time), 1)));
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}

		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
		}

		if (this.hasBeamTarget()) {
			if (this.beamTicks < this.getWarmupTime()) {
				++this.beamTicks;
			}

			LivingEntity livingEntity = this.getBeamTarget();
			LivingEntity anchorEntity = this.getElectricBeamTarget();

			LivingEntity livingEntity2 = this.getBeamTarget2();
			LivingEntity anchorEntity2 = this.getElectricBeamTarget2();

			LivingEntity livingEntity3 = this.getBeamTarget3();
			LivingEntity anchorEntity3 = this.getElectricBeamTarget3();

			LivingEntity livingEntity4 = this.getBeamTarget4();
			LivingEntity anchorEntity4 = this.getElectricBeamTarget4();
			if (livingEntity != null && anchorEntity != null) {
				this.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
				this.getLookControl().tick();
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity.getX() - anchorEntity.getX();
				double f = livingEntity.getBodyY(0.5D) - anchorEntity.getEyeY();
				double g = livingEntity.getZ() - anchorEntity.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += d + this.random.nextDouble() * d;
					this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, anchorEntity.getX() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + e * j,
							anchorEntity.getEyeY() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + f * j,
							anchorEntity.getZ() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + g * j, 0, 0, 0);
				}
			}
			if (livingEntity2 != null && anchorEntity2 != null) {
				this.getLookControl().lookAt(livingEntity2, 90.0F, 90.0F);
				this.getLookControl().tick();
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity2.getX() - anchorEntity2.getX();
				double f = livingEntity2.getBodyY(0.5D) - anchorEntity2.getEyeY();
				double g = livingEntity2.getZ() - anchorEntity2.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += d + this.random.nextDouble() * d;
					this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, anchorEntity2.getX() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + e * j,
							anchorEntity2.getEyeY() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + f * j,
							anchorEntity2.getZ() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + g * j, 0, 0, 0);
				}
			}
			if (livingEntity3 != null && anchorEntity3 != null) {
				this.getLookControl().lookAt(livingEntity3, 90.0F, 90.0F);
				this.getLookControl().tick();
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity3.getX() - anchorEntity3.getX();
				double f = livingEntity3.getBodyY(0.5D) - anchorEntity3.getEyeY();
				double g = livingEntity3.getZ() - anchorEntity3.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += d + this.random.nextDouble() * d;
					this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, anchorEntity3.getX() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + e * j,
							anchorEntity3.getEyeY() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + f * j,
							anchorEntity3.getZ() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + g * j, 0, 0, 0);
				}
			}
			if (livingEntity4 != null && anchorEntity4 != null) {
				this.getLookControl().lookAt(livingEntity4, 90.0F, 90.0F);
				this.getLookControl().tick();
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity4.getX() - anchorEntity4.getX();
				double f = livingEntity4.getBodyY(0.5D) - anchorEntity4.getEyeY();
				double g = livingEntity4.getZ() - anchorEntity4.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += d + this.random.nextDouble() * d;
					this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, anchorEntity4.getX() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + e * j,
							anchorEntity4.getEyeY() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + f * j,
							anchorEntity4.getZ() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + g * j, 0, 0, 0);
				}
			}
		}
	}

	protected int lightningCounter;

	public void lightning(LivingEntity origin){
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, origin.getBoundingBox().expand(10.0));
		Iterator var9 = list.iterator();
		while (true) {
			LivingEntity livingEntity;
			do {
				do {
					do {
						do {
							if (!var9.hasNext()) {
								return;
							}

							livingEntity = (LivingEntity) var9.next();
						} while (livingEntity == origin);
					} while (livingEntity.hasPassenger(origin));
				} while (livingEntity.getVehicle() == origin);
			} while (origin.squaredDistanceTo(livingEntity) > 100);

			if (lightningCounter > 0 && livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) {
				ZombiePropEntity passenger = null;
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe) {
						passenger = zpe;
					}
				}
				LivingEntity damaged = livingEntity;
				if (passenger != null) {
					damaged = passenger;
				}
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(damaged.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic" -> PvZSounds.BUCKETHITEVENT;
					case "plastic" -> PvZSounds.CONEHITEVENT;
					case "stone" -> PvZSounds.STONEHITEVENT;
					default -> PvZSounds.PEAHITEVENT;
				};
				damaged.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
				if (livingEntity.isWet() || livingEntity.hasStatusEffect(PvZCubed.WET)){
					damaged.damage(PvZCubed.LIGHTNING_DAMAGE, 2);
				}
				else {
					damaged.damage(PvZCubed.LIGHTNING_DAMAGE, 1);
				}
				damaged.damage(DamageSource.thrownProjectile(this, this), 0);
				setSparkTarget(damaged.getId());
				this.world.sendEntityStatus(this, (byte) 121);
				if (zombieMaterial.equals("plastic")){
					this.lightningCounter -= 2;
				}
				else if (!zombieMaterial.equals("metallic")){
					--this.lightningCounter;
				}
				if (getBeamTarget2() == null && getElectricBeamTarget2() == null){
					this.setHypnoBeamTarget2(damaged.getId());
					this.setElectricBeamTargetId2(origin.getId());
				}
				else if (getBeamTarget3() == null && getElectricBeamTarget3() == null && getElectricBeamTarget2() != null){
					this.setHypnoBeamTarget3(damaged.getId());
					this.setElectricBeamTargetId3(this.getElectricBeamTarget2().getId());
				}
				else if (getBeamTarget4() == null && getElectricBeamTarget4() == null && getElectricBeamTarget3() != null){
					this.setHypnoBeamTarget4(this.getElectricBeamTarget3().getId());
					this.setElectricBeamTargetId4(damaged.getId());
				}
			}
		}
	}


	/**
	 * /~*~//~*INTERACTION*~//~*~/
	 **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.ZAPRICOT_SEED_PACKET.getDefaultStack();
	}


	/**
	 * //~*~//~ATTRIBUTES~//~*~//
	 **/

	public static DefaultAttributeContainer.Builder createZapricotAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
	}

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 1F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZSounds.SILENCEVENET;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return PvZSounds.PLANTPLANTEDEVENT;
	}

	public boolean hurtByWater() {
		return false;
	}

	public boolean isPushable() {
		return false;
	}

	public void onDeath(DamageSource source) {
		super.onDeath(source);
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


	/**
	 * //~*~//~DAMAGE HANDLER~//~*~//
	 **/

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
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}


	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canZapricotSpawn(EntityType<? extends ZapricotEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float nightchance = random.nextFloat();
		if (nightchance <= 0.5){
			return !world.getBlockState(blockPos).isOf(Blocks.AIR) && !world.getBlockState(blockPos).isOf(Blocks.CAVE_AIR) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_PLANT_SPAWN);
		}
		else {
			return !world.getBlockState(blockPos).isOf(Blocks.AIR) && !world.getBlockState(blockPos).isOf(Blocks.CAVE_AIR) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() && world.getAmbientDarkness() < 4 &&
					world.getLightLevel(LightType.SKY, pos) > 10 && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_PLANT_SPAWN);
		}
	}


	/**
	 * /~*~//~*GOALS*~//~*~/
	 **/

	public int getWarmupTime() {
		return 20;
	}

	private void setHypnoBeamTarget(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID, entityId);
	}

	public boolean hasBeamTarget() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID) != 0;
	}

	private void setElectricBeamTargetId(int entityId) {
		this.dataTracker.set(ELECTRIC_BEAM_TARGET_ID, entityId);
	}

	public boolean hasElectricBeamTarget() {
		return (Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID) != 0;
	}

	private void setHypnoBeamTarget2(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID2, entityId);
	}

	public boolean hasBeamTarget2() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID2) != 0;
	}

	private void setElectricBeamTargetId2(int entityId) {
		this.dataTracker.set(ELECTRIC_BEAM_TARGET_ID2, entityId);
	}

	public boolean hasElectricBeamTarget2() {
		return (Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID2) != 0;
	}

	private void setHypnoBeamTarget3(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID3, entityId);
	}

	public boolean hasBeamTarget3() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID3) != 0;
	}

	private void setElectricBeamTargetId3(int entityId) {
		this.dataTracker.set(ELECTRIC_BEAM_TARGET_ID3, entityId);
	}

	public boolean hasElectricBeamTarget3() {
		return (Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID3) != 0;
	}

	private void setHypnoBeamTarget4(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID4, entityId);
	}

	public boolean hasBeamTarget4() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID4) != 0;
	}

	private void setElectricBeamTargetId4(int entityId) {
		this.dataTracker.set(ELECTRIC_BEAM_TARGET_ID4, entityId);
	}

	public boolean hasElectricBeamTarget4() {
		return (Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID4) != 0;
	}

	private void setSparkTarget(int entityId) {
		this.dataTracker.set(SPARK_TARGET, entityId);
	}

	public boolean hasSparkTarget() {
		return (Integer)this.dataTracker.get(SPARK_TARGET) != 0;
	}

	@Nullable
	public LivingEntity getBeamTarget() {
		if (!this.hasBeamTarget()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget != null) {
				return this.cachedBeamTarget;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget = (LivingEntity)entity;
					return this.cachedBeamTarget;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	@Nullable
	public LivingEntity getElectricBeamTarget() {
		if (!this.hasElectricBeamTarget()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget2 != null) {
				return this.cachedBeamTarget2;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget2 = (LivingEntity)entity;
					return this.cachedBeamTarget2;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	@Nullable
	public LivingEntity getBeamTarget2() {
		if (!this.hasBeamTarget2()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget3 != null) {
				return this.cachedBeamTarget3;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID2));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget3 = (LivingEntity)entity;
					return this.cachedBeamTarget3;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	@Nullable
	public LivingEntity getElectricBeamTarget2() {
		if (!this.hasElectricBeamTarget2()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget4 != null) {
				return this.cachedBeamTarget4;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID2));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget4 = (LivingEntity)entity;
					return this.cachedBeamTarget4;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	@Nullable
	public LivingEntity getBeamTarget3() {
		if (!this.hasBeamTarget3()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget5 != null) {
				return this.cachedBeamTarget5;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID3));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget5 = (LivingEntity)entity;
					return this.cachedBeamTarget5;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	@Nullable
	public LivingEntity getElectricBeamTarget3() {
		if (!this.hasElectricBeamTarget3()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget6 != null) {
				return this.cachedBeamTarget6;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID3));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget6 = (LivingEntity)entity;
					return this.cachedBeamTarget6;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	@Nullable
	public LivingEntity getBeamTarget4() {
		if (!this.hasBeamTarget4()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget7 != null) {
				return this.cachedBeamTarget7;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID4));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget7 = (LivingEntity)entity;
					return this.cachedBeamTarget7;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	@Nullable
	public LivingEntity getElectricBeamTarget4() {
		if (!this.hasElectricBeamTarget4()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget8 != null) {
				return this.cachedBeamTarget8;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID4));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget8 = (LivingEntity)entity;
					return this.cachedBeamTarget8;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	@Nullable
	public LivingEntity getSparkTarget() {
		if (!this.hasSparkTarget()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedSparkTarget != null) {
				return this.cachedSparkTarget;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(SPARK_TARGET));
				if (entity instanceof LivingEntity) {
					this.cachedSparkTarget = (LivingEntity)entity;
					return this.cachedSparkTarget;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	public float getBeamProgress(float tickDelta) {
		return ((float)this.beamTicks + tickDelta) / (float)this.getWarmupTime();
	}

	static class FireBeamGoal extends Goal {
		private final ZapricotEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(ZapricotEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
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
			this.plantEntity.setHypnoBeamTarget(0);
			this.plantEntity.setHypnoBeamTarget2(0);
			this.plantEntity.setHypnoBeamTarget3(0);
			this.plantEntity.setHypnoBeamTarget4(0);
			this.plantEntity.setElectricBeamTargetId(0);
			this.plantEntity.setElectricBeamTargetId2(0);
			this.plantEntity.setElectricBeamTargetId3(0);
			this.plantEntity.setElectricBeamTargetId4(0);
			this.plantEntity.setSparkTarget(0);
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
			if (plantEntity.getTarget() != null){
				this.plantEntity.attack(plantEntity.getTarget(), 0);
			}
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
						this.plantEntity.setElectricBeamTargetId(this.plantEntity.getId());
						this.plantEntity.setHypnoBeamTarget(this.plantEntity.getTarget().getId());
						this.plantEntity.setElectricBeamTargetId3(this.plantEntity.getTarget().getId());
						if (livingEntity != null && livingEntity.isAlive()) {
							ZombiePropEntity passenger = null;
							for (Entity entity1 : livingEntity.getPassengerList()) {
								if (entity1 instanceof ZombiePropEntity zpe) {
									passenger = zpe;
								}
							}
							LivingEntity damaged = livingEntity;
							if (passenger != null) {
								damaged = passenger;
							}
							String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(damaged.getType()).orElse("flesh");
							SoundEvent sound;
							sound = switch (zombieMaterial) {
								case "metallic" -> PvZSounds.BUCKETHITEVENT;
								case "plastic" -> PvZSounds.CONEHITEVENT;
								case "stone" -> PvZSounds.STONEHITEVENT;
								default -> PvZSounds.PEAHITEVENT;
							};
							this.plantEntity.playSound(PvZSounds.LIGHTNINGSHOOTEVENT, 0.75F, (float) (0.75F + (Math.random() / 2)));
							damaged.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
							if (livingEntity.isWet() || livingEntity.hasStatusEffect(PvZCubed.WET)){
								damaged.damage(PvZCubed.LIGHTNING_DAMAGE, 2);
							}
							else {
								damaged.damage(PvZCubed.LIGHTNING_DAMAGE, 1);
							}
							damaged.damage(DamageSource.thrownProjectile(this.plantEntity, this.plantEntity), 0);
							this.plantEntity.lightningCounter = 3;
							if (zombieMaterial.equals("plastic")){
								this.plantEntity.lightningCounter = 2;
							}
							this.plantEntity.lightning(damaged);
							this.plantEntity.lightningCounter = 3;
							this.beamTicks = -7;
							this.plantEntity.targetZombie = damaged;
						}
					}
				} else if (this.animationTicks <= -2){
					this.plantEntity.setHypnoBeamTarget(0);
					this.plantEntity.setHypnoBeamTarget2(0);
					this.plantEntity.setHypnoBeamTarget3(0);
					this.plantEntity.setHypnoBeamTarget4(0);
					this.plantEntity.setElectricBeamTargetId(0);
					this.plantEntity.setElectricBeamTargetId2(0);
					this.plantEntity.setElectricBeamTargetId3(0);
					this.plantEntity.setElectricBeamTargetId4(0);
				}
				else if (this.animationTicks >= 0) {
					this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
