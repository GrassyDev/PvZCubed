package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gloomshroom.GloomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.fume.FumeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.FumeshroomVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.FumeVariants;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.GloomshroomSeeds;
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
import java.util.Optional;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class FumeshroomEntity extends PlantEntity implements IAnimatable, RangedAttackMob {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private String controllerName = "fumecontroller";

	private boolean isFiring;

	public FumeshroomEntity(EntityType<? extends FumeshroomEntity> entityType, World world) {
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
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(FumeshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public FumeshroomVariants getVariant() {
		return FumeshroomVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(FumeshroomVariants variant) {
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
		if (this.getIsAsleep()) {
			event.getController().setAnimation(new AnimationBuilder().loop("fumeshroom.asleep"));
		} else if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().playOnce("fumeshroom.attack"));
		} else {
			event.getController().setAnimation(new AnimationBuilder().loop("fumeshroom.idle"));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new FumeshroomEntity.FireBeamGoal(this));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {

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
					this.dropItem(ModItems.FUMESHROOM_SEED_PACKET);
				}
				this.discard();
			}

		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		//ambient darkness: daytime = 0, rain = 2, thunder/night > 2
		//skylight is the light of the sky hitting the block. Allows for mushrooms to stay awake underground while preventing light from torches making them asleep
		//we need this switch to prevent high server lag because of the goals
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
			this.targetZombies(this.getPos(), 5, false, false, true);
		}
		super.tick();
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
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
			dropItem(ModItems.FUMESHROOM_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		Item item = itemStack.getItem();
		if (itemStack.isOf(ModItems.GLOOMSHROOM_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT);
			if ((this.world instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.world;
				GloomshroomEntity gloomshroomEntity = (GloomshroomEntity) PvZEntity.GLOOMSHROOM.create(world);
				gloomshroomEntity.setTarget(this.getTarget());
				gloomshroomEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				gloomshroomEntity.initialize(serverWorld, world.getLocalDifficulty(gloomshroomEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);
				gloomshroomEntity.setAiDisabled(this.isAiDisabled());
				gloomshroomEntity.setPersistent();
				if (this.hasCustomName()) {
					gloomshroomEntity.setCustomName(this.getCustomName());
					gloomshroomEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				if (this.hasVehicle()){
					gloomshroomEntity.startRiding(this.getVehicle(), true);
				}
				if (this.getVariant().equals(FumeshroomVariants.TRANS)){
					gloomshroomEntity.setVariant(FumeshroomVariants.TRANS);
				}
				else if (this.getVariant().equals(FumeshroomVariants.GAY)){
					gloomshroomEntity.setVariant(FumeshroomVariants.GAY);
				}
				else {
					gloomshroomEntity.setVariant(FumeshroomVariants.DEFAULT);
				}
				serverWorld.spawnEntityAndPassengers(gloomshroomEntity);
				this.remove(RemovalReason.DISCARDED);
			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !world.getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.GLOOMSHROOM_SEED_PACKET, GloomshroomSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		if (!this.getVariant().equals(FumeshroomVariants.DEFAULT) && itemStack.isOf(Items.WHITE_DYE)) {
			this.setVariant(FumeshroomVariants.DEFAULT);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(FumeshroomVariants.GAY) &&
				(itemStack.isOf(Items.RED_DYE) || itemStack.isOf(Items.ORANGE_DYE) || itemStack.isOf(Items.YELLOW_DYE) || itemStack.isOf(Items.LIME_DYE) || itemStack.isOf(Items.BLUE_DYE) || itemStack.isOf(Items.PURPLE_DYE))) {
			this.setVariant(FumeshroomVariants.GAY);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(FumeshroomVariants.TRANS) &&
				(itemStack.isOf(Items.PINK_DYE) || itemStack.isOf(Items.LIGHT_BLUE_DYE))) {
			this.setVariant(FumeshroomVariants.TRANS);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.FUMESHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createFumeshroomAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 28.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 6D);
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
		private final FumeshroomEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(FumeshroomEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue() && !this.plantEntity.getIsAsleep();
		}

		public void start() {
			this.beamTicks = -8;
			this.animationTicks = -21;
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.plantEntity.canSee(livingEntity) && this.animationTicks >= 0) || this.plantEntity.getIsAsleep()){
				this.plantEntity.setTarget((LivingEntity) null);
			} else {
				this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -4) {
					double time = 1;
					Vec3d targetPos = livingEntity.getPos();
					Vec3d predictedPos = targetPos.add(livingEntity.getVelocity().multiply(time));
					double d = this.plantEntity.squaredDistanceTo(predictedPos);
					float df = (float)d;
					double e = predictedPos.getX() - this.plantEntity.getX();
					double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
					double g = predictedPos.getZ() - this.plantEntity.getZ();
					float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
					FumeEntity proj = new FumeEntity(PvZEntity.FUME, this.plantEntity.world);
					proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.85F, 0F);
					proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.5D, this.plantEntity.getZ());
					proj.setOwner(this.plantEntity);
					if (this.plantEntity.getVariant().equals(FumeshroomVariants.GAY)) {
						proj.setVariant(FumeVariants.GAY);
					} else if (this.plantEntity.getVariant().equals(FumeshroomVariants.TRANS)) {
						proj.setVariant(FumeVariants.TRANS);
					}
					if (livingEntity.isAlive()) {
						this.beamTicks = -2;
						this.plantEntity.playSound(PvZSounds.FUMESHROOMSHOOTEVENT, 0.3F, 1);
						this.plantEntity.world.spawnEntity(proj);
					}
				}
				if (this.animationTicks >= 0) {
					this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
					this.beamTicks = -8;
					this.animationTicks = -21;
				}
				super.tick();
			}
		}
	}
}
