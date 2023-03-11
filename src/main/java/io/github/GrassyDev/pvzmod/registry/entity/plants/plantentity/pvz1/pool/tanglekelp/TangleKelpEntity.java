package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tanglekelp;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.GameRules;
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

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class TangleKelpEntity extends PlantEntity implements IAnimatable {

    private String controllerName = "kelpcontroller";



	public boolean isFiring;
	private int animationTicksLeft;
	public boolean firstAttack;
	public boolean inAnimation;
	public boolean attackLock;
	public boolean statusSwitch = true;
	private boolean stopAnimation;
	private int amphibiousRaycastDelay;
	public static final UUID MAX_RANGE_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private boolean dryLand;

	public Vec3d originalVec3d;

    public TangleKelpEntity(EntityType<? extends TangleKelpEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
		amphibiousRaycastDelay = 1;
		this.setNoGravity(true);
    }

	public TangleKelpEntity(World world, double x, double y, double z) {
		this(PvZEntity.TANGLE_KELP, world);
		this.setPosition(x, y, z);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2){
			super.handleStatus(status);
		}
		RandomGenerator randomGenerator = this.getRandom();
		if (status == 113) {
			this.inAnimation = true;
		} else if (status == 112) {
			this.inAnimation = false;
		}
		if (status == 107) {
			for(int i = 0; i < 128; ++i) {
				double e = (double) MathHelper.nextBetween(randomGenerator, 5F, 20F);
				this.world.addParticle(ParticleTypes.WATER_SPLASH, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						this.getY() + (double) MathHelper.nextBetween(randomGenerator, 0F, 3F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						0, e, 0);
			}
		}
		if (status == 100) {
			for(int i = 0; i < 64; ++i) {
				double e = (double) MathHelper.nextBetween(randomGenerator, 5F, 20F);
				this.world.addParticle(ParticleTypes.WATER_SPLASH, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						this.getY() + (double) MathHelper.nextBetween(randomGenerator, 0F, 1F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						0, e, 0);
			}
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
		if (this.dryLand) {
			event.getController().setAnimation(new AnimationBuilder().loop("tanglekelp.onground"));
		}
		else if (inAnimation && !stopAnimation) {
			event.getController().setAnimation(new AnimationBuilder().loop("tanglekelp.attack"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("tanglekelp.idle"));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new TangleKelpEntity.AttackGoal());
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && livingEntity.isInsideWaterOrBubbleColumn() && !(generalPvZombieEntity.getHypno())
					&& !(generalPvZombieEntity instanceof GargantuarEntity) && (!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity))
					&& generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 11);
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && livingEntity.isInsideWaterOrBubbleColumn() && !(generalPvZombieEntity.getHypno())
					&& !(generalPvZombieEntity instanceof GargantuarEntity) && (!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity))
					&& generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 10);
		}));
		this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && livingEntity.isInsideWaterOrBubbleColumn() && !(generalPvZombieEntity.getHypno())
					&& !(generalPvZombieEntity instanceof GargantuarEntity) && (!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity))
					&& generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 9);
		}));
		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && livingEntity.isInsideWaterOrBubbleColumn() && !(generalPvZombieEntity.getHypno())
					&& !(generalPvZombieEntity instanceof GargantuarEntity) && (!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity))
					&& generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 8);
		}));
		this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && livingEntity.isInsideWaterOrBubbleColumn() && !(generalPvZombieEntity.getHypno())
					&& !(generalPvZombieEntity instanceof GargantuarEntity) && (!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity))
					&& generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 7);
		}));
		this.targetSelector.add(6, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && livingEntity.isInsideWaterOrBubbleColumn() && !(generalPvZombieEntity.getHypno())
					&& !(generalPvZombieEntity instanceof GargantuarEntity) && (!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity))
					&& generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 6);
		}));
		this.targetSelector.add(7, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && livingEntity.isInsideWaterOrBubbleColumn() && !(generalPvZombieEntity.getHypno())
					&& !(generalPvZombieEntity instanceof GargantuarEntity) && (!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity))
					&& generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 5);
		}));
		this.targetSelector.add(8, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && livingEntity.isInsideWaterOrBubbleColumn() && !(generalPvZombieEntity.getHypno())
					&& !(generalPvZombieEntity instanceof GargantuarEntity) && (!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity))
					&& generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 4);
		}));
		this.targetSelector.add(9, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && livingEntity.isInsideWaterOrBubbleColumn() && !(generalPvZombieEntity.getHypno())
					&& !(generalPvZombieEntity instanceof GargantuarEntity) && (!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity))
					&& generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 3);
		}));
		this.targetSelector.add(10, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && livingEntity.isInsideWaterOrBubbleColumn() && !(generalPvZombieEntity.getHypno())
					&& !(generalPvZombieEntity instanceof GargantuarEntity) && (!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity))
					&& generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 2);
		}));
		this.targetSelector.add(11, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && livingEntity.isInsideWaterOrBubbleColumn() && !(generalPvZombieEntity.getHypno())
					&& !(generalPvZombieEntity instanceof GargantuarEntity) && (!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity))
					&& generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 1);
		}));
		this.targetSelector.add(12, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && livingEntity.isInsideWaterOrBubbleColumn() && !(generalPvZombieEntity.getHypno())
					&& !(generalPvZombieEntity instanceof GargantuarEntity) && (!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity))
					&& generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 0);
		}));
		this.targetSelector.add(13, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && livingEntity.isInsideWaterOrBubbleColumn() && !(livingEntity instanceof GeneralPvZombieEntity);
		}));
    }


	//Smash
	public boolean tryAttack(Entity target) {
		if (!this.hasStatusEffect(PvZCubed.FROZEN) && target != null) {
			if (this.firstAttack && this.animationTicksLeft <= 0 && (target.isInsideWaterOrBubbleColumn() && !this.dryLand)) {
				this.animationTicksLeft = 65;
				if (!attackLock){
					this.playSound(SoundEvents.ENTITY_PLAYER_SPLASH);
					world.sendEntityStatus(this, (byte) 100);
				}
				this.firstAttack = false;
			}
		}
		return false;
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
			if (this.animationTicksLeft <= 0) {
				BlockPos blockPos2 = this.getBlockPos();
				if (!blockPos2.equals(blockPos)) {
					this.kill();
				}
			}
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		LivingEntity target = this.getTarget();
		if (target != null){
			if (target instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()){
				this.setTarget(null);
			}
			else {
				this.tryAttack(getTarget());
			}
		}
		if (age <= 5){
			this.originalVec3d = this.getPos();
		}
		if (statusSwitch) {
			EntityAttributeInstance maxRangeAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE);
			maxRangeAttribute.removeModifier(MAX_RANGE_UUID);
			statusSwitch = false;
		}
		if (this.animationTicksLeft > 0 && this.animationTicksLeft <= 35 && !this.attackLock) {
			Entity entity = this.getTarget();
			if (entity != null && !this.isInsideWaterOrBubbleColumn()){
				this.setPosition(entity.getX(), entity.getY(), entity.getZ());
			}
			else if (entity != null && this.isInsideWaterOrBubbleColumn()){
				this.setPosition(entity.getX(), entity.getY() - 0.25, entity.getZ());
			}
			if (this.hasVehicle()){
				this.dismountVehicle();
				this.setPosition(this.getX(), getY(), getZ());
			}
		}
		else {
			this.setPosition(this.getX(), this.getY(), this.getZ());
		}
		if (this.animationTicksLeft > 0 && this.animationTicksLeft > 25) {
			Entity entity = this.getTarget();
			if (entity == null) {
				this.firstAttack = true;
				this.stopAnimation = true;
			}
		}
		BlockPos blockPos = this.getBlockPos();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}

		if (this.isInsideWaterOrBubbleColumn()){
			kill();
		}


		if (--amphibiousRaycastDelay >= 0) {
			amphibiousRaycastDelay = 60;
			HitResult hitResult = amphibiousRaycast(0.25);
			if (hitResult.getType() == HitResult.Type.MISS) {
				kill();
			}
			if (this.age != 0) {
				BlockPos blockPos2 = this.getBlockPos();
				BlockState blockState = this.getLandingBlockState();
				FluidState fluidState = world.getFluidState(this.getBlockPos().add(0, -0.5, 0));
				if (!(fluidState.getFluid() == Fluids.WATER)) {
					this.dryLand = true;
					onWater = false;
					this.kill();
				} else {
					this.dryLand = false;
					onWater = true;
				}
				if (!blockPos2.equals(blockPos) || (!(fluidState.getFluid() == Fluids.WATER) && !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.TANGLEKELP_SEED_PACKET);
				}
				this.kill();
				}
			}
		}
	}

	public void mobTick() {
		super.mobTick();
		if (this.animationTicksLeft == 2) {
			this.discard();
		}
		LivingEntity livingEntity = this.getTarget();
		if (livingEntity != null && livingEntity.isInsideWaterOrBubbleColumn()) {
			if (this.animationTicksLeft == 22){
				world.sendEntityStatus(this, (byte) 107);
				this.playSound(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 1.5F, 1.0F);
			}
			if (this.animationTicksLeft == 6) {
				this.attackLock = true;
				world.sendEntityStatus(this, (byte) 107);
				this.playSound(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 1.5F, 1.0F);
				if (getTarget() != null) {
					this.firstAttack = true;
				}

				float damage = 9999f;
				boolean bl2 = livingEntity.damage(DamageSource.mob(this), damage);
				if (bl2) {
					this.applyDamageEffects(this, livingEntity);
				}
				else {
					this.discard();
				}
			}
		}
		if (getTarget() == null){
			this.firstAttack = true;
		}
		if (this.animationTicksLeft > 0) {
			this.stopAnimation = false;
			this.addStatusEffect((new StatusEffectInstance(StatusEffects.RESISTANCE, 999999999, 999999999)));
			--this.animationTicksLeft;
			this.world.sendEntityStatus(this, (byte) 113);
		}
		else{
			this.removeStatusEffect(StatusEffects.RESISTANCE);
			this.world.sendEntityStatus(this, (byte) 112);
		}
		if (this.age == 3) {
			EntityAttributeInstance maxRangeAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE);
			maxRangeAttribute.addPersistentModifier(createRangeAttribute(3.0D));
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.TANGLEKELP_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static EntityAttributeModifier createRangeAttribute(double amount) {
		return new EntityAttributeModifier(
				MAX_RANGE_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static DefaultAttributeContainer.Builder createTangleKelpAttributes() {
        return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0D);
    }

	protected boolean canClimb() {return false;}

	public boolean collides() {return true;}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.075F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return SILENCEVENET;
	}

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


	/** /~*~//~*GOALS*~//~*~/ **/

	private class AttackGoal extends MeleeAttackGoal {
		public AttackGoal() {
			super(TangleKelpEntity.this, 1.0, true);
		}

		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			float f = TangleKelpEntity.this.getWidth() - 0.1F;
			return (double)(f * 2F * f * 2F + entity.getWidth());
		}
	}
}
