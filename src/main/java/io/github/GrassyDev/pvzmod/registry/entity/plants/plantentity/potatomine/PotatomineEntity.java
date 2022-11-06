package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.BombardEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.world.explosions.PvZExplosion;
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
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class PotatomineEntity extends BombardEntity implements IAnimatable {
	private String controllerName = "potatocontroller";
    private static final TrackedData<Integer> FUSE_SPEED;
    private static final TrackedData<Boolean> CHARGED;
    private static final TrackedData<Boolean> IGNITED;
    private int currentFuseTime;
    private int fuseTime = 1;
	private int explosionRadius = 1;
	private int potatoAnimationTime;
	private int potatoPreparingTime;
	private boolean canAnimate;
	private boolean playSoundRise;

	public AnimationFactory factory = new AnimationFactory(this);

	public PotatomineEntity(EntityType<? extends PotatomineEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
		this.potatoPreparingTime = 1200;
		potatoAnimationTime = 10;
		playSoundRise = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FUSE_SPEED, -1);
		this.dataTracker.startTracking(CHARGED, false);
		this.dataTracker.startTracking(IGNITED, false);
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, false);
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		if ((Boolean)this.dataTracker.get(CHARGED)) {
			tag.putBoolean("powered", true);
		}

		tag.putShort("Fuse", (short)this.fuseTime);
		tag.putByte("ExplosionRadius", (byte)this.explosionRadius);
		tag.putBoolean("ignited", this.getIgnited());
		tag.putBoolean("Prepared", this.getPotatoStage());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(CHARGED, tag.getBoolean("powered"));
		if (tag.contains("Fuse", 99)) {
			this.fuseTime = tag.getShort("Fuse");
		}

		if (tag.contains("ExplosionRadius", 99)) {
			this.explosionRadius = tag.getByte("ExplosionRadius");
		}

		if (tag.getBoolean("ignited")) {
			this.ignite();
		}

		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Prepared"));
	}


	static {
		FUSE_SPEED = DataTracker.registerData(PotatomineEntity.class, TrackedDataHandlerRegistry.INTEGER);
		CHARGED = DataTracker.registerData(PotatomineEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IGNITED = DataTracker.registerData(PotatomineEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		RandomGenerator randomGenerator = this.getRandom();
		ItemStack itemStack = Items.POTATO.getDefaultStack();
		if (status == 3) {
			for(int i = 0; i < 96; ++i) {
				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX(), this.getY(), this.getZ(), d, e, f);
				this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX(), this.getY(), this.getZ(), d * -1, e, f * -1);
				this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX(), this.getY(), this.getZ(), d * -1, e, f);
				this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX(), this.getY(), this.getZ(), d, e, f * -1);
			}
		}
		if (status == 6) {
			for(int i = 0; i < 8; ++i) {
				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1) * 0.33;
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1) * 0.33;
				this.world.addParticle(ParticleTypes.SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 8; ++i) {
				double e = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 0.5);
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double)MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + (this.random.range(-1, 1)),
						this.getZ()  + (double)MathHelper.nextBetween(randomGenerator,
								-0.5F, 0.5F), 0, e, 0);
			}
		}
		BlockState blockState = this.getLandingBlockState();
		if (status == 8) {
			for(int i = 0; i < 4; ++i) {
				double d = this.getX() + (double)MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
				double e = this.getY();
				double f = this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
				this.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), d, e, f, 0.0, 0.0, 0.0);
			}
		}
		if (status == 9) {
			canAnimate = true;
		}
		if (status == 7) {
			canAnimate = false;
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(PotatomineEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum PotatoStage {
		UNARMED(false),
		PREPARED(true);

		PotatoStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	private Boolean getPotatoStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	private void setPotatoStage(PotatomineEntity.PotatoStage potatoStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, potatoStage.getId());
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
		if (canAnimate) {
			world.sendEntityStatus(this, (byte) 30);
			event.getController().setAnimation(new AnimationBuilder().addAnimation("potatomine.ready", false));
		}
		else if (this.getPotatoStage()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("potatomine.idle", true));
		} else {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("potatomine.unarmed", true));
		}
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(2, new PotatoIgniteGoal(this));
		this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0D, false));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof HypnoDancingZombieEntity) &&
					!(livingEntity instanceof HypnoFlagzombieEntity);
		}));
	}

	public boolean tryAttack(Entity target) {
		return true;
	}

	public int getFuseSpeed() {
		return (Integer)this.dataTracker.get(FUSE_SPEED);
	}

	public void setFuseSpeed(int fuseSpeed) {
		this.dataTracker.set(FUSE_SPEED, fuseSpeed);
	}

	public boolean getIgnited() {
		return (Boolean)this.dataTracker.get(IGNITED);
	}

	public void ignite() {
		this.dataTracker.set(IGNITED, true);
	}

	private void explode() {
		if (!this.world.isClient) {
			PvZExplosion explosion = new PvZExplosion(world, this, this.getX(), this.getY(), this.getZ(), 1.25f, null, Explosion.DestructionType.NONE);
			this.world.sendEntityStatus(this, (byte) 3);
			this.world.sendEntityStatus(this, (byte) 6);
			this.removeStatusEffect(StatusEffects.RESISTANCE);
			explosion.collectBlocksAndDamageEntities();
			explosion.affectWorld(true);
			Explosion.DestructionType destructionType = Explosion.DestructionType.NONE;
			this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 0, destructionType);
			this.playSound(PvZCubed.POTATOMINEEXPLOSIONEVENT, 1F, 1F);
			this.dead = true;
			this.remove(RemovalReason.KILLED);
			this.spawnEffectsCloud();
		}

	}

	private void spawnEffectsCloud() {
		Collection<StatusEffectInstance> collection = this.getStatusEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
			areaEffectCloudEntity.setRadius(3.5F);
			areaEffectCloudEntity.setRadiusOnUse(-0.5F);
			areaEffectCloudEntity.setWaitTime(10);
			areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
			areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
			Iterator var3 = collection.iterator();

			while(var3.hasNext()) {
				StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var3.next();
				areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
			}

			this.world.spawnEntity(areaEffectCloudEntity);
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
		if (this.isAlive() && this.potatoPreparingTime > 0){
			--this.potatoPreparingTime;
		}
		if (this.isAlive() && this.potatoPreparingTime <= 0 && this.potatoAnimationTime > 0 && !this.getPotatoStage()) {
			--this.potatoAnimationTime;
			this.world.sendEntityStatus(this, (byte) 9);
			this.world.sendEntityStatus(this, (byte) 8);
		}
		if (this.isAlive() && this.potatoPreparingTime <= 0 && this.potatoAnimationTime > 0 && !this.getPotatoStage() && this.playSoundRise) {
			this.playSound(PvZCubed.ENTITYRISINGEVENT, 1.0F, 1.0F);
			this.playSoundRise = false;
		}
		if (this.isAlive() && this.potatoAnimationTime <= 0) {
			this.setPotatoStage(PotatoStage.PREPARED);
			this.world.sendEntityStatus(this, (byte) 7);
		}
		if (this.isAlive() && this.getPotatoStage()) {
			if (this.getIgnited()) {
				this.setFuseSpeed(1);
			}

			int i = this.getFuseSpeed();
			if (i > 0 && this.currentFuseTime == 0) {
				this.addStatusEffect((new StatusEffectInstance(StatusEffects.RESISTANCE, 999999999, 999999999)));
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
			}

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
				removeStatusEffect(StatusEffects.RESISTANCE);
			}

			if (this.currentFuseTime >= this.fuseTime) {
				this.currentFuseTime = this.fuseTime;
				this.explode();
			}
		}
		if (this.getTarget() instanceof Entity && this.getPotatoStage()){
			this.addStatusEffect((new StatusEffectInstance(StatusEffects.RESISTANCE, 999999999, 999999999)));
		}
	}

	public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.clearStatusEffects();
            this.damage(DamageSource.GENERIC, 9999);
        }
    }


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.SMALLSUN) && !this.getPotatoStage()) {
			this.potatoPreparingTime = 0;
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		} else {
			return ActionResult.CONSUME;
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createPotatomineAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 2D)
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
			this.clearStatusEffects();
			return this.damage(DamageSource.player(playerEntity), 9999.0F);
		}
		else {
			this.explode();
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
}
