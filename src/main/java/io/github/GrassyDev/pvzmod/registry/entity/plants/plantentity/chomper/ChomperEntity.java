package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.chomper;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.basicgrave.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.nightgrave.NightGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.EnforceEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.ChomperVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor.ScreendoorEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
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

import java.util.*;

public class ChomperEntity extends EnforceEntity implements IAnimatable {

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(ChomperEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public AnimationFactory factory = new AnimationFactory(this);
    public int healingTime;
    private int attackTicksLeft;
    public boolean notEating;
    public boolean eatingShield;
	private String controllerName = "chompcontroller";

    public ChomperEntity(EntityType<? extends ChomperEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 600;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		//Variant//
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		//Variant//
		tag.putInt("Variant", this.getTypeVariant());
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 4) {
			this.attackTicksLeft = 200;
			this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
			this.eatingShield = false;
			this.notEating = false;
		}
		if (status == 5) {
			this.attackTicksLeft = 200;
			this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
			this.eatingShield = true;
			this.notEating = false;
		}
		else if (status == 6) {
			this.attackTicksLeft = 30;
			this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
			this.eatingShield = false;
			this.notEating = true;
		}else {
			super.handleStatus(status);
		}
	}


	/** /~*~//~*VARIANTS~//~*~// **/

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		ChomperVariants variant = Util.getRandom(ChomperVariants.values(), this.random);
		setVariant(variant);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public ChomperVariants getVariant() {
		return ChomperVariants.byId(this.getTypeVariant() & 255);
	}

	private void setVariant(ChomperVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
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
        int i = this.attackTicksLeft;
        if (this.eatingShield) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("chomper.chomp3", false));
        }
        else if (i <= 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("chomper.idle", true));
        }
        else if (this.notEating) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("chomper.chomp2", false));
        }
        else if (i > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("chomper.chomp", false));
        }
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI~//~*~// **/

	protected void initGoals() {
		this.goalSelector.add(1, new ChomperEntity.AttackGoal());
		this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 5.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
					!(livingEntity instanceof HypnoFlagzombieEntity);
		}));
	}

	private class AttackGoal extends MeleeAttackGoal {
		public AttackGoal() {
			super(ChomperEntity.this, 1.0, true);
		}

		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			float f = ChomperEntity.this.getWidth() - 0.1F;
			return (double)(f * 2.8F * f * 2.8F + entity.getWidth());
		}
	}

	public boolean tryAttack(Entity target) {
		int i = this.attackTicksLeft;
		if (target instanceof ScreendoorEntity) {
			if (i <= 0) {
				this.attackTicksLeft = 200;
				this.world.sendEntityStatus(this, (byte) 5);
				float f = 153f;
				boolean bl = target.damage(DamageSource.mob(this), f);
				if (bl) {
					this.applyDamageEffects(this, target);
				}
				this.playSound(PvZCubed.CHOMPERBITEVENT, 1.0F, 1.0F);
				return bl;
			} else {
				return false;
			}
		}
		else if ((target instanceof BasicGraveEntity) ||
				(target instanceof NightGraveEntity) ||
				(target instanceof GargantuarEntity) ||
				(target instanceof ImpEntity)) {
			if (i <= 0) {
				this.attackTicksLeft = 30;
				this.world.sendEntityStatus(this, (byte) 6);
				float f = 32f;
				boolean bl = target.damage(DamageSource.mob(this), f);
				if (bl) {
					this.applyDamageEffects(this, target);
				}
				this.playSound(PvZCubed.CHOMPERBITEVENT, 1.0F, 1.0F);
				return bl;
			} else {
				return false;
			}
		}
		else {
			if (i <= 0) {
				this.attackTicksLeft = 200;
				this.world.sendEntityStatus(this, (byte) 4);
				float f = this.getAttackDamage();
				boolean bl = target.damage(DamageSource.mob(this), f);
				if (bl) {
					this.applyDamageEffects(this, target);
				}
				this.playSound(PvZCubed.CHOMPERBITEVENT, 1.0F, 1.0F);
				return bl;
			} else {
				return false;
			}
		}
	}


	/** //~*~//~POSITION~//~*~// **/

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


	/** //~*~//~TICKING~//~*~// **/

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

		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
		}

		if (this.attackTicksLeft <= 0){
			this.eatingShield = false;
			this.notEating = false;
		}
	}


	/** //~*~//~ATTRIBUTES~//~*~// **/

	public static DefaultAttributeContainer.Builder createChomperAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 45.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 3.5D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 999.0D);
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

	public void onDeath(DamageSource source) {
		super.onDeath(source);
	}

	protected void pushAway(Entity entity) {
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
			this.playSound(PvZCubed.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.damage(DamageSource.GENERIC, 9999);
		}
		this.playBlockFallSound();
		return true;
	}


	/** //~*~//~SPAWNING~//~*~// **/

	public static boolean canChomperSpawn(EntityType<ChomperEntity> entity, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
		return pos.getY() > 60;
	}

	@Override
	public boolean canSpawn(WorldView worldreader) {
		return worldreader.doesNotIntersectEntities(this, VoxelShapes.cuboid(this.getBoundingBox()));
	}
}
