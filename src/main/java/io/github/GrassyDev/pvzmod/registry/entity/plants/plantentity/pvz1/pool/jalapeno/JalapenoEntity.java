package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
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
import net.minecraft.sound.SoundEvents;
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

import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.ZOMBIE_STRENGTH;

public class JalapenoEntity extends PlantEntity implements IAnimatable {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private static final TrackedData<Integer> FUSE_SPEED;
	private static final TrackedData<Boolean> CHARGED;
	private static final TrackedData<Boolean> IGNITED;
	private int currentFuseTime;
	private int fuseTime = 30;
	private int explosionRadius = 1;
	private String controllerName = "bombcontroller";

	public JalapenoEntity(EntityType<? extends JalapenoEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FUSE_SPEED, -1);
		this.dataTracker.startTracking(CHARGED, false);
		this.dataTracker.startTracking(IGNITED, false);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if ((Boolean) this.dataTracker.get(CHARGED)) {
			nbt.putBoolean("powered", true);
		}

		nbt.putShort("Fuse", (short) this.fuseTime);
		nbt.putByte("ExplosionRadius", (byte) this.explosionRadius);
		nbt.putBoolean("ignited", this.getIgnited());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(CHARGED, nbt.getBoolean("powered"));
		if (nbt.contains("Fuse", 99)) {
			this.fuseTime = nbt.getShort("Fuse");
		}

		if (nbt.contains("ExplosionRadius", 99)) {
			this.explosionRadius = nbt.getByte("ExplosionRadius");
		}

		if (nbt.getBoolean("ignited")) {
			this.ignite();
		}

	}

	static {
		FUSE_SPEED = DataTracker.registerData(JalapenoEntity.class, TrackedDataHandlerRegistry.INTEGER);
		CHARGED = DataTracker.registerData(JalapenoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IGNITED = DataTracker.registerData(JalapenoEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2){
			super.handleStatus(status);
		}
	}


	/**
	 * /~*~//~*GECKOLIB ANIMATION*~//~*~/
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
		int i = this.getFuseSpeed();
		if (i > 0) {
			event.getController().setAnimation(new AnimationBuilder().playOnce("jalapeno.explosion"));
		} else {
			event.getController().setAnimation(new AnimationBuilder().loop("jalapeno.idle"));
		}
		return PlayState.CONTINUE;
	}


	/**
	 * /~*~//~*AI*~//~*~/
	 **/

	protected void initGoals() {
		int i = this.getFuseSpeed();
		this.goalSelector.add(2, new JalapenoIgniteGoal(this));
		this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0D, false));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 11);
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 10);
		}));
		this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 9);
		}));
		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 8);
		}));
		this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 7);
		}));
		this.targetSelector.add(6, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 6);
		}));
		this.targetSelector.add(7, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 5);
		}));
		this.targetSelector.add(8, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 4);
		}));
		this.targetSelector.add(9, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 3);
		}));
		this.targetSelector.add(10, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 2);
		}));
		this.targetSelector.add(11, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 1);
		}));
		this.targetSelector.add(12, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) &&
					!(generalPvZombieEntity.isFlying()) &&
					(ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0) == 0);
		}));
		this.targetSelector.add(13, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity);
		}));
	}

	public boolean tryAttack(Entity target) {
		return true;
	}

	public int getFuseSpeed() {
		return (Integer) this.dataTracker.get(FUSE_SPEED);
	}

	public void setFuseSpeed(int fuseSpeed) {
		this.dataTracker.set(FUSE_SPEED, fuseSpeed);
	}

	public boolean getIgnited() {
		return (Boolean) this.dataTracker.get(IGNITED);
	}

	public void ignite() {
		this.dataTracker.set(IGNITED, true);
	}

	private float boxOffset;
	List<LivingEntity> checkList = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().shrink(0.5, 0, 0));

	private void raycastExplode() {
		Vec3d vec3d2 = new Vec3d((double) boxOffset, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1, 4, 1).offset(vec3d2).offset(0, -1.5, 0));
		Vec3d vec3d3 = this.getBoundingBox().offset(vec3d2).getCenter();
		FireTrailEntity fireTrailEntity = new FireTrailEntity(PvZEntity.FIRETRAIL, this.world);
		fireTrailEntity.updatePositionAndAngles(vec3d3.getX(), this.getBlockY() + 1, vec3d3.getZ(), this.bodyYaw, 0.0F);
		List<SunflowerEntity> listFlames = this.world.getNonSpectatingEntities(SunflowerEntity.class, fireTrailEntity.getBoundingBox());
		if (listFlames.isEmpty()) {
			world.spawnEntity(fireTrailEntity);
		}
		if (!fireTrailEntity.isWet()) {
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
				} while (this.squaredDistanceTo(livingEntity) > 100);

				float damage = 180;

				if (((livingEntity instanceof Monster &&
						!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()) &&
						!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
								&& (generalPvZombieEntity.getHypno()))) && checkList != null && !checkList.contains(livingEntity))) {
					ZombiePropEntity zombiePropEntity2 = null;
					for (Entity entity1 : livingEntity.getPassengerList()) {
						if (entity1 instanceof ZombiePropEntity zpe) {
							zombiePropEntity2 = zpe;
						}
					}
					if (damage > livingEntity.getHealth() &&
							!(livingEntity instanceof ZombieShieldEntity) &&
							livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - livingEntity.getHealth();
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this), damage2);
						checkList.add(livingEntity);
						checkList.add(generalPvZombieEntity);
					} else if (zombiePropEntity2 instanceof ZombieShieldEntity) {
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						checkList.add(livingEntity);
					} else {
						if (livingEntity instanceof ZombiePropEntity zombiePropEntity && livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity) {
							String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(zombiePropEntity.getType()).orElse("flesh");
							if ("paper".equals(zombieMaterial)) {
								livingEntity.kill();
							} else {
								livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
							}
							checkList.add(livingEntity);
							if (!(livingEntity instanceof ZombieShieldEntity)) {
								checkList.add(generalPvZombieEntity);
							}
						} else if ((zombiePropEntity2 == null)
								&& !checkList.contains(livingEntity)) {
							livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
							checkList.add(livingEntity);
						}
					}

					if ((zombiePropEntity2 == null ||
							zombiePropEntity2 instanceof ZombieShieldEntity) && !livingEntity.hasStatusEffect(PvZCubed.WET) && !livingEntity.isWet()) {
						livingEntity.removeStatusEffect(PvZCubed.FROZEN);
						livingEntity.removeStatusEffect(PvZCubed.ICE);
						livingEntity.setOnFireFor(4);
						this.world.sendEntityStatus(this, (byte) 3);
						this.remove(RemovalReason.DISCARDED);
					}
					if (!(livingEntity instanceof ZombieShieldEntity)) {
						livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 40, 1)));
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
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.JALAPENO_SEED_PACKET);
				}
				this.kill();
			}

		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/


	public void tick() {
		super.tick();
		RandomGenerator randomGenerator = this.getRandom();
		if (this.isWet()){
			this.setTarget(null);
		}
		if (this.getTarget() != null){
			this.getLookControl().lookAt(this.getTarget(), 90.0F, 90.0F);
		}
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		if (this.isAlive()) {
			if (this.getIgnited()) {
				this.setFuseSpeed(1);
			}

			int i = this.getFuseSpeed();
			if (i > 0 && this.currentFuseTime == 0) {
				this.addStatusEffect((new StatusEffectInstance(StatusEffects.RESISTANCE, 999999999, 999999999)));
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
				for(int j = 0; j < 4; ++j) {
					double e = (double)MathHelper.nextBetween(randomGenerator, 0.025F, 0.075F);
					this.world.addParticle(ParticleTypes.SMALL_FLAME, this.getX(), this.getY() + 0.75, this.getZ(), 0, e, 0);
				}
			}

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
				removeStatusEffect(StatusEffects.RESISTANCE);
			}

			if (this.currentFuseTime >= this.fuseTime) {
				this.currentFuseTime = this.fuseTime;
				this.playSound(PvZCubed.JALAPENOEXPLOSIONEVENT, 3F, 1F);
				if (!this.isWet()){
					for (int u = -9; u < 10; ++u) {
						this.boxOffset = (float) u;
						this.raycastExplode();
					}
				}
				this.world.sendEntityStatus(this, (byte) 106);
				this.dead = true;
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}


	public void tickMovement() {
        super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.clearStatusEffects();
            this.kill();
        }
    }


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.JALAPENO_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createJalapenoAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 180);
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
		return null	;
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
			this.clearStatusEffects();
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
}
