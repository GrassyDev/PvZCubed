package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bellflower;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.SpearEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.jingle.JingleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
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
import java.util.List;

public class BellflowerEntity extends SpearEntity implements IAnimatable, RangedAttackMob {

    private String controllerName = "bellcontroller";



	public boolean isFiring;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public BellflowerEntity(EntityType<? extends BellflowerEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;

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
			event.getController().setAnimation(new AnimationBuilder().playOnce("bellflower.shoot"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("bellflower.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new BellflowerEntity.FireBeamGoal(this));
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
		LivingEntity target = this.getTarget();
		if (target != null){
			if (target instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
				this.setTarget(null);
				snorkelGoal();
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.kill();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.BELLFLOWER_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createBellflowerAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 6.0D);
    }

	protected boolean canClimb() {return false;}

	public boolean collides() {return true;}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {return PvZCubed.ZOMBIEBITEEVENT;}

	@Nullable
	protected SoundEvent getDeathSound() {return PvZCubed.PLANTPLANTEDEVENT;}

	public boolean hurtByWater() {return false;}

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

	public static boolean canBellflowerSpawn(EntityType<? extends BellflowerEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		return checkVillager(Vec3d.ofCenter(pos), world) && !checkBellflower(Vec3d.ofCenter(pos), world);
	}

	public static boolean checkVillager(Vec3d pos, ServerWorldAccess world) {
		List<VillagerEntity> list = world.getNonSpectatingEntities(VillagerEntity.class, PvZEntity.BELLFLOWER.getDimensions().getBoxAt(pos).expand(15));
		return !list.isEmpty();
	}

	public static boolean checkBellflower(Vec3d pos, ServerWorldAccess world) {
		List<BellflowerEntity> list = world.getNonSpectatingEntities(BellflowerEntity.class, PvZEntity.BELLFLOWER.getDimensions().getBoxAt(pos).expand(20));
		return !list.isEmpty();
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final BellflowerEntity bellflower;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(BellflowerEntity bellflower) {
			this.bellflower = bellflower;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.bellflower.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.bellflower.getNavigation().stop();
			this.bellflower.getLookControl().lookAt(this.bellflower.getTarget(), 90.0F, 90.0F);
			this.bellflower.velocityDirty = true;
		}

		public void stop() {
			this.bellflower.world.sendEntityStatus(this.bellflower, (byte) 10);
			this.bellflower.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.bellflower.getTarget();
			this.bellflower.getNavigation().stop();
			this.bellflower.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.bellflower.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.bellflower.setTarget((LivingEntity) null);
			} else {
				this.bellflower.world.sendEntityStatus(this.bellflower, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					// Huge thanks to pluiedev (Leah) for being cute and helping me with the code to predict trajectory
					if (!this.bellflower.isInsideWaterOrBubbleColumn()) {
						double time = 1;
						Vec3d targetPos = livingEntity.getPos();
						Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
						double d = this.bellflower.squaredDistanceTo(predictedPos);
						float df = (float)d;
						double e = predictedPos.getX() - this.bellflower.getX();
						double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? -0.07500000111758709 : livingEntity.getY() - this.bellflower.getY();
						double g = predictedPos.getZ() - this.bellflower.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						JingleEntity proj = new JingleEntity(PvZEntity.JINGLE, this.bellflower.world);
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.85F, 0F);
						proj.updatePosition(this.bellflower.getX(), this.bellflower.getY() + 0.5D, this.bellflower.getZ());
						proj.setOwner(this.bellflower);
						if (livingEntity.isAlive()) {
							this.beamTicks = -7;
							this.bellflower.world.sendEntityStatus(this.bellflower, (byte) 11);
							this.bellflower.playSound(SoundEvents.BLOCK_BELL_USE, 0.3F, (float) (1.5 + Math.random() * (3 - 1.5)));
							this.bellflower.world.spawnEntity(proj);
						}
					}
				}
				else if (this.animationTicks >= 0)
				{
					this.bellflower.world.sendEntityStatus(this.bellflower, (byte) 10);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
