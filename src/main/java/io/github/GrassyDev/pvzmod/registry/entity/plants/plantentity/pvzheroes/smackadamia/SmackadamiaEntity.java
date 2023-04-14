package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smackadamia;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;
import static io.github.GrassyDev.pvzmod.PvZCubed.ZOMBIE_SIZE;

public class SmackadamiaEntity extends PlantEntity implements IAnimatable {


	private int amphibiousRaycastDelay;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private int attackTicksLeft;

	private String controllerName = "smackcontroller";

	private boolean isTargetFlying;

	public SmackadamiaEntity(EntityType<? extends SmackadamiaEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
		amphibiousRaycastDelay = 1;

		this.setNoGravity(true);
	}

	public SmackadamiaEntity(World world, double x, double y, double z) {
		this(PvZEntity.SMACKADAMIA, world);
		this.setPosition(x, y, z);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 106) {
			this.attackTicksLeft = 20;
		}
		if (status == 108) {
			this.isTargetFlying = true;
		}
		else if (status == 109) {
			this.isTargetFlying = false;
		}
	}

	/**
	 * /~*~//~*GECKOLIB ANIMATION~//~*~//
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
		int i = this.attackTicksLeft;
		LivingEntity livingEntity = this.getTarget();
		if (i <= 0){
			event.getController().setAnimation(new AnimationBuilder().loop("smackadamia.idle"));
		}
		else if (this.isTargetFlying) {
			event.getController().setAnimation(new AnimationBuilder().playOnce("smackadamia.smack2"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().playOnce("smackadamia.smack"));
		}
		return PlayState.CONTINUE;
	}

	/** /~*~//~*AIT*~//~*~// **/

	protected void initGoals() {
		this.goalSelector.add(1, new SmackadamiaEntity.AttackGoal());
		this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 5.0F));
		this.goalSelector.add(2, new LookAtEntityGoal(this, GeneralPvZombieEntity.class, 15.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					(generalPvZombieEntity.isFlying() || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("gargantuar") || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("big")|| ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("tall"));
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity));
		}));
		this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
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
			super(SmackadamiaEntity.this, 1.0, true);
		}

		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			float f = SmackadamiaEntity.this.getWidth() - 0.1F;
			return (double)(f * 3.5F * f * 3.5F + entity.getWidth());
		}
	}

	public boolean tryAttack(Entity target) {
		int i = this.attackTicksLeft;
		if ((target instanceof GeneralPvZombieEntity generalPvZombieEntity &&
				(generalPvZombieEntity.isFlying() || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("gargantuar") || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("big")|| ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("tall"))
				&& target.squaredDistanceTo(this) <= 25) ||
		this.squaredDistanceTo(target) <= 1.5625) {
			ZombiePropEntity passenger = null;
			for (Entity entity1 : target.getPassengerList()) {
				if (entity1 instanceof ZombiePropEntity zpe) {
					passenger = zpe;
				}
			}
			Entity damaged = target;
			if (passenger != null && !(passenger instanceof ZombieShieldEntity)) {
				damaged = passenger;
			}
			if (i <= 0) {
				this.attackTicksLeft = 20;
				this.world.sendEntityStatus(this, (byte) 106);
				boolean bl = damaged.damage(DamageSource.mob(this), this.getAttackDamage());
				if (bl) {
					this.applyDamageEffects(this, target);
				}
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(damaged.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic" -> PvZCubed.BUCKETHITEVENT;
					case "plastic" -> PvZCubed.CONEHITEVENT;
					case "stone" -> PvZCubed.STONEHITEVENT;
					default -> PvZCubed.PEAHITEVENT;
				};
				target.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
				this.chomperAudioDelay = 3;
				return bl;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


	/**
	 * //~*~//~POSITION~//~*~//
	 **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age > 1) {
			BlockPos blockPos2 = this.getBlockPos();
			if (!blockPos2.equals(blockPos)) {
				this.discard();
			}

		}
	}


	/**
	 * //~*~//~TICKING~//~*~//
	 **/

	private int chomperAudioDelay = -1;

	public void tick() {
		super.tick();
		if (!(this.getTarget() instanceof GeneralPvZombieEntity generalPvZombieEntity &&
				(generalPvZombieEntity.isFlying() || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("gargantuar") || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("big")|| ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("tall")))){
			if (this.getTarget() != null && this.getTarget().squaredDistanceTo(this) > 1.5625){
				this.setTarget(null);
			}
		}
		if (--this.chomperAudioDelay == 0){
			this.playSound(PvZCubed.PEASHOOTEVENT, 1.0F, 1.0F);
		}
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		BlockPos blockPos = this.getBlockPos();
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
				onWater = fluidState.getFluid() == Fluids.WATER;
				if (!blockPos2.equals(blockPos) || (!(fluidState.getFluid() == Fluids.WATER) && !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
					if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
						this.dropItem(ModItems.SMACKADAMIA_SEED_PACKET);
					}
					this.discard();
				}
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}

		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
		}
	}

	@Override
	protected void mobTick() {
		super.mobTick();
		if (this.getTarget() instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.isFlying() || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("gargantuar") || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("big")|| ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("tall"))){
			world.sendEntityStatus(this, (byte) 108);
		}
		else {
			world.sendEntityStatus(this, (byte) 109);
		}
	}

	/**
	 * /~*~//~*INTERACTION*~//~*~/
	 **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.SMACKADAMIA_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.SMACKADAMIA_SEED_PACKET.getDefaultStack();
	}


	/**
	 * //~*~//~ATTRIBUTES~//~*~//
	 **/

	public static DefaultAttributeContainer.Builder createSmackadamiaAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 65.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 4D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9.0D);
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

	private float getAttackDamage() {
		return (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
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


	/**
	 * //~*~//~DAMAGE HANDLER~//~*~//
	 **/

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
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}
}
