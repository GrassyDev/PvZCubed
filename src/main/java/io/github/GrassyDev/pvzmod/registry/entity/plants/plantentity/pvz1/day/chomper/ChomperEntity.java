package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.ChomperVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
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

public class ChomperEntity extends PlantEntity implements IAnimatable {

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(ChomperEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private int attackTicksLeft;
    public boolean notEating;
    public boolean eatingShield;
	private String controllerName = "chompcontroller";

    public ChomperEntity(EntityType<? extends ChomperEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
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
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public ChomperVariants getVariant() {
		return ChomperVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(ChomperVariants variant) {
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
            event.getController().setAnimation(new AnimationBuilder().playOnce("chomper.chomp3"));
        }
        else if (i <= 0) {
            event.getController().setAnimation(new AnimationBuilder().loop("chomper.idle"));
        }
        else if (this.notEating) {
            event.getController().setAnimation(new AnimationBuilder().playOnce("chomper.chomp2"));
        }
        else if (i > 0) {
            event.getController().setAnimation(new AnimationBuilder().playOnce("chomper.chomp"));
        }
        return PlayState.CONTINUE;
    }

	/** /~*~//~**~//~*~// **/

	protected void initGoals() {
		this.goalSelector.add(1, new ChomperEntity.AttackGoal());
		this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 5.0F));
		this.goalSelector.add(2, new LookAtEntityGoal(this, GeneralPvZombieEntity.class, 15.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					!(livingEntity instanceof ZombiePropEntity) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel());
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity);
		}));
		snorkelGoal();
	}
	protected void snorkelGoal() {
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel() && !(snorkelEntity.getHypno());
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
		ZombiePropEntity passenger = null;
		for (Entity entity1 : target.getPassengerList()) {
			if (entity1 instanceof ZombiePropEntity zpe) {
				passenger = zpe;
			}
		}
		if (passenger instanceof ZombieShieldEntity zombieShieldEntity) {
			if (i <= 0) {
				this.attackTicksLeft = 200;
				this.world.sendEntityStatus(this, (byte) 5);
				boolean bl = zombieShieldEntity.damage(DamageSource.mob(this), 999);
				if (bl) {
					this.applyDamageEffects(this, target);
				}
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(passenger.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic" -> PvZCubed.BUCKETHITEVENT;
					case "plastic" -> PvZCubed.CONEHITEVENT;
					default -> PvZCubed.PEAHITEVENT;
				};
				passenger.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
				this.chomperAudioDelay = 3;
				return bl;
			} else {
				return false;
			}
		}
		if (target instanceof GraveEntity ||
				target instanceof ImpEntity ||
		        target instanceof GargantuarEntity) {
			Entity damaged = target;
			if (passenger != null){
				damaged = passenger;
			}
			if (i <= 0) {
				this.attackTicksLeft = 30;
				this.world.sendEntityStatus(this, (byte) 6);
				boolean bl = damaged.damage(DamageSource.mob(this), 32);
				if (bl) {
					this.applyDamageEffects(this, target);
				}
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(target.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic" -> PvZCubed.BUCKETHITEVENT;
					case "plastic" -> PvZCubed.CONEHITEVENT;
					default -> PvZCubed.PEAHITEVENT;
				};
				target.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
				this.chomperAudioDelay = 3;
				return bl;
			} else {
				return false;
			}
		}
		else {
			if (i <= 0) {
				this.attackTicksLeft = 200;
				this.world.sendEntityStatus(this, (byte) 4);
				boolean bl = target.damage(DamageSource.mob(this), 999);
				if (bl) {
					this.applyDamageEffects(this, target);
				}
				this.chomperAudioDelay = 3;
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
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.CHOMPER_SEED_PACKET);
				}
				this.kill();
			}

		}
	}


	/** //~*~//~TICKING~//~*~// **/

	private int chomperAudioDelay = -1;

	public void tick() {
		super.tick();
		if (--this.chomperAudioDelay == 0){
			this.playSound(PvZCubed.CHOMPERBITEVENT, 1.0F, 1.0F);
		}
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

		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
		}

		if (this.attackTicksLeft <= 0){
			this.eatingShield = false;
			this.notEating = false;
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (!this.getVariant().equals(ChomperVariants.DEFAULT) && itemStack.isOf(Items.WHITE_DYE)) {
			this.setVariant(ChomperVariants.DEFAULT);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(ChomperVariants.ENBY) &&
				(itemStack.isOf(Items.BLACK_DYE) || itemStack.isOf(Items.YELLOW_DYE) || itemStack.isOf(Items.PURPLE_DYE))) {
			this.setVariant(ChomperVariants.ENBY);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(ChomperVariants.DEMIGIRL) &&
				(itemStack.isOf(Items.GRAY_DYE) || itemStack.isOf(Items.LIGHT_GRAY_DYE) || itemStack.isOf(Items.PINK_DYE))) {
			this.setVariant(ChomperVariants.DEMIGIRL);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(ChomperVariants.PIRANHAPLANT) &&
				(itemStack.isOf(Items.FIRE_CHARGE) || itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.HOPPER))) {
			this.setVariant(ChomperVariants.PIRANHAPLANT);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else {
			return ActionResult.CONSUME;
		}
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.CHOMPER_SEED_PACKET.getDefaultStack();
	}


	/** //~*~//~ATTRIBUTES~//~*~// **/

	public static DefaultAttributeContainer.Builder createChomperAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 36.0D)
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
			this.playSound(PvZCubed.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.kill();
		}
		this.playBlockFallSound();
		return true;
	}
}
