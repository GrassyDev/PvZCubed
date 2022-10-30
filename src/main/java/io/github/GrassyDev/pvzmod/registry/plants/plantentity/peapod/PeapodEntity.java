package io.github.GrassyDev.pvzmod.registry.plants.plantentity.peapod;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.gatlingpea.GatlingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.plants.planttypes.AppeaseEntity;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.ShootingPeaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.Vector3d;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import oshi.util.Util;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.swing.text.Utilities;
import java.util.EnumSet;
import java.util.Random;
import java.util.Vector;

public class PeapodEntity extends AppeaseEntity implements RangedAttackMob, IAnimatable {
	private String controllerName = "peacontroller";

	public int healingTime;

	public boolean isFiring;

	public AnimationFactory factory = new AnimationFactory(this);

	public PeapodEntity(EntityType<? extends PeapodEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
		this.healingTime = 6000;
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
		AnimationController controller = new AnimationController(this, controllerName, 5, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("peapod.shoot5", false));
		} else {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("peapod.idle5", true));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new PeapodEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(40) + 35, 15.0F));
		this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
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


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (itemStack.isOf(ModItems.GATLINGPEA_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				GatlingpeaEntity gatlingpeaEntity = (GatlingpeaEntity) PvZEntity.GATLINGPEA.create(world);
				gatlingpeaEntity.setTarget(this.getTarget());
				gatlingpeaEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				gatlingpeaEntity.initialize(serverWorld, world.getLocalDifficulty(gatlingpeaEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);
				gatlingpeaEntity.setAiDisabled(this.isAiDisabled());
				if (this.hasCustomName()) {
					gatlingpeaEntity.setCustomName(this.getCustomName());
					gatlingpeaEntity.setCustomNameVisible(this.isCustomNameVisible());
				}

				gatlingpeaEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(gatlingpeaEntity);
				this.remove(RemovalReason.DISCARDED);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.GATLINGPEA_SEED_PACKET, 700);
			}
			return ActionResult.SUCCESS;
		} else {
			return ActionResult.CONSUME;
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createPeapodAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 6)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15D);
	}

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
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

	public static boolean canPeapodSpawn(EntityType<PeapodEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
		return pos.getY() > 60;
	}

	@Override
	public boolean canSpawn(WorldView worldreader) {
		return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final PeapodEntity peapodEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(PeapodEntity peapodEntity) {
			this.peapodEntity = peapodEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.peapodEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -9;
			this.animationTicks = -16;
			this.peapodEntity.getNavigation().stop();
			this.peapodEntity.getLookControl().lookAt(this.peapodEntity.getTarget(), 90.0F, 90.0F);
			this.peapodEntity.velocityDirty = true;
		}

		public void stop() {
			this.peapodEntity.world.sendEntityStatus(this.peapodEntity, (byte) 10);
			this.peapodEntity.setTarget((LivingEntity) null);
		}

		public void tick() {
			LivingEntity livingEntity = this.peapodEntity.getTarget();
			this.peapodEntity.getNavigation().stop();
			this.peapodEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.peapodEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.peapodEntity.setTarget((LivingEntity) null);
			} else {
				this.peapodEntity.world.sendEntityStatus(this.peapodEntity, (byte) 11);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					if (!this.peapodEntity.isInsideWaterOrBubbleColumn()) {
						// Bottom Pea
						ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, this.peapodEntity.world);
						double d = this.peapodEntity.squaredDistanceTo(livingEntity);
						float df = (float) d;
						double e = livingEntity.getX() - this.peapodEntity.getX();
						double f = livingEntity.getY() - this.peapodEntity.getY();
						double g = livingEntity.getZ() - this.peapodEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
						proj.updatePosition(this.peapodEntity.getX(), this.peapodEntity.getY() + 0.33D, this.peapodEntity.getZ());
						proj.setOwner(this.peapodEntity);
						if (livingEntity.isAlive()) {
							this.peapodEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.peapodEntity.world.spawnEntity(proj);
						}
						// Right Pea
						ShootingPeaEntity proj2 = new ShootingPeaEntity(PvZEntity.PEA, this.peapodEntity.world);
						Vec3d vec3d2 = this.peapodEntity.getRotationVec(1.0F).rotateY(90);
						double d2 = this.peapodEntity.squaredDistanceTo(livingEntity);
						float df2 = (float) d2;
						double e2 = livingEntity.getX() - this.peapodEntity.getX();
						double f2 = livingEntity.getY() - this.peapodEntity.getY();
						double g2 = livingEntity.getZ() - this.peapodEntity.getZ();
						float h2 = MathHelper.sqrt(MathHelper.sqrt(df2)) * 0.5F;
						proj2.setVelocity(e2 * (double) h2, f2 * (double) h2, g2 * (double) h2, 0.33F, 0);
						proj2.updatePosition(this.peapodEntity.getX() + vec3d2.x * 0.75, this.peapodEntity.getY() + 0.3, this.peapodEntity.getZ() + vec3d2.z * 0.75);
						proj2.setOwner(this.peapodEntity);
						if (livingEntity.isAlive()) {
							this.peapodEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.peapodEntity.world.spawnEntity(proj2);
						}
						// Left Pea
						ShootingPeaEntity proj3 = new ShootingPeaEntity(PvZEntity.PEA, this.peapodEntity.world);
						Vec3d vec3d3 = this.peapodEntity.getRotationVec(1.0F).rotateY(-90);
						double d3 = this.peapodEntity.squaredDistanceTo(livingEntity);
						float df3 = (float) d3;
						double e3 = livingEntity.getX() - this.peapodEntity.getX();
						double f3 = livingEntity.getY() - this.peapodEntity.getY();
						double g3 = livingEntity.getZ() - this.peapodEntity.getZ();
						float h3 = MathHelper.sqrt(MathHelper.sqrt(df3)) * 0.5F;
						double xx = 0;
						double zz = 0;
						proj3.setVelocity(e3 * (double) h3, f3 * (double) h3, g3 * (double) h3, 0.33F, 0F);
						proj3.updatePosition(this.peapodEntity.getX() + vec3d3.x * 0.75, this.peapodEntity.getY() + 0.3, this.peapodEntity.getZ() + vec3d3.z * 0.75);
						proj3.setOwner(this.peapodEntity);
						if (livingEntity.isAlive()) {
							this.beamTicks = -16;
							this.peapodEntity.world.sendEntityStatus(this.peapodEntity, (byte) 11);
							this.peapodEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.peapodEntity.world.spawnEntity(proj3);
						}
						// Middle Pea
						ShootingPeaEntity proj4 = new ShootingPeaEntity(PvZEntity.PEA, this.peapodEntity.world);
						double d4 = this.peapodEntity.squaredDistanceTo(livingEntity);
						float df4 = (float) d4;
						double e4 = livingEntity.getX() - this.peapodEntity.getX();
						double f4 = livingEntity.getY() - this.peapodEntity.getY();
						double g4 = livingEntity.getZ() - this.peapodEntity.getZ();
						float h4 = MathHelper.sqrt(MathHelper.sqrt(df4)) * 0.5F;
						proj4.setVelocity(e4 * (double) h4, f4 * (double) h4, g4 * (double) h4, 0.33F, 0F);
						proj4.updatePosition(this.peapodEntity.getX(), this.peapodEntity.getY() + 0.75D, this.peapodEntity.getZ());
						proj4.setOwner(this.peapodEntity);
						if (livingEntity.isAlive()) {
							this.peapodEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.peapodEntity.world.spawnEntity(proj4);
						}
						// Top Pea
						ShootingPeaEntity proj5 = new ShootingPeaEntity(PvZEntity.PEA, this.peapodEntity.world);
						double d5 = this.peapodEntity.squaredDistanceTo(livingEntity);
						float df5 = (float) d5;
						double e5 = livingEntity.getX() - this.peapodEntity.getX();
						double f5 = livingEntity.getY() - this.peapodEntity.getY();
						double g5 = livingEntity.getZ() - this.peapodEntity.getZ();
						float h5 = MathHelper.sqrt(MathHelper.sqrt(df5)) * 0.5F;
						proj5.setVelocity(e5 * (double) h5, f5 * (double) h5, g5 * (double) h5, 0.33F, 0F);
						proj5.updatePosition(this.peapodEntity.getX(), this.peapodEntity.getY() + 1.25D, this.peapodEntity.getZ());
						proj5.setOwner(this.peapodEntity);
						if (livingEntity.isAlive()) {
							this.peapodEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.3F, 1);
							this.peapodEntity.world.spawnEntity(proj5);
						}
					}
				}
				else if (this.animationTicks >= 0) {
					this.peapodEntity.world.sendEntityStatus(this.peapodEntity, (byte) 10);
					this.beamTicks = -9;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
