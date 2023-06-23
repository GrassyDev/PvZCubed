package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.squash;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
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
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
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
import net.minecraft.world.RaycastContext;
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
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;
import static io.github.GrassyDev.pvzmod.registry.PvZSounds.SILENCEVENET;
import static io.github.GrassyDev.pvzmod.registry.PvZSounds.SQUASHHUMEVENT;

public class SquashEntity extends PlantEntity implements IAnimatable {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private int animationTicksLeft;
	public boolean firstAttack;
	public boolean inAnimation;
	public boolean attackLock;
	public boolean statusSwitch = true;
	private String controllerName = "chompcontroller";
	public static final UUID MAX_RANGE_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));
	private boolean stopAnimation;

	public Vec3d originalVec3d = this.getPos();

	public SquashEntity(EntityType<? extends SquashEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
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
	}


	/** /~*~//~*GECKOLIB ANIMATION~//~*~// **/

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
		if (inAnimation && !stopAnimation){
			event.getController().setAnimation(new AnimationBuilder().playOnce("squash.smash"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("squash.idle"));
		}
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI*~//~*~// **/

	protected void initGoals() {
		this.goalSelector.add(1, new SquashEntity.AttackGoal());
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25  &&
					!(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 11);
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					!(generalPvZombieEntity.getHypno()) &&
			(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 10);
		}));
		this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					!(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 9);
		}));
		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					!(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 8);
		}));
		this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					!(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 7);
		}));
		this.targetSelector.add(6, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					!(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 6);
		}));
		this.targetSelector.add(7, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					!(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 5);
		}));
		this.targetSelector.add(8, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					!(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 4);
		}));
		this.targetSelector.add(9, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					!(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 3);
		}));
		this.targetSelector.add(10, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					!(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 2);
		}));
		this.targetSelector.add(11, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					!(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 1);
		}));
		this.targetSelector.add(12, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.squaredDistanceTo(originalVec3d) <= 25 &&
					!(generalPvZombieEntity.isFlying()) &&
					!(generalPvZombieEntity.getHypno()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 0);
		}));
		this.targetSelector.add(13, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity);
		}));
	}

	//Smash
	public boolean tryAttack(Entity target) {
		if (!this.hasStatusEffect(PvZCubed.FROZEN)) {
			if (this.firstAttack && this.animationTicksLeft <= 0 && (target.isOnGround() || target.isInsideWaterOrBubbleColumn())) {
				this.animationTicksLeft = 55;
				if (!attackLock){
					this.playSound(SQUASHHUMEVENT);
				}
				this.firstAttack = false;
			}
		}
		return false;
	}
	List<LivingEntity> checkList = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().shrink(0.5, 0, 0));

	protected void splashDamage() {
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1));
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
			} while (this.squaredDistanceTo(livingEntity) > 6);

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
				float damage = 180;
				if (((livingEntity instanceof Monster &&
						!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
								&& (generalPvZombieEntity.getHypno()))) && checkList != null && !checkList.contains(livingEntity) &&
						!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
								generalPvZombieEntity.isFlying()))) {
					if (damage > livingEntity.getHealth() &&
							!(livingEntity instanceof ZombieShieldEntity) &&
							livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity) {
						float damage2 = damage - livingEntity.getHealth();
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this), damage2);
						checkList.add(livingEntity);
						checkList.add(generalPvZombieEntity);
					} else {
						if (livingEntity instanceof ZombiePropEntity zombiePropEntity && livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity) {
							livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
							checkList.add(livingEntity);
							checkList.add(generalPvZombieEntity);
						}
						else {
							livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
							checkList.add(livingEntity);
						}
					}
				}
			}
		}
	}


	/** //~*~//~POSITION~//~*~// **/

	public void newPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double) MathHelper.floor(y + 0.5), (double) MathHelper.floor(z) + 0.5);
		}

		if (this.age != 0) {
			if (this.animationTicksLeft <= 0) {
				BlockPos blockPos2 = this.getBlockPos();
				BlockState blockState = this.getLandingBlockState();
				if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
					if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
						this.dropItem(ModItems.SQUASH_SEED_PACKET);
					}
				this.discard();
				}
			}
		}
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (age <= 5){
			this.originalVec3d = this.getPos();
		}
		if (statusSwitch) {
			EntityAttributeInstance maxRangeAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE);
			maxRangeAttribute.removeModifier(MAX_RANGE_UUID);
			statusSwitch = false;
		}
		if (this.animationTicksLeft > 0 && this.animationTicksLeft <= 25 && !this.attackLock) {
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
			this.newPosition(this.getX(), this.getY(), this.getZ());
		}
		if (this.animationTicksLeft > 0 && this.animationTicksLeft > 25) {
			Entity entity = this.getTarget();
			if (entity == null) {
				this.animationTicksLeft = 0;
				this.firstAttack = true;
				this.stopAnimation = true;
			}
		}
		if (this.getTarget() != null){
			this.tryAttack(getTarget());
		}
	}

	public void mobTick() {
		super.mobTick();
		if (this.animationTicksLeft == 1) {
			this.discard();
		}
		if (this.animationTicksLeft == 9 && !this.isInsideWaterOrBubbleColumn()) {
			this.attackLock = true;
			this.playSound(PvZSounds.GARGANTUARSMASHEVENT, 1F, 1.0F);
			if (getTarget() != null) {
				this.firstAttack = true;
			}
			this.splashDamage();
		}
		else if (this.animationTicksLeft == 9 && this.isInsideWaterOrBubbleColumn()) {
			world.sendEntityStatus(this, (byte) 107);
			this.attackLock = true;
			this.playSound(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 1.5F, 1.0F);
			if (getTarget() != null) {
				this.firstAttack = true;
			}
			this.splashDamage();
			this.discard();
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
			maxRangeAttribute.addPersistentModifier(createRangeAttribute(4.0D));
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0 && this.animationTicksLeft <= 0) {
			this.discard();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.SQUASH_SEED_PACKET.getDefaultStack();
	}


	/** //~*~//~ATTRIBUTES~//~*~// **/

	public static EntityAttributeModifier createRangeAttribute(double amount) {
		return new EntityAttributeModifier(
				MAX_RANGE_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static DefaultAttributeContainer.Builder createSquashAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0D);
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

	private float getAttackDamage(){
		return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return SILENCEVENET;
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


	/** //~*~//~DAMAGE HANDLER~//~*~// **/

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


	/** /~*~//~*GOALS*~//~*~/ **/

	private class AttackGoal extends MeleeAttackGoal {
		public AttackGoal() {
			super(SquashEntity.this, 1.0, true);
		}

		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			float f = SquashEntity.this.getWidth() - 0.1F;
			return (double)(f * 2F * f * 2F + entity.getWidth());
		}
	}
}
