package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.hypnoshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.EnchantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.backupdancer.BackupDancerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.berserker.BerserkerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.buckethead.modernday.BucketheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.conehead.modernday.ConeheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dancingzombie.DancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football.FootballEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper.NewspaperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.polevaulting.PoleVaultingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor.ScreendoorEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

public class HypnoshroomEntity extends EnchantEntity implements IAnimatable, RangedAttackMob {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "hypnocontroller";
    public boolean isAsleep;
    public boolean isTired;

    public int healingTime;
    private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID;
    private LivingEntity cachedBeamTarget;
    private int beamTicks;

    public HypnoshroomEntity(EntityType<? extends HypnoshroomEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 6000;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID, 0);
	}

	public void onTrackedDataSet(TrackedData<?> data) {
		if (HYPNO_BEAM_TARGET_ID.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget = null;
		}

		super.onTrackedDataSet(data);
	}

	static {
		HYPNO_BEAM_TARGET_ID = DataTracker.registerData(HypnoshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 13) {
			this.isTired = true;
		}
		else if (status == 12) {
			this.isTired = false;
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
        if (this.isTired) {
            event.getController().setAnimation(new AnimationBuilder().loop("hypnoshroom.asleep"));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().loop("hypnoshroom.idle"));
        }
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new HypnoshroomEntity.FireBeamGoal(this));
		this.targetSelector.add(9, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof ImpEntity; }));
		this.targetSelector.add(8, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof BrowncoatEntity; }));
		this.targetSelector.add(7, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof ConeheadEntity; }));
		this.targetSelector.add(7, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof PoleVaultingEntity; }));
		this.targetSelector.add(7, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof BackupDancerEntity; }));
		this.targetSelector.add(6, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof NewspaperEntity; }));
		this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof BucketheadEntity; }));
		this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof ScreendoorEntity; }));
		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof DancingZombieEntity; }));
		this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof FlagzombieEntity; }));
		this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof FootballEntity; }));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof BerserkerEntity; }));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) ->
		{ return livingEntity instanceof GargantuarEntity; }));
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

		if (this.age != 0) {
			BlockPos blockPos2 = this.getBlockPos();
			if (!blockPos2.equals(blockPos)) {
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
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && --this.healingTime <= 0 && !this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.heal(4.0F);
			this.healingTime = 6000;
		}

		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.damage(DamageSource.GENERIC, 9999);
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

	protected void mobTick() {
		float f = this.getLightLevelDependentValue();
		if (f > 0.25f) {
			this.world.sendEntityStatus(this, (byte) 13);
			this.clearGoalsAndTasks();
		} else {
			this.world.sendEntityStatus(this, (byte) 12);
			this.initGoals();
		}
		super.mobTick();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createHypnoshroomAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D)
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
			this.damage(DamageSource.GENERIC, 9999);
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
        private final HypnoshroomEntity hypnoshroom;
        private int beamTicks;

        public FireBeamGoal(HypnoshroomEntity hypnoshroom) {
            this.hypnoshroom = hypnoshroom;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.hypnoshroom.getTarget();
            return livingEntity != null && livingEntity.isAlive();
        }

        public boolean shouldContinue() {
            return super.shouldContinue() && (this.hypnoshroom.squaredDistanceTo(this.hypnoshroom.getTarget()) > 9.0D);
        }

        public void start() {
            this.beamTicks = -10;
            this.hypnoshroom.getNavigation().stop();
            this.hypnoshroom.getLookControl().lookAt(this.hypnoshroom.getTarget(), 90.0F, 90.0F);
            this.hypnoshroom.velocityDirty = true;
        }

        public void stop() {
            this.hypnoshroom.setHypnoBeamTarget(0);
            this.hypnoshroom.setTarget((LivingEntity)null);
        }

        public void tick() {
            LivingEntity livingEntity = this.hypnoshroom.getTarget();
            this.hypnoshroom.getNavigation().stop();
            this.hypnoshroom.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
            if (!this.hypnoshroom.canSee(livingEntity) || this.hypnoshroom.isAsleep) {
                this.hypnoshroom.setTarget((LivingEntity) null);
            } else {
                ++this.beamTicks;
                if (this.beamTicks == 0) {
                    this.hypnoshroom.setHypnoBeamTarget(this.hypnoshroom.getTarget().getId());
                } else if (this.beamTicks >= this.hypnoshroom.getWarmupTime()) {
                    float f = 1.0F;
                    livingEntity.damage(DamageSource.magic(this.hypnoshroom, this.hypnoshroom), f);
                    livingEntity.damage(DamageSource.mob(this.hypnoshroom), (float) this.hypnoshroom.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
                    ((LivingEntity) livingEntity).addStatusEffect((new StatusEffectInstance(PvZCubed.HYPNOTIZED, 999, 1))); // applies a status effect
                    this.hypnoshroom.setTarget((LivingEntity) null);
                    this.hypnoshroom.remove(RemovalReason.DISCARDED);
                }
                super.tick();
            }
        }
    }
}
