package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.mummy.MummyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plastichelmet.PlasticHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet.MetalHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicobstacle.MetalObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicshield.MetalShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.stonehelmet.StoneHelmetEntity;
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

public class BrowncoatEntity extends PvZombieEntity implements IAnimatable {
    private MobEntity owner;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "walkingcontroller";
	boolean isFrozen;
	boolean isIced;
	public boolean speedSwitch;
	public static final UUID MAX_SPEED_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));

	public BrowncoatEntity(EntityType<? extends BrowncoatEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 3;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
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
	}

	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(BrowncoatEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.CONEHEAD) ||
				this.getType().equals(PvZEntity.MUMMYCONE)){
			setVariant(BrowncoatVariants.CONEHEAD);
			createConeheadProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.PEASANTCONE)){
			setVariant(BrowncoatVariants.CONEHEAD);
			createTowerProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.BUCKETHEAD) ||
				this.getType().equals(PvZEntity.MUMMYBUCKET) ||
				this.getType().equals(PvZEntity.PEASANTBUCKET)){
			setVariant(BrowncoatVariants.BUCKETHEAD);
			createBucketProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.PYRAMIDHEAD)){
			this.setCanHypno(CanHypno.FALSE);
			setVariant(BrowncoatVariants.PYRAMIDHEAD);
			createPyramidProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.PEASANTKNIGHT)){
			setVariant(BrowncoatVariants.PEASANTKNIGHT);
			createKnightProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.BRICKHEAD)){
			setVariant(BrowncoatVariants.BRICKHEAD);
			createBrickProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.SCREENDOOR)){
			createShield();
			setVariant(BrowncoatVariants.SCREENDOOR);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.TRASHCAN)){
			createObstacle();
			setVariant(BrowncoatVariants.TRASHCAN);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.BROWNCOATHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTHYPNO)){
			setVariant(BrowncoatVariants.BROWNCOATHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.CONEHEADHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYCONEHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTCONEHYPNO)){
			setVariant(BrowncoatVariants.CONEHEADHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.BUCKETHEADHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYBUCKETHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTBUCKETHYPNO)){
			setVariant(BrowncoatVariants.BUCKETHEADHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.PYRAMIDHEADHYPNO)){
			setVariant(BrowncoatVariants.PYRAMIDHEADHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.PEASANTKNIGHTHYPNO)){
			setVariant(BrowncoatVariants.PEASANTKNIGHTHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.BRICKHEADHYPNO)){
			setVariant(BrowncoatVariants.BRICKHEADHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.SCREENDOORHYPNO)){
			setVariant(BrowncoatVariants.SCREENDOORHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.TRASHCANHYPNO)){
			setVariant(BrowncoatVariants.TRASHCANHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(BrowncoatVariants.BROWNCOAT);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public BrowncoatVariants getVariant() {
		return BrowncoatVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(BrowncoatVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	public void createConeheadProp(){
		PlasticHelmetEntity propentity = new PlasticHelmetEntity(PvZEntity.CONEHEADGEAR, this.world);
		propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		propentity.startRiding(this);
	}

	public void createBucketProp(){
		MetalHelmetEntity propentity = new MetalHelmetEntity(PvZEntity.BUCKETGEAR, this.world);
		propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		propentity.startRiding(this);
	}

	public ZombiePropEntity createKnightProp(){
		MetalHelmetEntity propentity = new MetalHelmetEntity(PvZEntity.KNIGHTGEAR, this.world);
		propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		propentity.startRiding(this);
		return propentity;
	}

	public void createBrickProp(){
		StoneHelmetEntity propentity = new StoneHelmetEntity(PvZEntity.BRICKGEAR, this.world);
		propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		propentity.startRiding(this);
	}

	public void createPyramidProp(){
		StoneHelmetEntity propentity = new StoneHelmetEntity(PvZEntity.PYRAMIDGEAR, this.world);
		propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		propentity.startRiding(this);
	}

	public void createTowerProp(){
		StoneHelmetEntity propentity = new StoneHelmetEntity(PvZEntity.TOWERGEAR, this.world);
		propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		propentity.startRiding(this);
	}

	public void createShield(){
		MetalShieldEntity metalShieldEntity = new MetalShieldEntity(PvZEntity.SCREENDOORSHIELD, this.world);
		metalShieldEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		metalShieldEntity.startRiding(this);
	}

	public void createObstacle(){
		MetalObstacleEntity metalObstacleEntity = new MetalObstacleEntity(PvZEntity.TRASHCANBIN, this.world);
		metalObstacleEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		metalObstacleEntity.startRiding(this);
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
		Entity entity = this.getFirstPassenger();
		if (this.isInsideWaterOrBubbleColumn()) {
			if (this.hasPassenger(entity) && entity instanceof ZombieShieldEntity){
				event.getController().setAnimation(new AnimationBuilder().loop("screendoor.ducky"));
			}
			else if (this.getVariant().equals(BrowncoatVariants.SCREENDOOR) || this.getVariant().equals(BrowncoatVariants.SCREENDOORHYPNO) ||
					this.getVariant().equals(BrowncoatVariants.TRASHCAN) || this.getVariant().equals(BrowncoatVariants.TRASHCANHYPNO) ) {
				event.getController().setAnimation(new AnimationBuilder().loop("screendoor.ducky2"));
			}
			else {
				event.getController().setAnimation(new AnimationBuilder().loop("headwear.ducky"));
			}
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		} else {
			if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				if (this.hasPassenger(entity) && entity instanceof ZombieShieldEntity){
					event.getController().setAnimation(new AnimationBuilder().loop("screendoor.walking"));
				}
				else {
					event.getController().setAnimation(new AnimationBuilder().loop("headwear.walking"));
				}
			} else {
				if (this.hasPassenger(entity) && entity instanceof ZombieShieldEntity){
					event.getController().setAnimation(new AnimationBuilder().loop("screendoor.idle"));
				}
				else {
					event.getController().setAnimation(new AnimationBuilder().loop("headwear.idle"));
				}
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
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		if (this.getType().equals(PvZEntity.BROWNCOATHYPNO) ||
				this.getType().equals(PvZEntity.CONEHEADHYPNO) ||
				this.getType().equals(PvZEntity.BUCKETHEADHYPNO) ||
				this.getType().equals(PvZEntity.BRICKHEADHYPNO) ||
				this.getType().equals(PvZEntity.SCREENDOORHYPNO) ||
				this.getType().equals(PvZEntity.TRASHCANHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYCONEHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYBUCKETHYPNO) ||
				this.getType().equals(PvZEntity.PYRAMIDHEADHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTCONEHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTBUCKETHYPNO) ||
				this.getType().equals(PvZEntity.PEASANTKNIGHTHYPNO)) {
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
    }

    protected void initCustomGoals() {

		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));

		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof PlantEntity plantEntity && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("ground")) && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying"));
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

	public void initHypnoGoals(){
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

	protected void mobTick() {
		super.mobTick();
		if (this.hasStatusEffect(PvZCubed.FROZEN)){
			this.world.sendEntityStatus(this, (byte) 70);
		}
		else if (this.hasStatusEffect(PvZCubed.ICE)){
			this.world.sendEntityStatus(this, (byte) 71);
		}
		else {
			this.world.sendEntityStatus(this, (byte) 72);
		}

		var zombieObstacleEntity = this.getPassengerList()
				.stream()
				.filter(e -> e instanceof ZombieObstacleEntity)
				.map(e -> (ZombieObstacleEntity) e)
				.findFirst();

		var zombieShieldEntity = this.getPassengerList()
				.stream()
				.filter(e -> e instanceof ZombieShieldEntity)
				.map(e -> (ZombieShieldEntity) e)
				.findFirst();

		ZombiePropEntity pyramidPropEntity = null;
		for (Entity pyramidHead : this.getPassengerList()) {
			if (pyramidHead.getType().equals(PvZEntity.PYRAMIDGEAR)) {
				pyramidPropEntity = (ZombiePropEntity) pyramidHead;
			}
		}

		if (this.isInsideWaterOrBubbleColumn() && zombieObstacleEntity.isPresent()){
			zombieObstacleEntity.get().stopRiding();
		}
		if (zombieObstacleEntity.isEmpty() && zombieShieldEntity.isEmpty() && this.CollidesWithObstacle() != null && this.CollidesWithObstacle().getType().equals(PvZEntity.TRASHCANBIN) && !this.CollidesWithObstacle().hasVehicle() && !this.CollidesWithObstacle().beingEaten && !this.isInsideWaterOrBubbleColumn()
		&& (this.getVariant().equals(BrowncoatVariants.TRASHCAN) ||
				this.getVariant().equals(BrowncoatVariants.TRASHCANHYPNO) ||
				this.getType().equals(PvZEntity.BROWNCOAT) ||
				this.getType().equals(PvZEntity.BROWNCOATHYPNO))){
			this.CollidesWithObstacle().startRiding(this, true);
		}

		EntityAttributeInstance maxSpeedAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		if (this instanceof MummyEntity) {
			if (pyramidPropEntity == null) {
				if (this.speedSwitch) {
					assert maxSpeedAttribute != null;
					maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
					this.speedSwitch = false;
				}
			} else {
				if (!this.speedSwitch) {
					assert maxSpeedAttribute != null;
					maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
					maxSpeedAttribute.addPersistentModifier(createSpeedModifier(-0.035));
					this.speedSwitch = true;
				}
			}
		}
		if (!(this instanceof MummyEntity)) {
			if (zombieObstacleEntity.isEmpty()) {
				if (this.speedSwitch) {
					assert maxSpeedAttribute != null;
					maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
					this.speedSwitch = false;
				}
			} else {
				if (!this.speedSwitch) {
					assert maxSpeedAttribute != null;
					maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
					maxSpeedAttribute.addPersistentModifier(createSpeedModifier(-0.035));
					this.speedSwitch = true;
				}
			}
		}
	}

	@Override
	public void updatePassengerPosition(Entity passenger) {
		if (this.getVariant().equals(BrowncoatVariants.SCREENDOOR) ||
				this.getVariant().equals(BrowncoatVariants.SCREENDOORHYPNO)) {
			if (this.hasPassenger(passenger)) {
				float g = (float) ((this.isRemoved() ? 0.01F : this.getMountedHeightOffset()) + passenger.getHeightOffset());
				float f = 0.4F;

				Vec3d vec3d = new Vec3d((double) f, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
				passenger.setBodyYaw(this.bodyYaw);
			}
		}
		else if (this.getVariant().equals(BrowncoatVariants.TRASHCAN) ||
				this.getVariant().equals(BrowncoatVariants.TRASHCANHYPNO) ||
				this.getVariant().equals(BrowncoatVariants.BROWNCOATHYPNO) ||
				this.getVariant().equals(BrowncoatVariants.BROWNCOAT)) {
			if (this.hasPassenger(passenger)) {
				float g = (float) ((this.isRemoved() ? 0.01F : this.getMountedHeightOffset()) + passenger.getHeightOffset());
				float f = 0.9F;

				Vec3d vec3d = new Vec3d((double) f, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
				passenger.setBodyYaw(this.bodyYaw);
			}
		}
		else {
			super.updatePassengerPosition(passenger);
		}
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getVariant().equals(BrowncoatVariants.CONEHEAD) || this.getVariant().equals(BrowncoatVariants.CONEHEADHYPNO)){
			itemStack = ModItems.CONEHEADEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.BUCKETHEAD) || this.getVariant().equals(BrowncoatVariants.BUCKETHEADHYPNO)){
			itemStack = ModItems.BUCKETHEADEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.BRICKHEAD) || this.getVariant().equals(BrowncoatVariants.BRICKHEADHYPNO)){
			itemStack = ModItems.BRICKHEADEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.SCREENDOOR) || this.getVariant().equals(BrowncoatVariants.SCREENDOORHYPNO)){
			itemStack = ModItems.SCREENDOOREGG.getDefaultStack();
		}
		else if (this.getVariant().equals(BrowncoatVariants.TRASHCAN) || this.getVariant().equals(BrowncoatVariants.TRASHCANHYPNO)){
			itemStack = ModItems.TRASHCANEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.BROWNCOATEGG.getDefaultStack();
		}
		return itemStack;
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static EntityAttributeModifier createSpeedModifier(double amount) {
		return new EntityAttributeModifier(
				MAX_SPEED_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

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

	public static DefaultAttributeContainer.Builder createBrowncoatAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.browncoatH());
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
		if (this.getType().equals(PvZEntity.CONEHEAD)){
			hypnoType = PvZEntity.CONEHEADHYPNO;
		}
		else if (this.getType().equals(PvZEntity.BUCKETHEAD)){
			hypnoType = PvZEntity.BUCKETHEADHYPNO;
		}
		else if (this.getType().equals(PvZEntity.BRICKHEAD)){
			hypnoType = PvZEntity.BRICKHEADHYPNO;
		}
		else if (this.getType().equals(PvZEntity.SCREENDOOR)){
			hypnoType = PvZEntity.SCREENDOORHYPNO;
		}
		else if (this.getType().equals(PvZEntity.TRASHCAN)){
			hypnoType = PvZEntity.TRASHCANHYPNO;
		}
		else if (this.getType().equals(PvZEntity.MUMMY)){
			hypnoType = PvZEntity.MUMMYHYPNO;
		}
		else if (this.getType().equals(PvZEntity.MUMMYCONE)){
			hypnoType = PvZEntity.MUMMYCONEHYPNO;
		}
		else if (this.getType().equals(PvZEntity.MUMMYBUCKET)){
			hypnoType = PvZEntity.MUMMYBUCKETHYPNO;
		}
		else if (this.getType().equals(PvZEntity.PYRAMIDHEAD)){
			hypnoType = PvZEntity.PYRAMIDHEADHYPNO;
		}
		else if (this.getType().equals(PvZEntity.PEASANT)){
			hypnoType = PvZEntity.PEASANTHYPNO;
		}
		else if (this.getType().equals(PvZEntity.PEASANTCONE)){
			hypnoType = PvZEntity.PEASANTCONEHYPNO;
		}
		else if (this.getType().equals(PvZEntity.PEASANTBUCKET)){
			hypnoType = PvZEntity.PEASANTBUCKETHYPNO;
		}
		else if (this.getType().equals(PvZEntity.PEASANTKNIGHT)){
			hypnoType = PvZEntity.PEASANTKNIGHTHYPNO;
		}
		else {
			hypnoType = PvZEntity.BROWNCOATHYPNO;
		}
	}

	public boolean damage(DamageSource source, float amount) {
        if (!super.damage(source, amount)) {
            return false;
        } else if (!(this.world instanceof ServerWorld)) {
            return false;
        }
		else {
            ServerWorld serverWorld = (ServerWorld)this.world;
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
                livingEntity = (LivingEntity)source.getAttacker();
            }

            if (this.getRecentDamageSource() == PvZCubed.HYPNO_DAMAGE && !(this.getHypno())) {
				checkHypno();
                this.playSound(PvZCubed.HYPNOTIZINGEVENT, 1.5F, 1.0F);
                BrowncoatEntity hypnotizedZombie = (BrowncoatEntity) hypnoType.create(world);
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
            return BrowncoatEntity.this.owner != null && BrowncoatEntity.this.owner.getTarget() != null && this.canTrack(BrowncoatEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            BrowncoatEntity.this.setTarget(BrowncoatEntity.this.owner.getTarget());
            super.start();
        }
    }
}
