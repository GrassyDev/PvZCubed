package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.sunflowerseed;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.EnlightenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.ServerWorldAccess;
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

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class SunflowerSeedEntity extends EnlightenEntity implements IAnimatable, RangedAttackMob {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private String controllerName = "puffcontroller";
	public int sunProducingTime = 1200;
	public boolean produceSun;

	public boolean isFiring;

    public SunflowerSeedEntity(EntityType<? extends SunflowerSeedEntity> entityType, World world) {
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
		if (status == 11) {
			this.isFiring = true;
		} else if (status == 10) {
			this.isFiring = false;
		}
		if (status == 6) {
			for(int i = 0; i < 16; ++i) {
				this.world.addParticle(ParticleTypes.CRIT, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, 0, 0, 0);
				this.world.addParticle(ParticleTypes.END_ROD, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, 0, 0, 0);
				this.world.addParticle(ParticleTypes.END_ROD, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, 0, 0, 0);
				this.world.addParticle(ParticleTypes.CRIT, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, 0, 0, 0);
				this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, 0, 0, 0);
				this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, 0, 0, 0);
			}
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(SunflowerSeedEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

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

	public void setPuffshroomPermanency(SunflowerSeedEntity.PuffPermanency puffshroomPermanency) {
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
		if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().playOnce("small.sunflower.attack"));
		}
		else{
			event.getController().setAnimation(new AnimationBuilder().loop("small.sunflower"));
		}
		return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new SunflowerSeedEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 15.0F));
		this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof ZombiePropEntity) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel());
		}));
		snorkelGoal();
	}
	protected void snorkelGoal() {
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel();
		}));
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {
	}

	protected void splashDamage() {
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(2));
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
			} while (this.squaredDistanceTo(livingEntity) > 9);

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
				if (livingEntity instanceof Monster ) {
					if (livingEntity.getY() < (this.getY() + 1.5) && livingEntity.getY() > (this.getY() - 1.5)) {
						if (!world.isClient &&
								!(livingEntity.getFirstPassenger() instanceof ZombiePropEntity && !(livingEntity.getFirstPassenger() instanceof ZombieShieldEntity)) &&
								!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())) {
							String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
							SoundEvent sound;
							sound = switch (zombieMaterial) {
								case "metallic" -> PvZCubed.BUCKETHITEVENT;
								case "plastic" -> PvZCubed.CONEHITEVENT;
								default -> PvZCubed.PEAHITEVENT;
							};
							livingEntity.playSound(sound, 0.4F, (float) (0.5F + Math.random()));
							float damage = 4F;
							if (damage > livingEntity.getHealth() &&
									!(livingEntity instanceof ZombieShieldEntity) &&
									livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity) {
								float damage2 = damage - ((LivingEntity) livingEntity).getHealth();
								livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
								generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this), damage2);
							} else {
								livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
							}
						}
					}
				}
			}
		}
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
		if (this.age >= 600 && !this.getPuffshroomPermanency()) {
			this.discard();
		}
		float time = 300 / this.world.getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
		if (this.age <= time && !this.getPuffshroomPermanency() && !this.hasStatusEffect(StatusEffects.GLOWING)) {
			this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, (int) Math.floor(time), 1)));
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.kill();
		}
		--this.sunProducingTime;

		if (!this.world.isClient && this.isAlive() && this.sunProducingTime <= 0 && !this.isInsideWaterOrBubbleColumn()){
			if (this.produceSun){
				this.playSound(PvZCubed.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) * 0.75F + 1F);
				this.dropItem(ModItems.SMALLSUN);
				this.sunProducingTime = 1200;
				this.produceSun = false;
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.SUNFLOWERSEED_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/


	public static DefaultAttributeContainer.Builder createSunflowerSeedAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 3D);
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

	public static boolean canSunflowerSeedSpawn(EntityType<? extends SunflowerSeedEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		return world.getLightLevel(pos) >= 10;
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final SunflowerSeedEntity sunflowerSeedEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(SunflowerSeedEntity sunflowerSeedEntity) {
			this.sunflowerSeedEntity = sunflowerSeedEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.sunflowerSeedEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -6;
			this.animationTicks = -11;
			this.sunflowerSeedEntity.getNavigation().stop();
			this.sunflowerSeedEntity.getLookControl().lookAt(this.sunflowerSeedEntity.getTarget(), 90.0F, 90.0F);
			this.sunflowerSeedEntity.velocityDirty = true;
		}

		public void stop() {
			this.sunflowerSeedEntity.produceSun = false;
			this.sunflowerSeedEntity.world.sendEntityStatus(this.sunflowerSeedEntity, (byte) 10);
			this.sunflowerSeedEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.sunflowerSeedEntity.getTarget();
			this.sunflowerSeedEntity.getNavigation().stop();
			this.sunflowerSeedEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.sunflowerSeedEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.sunflowerSeedEntity.setTarget((LivingEntity) null);
			} else {
				this.sunflowerSeedEntity.world.sendEntityStatus(this.sunflowerSeedEntity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -5) {
					if (!this.sunflowerSeedEntity.isInsideWaterOrBubbleColumn()) {
						this.beamTicks = -6;
						this.sunflowerSeedEntity.world.sendEntityStatus(this.sunflowerSeedEntity, (byte) 11);
						this.sunflowerSeedEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
						this.sunflowerSeedEntity.splashDamage();
						this.sunflowerSeedEntity.world.sendEntityStatus(this.sunflowerSeedEntity, (byte) 6);
						this.sunflowerSeedEntity.produceSun = true;
					}
				}
				else if (this.animationTicks >= 0)
				{
					this.sunflowerSeedEntity.produceSun = false;
					this.sunflowerSeedEntity.world.sendEntityStatus(this.sunflowerSeedEntity, (byte) 10);
					this.beamTicks = -6;
					this.animationTicks = -11;
				}
				super.tick();
			}
		}
	}
}
