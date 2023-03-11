package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.FlagZombieVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;

public class FlagzombieEntity extends SummonerEntity implements IAnimatable {

	private String controllerName = "walkingcontroller";
    private MobEntity owner;
    private boolean isAggro;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	boolean isFrozen;
	boolean isIced;

    public FlagzombieEntity(EntityType<? extends FlagzombieEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 12;
        this.isAggro = false;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
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

	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2){
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

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(FlagzombieEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.FLAGZOMBIE_G)){
			setVariant(FlagZombieVariants.GAY);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.FLAGZOMBIE_T)){
			setVariant(FlagZombieVariants.TRANS);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.FLAGZOMBIEHYPNO)){
			setVariant(FlagZombieVariants.DEFAULTHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.FLAGZOMBIE_GHYPNO)){
			setVariant(FlagZombieVariants.GAYHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.FLAGZOMBIE_THYPNO)){
			setVariant(FlagZombieVariants.TRANSHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(FlagZombieVariants.DEFAULT);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public FlagZombieVariants getVariant() {
		return FlagZombieVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(FlagZombieVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimationData data)
	{
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory()
	{
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (this.isInsideWaterOrBubbleColumn()) {
			event.getController().setAnimation(new AnimationBuilder().loop("flagzombie.ducky"));
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		}else {
			if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("flagzombie.walking"));
					if (this.isFrozen) {
						event.getController().setAnimationSpeed(0);
					}
					else if (this.isIced) {
						event.getController().setAnimationSpeed(0.7);
					}
					else {
						event.getController().setAnimationSpeed(1.4);
					}
			} else {
				event.getController().setAnimation(new AnimationBuilder().loop("flagzombie.idle"));
				if (this.isFrozen) {
					event.getController().setAnimationSpeed(0);
				} else if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				} else {
					event.getController().setAnimationSpeed(1);
				}
			}
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/


	protected void initGoals() {
		if (this.getType().equals(PvZEntity.FLAGZOMBIEHYPNO) ||
				this.getType().equals(PvZEntity.FLAGZOMBIE_GHYPNO) ||
				this.getType().equals(PvZEntity.FLAGZOMBIE_THYPNO)){
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
	}

	protected void initCustomGoals() {
		this.goalSelector.add(1, new FlagzombieEntity.summonZombieGoal(this));

		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
		this.targetSelector.add(2, new FlagzombieEntity.TrackOwnerTargetGoal(this));
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));

		this.targetSelector.add(4, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof PlantEntity plantEntity && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("ground"));
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
		this.goalSelector.add(1, new FlagzombieEntity.summonZombieGoal(this));

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
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.FLAGZOMBIEEGG.getDefaultStack();
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

	public static DefaultAttributeContainer.Builder createFlagzombieZombieAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50D);
	}

	protected SoundEvent getAmbientSound() {
		return PvZCubed.ZOMBIEMOANEVENT;
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

	protected SoundEvent getCastSpellSound() {
		return PvZCubed.ENTITYRISINGEVENT;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	protected EntityType<?> hypnoType;
	protected void checkHypno(){
		if (this.getType().equals(PvZEntity.FLAGZOMBIE_G)){
			hypnoType = PvZEntity.FLAGZOMBIE_GHYPNO;
		}
		else if (this.getType().equals(PvZEntity.FLAGZOMBIE_T)){
			hypnoType = PvZEntity.FLAGZOMBIE_THYPNO;
		}
		else {
			hypnoType = PvZEntity.FLAGZOMBIEHYPNO;
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
				FlagzombieEntity hypnotizedZombie = (FlagzombieEntity) hypnoType.create(world);
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

			if (source.getAttacker() != null) {
				this.isAggro = true;
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

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = FlagzombieEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				if (FlagzombieEntity.this.isSpellcasting()) {
					return false;
				} else {
					return FlagzombieEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = FlagzombieEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			FlagzombieEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = FlagzombieEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				FlagzombieEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			FlagzombieEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				FlagzombieEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				FlagzombieEntity.this.playSound(FlagzombieEntity.this.getCastSpellSound(), 1.0F, 1.0F);
			}

		}

		protected abstract void castSpell();

		protected int getInitialCooldown() {
			return 20;
		}

		protected abstract int getSpellTicks();

		protected abstract int startTimeDelay();

		@Nullable
		protected abstract SoundEvent getSoundPrepare();

		protected abstract SummonerEntity.Spell getSpell();
	}

    class summonZombieGoal extends CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;
		private final FlagzombieEntity flagzombieEntity;

        private summonZombieGoal(FlagzombieEntity flagzombieEntity) {
            super();
			this.flagzombieEntity = flagzombieEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
        }

        public boolean canStart() {
            if (FlagzombieEntity.this.isAggro) {
                if (!super.canStart()) {
                    return false;
                } else {
                    int b = FlagzombieEntity.this.world.getTargets(BrowncoatEntity.class, this.closeZombiePredicate, FlagzombieEntity.this, FlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int p = FlagzombieEntity.this.world.getTargets(BrowncoatEntity.class, this.closeZombiePredicate, FlagzombieEntity.this, FlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int d = FlagzombieEntity.this.world.getTargets(BrowncoatEntity.class, this.closeZombiePredicate, FlagzombieEntity.this, FlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    int t = FlagzombieEntity.this.world.getTargets(BrowncoatEntity.class, this.closeZombiePredicate, FlagzombieEntity.this, FlagzombieEntity.this.getBoundingBox().expand(16.0D)).size();
                    return FlagzombieEntity.this.random.nextInt(8) + 1 > b &&
                            FlagzombieEntity.this.random.nextInt(8) + 1 > p &&
                            FlagzombieEntity.this.random.nextInt(8) + 1 > d &&
                            FlagzombieEntity.this.random.nextInt(8) + 1 > t ;
                }
            } else {
                return false;
            }
        }

		protected int getSpellTicks() {
			return 100;
		}

		protected int startTimeDelay() {
			return 160;
		}

        protected void castSpell() {

			EntityType<?> screen = PvZEntity.SCREENDOOR;
			EntityType<?> cone = PvZEntity.CONEHEAD;
			EntityType<?> bucket = PvZEntity.BUCKETHEAD;
			EntityType<?> coat = PvZEntity.BROWNCOAT;
			if (this.flagzombieEntity.getHypno()){
				screen = PvZEntity.SCREENDOORHYPNO;
				cone = PvZEntity.CONEHEADHYPNO;
				bucket = PvZEntity.BUCKETHEADHYPNO;
				coat = PvZEntity.BROWNCOATHYPNO;
			}

            ServerWorld serverWorld = (ServerWorld) FlagzombieEntity.this.world;
            for(int b = 0; b < 1; ++b) { // 1 Screendoor
                BlockPos blockPos = FlagzombieEntity.this.getBlockPos().add(-2 + FlagzombieEntity.this.random.nextInt(10), 1, -2 + FlagzombieEntity.this.random.nextInt(10));
				BrowncoatEntity screendoorEntity = (BrowncoatEntity) screen.create(FlagzombieEntity.this.world);
				screendoorEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				screendoorEntity.initialize(serverWorld, FlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				screendoorEntity.setOwner(FlagzombieEntity.this);
				if (this.flagzombieEntity.getHypno()){
					screendoorEntity.createShield();
				}
				serverWorld.spawnEntityAndPassengers(screendoorEntity);
            }
            for(int p = 0; p < 3; ++p) { // 3 Conehead
                BlockPos blockPos = FlagzombieEntity.this.getBlockPos().add(-2 + FlagzombieEntity.this.random.nextInt(10), 1, -2 + FlagzombieEntity.this.random.nextInt(10));
				BrowncoatEntity coneheadEntity = (BrowncoatEntity) cone.create(FlagzombieEntity.this.world);
                coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                coneheadEntity.initialize(serverWorld, FlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                coneheadEntity.setOwner(FlagzombieEntity.this);
				if (this.flagzombieEntity.getHypno()){
					coneheadEntity.createConeheadProp();
				}
                serverWorld.spawnEntityAndPassengers(coneheadEntity);
            }
            for(int d = 0; d < 2; ++d) { // 2 Buckethead
                BlockPos blockPos = FlagzombieEntity.this.getBlockPos().add(-2 + FlagzombieEntity.this.random.nextInt(10), 1, -2 + FlagzombieEntity.this.random.nextInt(10));
				BrowncoatEntity bucketheadEntity = (BrowncoatEntity) bucket.create(FlagzombieEntity.this.world);
                bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                bucketheadEntity.initialize(serverWorld, FlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                bucketheadEntity.setOwner(FlagzombieEntity.this);
				if (this.flagzombieEntity.getHypno()){
					bucketheadEntity.createBucketProp();
				}
                serverWorld.spawnEntityAndPassengers(bucketheadEntity);
            }
            for(int t = 0; t < 6; ++t) { // 6 Browncoat
                BlockPos blockPos = FlagzombieEntity.this.getBlockPos().add(-2 + FlagzombieEntity.this.random.nextInt(10), 1, -2 + FlagzombieEntity.this.random.nextInt(10));
                BrowncoatEntity browncoatEntity = (BrowncoatEntity) coat.create(FlagzombieEntity.this.world);
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, FlagzombieEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(FlagzombieEntity.this);
				((ServerWorld) world).spawnEntityAndPassengers(browncoatEntity);
            }
        }

        protected SoundEvent getSoundPrepare() {
            return PvZCubed.GRAVERISINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }

    class TrackOwnerTargetGoal extends TrackTargetGoal {
		private final TargetPredicate TRACK_OWNER_PREDICATE = TargetPredicate.createNonAttackable().ignoreVisibility().ignoreDistanceScalingFactor();

        public TrackOwnerTargetGoal(PathAwareEntity mob) {
            super(mob, false);
        }

        public boolean canStart() {
            return FlagzombieEntity.this.owner != null && FlagzombieEntity.this.owner.getTarget() != null && this.canTrack(FlagzombieEntity.this.owner.getTarget(), this.TRACK_OWNER_PREDICATE);
        }

        public void start() {
            FlagzombieEntity.this.setTarget(FlagzombieEntity.this.owner.getTarget());
            super.start();
        }
    }
}
