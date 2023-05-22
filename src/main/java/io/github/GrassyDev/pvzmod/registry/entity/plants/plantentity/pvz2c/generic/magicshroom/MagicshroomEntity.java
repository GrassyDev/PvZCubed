package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.magicshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.card.ShootingCardEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
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

import java.util.*;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class MagicshroomEntity extends PlantEntity implements IAnimatable, RangedAttackMob {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private String controllerName = "magiccontroller";


	private boolean isFiring;

	private boolean isHatFiring;

    public MagicshroomEntity(EntityType<? extends MagicshroomEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, true);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Variant", this.hasHat());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getBoolean("Variant"));
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
		if (status == 117){
			this.isHatFiring = true;
		}
		else if (status == 118){
			this.isHatFiring = false;
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(MagicshroomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public enum MagicHat {
		FALSE(false),
		TRUE(true);

		MagicHat(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean hasHat() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public void setHat(MagicshroomEntity.MagicHat magicHat) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, magicHat.getId());
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
		if (this.isHatFiring) {
			event.getController().setAnimation(new AnimationBuilder().playOnce("magicshroom.hat"));
		}
		else if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().playOnce("magicshroom.shoot"));
		}
		else {
			event.getController().setAnimation(new AnimationBuilder().loop("magicshroom.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new MagicshroomEntity.FireBeamGoal(this));
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {
	}


	protected List<HostileEntity> checkForZombiesHAT() {
		List<HostileEntity> list = this.world.getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(10));
		List<HostileEntity> list2 = new ArrayList<>();
		Iterator var9 = list.iterator();
		while (true) {
			HostileEntity hostileEntity;
			if (!var9.hasNext()) {
				return list2;
			}
			hostileEntity = (HostileEntity) var9.next();

			if (hostileEntity.squaredDistanceTo(this) <= 100) {
				if (!(hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
					list2.add(hostileEntity);
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

		if (this.age > 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.MAGICSHROOM_SEED_PACKET);
				}
				this.discard();
			}

		}
	}

	/** /~*~//~**TICKING**~//~*~/ **/

	private int hatTicks = 600;


	public void tick() {
		if (!this.world.isClient) {
			if ((this.world.getAmbientDarkness() >= 2 ||
					this.world.getLightLevel(LightType.SKY, this.getBlockPos()) < 2 ||
					this.world.getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS)))) {
				this.setIsAsleep(IsAsleep.FALSE);
			} else if (this.world.getAmbientDarkness() < 2 &&
					this.world.getLightLevel(LightType.SKY, this.getBlockPos()) >= 2 &&
					!this.world.getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS))) {
				this.setIsAsleep(IsAsleep.TRUE);
			}
		}
		if (this.getIsAsleep()){
			this.setTarget(null);
		}
		else {
			if (!this.checkForZombiesHAT().isEmpty() && this.hasHat()) {
				this.targetZombies(this.getPos(), 5, false, true, true);
			}
			else{
				this.targetZombies(this.getPos(), 5, false, false, true);
			}
		}
		super.tick();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		if (this.hasHat()){
			this.hatTicks = 600;
		}
		else {
			if (--this.hatTicks <= 0){
				this.setHat(MagicHat.TRUE);
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
			dropItem(ModItems.MAGICSHROOM_SEED_PACKET);
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
		return ModItems.MAGICSHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createMagicshroomAttributes() {
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
		private final MagicshroomEntity plantEntity;
		private int beamTicks;
		private int animationTicks;
		private int cardsShot;

		private boolean shootingHat;

		public FireBeamGoal(MagicshroomEntity plantEntity) {
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
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 118);
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
				if (!this.plantEntity.getIsAsleep()) {
					if (this.animationTicks == -16) {
						this.shootingHat = !this.plantEntity.checkForZombiesHAT().isEmpty() && this.plantEntity.hasHat();
					}
					++this.animationTicks;
					++this.beamTicks;
					Vec3d vec3d2 = Vec3d.ZERO;
					if (livingEntity.isAlive()){
						vec3d2 = new Vec3d((double) 1, 0.0, 0).rotateY(-livingEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
					Vec3d vec3d3 = Vec3d.ofCenter(livingEntity.getBlockPos());
					Vec3d blockpos = new Vec3d(vec3d3.x + vec3d2.x, vec3d3.y + vec3d2.y, vec3d3.z + vec3d2.z);
					List<PlantEntity> list = this.plantEntity.world.getNonSpectatingEntities(PlantEntity.class, PvZEntity.MAGICHAT.getDimensions().getBoxAt(blockpos.getX(), blockpos.getY(), blockpos.getZ()));
					if (this.shootingHat && list.isEmpty()) {
						this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 117);
						if (this.beamTicks >= 0 && this.animationTicks >= -5) {
							if (livingEntity.isAlive()) {
								this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 14);
								MagichatEntity hat = (MagichatEntity) PvZEntity.MAGICHAT.create(this.plantEntity.world);
								hat.refreshPositionAndAngles(blockpos.getX(), livingEntity.getY(), blockpos.getZ(), 0, 0);
								if (this.plantEntity.world instanceof ServerWorld serverWorld && list.isEmpty()) {
									hat.initialize(serverWorld, this.plantEntity.world.getLocalDifficulty(livingEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
									serverWorld.spawnEntityAndPassengers(hat);
									this.plantEntity.setHat(MagicHat.FALSE);
									this.plantEntity.world.spawnEntity(hat);
								}
								this.beamTicks = -13;
								this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
							}
						} else if (this.animationTicks >= 0) {
							this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
							this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 118);
							this.beamTicks = -7;
							this.animationTicks = -16;
						}
					} else {
						this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
						if (this.beamTicks >= 0 && this.animationTicks <= -7 && cardsShot <= 3) {
							if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
								ShootingCardEntity proj = new ShootingCardEntity(PvZEntity.CARDPROJ, this.plantEntity.world);
								double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 36) ? 50 : 1;
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
								++this.cardsShot;
								if (livingEntity.isAlive()) {
									this.beamTicks = -1;
									this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.125F, 1);
									this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
									this.plantEntity.world.spawnEntity(proj);
								}
							}
						} else if (this.animationTicks >= 0) {
							this.cardsShot = 0;
							this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
							this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 118);
							this.beamTicks = -7;
							this.animationTicks = -16;
						}
					}
					super.tick();
				}
			}
		}
	}
}
