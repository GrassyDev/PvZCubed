package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.scaredyshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spore.SporeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.ScaredyshroomVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
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
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ScaredyshroomEntity extends PlantEntity implements IAnimatable, RangedAttackMob {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private String controllerName = "scaredycontroller";

	private boolean isTired;

	private boolean isFiring;

	private boolean isAfraid;

	private int animationScare;

    public ScaredyshroomEntity(EntityType<? extends ScaredyshroomEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;

        this.animationScare = 30;
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
		if (status == 14) {
			this.isAfraid = false;
			this.animationScare = 30;
		}
		if (status == 4) {
			this.isAfraid = true;
			this.isFiring = false;
		}
		if (status == 11) {
			this.isFiring = true;
		} else if (status == 10) {
			this.isFiring = false;
		}
		if (status == 13) {
			this.isTired = true;
		}
		else if (status == 12) {
			this.isTired = false;
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(ScaredyshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public ScaredyshroomVariants getVariant() {
		return ScaredyshroomVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(ScaredyshroomVariants variant) {
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
		if (this.isTired) {
			event.getController().setAnimation(new AnimationBuilder().loop("scaredyshroom.asleep"));
		}
		else if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().playOnce("scaredyshroom.attack"));
		}
		else if (this.animationScare <= 0 && this.isAfraid){
			event.getController().setAnimation(new AnimationBuilder().loop("scaredyshroom.afraid"));
		}
		else if (this.isAfraid){
			event.getController().setAnimation(new AnimationBuilder().playOnce("scaredyshroom.hiding"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("scaredyshroom.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity);
		}));
		this.goalSelector.add(1, new ScaredyshroomEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, this.random.nextInt(45) + 40, 30.0F));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					!(livingEntity instanceof ZombiePropEntity) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !(livingEntity.hasStatusEffect(PvZCubed.PVZPOISON));
		}));
		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					!(livingEntity instanceof ZombiePropEntity) &&
					!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel());
		}));
		this.targetSelector.add(3, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity) && !(livingEntity.hasStatusEffect(PvZCubed.PVZPOISON));
		}));
		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, true, false, (livingEntity) -> {
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


	protected List<HostileEntity> checkForZombies() {
		List<HostileEntity> list = this.world.getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(5));
		Iterator var9 = list.iterator();
		while (true) {
			HostileEntity hostileEntity;
			do {
				if (!var9.hasNext()) {
					return list;
				}
				hostileEntity = (HostileEntity) var9.next();
			} while (this.squaredDistanceTo(hostileEntity) > 49);

			if (!(hostileEntity instanceof ZombiePropEntity)) {
				if (hostileEntity.getY() < (this.getY() + 2) && hostileEntity.getY() > (this.getY() - 2)) {
					list.add(hostileEntity);
					return list;
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
					this.dropItem(ModItems.SCAREDYSHROOM_SEED_PACKET);
				}
				this.kill();
			}

		}
	}

	/** /~*~//~**TICKING**~//~*~/ **/

	public void tick() {
		super.tick();
		this.checkForZombies();
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

		if (this.animationScare > 0 && this.isAfraid) {
			--this.animationScare;
		}
	}

	boolean sleepSwitch = false;
	boolean awakeSwitch = false;

	protected void mobTick() {
		if ((this.world.getAmbientDarkness() >= 2 ||
				this.world.getLightLevel(LightType.SKY, this.getBlockPos()) < 2 ||
				this.world.getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS)))
				&& !awakeSwitch) {
			this.world.sendEntityStatus(this, (byte) 12);
			this.initGoals();
			sleepSwitch = false;
			awakeSwitch = true;
		}
		else if (this.world.getAmbientDarkness() < 2 &&
				this.world.getLightLevel(LightType.SKY, this.getBlockPos()) >= 2 &&
				!this.world.getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS))
				&& !sleepSwitch) {
			this.world.sendEntityStatus(this, (byte) 13);
			this.clearGoalsAndTasks();
			sleepSwitch = true;
			awakeSwitch = false;
		}
		super.mobTick();
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (!this.getVariant().equals(ScaredyshroomVariants.DEFAULT) && itemStack.isOf(Items.WHITE_DYE)) {
			this.setVariant(ScaredyshroomVariants.DEFAULT);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(ScaredyshroomVariants.DEMIBOY) &&
				(itemStack.isOf(Items.GRAY_DYE) || itemStack.isOf(Items.LIGHT_GRAY_DYE) || itemStack.isOf(Items.LIGHT_BLUE_DYE))) {
			this.setVariant(ScaredyshroomVariants.DEMIBOY);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(ScaredyshroomVariants.LINK) &&
				(itemStack.isOf(Items.WOODEN_SWORD) && !itemStack.hasEnchantments() ||
						itemStack.isOf(Items.IRON_SWORD) && !itemStack.hasEnchantments() ||
						itemStack.isOf(Items.SHIELD) && !itemStack.hasEnchantments() ||
						itemStack.isOf(Items.BOW) && !itemStack.hasEnchantments() ||
						itemStack.isOf(Items.TNT))) {
			this.setVariant(ScaredyshroomVariants.LINK);
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
		return ModItems.SCAREDYSHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createScaredyshroomAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30.0D);
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

	static class FireBeamGoal extends Goal {
		private final ScaredyshroomEntity scaredyshroomEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(ScaredyshroomEntity scaredyshroomEntity) {
			this.scaredyshroomEntity = scaredyshroomEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.scaredyshroomEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive() && !this.scaredyshroomEntity.isTired;
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.scaredyshroomEntity.getNavigation().stop();
			this.scaredyshroomEntity.getLookControl().lookAt(this.scaredyshroomEntity.getTarget(), 90.0F, 90.0F);
			this.scaredyshroomEntity.velocityDirty = true;
		}

		public void stop() {
			this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 10);
			this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 14);
			this.scaredyshroomEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.scaredyshroomEntity.getTarget();
			this.scaredyshroomEntity.getNavigation().stop();
			this.scaredyshroomEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.scaredyshroomEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 14);
				this.scaredyshroomEntity.setTarget((LivingEntity) null);
			}
			else {
				if (!this.scaredyshroomEntity.isTired && !this.scaredyshroomEntity.isAfraid) {
					this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 11);
					++this.animationTicks;
					++this.beamTicks;
					if (this.beamTicks >= 0 && this.animationTicks >= -7){
						if (!(this.scaredyshroomEntity.checkForZombies().isEmpty())){
							this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 4);
						}
						else {
							this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 14);
						}
					}
					if (this.scaredyshroomEntity.checkForZombies().isEmpty())  {
						if (this.beamTicks >= 0 && this.animationTicks >= -7) {
							if (!this.scaredyshroomEntity.isInsideWaterOrBubbleColumn()) {
								this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 14);
								SporeEntity proj = new SporeEntity(PvZEntity.SPORE, this.scaredyshroomEntity.world);
								double time = (this.scaredyshroomEntity.squaredDistanceTo(livingEntity) > 36) ? 50 : 1;
								Vec3d targetPos = livingEntity.getPos();
								Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
								double d = this.scaredyshroomEntity.squaredDistanceTo(predictedPos);
								float df = (float)d;
								double e = predictedPos.getX() - this.scaredyshroomEntity.getX();
								double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? -0.07500000111758709 : livingEntity.getY() - this.scaredyshroomEntity.getY();
								double g = predictedPos.getZ() - this.scaredyshroomEntity.getZ();
								float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
								proj.sporeAge = 41;
								proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.9F, 0F);
								proj.updatePosition(this.scaredyshroomEntity.getX(), this.scaredyshroomEntity.getY() + 0.75D, this.scaredyshroomEntity.getZ());
								proj.setOwner(this.scaredyshroomEntity);
								if (livingEntity.isAlive()) {
									this.beamTicks = -13;
									this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 11);
									this.scaredyshroomEntity.playSound(PvZCubed.PEASHOOTEVENT, 0.2F, 1);
									this.scaredyshroomEntity.world.spawnEntity(proj);
								}
							}
						}
						else if (this.animationTicks >= 0)
						{
							this.scaredyshroomEntity.world.sendEntityStatus(this.scaredyshroomEntity, (byte) 10);
							this.beamTicks = -7;
							this.animationTicks = -16;
						}
					}
				}
				super.tick();
			}
		}
	}
}
