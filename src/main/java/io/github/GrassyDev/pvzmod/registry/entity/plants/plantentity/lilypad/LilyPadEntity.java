package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.lilypad;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.snowpea.SnowpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.ReinforceEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.LilypadHats;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.SnowPeaVariants;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
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

;import java.util.List;

public class LilyPadEntity extends ReinforceEntity implements IAnimatable {

	private static final TrackedData<Integer> DATA_ID_TYPE_HAT =
			DataTracker.registerData(LilyPadEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private String controllerName = "wallcontroller";

    public int healingTime;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private boolean dryLand;

	public LilyPadEntity(EntityType<? extends LilyPadEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.healingTime = 1200;
		this.setNoGravity(true);
		LilypadHats hat = Util.getRandom(LilypadHats.values(), this.random);
		setHat(hat);
    }

	public LilyPadEntity(World world, double x, double y, double z) {
		this(PvZEntity.LILYPAD, world);
		this.setPosition(x, y, z);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_HAT, 0);
	}
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		//Hat//
		this.dataTracker.set(DATA_ID_TYPE_HAT, tag.getInt("Hat"));
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		//Variant//
		tag.putInt("Hat", this.getTypeHat());
	}

	static {
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		LilypadHats hat = Util.getRandom(LilypadHats.values(), this.random);
		setHat(hat);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeHat() {
		return this.dataTracker.get(DATA_ID_TYPE_HAT);
	}

	public LilypadHats getHat() {
		return LilypadHats.byId(this.getTypeHat() & 255);
	}

	private void setHat(LilypadHats hat) {
		this.dataTracker.set(DATA_ID_TYPE_HAT, hat.getId() & 255);
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
		if (this.getHat().equals(LilypadHats.LILY)){
			if (this.dryLand) {
				event.getController().setAnimation(new AnimationBuilder().loop("lilypad.onground.lily"));
			}
			else {
				event.getController().setAnimation(new AnimationBuilder().loop("lilypad.idle.lily"));
			}
		}
		else {
			if (this.dryLand) {
				event.getController().setAnimation(new AnimationBuilder().loop("lilypad.onground"));
			}
			else {
				event.getController().setAnimation(new AnimationBuilder().loop("lilypad.idle"));
			}
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new LookAtEntityGoal(this, HostileEntity.class, 50.0F));
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
			if (!blockPos2.equals(blockPos)) {
				this.kill();
			}

		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}

		if (this.isInsideWaterOrBubbleColumn()){
			kill();
		}

		HitResult hitResult = amphibiousRaycast(0.25);
		if (hitResult.getType() == HitResult.Type.MISS){
			kill();
		}
		if (this.age != 0) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if (!blockPos2.equals(blockPos) || blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) {
				this.dryLand = true;
				onWater = false;
			}
			else {
				this.dryLand = false;
				onWater = true;
			}
		}

		List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox().expand(0.20000000298023224, -0.009999999776482582, 0.20000000298023224), EntityPredicates.rides(this));
		if (!list.isEmpty()) {
			boolean bl = !this.world.isClient && !(this.getPrimaryPassenger() instanceof PlayerEntity);

			for(int j = 0; j < list.size(); ++j) {
				Entity entity = (Entity)list.get(j);
				if (!entity.hasPassenger(this)) {
					if (bl && this.getPassengerList().size() < 1 && !entity.hasVehicle() && entity.getWidth() < this.getWidth() && entity instanceof LivingEntity && !(entity instanceof WaterCreatureEntity ||
							entity instanceof HostileEntity || entity instanceof HypnoZombieEntity || entity instanceof HypnoSummonerEntity) && !(entity instanceof PlayerEntity)) {
						entity.startRiding(this);
					}
				}
			}
		}
    }

	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient && this.isAlive() && --this.healingTime <= 0 &&  this.deathTime == 0) {
			this.heal(4.0F);
			this.healingTime = 1200;
		}

		//if (!this.world.isClient && this.isAlive() && this.deathTime == 0) {
		//	this.damage(DamageSource.GENERIC, 9999);
		//}
	}

	public HitResult amphibiousRaycast(double maxDistance) {
		Vec3d vec3d1 = this.getPos();
		Vec3d vec3d2 = new Vec3d(vec3d1.x, vec3d1.y - maxDistance, vec3d1.z);
		return this.world.raycast(new RaycastContext(vec3d1, vec3d2, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, this));
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (!this.getHat().equals(LilypadHats.DEFAULT) && itemStack.isOf(Items.WHITE_DYE)) {
			this.setHat(LilypadHats.DEFAULT);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getHat().equals(LilypadHats.LILY) && itemStack.isOf(Items.SPORE_BLOSSOM)) {
			this.setHat(LilypadHats.LILY);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		return addPlants(player, hand);
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createLilyPadAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
    }

	public boolean collidesWith(Entity other) {
		return canCollide(this, other);
	}

	public static boolean canCollide(Entity entity, Entity other) {
		return (other.isCollidable() || other.isPushable()) && !entity.isConnectedThroughVehicle(other);
	}

	public double getMountedHeightOffset() {
		return 0;
	}

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return !this.isRemoved();
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.125F;
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

	public boolean isCollidable() {
		return this.isAlive();
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

	/**public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZCubed.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.damage(DamageSource.GENERIC, 9999);
		}
		this.playBlockFallSound();
		return true;
	}**/
}
