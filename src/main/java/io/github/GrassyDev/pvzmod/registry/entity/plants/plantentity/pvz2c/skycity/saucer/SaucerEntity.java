package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.saucer;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class SaucerEntity extends PlantEntity implements IAnimatable {


	private int amphibiousRaycastDelay;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private int attackTicksLeft;

	private String controllerName = "smackcontroller";

	public SaucerEntity(EntityType<? extends SaucerEntity> entityType, World world) {
		super(entityType, world);
		this.ignoreCameraFrustum = true;
		amphibiousRaycastDelay = 1;

		this.setNoGravity(true);
	}

	public SaucerEntity(World world, double x, double y, double z) {
		this(PvZEntity.SAUCER, world);
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
			RandomGenerator randomGenerator = this.getRandom();
			for(int i = 0; i < 2; ++i) {
				double d = this.random.nextDouble() / 2 * 0.75;
				double f = this.random.nextDouble() / 2 * 0.75;
				double d1 = this.random.nextDouble() / 2 * 0.75;
				double f1 = this.random.nextDouble() / 2 * 0.75;
				double d2 = this.random.nextDouble() / 2 * 0.75;
				double f2 = this.random.nextDouble() / 2 * 0.75;
				double d3 = this.random.nextDouble() / 2 * 0.75;
				double f3 = this.random.nextDouble() / 2 * 0.75;
				this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						d, 0, f);
				this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						d1, 0, f1 * -1);
				this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						d2 * -1, 0, f2 * -1);
				this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						d3 * -1, 0, f3);
			}
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
		event.getController().setAnimation(new AnimationBuilder().loop("loquat.idle"));
		return PlayState.CONTINUE;
	}

	/** /~*~//~*AIT*~//~*~// **/

	protected void initGoals() {
		this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 5.0F));
		this.goalSelector.add(2, new LookAtEntityGoal(this, GeneralPvZombieEntity.class, 15.0F));
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

	private void damageEntity() {
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
			} while (this.squaredDistanceTo(livingEntity) > 1);

			ZombiePropEntity zombiePropEntity2 = null;
			for (Entity entity1 : livingEntity.getPassengerList()) {
				if (entity1 instanceof ZombiePropEntity zpe) {
					zombiePropEntity2 = zpe;
				}
			}
			if ((livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("small") || (zombiePropEntity2 == null || zombiePropEntity2 instanceof ZombieShieldEntity))) && !(ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("gargantuar") || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("big") || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("tall")) && !generalPvZombieEntity.isFlying() && !(livingEntity instanceof ZombiePropEntity) && !generalPvZombieEntity.isCovered()){
				livingEntity.setVelocity(0, 0, 0);
				livingEntity.addVelocity(0, 0.5, 0);
				livingEntity.kill();
				this.attacking = true;
			}
			else if ((livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1 &&
							generalPvZombieEntity1.isFlying())) && !livingEntity.isInsideWaterOrBubbleColumn() && !(livingEntity instanceof ZombieShieldEntity) &&
					!(ZOMBIE_SIZE.get(livingEntity.getType()).orElse("medium").equals("gargantuar") || ZOMBIE_SIZE.get(livingEntity.getType()).orElse("medium").equals("big"))) {
				if (zombiePropEntity2 == null ||
						zombiePropEntity2 instanceof ZombieShieldEntity) {
					livingEntity.playSound(PvZCubed.PEAHITEVENT, 0.1F, (float) (0.5F + Math.random()));
					if (!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(DISABLE)){
						livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.STUN, 20, 5)));
					}
					this.zombieList.add(livingEntity);
					this.attacking = true;
				}
			}
			else {
				this.zombieList.remove(livingEntity);
			}
		}
	}
	protected boolean attacking = false;
	protected List<LivingEntity> zombieList = new ArrayList<>();

	private int tickDamage = 20;
	private int tickPermanency = 200;

	public void tick() {
		super.tick();
		if (attacking) {
			this.world.sendEntityStatus(this, (byte) 106);
			if (--tickPermanency <= 0) {
				this.discard();
			}
		}
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		if (--tickDamage <= 0){
			this.zombieList.clear();
			this.damageEntity();
			if (this.attacking){
				tickDamage = 20;
			}
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
						this.dropItem(ModItems.SAUCER_SEED_PACKET);
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
	}

	/**
	 * /~*~//~*INTERACTION*~//~*~/
	 **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.SAUCER_SEED_PACKET);
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
		return ModItems.SAUCER_SEED_PACKET.getDefaultStack();
	}


	/**
	 * //~*~//~ATTRIBUTES~//~*~//
	 **/

	public static DefaultAttributeContainer.Builder createSaucerAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
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
