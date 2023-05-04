package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.tulimpeter;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.hypnoproj.HypnoProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.jingle.JingleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.TulipVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class TulimpeterEntity extends PlantEntity implements IAnimatable, RangedAttackMob {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private String controllerName = "tulipcontroller";


	private boolean isFiring;

    public TulimpeterEntity(EntityType<? extends TulimpeterEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 111) {
			this.isFiring = true;
		} else if (status == 110) {
			this.isFiring = false;
		}
		if (status == 116) {
			for(int i = 0; i < 128; ++i) {
				this.world.addParticle(ParticleTypes.WAX_ON, this.getX() + (this.random.range(-4, 4)), this.getY() + (this.random.range(-4, 4)), this.getZ() + (this.random.range(-4, 4)), 0, 0, 0);
			}
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(TulimpeterEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public TulipVariants getVariant() {
		return TulipVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(TulipVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
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
			if (this.getVariant().equals(TulipVariants.HEAL)){
				event.getController().setAnimation(new AnimationBuilder().playOnce("tulimpeter.shoot2"));
			}
			else {
				event.getController().setAnimation(new AnimationBuilder().playOnce("tulimpeter.shoot"));}
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("tulimpeter.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new TulimpeterEntity.FireBeamGoal(this));
		this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) && !(generalPvZombieEntity.isFlying())) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity)) &&
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

	@Override
	public void attack(LivingEntity target, float pullProgress) {
	}


	protected List<HostileEntity> checkForZombiesHEAL() {
		List<HostileEntity> list = this.world.getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(10));
		List<HostileEntity> list2 = new ArrayList<>();
		Iterator var9 = list.iterator();
		while (true) {
			HostileEntity hostileEntity;
			if (!var9.hasNext()) {
				return list2;
			}
			hostileEntity = (HostileEntity) var9.next();

			if (hostileEntity.squaredDistanceTo(this) <= 36) {
				if (!(hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
					list2.add(hostileEntity);
				}
			}
		}
	}


	protected List<HostileEntity> checkForZombiesHYPNO() {
		List<HostileEntity> list = this.world.getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(15));
		List<HostileEntity> list2 = new ArrayList<>();
		Iterator var9 = list.iterator();
		while (true) {
			HostileEntity hostileEntity;
			if (!var9.hasNext()) {
				return list2;
			}
			hostileEntity = (HostileEntity) var9.next();

			if (hostileEntity.squaredDistanceTo(this) <= 100) {
				list2.add(hostileEntity);
			}
		}
	}


	protected List<PlantEntity> healPlants() {
		List<PlantEntity> list = this.world.getNonSpectatingEntities(PlantEntity.class, this.getBoundingBox().expand(5));
		Iterator var9 = list.iterator();
		while (true) {
			PlantEntity plantEntity;
			do {
				if (!var9.hasNext()) {
					return list;
				}
				plantEntity = (PlantEntity) var9.next();
			} while (this.squaredDistanceTo(plantEntity) > 16);

			list.add(plantEntity);
			return list;
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

		if (this.age > 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.SCAREDYSHROOM_SEED_PACKET);
				}
				this.discard();
			}

		}
	}

	/** /~*~//~**TICKING**~//~*~/ **/

	boolean sleepSwitch = false;
	boolean awakeSwitch = false;

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
			if (target instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()){
				this.setTarget(null);
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.PEASHOOTER_SEED_PACKET);
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
		return ModItems.PEASHOOTER_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createTulimperAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15.0D);
	}

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
        return true;
    }

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.5F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZSounds.SILENCEVENET;
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
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final TulimpeterEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(TulimpeterEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive() && !this.plantEntity.getIsAsleep();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 14);
			this.plantEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.plantEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 14);
				this.plantEntity.setTarget((LivingEntity) null);
			}
			else {
				this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
				if (this.animationTicks == -16) {
					if (!this.plantEntity.checkForZombiesHEAL().isEmpty()) {
						this.plantEntity.setVariant(TulipVariants.HEAL);
					} else if (!this.plantEntity.checkForZombiesHYPNO().isEmpty()) {
						this.plantEntity.setVariant(TulipVariants.HYPNO);
					}
					else {
						this.plantEntity.setVariant(TulipVariants.DEFAULT);
					}
				}
				++this.animationTicks;
				++this.beamTicks;
				if (this.beamTicks >= 0 && this.animationTicks >= -7) {
					if (this.plantEntity.getVariant().equals(TulipVariants.DEFAULT)) {
						if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
							this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 14);
							JingleEntity proj = new JingleEntity(PvZEntity.JINGLE, this.plantEntity.world);
							double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 225) ? 50 : 5;
							Vec3d targetPos = livingEntity.getPos();
							Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
							double d = this.plantEntity.squaredDistanceTo(predictedPos);
							float df = (float) d;
							double e = predictedPos.getX() - this.plantEntity.getX();
							double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
							double g = predictedPos.getZ() - this.plantEntity.getZ();
							float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
							proj.jingleAge = 60;
							proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
							proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.75D, this.plantEntity.getZ());
							proj.setOwner(this.plantEntity);
							if (livingEntity.isAlive()) {
								this.beamTicks = -13;
								this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
								this.plantEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_FLUTE, 0.2F, (this.plantEntity.random.nextFloat() - this.plantEntity.random.nextFloat()) + 0.75F);
								this.plantEntity.world.spawnEntity(proj);
							}
						}
					}
					else if (this.plantEntity.getVariant().equals(TulipVariants.HYPNO)) {
						if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
							this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 14);
							HypnoProjEntity proj = new HypnoProjEntity(PvZEntity.HYPNOPROJ, this.plantEntity.world);
							double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 225) ? 50 : 5;
							Vec3d targetPos = livingEntity.getPos();
							Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
							double d = this.plantEntity.squaredDistanceTo(predictedPos);
							float df = (float) d;
							double e = predictedPos.getX() - this.plantEntity.getX();
							double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
							double g = predictedPos.getZ() - this.plantEntity.getZ();
							float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
							proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
							proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.75D, this.plantEntity.getZ());
							proj.setOwner(this.plantEntity);
							if (livingEntity.isAlive()) {
								this.beamTicks = -13;
								this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
								this.plantEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_FLUTE, 0.2F, (this.plantEntity.random.nextFloat() - this.plantEntity.random.nextFloat()) + 0.75F);
								this.plantEntity.world.spawnEntity(proj);
							}
						}
					}
					else if (this.plantEntity.getVariant().equals(TulipVariants.HEAL)){
						this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 14);
						for (PlantEntity plantEntity1 : this.plantEntity.healPlants()){
							plantEntity1.heal(2);
						}
						this.beamTicks = -13;
						this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
						this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 116);
						this.plantEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_FLUTE, 0.2F, (this.plantEntity.random.nextFloat() - this.plantEntity.random.nextFloat()) + 0.75F);
					}
				} else if (this.animationTicks >= 0) {
					this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
