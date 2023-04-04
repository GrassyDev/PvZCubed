package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.zombieking;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.DefaultAndHypnoVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.darkages.PeasantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.PvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
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

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class ZombieKingEntity extends PvZombieEntity implements IAnimatable {
    private MobEntity owner;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "walkingcontroller";
	boolean isFrozen;
	boolean isIced;
	public LivingEntity impTarget;
	public int spawningTicks;
	public boolean startSpawn;
	public int convertTicks = 0;
	int animationMultiplier = 1;
	public boolean convertIs;
	public static final UUID MAX_SPEED_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));

	public ZombieKingEntity(EntityType<? extends ZombieKingEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 12;
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
		else if (status == 113){
			this.startSpawn = true;
		}
		else if (status == 114){
			this.startSpawn = false;
		}
		else if (status == 115){
			this.convertIs = true;
		}
		else if (status == 116){
			this.convertIs = false;
		}

	}

	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(ZombieKingEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.ZOMBIEKINGHYPNO)){
			setVariant(DefaultAndHypnoVariants.HYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(DefaultAndHypnoVariants.DEFAULT);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public DefaultAndHypnoVariants getVariant() {
		return DefaultAndHypnoVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(DefaultAndHypnoVariants variant) {
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
		if (this.isInsideWaterOrBubbleColumn()) {
			if (this.startSpawn) {
				event.getController().setAnimation(new AnimationBuilder().loop("zombieking.fallingwater"));
			} else if (this.convertIs) {
				event.getController().setAnimation(new AnimationBuilder().loop("zombieking.crowningwater"));
			} else {
				event.getController().setAnimation(new AnimationBuilder().loop("zombieking.idlewater"));
			}
		}
		else {
			if (this.startSpawn) {
				event.getController().setAnimation(new AnimationBuilder().loop("zombieking.falling"));
			} else if (this.convertIs) {
				event.getController().setAnimation(new AnimationBuilder().loop("zombieking.crowning"));
			} else {
				event.getController().setAnimation(new AnimationBuilder().loop("zombieking.idle"));
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
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		if (this.getType().equals(PvZEntity.ZOMBIEKINGHYPNO)) {
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
    }

    protected void initCustomGoals() {
		////////// Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof PeasantEntity peasantEntity && !(peasantEntity.getHypno()) && peasantEntity.getVariant().equals(BrowncoatVariants.BROWNCOAT));
		}));
    }

	protected void initHypnoGoals(){
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof PeasantEntity peasantEntity && peasantEntity.getVariant().equals(BrowncoatVariants.BROWNCOAT));
		}));
	}

	public void upgradeKnight(LivingEntity livingEntity) {
		if (this.world instanceof ServerWorld) {
			livingEntity.playSound(PvZCubed.KNIGHTTRANSFORMEVENT, 1F, 1.0F);
			ServerWorld serverWorld = (ServerWorld) this.world;
			PeasantEntity knightEntity;
			if (this.getType().equals(PvZEntity.ZOMBIEKINGHYPNO)){
				knightEntity = (PeasantEntity) PvZEntity.PEASANTKNIGHTHYPNO.create(world);
			}
			else {
				knightEntity = (PeasantEntity) PvZEntity.PEASANTKNIGHT.create(world);
			}
			knightEntity.refreshPositionAndAngles(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.getYaw(), livingEntity.getPitch());
			knightEntity.initialize(serverWorld, this.world.getLocalDifficulty(knightEntity.getBlockPos()), SpawnReason.CONVERSION, (EntityData)null, (NbtCompound) null);

			if (knightEntity.getHypno() && this.getHypno()){
				knightEntity.createKnightProp().setHypno(IsHypno.TRUE);
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe) {
						zpe.setHypno(IsHypno.TRUE);
						zpe.startRiding(knightEntity);
					}
				}
			}
			knightEntity.setHealth(livingEntity.getHealth());
			if (livingEntity.hasCustomName()) {
				knightEntity.setCustomName(livingEntity.getCustomName());
				knightEntity.setCustomNameVisible(livingEntity.isCustomNameVisible());
			}

			knightEntity.setPersistent();
			serverWorld.spawnEntityAndPassengers(knightEntity);
			knightEntity.setVelocity(livingEntity.getVelocity());
			knightEntity.copyPositionAndRotation(livingEntity);
			livingEntity.remove(RemovalReason.DISCARDED);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		if (this.getTarget() instanceof PeasantEntity peasantEntity && (peasantEntity.getVariant().equals(BrowncoatVariants.PEASANTKNIGHT) || peasantEntity.getVariant().equals(BrowncoatVariants.PEASANTKNIGHTHYPNO))) {
			this.setTarget(null);
		}
		if (impTarget != null && impTarget.isAlive()){
			this.getLookControl().lookAt(impTarget);
		}
		super.tick();
		double random = Math.random();
		if (--spawningTicks <= 0){
			--convertTicks;
		}
		if (convertTicks == 25 * animationMultiplier && this.getTarget() instanceof PeasantEntity peasantEntity && (peasantEntity.getVariant().equals(BrowncoatVariants.BROWNCOAT) || peasantEntity.getVariant().equals(BrowncoatVariants.BROWNCOATHYPNO)) && !this.hasStatusEffect(FROZEN)){
			this.upgradeKnight(peasantEntity);
		}
		if (convertTicks <= 0 && this.getTarget() instanceof PeasantEntity peasantEntity && random <= 0.01 && (peasantEntity.getVariant().equals(BrowncoatVariants.BROWNCOAT) || peasantEntity.getVariant().equals(BrowncoatVariants.BROWNCOATHYPNO)) && !this.hasStatusEffect(PvZCubed.FROZEN)) {
			this.convertTicks = 45 * animationMultiplier;
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN)){
			this.convertTicks = 0;
		}
		if (!this.world.isClient) {
			if (this.age > 2400 && this.getTarget() == null) {
				this.discard();
			}
		}
	}

	protected void mobTick() {
		super.mobTick();
		if (spawningTicks > 0){
			this.world.sendEntityStatus(this, (byte) 113);
		}
		else {
			this.world.sendEntityStatus(this, (byte) 114);
		}
		if (convertTicks > 0) {
			this.world.sendEntityStatus(this, (byte) 115);
		}
		else {
			this.world.sendEntityStatus(this, (byte) 116);
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN)){
			this.world.sendEntityStatus(this, (byte) 70);
		}
		else if (this.hasStatusEffect(PvZCubed.ICE)){
			this.animationMultiplier = 2;
			this.world.sendEntityStatus(this, (byte) 71);
		}
		else {
			this.animationMultiplier = 1;
			this.world.sendEntityStatus(this, (byte) 72);
		}
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.ZOMBIEKINGEGG.getDefaultStack();
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

	public static DefaultAttributeContainer.Builder createZombieKingAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.zombiekingH());
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
		hypnoType = PvZEntity.ZOMBIEKINGHYPNO;
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
                ZombieKingEntity hypnotizedZombie = (ZombieKingEntity) PvZEntity.ZOMBIEKINGHYPNO.create(world);
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
}
