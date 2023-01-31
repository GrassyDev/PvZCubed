package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.EnlightenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.SunflowerVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.PvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.SummonerEntity;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.TwinSunflowerSeeds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.RaycastContext;
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

import java.util.Iterator;
import java.util.List;

public class SunflowerEntity extends EnlightenEntity implements IAnimatable {

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(SunflowerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private String controllerName = "suncontroller";
    public int sunProducingTime = 3600;

    public int healingTime;
	int raycastDelay = 10;

	Entity prevZombie;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private boolean zombieSunCheck;

	public SunflowerEntity(EntityType<? extends SunflowerEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 6000;
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


	/** /~*~//~*VARIANTS*~//~*~/ **/

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		SunflowerVariants variant = Util.getRandom(SunflowerVariants.values(), this.random);
		setVariant(variant);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public SunflowerVariants getVariant() {
		return SunflowerVariants.byId(this.getTypeVariant() & 255);
	}

	private void setVariant(SunflowerVariants variant) {
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
        event.getController().setAnimation(new AnimationBuilder().loop("sunflower.idle"));
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 50.0F));
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
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
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
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && --this.sunProducingTime <= 0 && !this.isInsideWaterOrBubbleColumn()) {
			if (--raycastDelay >= 0){
				this.produceSun();
				raycastDelay = 10;
			}
		}
		if (!this.world.isClient && this.isAlive() && this.zombieSunCheck && !this.isInsideWaterOrBubbleColumn()){
			this.playSound(PvZCubed.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) * 0.75F + 1F);
			this.dropItem(ModItems.SUN);
			this.sunProducingTime = 3600;
			this.zombieSunCheck = false;
		}

		if (!this.world.isClient && this.isAlive() && --this.healingTime <= 0 && !this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.heal(4.0F);
			this.healingTime = 6000;
		}

		if (!this.world.isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.damage(DamageSource.GENERIC, 9999);
		}

	}

	protected void produceSun() {
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(15));
		List<PvZombieEntity> zombieList = this.world.getNonSpectatingEntities(PvZombieEntity.class, this.getBoundingBox().expand(15));
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
			} while (this.squaredDistanceTo(livingEntity) > 225);

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
				if (livingEntity instanceof PvZombieEntity || livingEntity instanceof SummonerEntity) {
					if (livingEntity.getY() < (this.getY() + 1) && livingEntity.getY() > (this.getY() - 1)){
						if ((this.prevZombie == null || zombieList.get(0) != prevZombie) && !zombieList.isEmpty()){
							prevZombie = zombieList.get(0);
							this.zombieSunCheck = true;
						}
					}
				}
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (itemStack.isOf(ModItems.TWINSUNFLOWER_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				TwinSunflowerEntity twinSunflowerEntity = (TwinSunflowerEntity) PvZEntity.TWINSUNFLOWER.create(world);
				twinSunflowerEntity.setTarget(this.getTarget());
				twinSunflowerEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				twinSunflowerEntity.initialize(serverWorld, world.getLocalDifficulty(twinSunflowerEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);
				twinSunflowerEntity.setAiDisabled(this.isAiDisabled());
				if (this.hasCustomName()) {
					twinSunflowerEntity.setCustomName(this.getCustomName());
					twinSunflowerEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				if (this.hasVehicle()){
					twinSunflowerEntity.startRiding(this.getVehicle(), true);
				}

				twinSunflowerEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(twinSunflowerEntity);
				this.remove(RemovalReason.DISCARDED);
			}
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
				player.getItemCooldownManager().set(ModItems.TWINSUNFLOWER_SEED_PACKET, TwinSunflowerSeeds.cooldown);
			}
			return ActionResult.SUCCESS;
		}
		if (!this.getVariant().equals(SunflowerVariants.DEFAULT) && itemStack.isOf(Items.WHITE_DYE)) {
			this.setVariant(SunflowerVariants.DEFAULT);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(SunflowerVariants.LESBIAN) &&
				(itemStack.isOf(Items.RED_DYE) || itemStack.isOf(Items.ORANGE_DYE) || itemStack.isOf(Items.MAGENTA_DYE))) {
			this.setVariant(SunflowerVariants.LESBIAN);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(SunflowerVariants.WLW) &&
				(itemStack.isOf(Items.PINK_DYE) || itemStack.isOf(Items.PURPLE_DYE))) {
			this.setVariant(SunflowerVariants.WLW);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(SunflowerVariants.MLM) &&
				(itemStack.isOf(Items.LIME_DYE) || itemStack.isOf(Items.GREEN_DYE))) {
			this.setVariant(SunflowerVariants.MLM);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else {
			return ActionResult.CONSUME;
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createSunflowerAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
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
			this.damage(DamageSource.GENERIC, 9999);
		}
		this.playBlockFallSound();
		return true;
	}
}
