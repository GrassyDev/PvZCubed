package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.NewspaperVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.papershield.NewspaperShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
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

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class NewspaperEntity extends PvZombieEntity implements IAnimatable {

    private MobEntity owner;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "walkingcontroller";
	private boolean speedUp;
	public boolean speedSwitch;
	public static final UUID MAX_SPEED_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));
	public static final UUID MAX_STRENGTH_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));
	boolean isFrozen;
	boolean isIced;

	public NewspaperEntity(EntityType<? extends NewspaperEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 3;
		this.speedSwitch = false;
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
		if (status == 70) {
			this.isFrozen = true;
			this.isIced = false;
		}
		else if (status == 71) {
			this.isIced = true;
			this.isFrozen = false;
		}
		else if (status == 72) {
			this.isIced = false;
			this.isFrozen = false;
		}
		if (status == 120) {
			this.speedUp = true;
		}
		else if (status == 122) {
			this.speedUp = false;
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(NewspaperEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.SUNDAYEDITION)) {
			createSundayShield();
			this.setVariant(NewspaperVariants.SUNDAYEDITION);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.NEWSPAPERHYPNO)){
			this.setVariant(NewspaperVariants.DEFAULTHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.SUNDAYEDITIONHYPNO)){
			this.setVariant(NewspaperVariants.SUNDAYEDITIONHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			createShield();
			this.setVariant(NewspaperVariants.DEFAULT);
			this.initCustomGoals();
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public NewspaperVariants getVariant() {
		return NewspaperVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(NewspaperVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	public void createShield(){
		NewspaperShieldEntity newspaperShieldEntity = new NewspaperShieldEntity(PvZEntity.NEWSPAPERSHIELD, this.world);
		newspaperShieldEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		newspaperShieldEntity.startRiding(this);
	}

	public void createSundayShield(){
		NewspaperShieldEntity newspaperShieldEntity = new NewspaperShieldEntity(PvZEntity.SUNDAYEDITIONSHIELD, this.world);
		newspaperShieldEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		newspaperShieldEntity.startRiding(this);
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
		if (this.isInsideWaterOrBubbleColumn()) {
			if (this.speedUp) {
				event.getController().setAnimation(new AnimationBuilder().loop("newspaper.ducky.angry"));
			} else {
				event.getController().setAnimation(new AnimationBuilder().loop("newspaper.ducky"));
			}
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		}else {
			if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				if (this.speedUp) {
					event.getController().setAnimation(new AnimationBuilder().loop("newspaper.angry"));
					if (this.isFrozen) {
						event.getController().setAnimationSpeed(0);
					}
					else if (this.isIced) {
						event.getController().setAnimationSpeed(1);
					}
					else {
						event.getController().setAnimationSpeed(2);
					}
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("newspaper.walking"));
					if (this.getType().equals(PvZEntity.SUNDAYEDITION) || this.getType().equals(PvZEntity.SUNDAYEDITIONHYPNO)){
						if (this.isFrozen) {
							event.getController().setAnimationSpeed(0);
						}
						else if (this.isIced) {
							event.getController().setAnimationSpeed(0.375);
						}
						else {
							event.getController().setAnimationSpeed(0.75);
						}
					}
					else {
						if (this.isFrozen) {
							event.getController().setAnimationSpeed(0);
						} else if (this.isIced) {
							event.getController().setAnimationSpeed(0.25);
						} else {
							event.getController().setAnimationSpeed(0.5);
						}
					}
				}
			} else {
				if (this.speedUp) {
					event.getController().setAnimation(new AnimationBuilder().loop("newspaper.idle.angry"));
				}
				else {
					event.getController().setAnimation(new AnimationBuilder().loop("newspaper.idle"));
				}
				if (this.isFrozen) {
					event.getController().setAnimationSpeed(0);
				}
				else if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				}
				else {
					event.getController().setAnimationSpeed(1);
				}
			}
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		if (this.getType().equals(PvZEntity.NEWSPAPERHYPNO) || this.getType().equals(PvZEntity.SUNDAYEDITIONHYPNO)) {
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
	}

	protected void initCustomGoals() {

		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
		this.targetSelector.add(2, new NewspaperEntity.TrackOwnerTargetGoal(this));
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));

		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof PlantEntity plantEntity && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("ground") && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying")));
		}));

		this.targetSelector.add(4, new TargetGoal<>(this, MerchantEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, IronGolemEntity.class, false, true));
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof ZombiePropEntity zombiePropEntity && zombiePropEntity.getHypno());
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity));
		}));
		////////// Must-Protect Plants ///////
		this.targetSelector.add(3, new TargetGoal<>(this, SunflowerEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, TwinSunflowerEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, SunshroomEntity.class, false, true));
	}

	protected void initHypnoGoals(){

		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(1, new HypnoPvZombieAttackGoal(this, 1.0D, true));
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof ZombiePropEntity zombiePropEntity && !(zombiePropEntity.getHypno()));
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity));
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity);
		}));
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (this.getAttacking() == null && !(this.getHypno())){
			if (this.CollidesWithPlant() != null){
				this.setVelocity(0, -0.3, 0);
				this.setTarget(CollidesWithPlant());
			}
			else if (this.CollidesWithPlayer() != null && !this.CollidesWithPlayer().isCreative()){
				this.setTarget(CollidesWithPlayer());
			}
		}
	}

	public void mobTick() {
		super.mobTick();
		if (this.isOnFire() && this.getFirstPassenger() instanceof NewspaperShieldEntity){
			this.getFirstPassenger().setOnFireFor(this.getFireTicks());
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN)){
			this.world.sendEntityStatus(this, (byte) 70);
		}
		else if (this.hasStatusEffect(PvZCubed.ICE)){
			this.world.sendEntityStatus(this, (byte) 71);
		}
		else {
			this.world.sendEntityStatus(this, (byte) 72);
		}
		EntityAttributeInstance maxSpeedAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		EntityAttributeInstance maxStrengthAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		if (this.getFirstPassenger() == null){
			this.world.sendEntityStatus(this, (byte) 120);
			if (this.speedSwitch) {
				this.playSound(NEWSPAPERANGRYEVENT, 1, 1);
				assert maxSpeedAttribute != null;
				maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
				assert maxStrengthAttribute != null;
				maxStrengthAttribute.removeModifier(MAX_STRENGTH_UUID);
				this.speedSwitch = false;
			}
		}
		else if (this.getFirstPassenger() instanceof ZombieShieldEntity) {
			this.world.sendEntityStatus(this, (byte) 122);
			if (!this.speedSwitch){
				assert maxSpeedAttribute != null;
				maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
				assert maxStrengthAttribute != null;
				maxStrengthAttribute.removeModifier(MAX_STRENGTH_UUID);
				if (this.getVariant().equals(NewspaperVariants.SUNDAYEDITION) ||
						this.getVariant().equals(NewspaperVariants.SUNDAYEDITIONHYPNO) ) {
					maxSpeedAttribute.addPersistentModifier(createSpeedModifier(-0.10D));
					maxStrengthAttribute.addPersistentModifier(createStrengthModifier(-8D));
				}
				else {
					maxSpeedAttribute.addPersistentModifier(createSpeedModifier(-0.09D));
					maxStrengthAttribute.addPersistentModifier(createStrengthModifier(-4D));
				}
				this.speedSwitch = true;
			}
		}
	}

	@Override
	public void updatePassengerPosition(Entity passenger) {
		if (this.hasPassenger(passenger)) {
			float g = (float)((this.isRemoved() ? 0.01F : this.getMountedHeightOffset()) + passenger.getHeightOffset());
			float f = 0.4F;

			Vec3d vec3d = new Vec3d((double)f, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double)g, this.getZ() + vec3d.z);
			passenger.setBodyYaw(this.bodyYaw);
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.NEWSPAPEREGG.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	@Override
	public double getMountedHeightOffset() {
		return 0;
	}

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public static EntityAttributeModifier createSpeedModifier(double amount) {
		return new EntityAttributeModifier(
				MAX_SPEED_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static EntityAttributeModifier createStrengthModifier(double amount) {
		return new EntityAttributeModifier(
				MAX_STRENGTH_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static DefaultAttributeContainer.Builder createNewspaperAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.21D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.newspaperH());
    }

	public static DefaultAttributeContainer.Builder createSundayEditionAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.sundayH());
	}

	protected SoundEvent getAmbientSound() {
		if (!this.getHypno()) {
			return PvZCubed.ZOMBIEMOANEVENT;
		}
		else {
			return PvZCubed.SILENCEVENET;
		}
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	public MobEntity getOwner() {
		return this.owner;
	}

	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	public void setOwner(MobEntity owner) {
		this.owner = owner;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	protected EntityType<?> hypnoType;
	protected void checkHypno(){
		if (this.getType().equals(PvZEntity.SUNDAYEDITION)){
			hypnoType = PvZEntity.SUNDAYEDITIONHYPNO;
		}
		else {
			hypnoType = PvZEntity.NEWSPAPERHYPNO;
		}
	}

	public boolean damage(DamageSource source, float amount) {
		if (!super.damage(source, amount)) {
			return false;
		} else if (!(this.world instanceof ServerWorld)) {
			return false;
		} else {
			ServerWorld serverWorld = (ServerWorld)this.world;
			LivingEntity livingEntity = this.getTarget();
			if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
				livingEntity = (LivingEntity)source.getAttacker();
			}

			if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE && !(this.getHypno())) {
				checkHypno();
				this.playSound(PvZCubed.HYPNOTIZINGEVENT, 1.5F, 1.0F);
				NewspaperEntity hypnotizedZombie = (NewspaperEntity) hypnoType.create(world);
				hypnotizedZombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				hypnotizedZombie.initialize(serverWorld, world.getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);
				hypnotizedZombie.setAiDisabled(this.isAiDisabled());
				hypnotizedZombie.setHealth(this.getHealth());
				if (this.hasCustomName()) {
					hypnotizedZombie.setCustomName(this.getCustomName());
					hypnotizedZombie.setCustomNameVisible(this.isCustomNameVisible());
				}
				for (Entity entity1 : this.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe) {
						zpe.setHypno(IsHypno.TRUE);
						zpe.startRiding(hypnotizedZombie);
					}
				}

				hypnotizedZombie.setPersistent();

				serverWorld.spawnEntityAndPassengers(hypnotizedZombie);
				this.remove(RemovalReason.DISCARDED);
			}

			return true;
		}
	}

	public boolean onKilledOther(ServerWorld serverWorld, LivingEntity livingEntity) {
		super.onKilledOther(serverWorld, livingEntity);
		boolean bl = super.onKilledOther(serverWorld, livingEntity);
		if ((serverWorld.getDifficulty() == Difficulty.NORMAL || serverWorld.getDifficulty() == Difficulty.HARD) && livingEntity instanceof VillagerEntity) {
			if (serverWorld.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
				return bl;
			}

			VillagerEntity villagerEntity = (VillagerEntity) livingEntity;
			ZombieVillagerEntity zombieVillagerEntity = (ZombieVillagerEntity) villagerEntity.convertTo(EntityType.ZOMBIE_VILLAGER, false);
			zombieVillagerEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), (NbtCompound) null);
			zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
			zombieVillagerEntity.setGossipData((NbtElement) villagerEntity.getGossip().serialize(NbtOps.INSTANCE).getValue());
			zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toNbt());
			zombieVillagerEntity.setXp(villagerEntity.getExperience());
			if (!this.isSilent()) {
				serverWorld.syncWorldEvent((PlayerEntity) null, 1026, this.getBlockPos(), 0);
			}
		}

		return bl;
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return NewspaperEntity.this.owner != null && NewspaperEntity.this.owner.getTarget() != null && this.canTrack(NewspaperEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            NewspaperEntity.this.setTarget(NewspaperEntity.this.owner.getTarget());
            super.start();
        }
    }

}
