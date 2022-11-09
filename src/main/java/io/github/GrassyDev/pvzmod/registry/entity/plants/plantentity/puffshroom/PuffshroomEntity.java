package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.puffshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.AilmentEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spore.SporeEntity;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

public class PuffshroomEntity extends AilmentEntity implements IAnimatable, RangedAttackMob {

	public AnimationFactory factory = new AnimationFactory(this);

    private String controllerName = "puffcontroller";
    public boolean isTired;

    public int healingTime;

	public boolean isFiring;

    public PuffshroomEntity(EntityType<? extends PuffshroomEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 6000;
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
			event.getController().setAnimation(new AnimationBuilder().addAnimation("puffshroom.asleep", true));
		} else if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("puffshroom.shoot", false));
		} else {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("puffshroom.idle", true));
		}
		return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new PuffshroomEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(25) + 20, 6.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
					!(livingEntity instanceof HypnoFlagzombieEntity);
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
		if (this.age >= 6000) {
			this.kill();
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
	}

	protected void mobTick() {
		float f = this.getLightLevelDependentValue();
		if (f > 0.5f) {
			this.world.sendEntityStatus(this, (byte) 13);
			this.clearGoalsAndTasks();
		} else {
			this.world.sendEntityStatus(this, (byte) 12);
			this.initGoals();
		}
		super.mobTick();
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


	/** /~*~//~*SPAWNING*~//~*~/ **/


	public static boolean isSpawnDark(ServerWorldAccess serverWorldAccess, BlockPos pos, Random random) {
        if (serverWorldAccess.getLightLevel(LightType.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            int i = serverWorldAccess.toServerWorld().isThundering() ? serverWorldAccess.getLightLevel(pos, 10) : serverWorldAccess.getLightLevel(pos);
            return i <= random.nextInt(11);
        }
    }

    public static boolean canPuffshroomSpawn(EntityType<PuffshroomEntity> entity, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos pos, Random random) {
        return pos.getY() > 1 && isSpawnDark(serverWorldAccess, pos, random);
    }

    @Override
    public boolean canSpawn(WorldView worldreader) {
        return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
    }


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final PuffshroomEntity puffshroomEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(PuffshroomEntity puffshroomEntity) {
			this.puffshroomEntity = puffshroomEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.puffshroomEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.puffshroomEntity.getNavigation().stop();
			this.puffshroomEntity.getLookControl().lookAt(this.puffshroomEntity.getTarget(), 90.0F, 90.0F);
			this.puffshroomEntity.velocityDirty = true;
		}

		public void stop() {
			this.puffshroomEntity.world.sendEntityStatus(this.puffshroomEntity, (byte) 10);
			this.puffshroomEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.puffshroomEntity.getTarget();
			this.puffshroomEntity.getNavigation().stop();
			this.puffshroomEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.puffshroomEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.puffshroomEntity.setTarget((LivingEntity) null);
			} else {
				this.puffshroomEntity.world.sendEntityStatus(this.puffshroomEntity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					if (!this.puffshroomEntity.isInsideWaterOrBubbleColumn()) {
						SporeEntity proj = new SporeEntity(PvZEntity.SPORE, this.puffshroomEntity.world);
						double d = this.puffshroomEntity.squaredDistanceTo(livingEntity);
						float df = (float)d;
						double e = livingEntity.getX() - this.puffshroomEntity.getX();
						double f = livingEntity.getY() - this.puffshroomEntity.getY();
						double g = livingEntity.getZ() - this.puffshroomEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						SporeEntity.sporeAge = 20;
						proj.setVelocity(e * (double)h, f * (double)h, g * (double)h, 0.33F, 0F);
						proj.updatePosition(this.puffshroomEntity.getX(), this.puffshroomEntity.getY() + 0.25D, this.puffshroomEntity.getZ());
						proj.setOwner(this.puffshroomEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -7;
							this.puffshroomEntity.world.sendEntityStatus(this.puffshroomEntity, (byte) 11);
							this.puffshroomEntity.playSound(PvZCubed.MUSHROOMSHOOTEVENT, 0.3F, 1);
							this.puffshroomEntity.world.spawnEntity(proj);
						}
					}
				}
				else if (this.animationTicks >= 0)
				{
					this.puffshroomEntity.world.sendEntityStatus(this.puffshroomEntity, (byte) 10);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
