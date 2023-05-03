package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.charmshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.*;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.ZOMBIE_SIZE;
import static io.github.GrassyDev.pvzmod.PvZCubed.ZOMBIE_STRENGTH;

public class CharmshroomEntity extends PlantEntity implements IAnimatable, RangedAttackMob {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "charmcontroller";


    private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID;
    private LivingEntity cachedBeamTarget;
    private int beamTicks;

    public CharmshroomEntity(EntityType<? extends CharmshroomEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;

    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, 0);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Count", this.getTypeCount());
	}

	public void onTrackedDataSet(TrackedData<?> data) {
		if (HYPNO_BEAM_TARGET_ID.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget = null;
		}

		super.onTrackedDataSet(data);
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getInt("Count"));
	}

	static {
		HYPNO_BEAM_TARGET_ID = DataTracker.registerData(CharmshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	//Charm Counter

	private static final TrackedData<Integer> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(CharmshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		//Set Count
		setCount(0);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeCount() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	private void setCount(Integer count) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, count);
	}

	private void addCount(){
		int count = getTypeCount();
		this.dataTracker.set(DATA_ID_TYPE_COUNT, count + 1);
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
        if (this.getIsAsleep()) {
            event.getController().setAnimation(new AnimationBuilder().loop("charmshroom.idle.asleep"));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().loop("charmshroom.idle"));
        }
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
	}

	protected void awakeGoals(){
		this.goalSelector.add(1, new CharmshroomEntity.FireBeamGoal(this));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity) && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isCovered()) && generalPvZombieEntity.canHypno()&&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 11);
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity) && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isCovered()) && generalPvZombieEntity.canHypno()&&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 10);
		}));
		this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity) && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isCovered()) && generalPvZombieEntity.canHypno()&&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 9);
		}));
		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity) && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isCovered()) && generalPvZombieEntity.canHypno()&&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 8);
		}));
		this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity) && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isCovered()) && generalPvZombieEntity.canHypno()&&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 7);
		}));
		this.targetSelector.add(6, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity) && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isCovered()) && generalPvZombieEntity.canHypno()&&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 6);
		}));
		this.targetSelector.add(7, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity) && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isCovered()) && generalPvZombieEntity.canHypno()&&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 5);
		}));
		this.targetSelector.add(8, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity) && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isCovered()) && generalPvZombieEntity.canHypno()&&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 4);
		}));
		this.targetSelector.add(9, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity) && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isCovered()) && generalPvZombieEntity.canHypno()&&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 3);
		}));
		this.targetSelector.add(10, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity) && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isCovered()) && generalPvZombieEntity.canHypno()&&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 2);
		}));
		this.targetSelector.add(11, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity) && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isCovered()) && generalPvZombieEntity.canHypno()&&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 1);
		}));
		this.targetSelector.add(12, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity) && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isCovered()) && generalPvZombieEntity.canHypno()&&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 0);
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
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age > 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.CHARMSHROOM_SEED_PACKET);
				}
				this.discard();
			}

		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	boolean sleepSwitch = false;
	boolean awakeSwitch = false;

	public void tick() {
		if (this.getTypeCount() >= 2){
			this.remove(RemovalReason.DISCARDED);
		}
		if (!this.world.isClient) {
			if ((this.world.getAmbientDarkness() >= 2 ||
					this.world.getLightLevel(LightType.SKY, this.getBlockPos()) < 2 ||
					this.world.getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS)))
					&& !awakeSwitch) {
				this.awakeGoals();
				this.setIsAsleep(IsAsleep.FALSE);
				sleepSwitch = false;
				awakeSwitch = true;
			} else if (this.world.getAmbientDarkness() < 2 &&
					this.world.getLightLevel(LightType.SKY, this.getBlockPos()) >= 2 &&
					!this.world.getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS))
					&& !sleepSwitch) {
				this.setIsAsleep(IsAsleep.TRUE);
				this.clearGoalsAndTasks();
				sleepSwitch = true;
				awakeSwitch = false;
			}
		}
		super.tick();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}

		if (this.hasBeamTarget()) {
			if (this.beamTicks < this.getWarmupTime()) {
				++this.beamTicks;
			}

			LivingEntity livingEntity = this.getBeamTarget();
			if (livingEntity != null) {
				this.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
				this.getLookControl().tick();
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity.getX() - this.getX();
				double f = livingEntity.getBodyY(0.5D) - this.getEyeY();
				double g = livingEntity.getZ() - this.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += 1.8D - d + this.random.nextDouble() * (1.7D - d);
					double rd = (double)(this.random.range(160, 255) & 255) / 255.0;
					double gr = (double) (170 & 255) / 255.0;
					double bl = (double)(this.random.range(200, 255) & 255) / 255.0;
					this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + e * j, this.getEyeY() + f * j, this.getZ() + g * j, rd, gr, bl);
				}
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.CHARMSHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createCharmshroomAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1D);
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
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	public int getWarmupTime() {
		return 20;
	}

	private void setHypnoBeamTarget(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID, entityId);
	}

	public boolean hasBeamTarget() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID) != 0;
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

	public float getBeamProgress(float tickDelta) {
		return ((float)this.beamTicks + tickDelta) / (float)this.getWarmupTime();
	}

    static class FireBeamGoal extends Goal {
        private final CharmshroomEntity plantEntity;
        private int beamTicks;

        public FireBeamGoal(CharmshroomEntity plantEntity) {
            this.plantEntity = plantEntity;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.plantEntity.getTarget();
            return livingEntity != null && livingEntity.isAlive();
        }

        public boolean shouldContinue() {
            return super.shouldContinue() && (this.plantEntity.squaredDistanceTo(this.plantEntity.getTarget()) > 9.0D);
        }

        public void start() {
            this.beamTicks = -10;
            this.plantEntity.getNavigation().stop();
            this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
            this.plantEntity.velocityDirty = true;
        }

        public void stop() {
            this.plantEntity.setHypnoBeamTarget(0);
            this.plantEntity.setTarget((LivingEntity)null);
        }

        public void tick() {
            LivingEntity livingEntity = this.plantEntity.getTarget();
            this.plantEntity.getNavigation().stop();
            this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
            if (!this.plantEntity.canSee(livingEntity) || this.plantEntity.getIsAsleep()) {
                this.plantEntity.setTarget((LivingEntity) null);
            } else {
                ++this.beamTicks;
                if (this.beamTicks == 0) {
					if (plantEntity.getTarget() != null) {
						this.plantEntity.setHypnoBeamTarget(this.plantEntity.getTarget().getId());
					}
                } else if (this.beamTicks >= this.plantEntity.getWarmupTime()) {
					if (livingEntity != null && livingEntity.isAlive()) {
						livingEntity.damage(PvZCubed.HYPNO_DAMAGE, 0);
						this.plantEntity.setTarget((LivingEntity) null);
						if (ZOMBIE_SIZE.get(livingEntity.getType()).orElse("medium").equals("gargantuar")){
							this.plantEntity.setCount(2);
						}
						else {
							this.plantEntity.addCount();
						}
						this.beamTicks = -10;
						this.stop();
					}
                }
                super.tick();
            }
        }
    }
}
