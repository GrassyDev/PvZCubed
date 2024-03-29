package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile.ScorchedTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile.SnowTile;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.FireTrailEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football.FootballEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan.SuperFanImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.zombieking.ZombieKingEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;
import static io.github.GrassyDev.pvzmod.registry.PvZSounds.*;

public abstract class GeneralPvZombieEntity extends HostileEntity {
	protected GeneralPvZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.stepHeight = PVZCONFIG.nestedGeneralZombie.zombieStep();
		this.setPathfindingPenalty(PathNodeType.RAIL, 0.0F);
		this.setPathfindingPenalty(PathNodeType.UNPASSABLE_RAIL, 0.0F);
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
	}

	private MobEntity owner;

	public MobEntity getOwner() {
		return this.owner;
	}

	public void setOwner(MobEntity owner) {
		this.owner = owner;
	}

	protected int animationMultiplier = 1;

	public boolean armless;
	public boolean geardmg;
	public boolean gearless;
	public boolean isFrozen;
	public boolean isIced;
	public boolean isPoisoned;
	public boolean isStunned;
	public int fireSplashTicks;

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FLYING_TAG, false);
		this.dataTracker.startTracking(CANHYPNO_TAG, true);
		this.dataTracker.startTracking(CANBURN_TAG, true);
		this.dataTracker.startTracking(COVERED_TAG, false);
		this.dataTracker.startTracking(STEALTH_TAG, false);
		this.dataTracker.startTracking(CHALLENGE_TAG, false);
		this.dataTracker.startTracking(DATA_ID_HYPNOTIZED, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("isFlying", this.isFlying());
		tag.putBoolean("canHypno", this.canHypno());
		tag.putBoolean("canBurn", this.canBurn());
		tag.putBoolean("isCovered", this.isCovered());
		tag.putBoolean("isStealth", this.isStealth());
		tag.putBoolean("isChallenge", this.isChallengeZombie());
		tag.putBoolean("Hypnotized", this.getHypno());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(FLYING_TAG, tag.getBoolean("isFlying"));
		this.dataTracker.set(CANHYPNO_TAG, tag.getBoolean("canHypno"));
		this.dataTracker.set(CANBURN_TAG, tag.getBoolean("canBurn"));
		this.dataTracker.set(COVERED_TAG, tag.getBoolean("isCovered"));
		this.dataTracker.set(STEALTH_TAG, tag.getBoolean("isStealth"));
		this.dataTracker.set(CHALLENGE_TAG, tag.getBoolean("isChallenge"));
		this.dataTracker.set(DATA_ID_HYPNOTIZED, tag.getBoolean("Hypnotized"));
	}

	static {
	}

	@Override
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 70) {
			this.isFrozen = true;
			this.isIced = false;
			animationMultiplier = 2;
		}
		else if (status == 71) {
			this.isIced = true;
			this.isFrozen = false;
			animationMultiplier = 1;
		}
		else if (status == 72) {
			this.isIced = false;
			this.isFrozen = false;
		}
		if (status == 75) {
			this.isPoisoned = true;
		}
		else if (status == 76){
			this.isPoisoned = false;
		}
		if (status == 77) {
			this.isStunned = true;
		}
		else if (status == 78){
			this.isStunned = false;
		}
		if (status == 80){
			this.fireSplashTicks = 10;
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/


	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}


	//Flying Tag

	protected static final TrackedData<Boolean> FLYING_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Flying {
		FALSE(false),
		TRUE(true);

		Flying(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isFlying() {
		return this.dataTracker.get(FLYING_TAG);
	}

	public void setFlying(GeneralPvZombieEntity.Flying flying) {
		this.dataTracker.set(FLYING_TAG, flying.getId());
	}


	//Covered Tag

	protected static final TrackedData<Boolean> COVERED_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Covered {
		FALSE(false),
		TRUE(true);

		Covered(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isCovered() {
		return this.dataTracker.get(COVERED_TAG);
	}

	public void setCoveredTag(GeneralPvZombieEntity.Covered coveredTag) {
		this.dataTracker.set(COVERED_TAG, coveredTag.getId());
	}


	//Stealth Tag

	protected static final TrackedData<Boolean> STEALTH_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Stealth {
		FALSE(false),
		TRUE(true);

		Stealth(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isStealth() {
		return this.dataTracker.get(STEALTH_TAG);
	}

	public void setStealthTag(GeneralPvZombieEntity.Stealth stealthTag) {
		this.dataTracker.set(STEALTH_TAG, stealthTag.getId());
	}


	//Challenge Tag

	protected static final TrackedData<Boolean> CHALLENGE_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Challenge {
		FALSE(false),
		TRUE(true);

		Challenge(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isChallengeZombie() {
		return this.dataTracker.get(CHALLENGE_TAG);
	}

	public void setChallengeZombie(GeneralPvZombieEntity.Challenge challenge) {
		this.dataTracker.set(CHALLENGE_TAG, challenge.getId());
	}

	// Hypno Tag

	protected static final TrackedData<Boolean> DATA_ID_HYPNOTIZED =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

		public enum IsHypno {
		FALSE(false),
		TRUE(true);

		IsHypno(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getHypno() {
		return this.dataTracker.get(DATA_ID_HYPNOTIZED);
	}

	public void setHypno(GeneralPvZombieEntity.IsHypno hypno) {
		this.dataTracker.set(DATA_ID_HYPNOTIZED, hypno.getId());
	}

	// Can be Hypnotized Tag

	protected static final TrackedData<Boolean> CANHYPNO_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum CanHypno {
		TRUE(true),
		FALSE(false);

		CanHypno(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean canHypno() {
		return this.dataTracker.get(CANHYPNO_TAG);
	}

	public void setCanHypno(GeneralPvZombieEntity.CanHypno canHypno) {
		this.dataTracker.set(CANHYPNO_TAG, canHypno.getId());
	}

	/** ----------------------------------------------------------------------- **/

	// Can be Burned Tag

	protected static final TrackedData<Boolean> CANBURN_TAG =
			DataTracker.registerData(GeneralPvZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum CanBurn {
		TRUE(true),
		FALSE(false);

		CanBurn(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean canBurn() {
		return this.dataTracker.get(CANBURN_TAG);
	}

	public void setCanBurn(GeneralPvZombieEntity.CanBurn canBurn) {
		this.dataTracker.set(CANBURN_TAG, canBurn.getId());
	}

	/** ----------------------------------------------------------------------- **/

	public EntityType<? extends HostileEntity> entityBox = PvZEntity.BROWNCOAT;

	@Override
	public boolean canBeRiddenInWater() {
		return true;
	}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return (this.getHypno()) ? PvZSounds.ZOMBIEBITEEVENT : PvZSounds.SILENCEVENET;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return PvZSounds.SILENCEVENET;
	}

	@Override
	public void onDeath(DamageSource source) {
		double randomChallenge = 0;
		if (this.isChallengeZombie()){
			randomChallenge = Math.random();
		}
		if (randomChallenge <= 0.33333) {
			if (this.world.getGameRules().getBoolean(PvZCubed.SHOULD_ZOMBIE_DROP)) {
				if (!(this instanceof ZombiePropEntity && !(this instanceof ZombieRiderEntity))) {
					double random = Math.random();
					float multiplier = ZOMBIE_STRENGTH.get(this.getType()).orElse(1);
					if (multiplier > 9) {
						multiplier = 10;
					}
					double multiplierFinal = Math.pow(multiplier / 5, 2);
					Item item = ModItems.SEED_PACKET_LIST.get(getRandom().nextInt(ModItems.SEED_PACKET_LIST.size()));
					if (random <= 0.05 * multiplierFinal) {
						dropItem(item);
						playSound(LOOTGIFTDEVENT);
					} else if (random <= 0.10 * multiplierFinal) {
						dropItem(Items.DIAMOND);
						playSound(LOOTDIAMONDEVENT);
					} else if (random <= 0.30 * multiplierFinal) {
						dropItem(Items.GOLD_NUGGET);
						playSound(LOOTNUGGETEVENT);
					} else if (random <= 0.70 * multiplierFinal) {
						dropItem(Items.IRON_NUGGET);
						playSound(LOOTNUGGETEVENT);
					}
					double random2 = Math.random();
					if (random2 <= 0.2 && this.isChallengeZombie()) {
						Item item2 = ModItems.PLANTFOOD_LIST.get(getRandom().nextInt(ModItems.PLANTFOOD_LIST.size()));
						dropItem(item2);
					}
				}
			}
		}
		super.onDeath(source);
	}



	@Override
	protected void dropLoot(DamageSource source, boolean causedByPlayer) {
		if (this.world.getGameRules().getBoolean(PvZCubed.SHOULD_ZOMBIE_DROP)){
			super.dropLoot(source, causedByPlayer);
		}
	}

	public LivingEntity CollidesWithPlant(Float colliderOffsetx, Float colliderOffsetz){
		Vec3d vec3d = new Vec3d((double)colliderOffsetx, 0.0, colliderOffsetz).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
		LivingEntity setPlant = null;
		for (LivingEntity plantEntity : list) {
			if (plantEntity instanceof PlantEntity) {
				if (plantEntity instanceof LilyPadEntity lilyPadEntity) {
					if (!(lilyPadEntity.hasPassengers()) && (!lilyPadEntity.getLowProfile() || (lilyPadEntity.getLowProfile() && TARGET_GROUND.get(this.getType()).orElse(false).equals(true)))) {
						setPlant = lilyPadEntity;
					} else {
						setPlant = (PlantEntity) lilyPadEntity.getFirstPassenger();
					}
				} else if ((PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("ground") || ((PlantEntity) plantEntity).getLowProfile()) && TARGET_GROUND.get(this.getType()).orElse(false).equals(true)) {
					setPlant = plantEntity;
				} else if (PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("ground") || ((PlantEntity) plantEntity).getLowProfile()) {
					setPlant = null;
				} else if (PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying") && TARGET_FLY.get(this.getType()).orElse(false).equals(true)) {
					setPlant = plantEntity;
				} else if (PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying")) {
					setPlant = null;
				} else {
					setPlant = plantEntity;
				}
			}
			else if (plantEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()){
				if (generalPvZombieEntity.hasPassengers()) {
					for (Entity entity1 : generalPvZombieEntity.getPassengerList()) {
						if (entity1 instanceof ZombiePropEntity zpe) {
							setPlant = zpe;
						}
					}
				}
				else {
					setPlant = generalPvZombieEntity;
				}
			}
		}
		return setPlant;
	}

	public LivingEntity CollidesWithZombie(Float colliderOffset){
		Vec3d vec3d = new Vec3d((double)colliderOffset, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
		LivingEntity setZombie = null;
		for (LivingEntity zombieEntity : list) {
			if (zombieEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.getHypno()) {
				setZombie = zombieEntity;
			}
			if (zombieEntity instanceof SuperFanImpEntity && this instanceof FootballEntity){
				setZombie = zombieEntity;
				break;
			}
		}
		return setZombie;
	}

	public TileEntity HasTile(BlockPos blockPos){
		List<TileEntity> list = world.getNonSpectatingEntities(TileEntity.class, entityBox.getDimensions().getBoxAt(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
		TileEntity setTile = null;
		for (TileEntity tileEntity : list) {
			setTile = tileEntity;
		}
		return setTile;
	}

	public PlayerEntity CollidesWithPlayer(Float colliderOffset){
		Vec3d vec3d = new Vec3d((double)colliderOffset, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<PlayerEntity> list = world.getNonSpectatingEntities(PlayerEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
		if (!list.isEmpty()){
			return list.get(0);
		}
		else {
			return null;
		}
	}

	public ZombieObstacleEntity CollidesWithObstacle(Float colliderOffset){
		Vec3d vec3d = new Vec3d((double)colliderOffset + 0.66, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<ZombieObstacleEntity> list = world.getNonSpectatingEntities(ZombieObstacleEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
		ZombieObstacleEntity obstacleEntity = null;
		if (!list.isEmpty()) {
			for (ZombieObstacleEntity zombieObstacleEntity : list) {
				if (!zombieObstacleEntity.hasVehicle() && !zombieObstacleEntity.isDead()) {
					obstacleEntity = zombieObstacleEntity;
				}
			}
		}
		return obstacleEntity;
	}

	public void createScorchedTile(BlockPos blockPos){
		if (this.world instanceof ServerWorld serverWorld) {
			ScorchedTile tile = (ScorchedTile) PvZEntity.SCORCHEDTILE.create(world);
			tile.refreshPositionAndAngles(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0);
			tile.initialize(serverWorld, world.getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);

			Vec3d vec3d = Vec3d.ofCenter(blockPos).add(0, -0.5, 0);

			List<PlantEntity> list = world.getNonSpectatingEntities(PlantEntity.class, entityBox.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));

			if (!list.isEmpty()){
				for (PlantEntity plantEntity : list) {
					if (!plantEntity.getFireImmune() && !PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying")) {
						damage(DamageSource.GENERIC, plantEntity.getMaxHealth() * 5);
					}
				}
			}
			tile.setHeadYaw(0);

			tile.setPersistent();
			serverWorld.spawnEntityAndPassengers(tile);
		}
	}

	public void createSnowTile(BlockPos blockPos){
		if (this.world instanceof ServerWorld serverWorld) {
			SnowTile tile = (SnowTile) PvZEntity.SNOWTILE.create(world);
			tile.refreshPositionAndAngles(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0);
			tile.initialize(serverWorld, world.getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);

			Vec3d vec3d = Vec3d.ofCenter(blockPos).add(0, -0.5, 0);

			List<PlantEntity> list = world.getNonSpectatingEntities(PlantEntity.class, entityBox.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
			List<TileEntity> tileCheck = world.getNonSpectatingEntities(TileEntity.class, entityBox.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));

			if (tileCheck.isEmpty()) {
				if (!list.isEmpty()) {
					for (PlantEntity plantEntity : list) {
						if (!PLANT_TYPE.get(plantEntity.getType()).orElse("appease").equals("pepper") &&
								!PLANT_TYPE.get(plantEntity.getType()).orElse("appease").equals("winter") &&
								!PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying")) {
							damage(DamageSource.GENERIC, plantEntity.getMaxHealth() * 5);
						}
					}
				}
				tile.setHeadYaw(0);

				tile.setPersistent();
				serverWorld.spawnEntityAndPassengers(tile);
			}
		}
	}


	boolean pop = true;

	int stuckTimes;

	@Override
	protected void mobTick() {
		if (this.hasStatusEffect(PvZCubed.FROZEN)){
			this.world.sendEntityStatus(this, (byte) 70);
		}
		else if (this.hasStatusEffect(PvZCubed.ICE) && !(this instanceof GargantuarEntity)){
			this.world.sendEntityStatus(this, (byte) 71);
		}
		else if (!this.hasStatusEffect(ICE)) {
			this.world.sendEntityStatus(this, (byte) 72);
		}
		if (this.hasStatusEffect(PVZPOISON)){
			this.world.sendEntityStatus(this, (byte) 75);
		}
		else {
			this.world.sendEntityStatus(this, (byte) 76);
		}
		if (this.hasStatusEffect(PvZCubed.STUN) || this.hasStatusEffect(PvZCubed.DISABLE)){
			this.world.sendEntityStatus(this, (byte) 77);
		}
		else {
			this.world.sendEntityStatus(this, (byte) 78);
		}
		super.mobTick();
	}

	@Override
	public Vec3d handleFrictionAndCalculateMovement(Vec3d movementInput, float slipperiness) {
		BlockPos blockPos = this.getVelocityAffectingPos();
		float p = this.world.getBlockState(blockPos).getBlock().getSlipperiness();
		if (p > 0.6f){
			this.updateVelocity(this.getMovementSpeed(0.6f * p), movementInput);
		}
		else {
			this.updateVelocity(this.getMovementSpeed(0.6f), movementInput);
		}
		this.setVelocity(this.applyClimbingSpeed(this.getVelocity()));
		this.move(MovementType.SELF, this.getVelocity());
		Vec3d vec3d = this.getVelocity();
		if ((this.horizontalCollision || this.jumping)
				&& (this.isClimbing() || this.getBlockStateAtPos().isOf(Blocks.POWDER_SNOW) && PowderSnowBlock.canWalkOnPowderSnow(this))) {
			vec3d = new Vec3d(vec3d.x, 0.2, vec3d.z);
		}

		return vec3d;
	}

	private Vec3d applyClimbingSpeed(Vec3d motion) {
		if (this.isClimbing()) {
			this.onLanding();
			float f = 0.15F;
			double d = MathHelper.clamp(motion.x, -0.15F, 0.15F);
			double e = MathHelper.clamp(motion.z, -0.15F, 0.15F);
			double g = Math.max(motion.y, -0.15F);

			motion = new Vec3d(d, g, e);
		}

		return motion;
	}

	private float getMovementSpeed(float slipperiness) {
		return this.onGround ? this.getMovementSpeed() * (0.21600002F / (slipperiness * slipperiness * slipperiness)) : this.flyingSpeed;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	public boolean damage(DamageSource source, float amount) {
		if ((!this.canHypno() || this.isCovered()) && source.equals(HYPNO_DAMAGE)) {
			return false;
		}
		else {
			return super.damage(source, amount);
		}
	}

	public int playerGetTick;
	protected boolean frozenStart = true;
	protected float frzYaw;
	protected float frzPitch;
	protected float frzBodyYaw;
	protected float frzHeadYaw;


	protected Vec3d firstPos;

	protected int attackingTick;

	private boolean canJump;

	@Override
	public void setHeadYaw(float headYaw) {
		if (this.onGround || this.isInsideWaterOrBubbleColumn() || this.isFlying() || this instanceof ZombiePropEntity) {
			super.setHeadYaw(headYaw);
		}
	}

	@Override
	public void setBodyYaw(float bodyYaw) {
		if (this.onGround || this.isInsideWaterOrBubbleColumn() || this.isFlying() || this instanceof ZombiePropEntity) {
			super.setBodyYaw(bodyYaw);
		}
	}

	@Override
	public void setYaw(float yaw) {
		if (this.onGround || this.isInsideWaterOrBubbleColumn() || this.isFlying() || this instanceof ZombiePropEntity) {
			super.setYaw(yaw);
		}
	}

	@Override
	protected void setRotation(float yaw, float pitch) {
		if (this.onGround || this.isInsideWaterOrBubbleColumn() || this.isFlying() || this instanceof ZombiePropEntity) {
			super.setRotation(yaw, pitch);
		}
	}

	private int unstuckDelay;
	private int jumpDelay;

	public void tick() {
		if (this.getOwner() instanceof GraveEntity graveEntity && graveEntity.isChallengeGrave()){
			this.setChallengeZombie(Challenge.TRUE);
		}
		this.stepHeight = PVZCONFIG.nestedGeneralZombie.zombieStep();
		if (this.isOnFire() || this.hasStatusEffect(WARM)){
			this.setStealthTag(Stealth.FALSE);
		}
		if (canJump && !this.world.isClient() && !this.isFlying() && !this.isInsideWaterOrBubbleColumn() && --jumpDelay <= 0 && this.age > 40) {
			jumpOverGap();
			jumpDelay = 20;
		}
		if (!(this instanceof ZombiePropEntity)) {
			this.canJump = this.onGround;
			if (!this.canJump && !this.isFlying() && !this.isInsideWaterOrBubbleColumn()) {
				this.getNavigation().stop();
			}
		}
		if (this.getTarget() != null) {
			if (this.isAttacking() && this.squaredDistanceTo(this.getTarget()) < 1) {
				attackingTick = 40;
			} else {
				--attackingTick;
			}
		}
		Vec3d lastPos = this.getPos();
		if (this.firstPos != null && !this.isFlying()) {
			if (lastPos.squaredDistanceTo(firstPos) < 0.0001 && this.CollidesWithPlant(1f, 0f) == null && !this.hasStatusEffect(PvZCubed.BOUNCED) && this.getTarget() != null && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE) && !this.hasStatusEffect(PvZCubed.ICE) && this.age >= 30 && this.attackingTick <= 0 && --this.unstuckDelay <= 0 && !this.isInsideWaterOrBubbleColumn()) {
				this.setVelocity(0, 0, 0);
				this.addVelocity(0, 0.3, 0);
				++this.stuckTimes;
				this.unstuckDelay = 20;
			}
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN)){
			this.removeStatusEffect(STUN);
		}
		// thanks to Pluiedev for this hipster code
		var zombiePropEntity = this.getPassengerList()
				.stream()
				.filter(e -> e instanceof ZombiePropEntity)
				.map(e -> (ZombiePropEntity) e)
				.findFirst();
		if (zombiePropEntity.isPresent()) {
			var e = zombiePropEntity.get();
			if (this.getType().equals(PvZEntity.PYRAMIDHEAD)){
				e.setHypno(IsHypno.FALSE);
			}
			if (e.hasStatusEffect(FROZEN)){
				e.removeStatusEffect(STUN);
				this.removeStatusEffect(STUN);
			}
			if (e.isCovered()){
				e.removeStatusEffect(STUN);
				this.removeStatusEffect(STUN);
			}
			if (e.isCovered()){
				this.removeStatusEffect(PVZPOISON);
				this.removeStatusEffect(StatusEffects.POISON);
			}
		}
		if (this.getAttacker() instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()){
			this.setTarget(generalPvZombieEntity);
		}
		if (zombiePropEntity.isPresent()) {
			var e = zombiePropEntity.get();
			if (e.getAttacker() instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()){
				this.setTarget(generalPvZombieEntity);
			}
		}
		if (this.getTarget() != null && this.getTarget() instanceof PlantEntity) {
			if ((PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("ground") || (this.getTarget() instanceof PlantEntity plantEntity && plantEntity.getLowProfile())) && TARGET_GROUND.get(this.getType()).orElse(false).equals(false)) {
				this.setTarget(null);
			} else if (PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("flying") && TARGET_FLY.get(this.getType()).orElse(false).equals(false)) {
				this.setTarget(null);
			}
		}
		if (IS_MACHINE.get(this.getType()).orElse(false).equals(false)){
			this.removeStatusEffect(DISABLE);
		}
		else {
			this.removeStatusEffect(STUN);
		}
		if (this.isCovered()){
			this.removeStatusEffect(STUN);
		}
		if (this.isCovered()){
			this.removeStatusEffect(PVZPOISON);
			this.removeStatusEffect(StatusEffects.POISON);
		}
		if (!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("metallic")) && this.hasStatusEffect(ACID)){
			this.removeStatusEffect(ACID);
		}
		LivingEntity target = this.getTarget();
		if (this.getHypno() && (target instanceof PlayerEntity || target instanceof PassiveEntity || target instanceof GolemEntity)){
			this.setTarget(null);
		}
		if (this.isInsideWall()){
			this.setPosition(this.getX(), this.getY() + 1, this.getZ());
		}
		if (!this.hasNoGravity() && !this.isFlying()) {
			if (target != null) {
				if (target.squaredDistanceTo(this) < 2.25 && !this.hasStatusEffect(PvZCubed.BOUNCED)) {
					this.setVelocity(0, -0.3, 0);
				}
			}
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN) && this.isInsideWaterOrBubbleColumn()){
			this.kill();
		}
		if (this.hasStatusEffect(BOUNCED) && ZOMBIE_SIZE.get(this.getType()).orElse("normal").equals("small") && this.isAlive()){
			this.addVelocity(0, 1, 0);
			this.kill();
		}

		if (this.world.isClient) {
			if (zombiePropEntity.isPresent()) {
				var e = zombiePropEntity.get();
				this.geardmg = e.getHealth() < e.getMaxHealth() / 2;
				this.gearless = false;
			} else {
				this.gearless = true;
				this.geardmg = false;
			}

			this.armless = this.getHealth() < this.getMaxHealth() / 2;
		}
		if (this.getHealth() < this.getMaxHealth() / 2 && !(this instanceof ZombiePropEntity) &&
				!ZOMBIE_SIZE.get(this.getType()).orElse("medium").equals("gargantuar") && !ZOMBIE_SIZE.get(this.getType()).orElse("medium").equals("small") &&
				!(this instanceof ZombieKingEntity) && !(this instanceof ZombieVehicleEntity) && IS_MACHINE.get(this.getType()).orElse(false).equals(false)) {
			if (this.pop && !this.dead) {
				playSound(PvZSounds.POPLIMBEVENT, 0.75f, (float) (0.5F + Math.random()));
				pop = false;
			}
		}
		if (this.getTarget() instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno() && this.getHypno()){
			this.setTarget(null);
		}
		if (this.getTarget() instanceof FireTrailEntity){
			this.setTarget(null);
		}
		if (this.getTarget() instanceof TileEntity){
			this.setTarget(null);
		}
		if (this.getTarget() instanceof ZombieObstacleEntity zombieObstacleEntity && !zombieObstacleEntity.getHypno() && !this.getHypno()){
			this.setTarget(null);
		}
		if (this.getTarget() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(this instanceof ZombieKingEntity)){
			var targetPropEntity = generalPvZombieEntity.getPassengerList()
					.stream()
					.filter(e -> e instanceof ZombiePropEntity)
					.map(e -> (ZombiePropEntity) e)
					.findFirst();
			if (targetPropEntity.isPresent()){
				var e = targetPropEntity.get();
				this.setTarget(e);
			}
		}
		if (this.submergedInWater){
			this.jump();
		}
		if (!(ZOMBIE_MATERIAL.get(this.getType()).orElse("flesh").equals("flesh")) && (this.hasStatusEffect(PVZPOISON) || this.hasStatusEffect(StatusEffects.POISON) ) && !(this instanceof ZombiePropEntity)){
			this.removeStatusEffect(PVZPOISON);
			this.removeStatusEffect(StatusEffects.POISON);
		}
		if (this.hasStatusEffect(WARM) || this.isOnFire()){
			this.removeStatusEffect(FROZEN);
			this.removeStatusEffect(ICE);
		}
		if (!this.getHypno() && !(this instanceof ZombieKingEntity) && this.getTarget() == null && --this.playerGetTick <= 0) {
			this.setTarget(this.world.getClosestPlayer(this.getX(), this.getY(), this.getZ(), 100, true));
			this.playerGetTick = 200;
		}
		if (frozenStart){
			this.frzYaw = this.getYaw();
			this.frzPitch = this.getPitch();
			this.frzHeadYaw = this.getHeadYaw();
			this.frzBodyYaw = this.bodyYaw;
		}
		if (this.hasStatusEffect(FROZEN) || this.hasStatusEffect(DISABLE) || this.hasStatusEffect(STUN) || this.isFrozen || this.isStunned) {
			this.setHeadYaw(frzHeadYaw);
			this.setBodyYaw(frzBodyYaw);
			frozenStart = false;
		}
		else if (!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(DISABLE) && !this.hasStatusEffect(STUN) && !this.isFrozen && !this.isStunned) {
			frozenStart = true;
		}
		super.tick();
		if (this.hasStatusEffect(FROZEN) || this.hasStatusEffect(DISABLE) || this.hasStatusEffect(STUN) || this.isFrozen || this.isStunned) {
			this.setHeadYaw(frzHeadYaw);
			this.setBodyYaw(frzBodyYaw);
			frozenStart = false;
		}
		else if (!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(DISABLE) && !this.hasStatusEffect(STUN) && !this.isFrozen && !this.isStunned) {
			frozenStart = true;
		}
		if (fireSplashTicks == 10){
			this.world.sendEntityStatus(this, (byte) 80);
		}
		--fireSplashTicks;
	}

	protected void jumpOverGap(){
		int airBlocks = 0;
		boolean hasBlockAtEnd = false;
		for (int x = 1; x < PVZCONFIG.nestedGeneralZombie.zombieBlockJump() + 2; ++x) {
			Vec3d vec3d2 = new Vec3d((double) x, -0.25, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			int l = MathHelper.floor(this.getPos().x + vec3d2.x);
			int m = MathHelper.floor(this.getPos().y + vec3d2.y);
			int n = MathHelper.floor(this.getPos().z + vec3d2.z);
			BlockPos blockPos2 = new BlockPos(l, m, n);
			if (!this.world.getBlockState(blockPos2).isAir()) {
				hasBlockAtEnd = true;
				break;
			} else {
				++airBlocks;
			}
		}
		if (airBlocks > 0 && hasBlockAtEnd) {
			Vec3d vec3d2 = new Vec3d((double) airBlocks / (Math.pow(2, (float) -airBlocks / 2) * 10), 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			if (airBlocks == 1){
				vec3d2 = new Vec3d(0.2, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			}
			this.setVelocity(vec3d2.x, 0.5, vec3d2.z);
			this.canJump = false;
		}
	}

	@Override
	public boolean tryAttack(Entity target) {
		if (this.age > 1) {
			if (this.getTarget() != null &&
					(((PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("ground") || (this.getTarget() instanceof PlantEntity plantEntity && plantEntity.getLowProfile())) &&
							TARGET_GROUND.get(this.getType()).orElse(false).equals(true)) ||
							(PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("flying") &&
									TARGET_FLY.get(this.getType()).orElse(false).equals(true)))) {
				if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
					float sound = 0.75f;
					if (this.getHypno()) {
						sound = 0.33f;
					}
					target.playSound(PvZSounds.ZOMBIEBITEEVENT, sound, 1f);
					this.setStealthTag(Stealth.FALSE);
					if (target instanceof HypnoshroomEntity hypnoshroomEntity && !hypnoshroomEntity.getIsAsleep() && !this.isCovered()){
						if (!ZOMBIE_SIZE.get(this.getType()).orElse("medium").equals("small")) {
							hypnoshroomEntity.damage(DamageSource.mob(this), hypnoshroomEntity.getMaxHealth() * 5);
						}
						this.damage(HYPNO_DAMAGE, 0);
					}
				}
				return super.tryAttack(target);
			} else if (this.getTarget() != null &&
					!((PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("ground"))) &&
					!(this.getTarget() instanceof PlantEntity plantEntity && plantEntity.getLowProfile()) &&
					!((PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("flying")))) {
				if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
					float sound = 0.75f;
					if (this.getHypno()) {
						sound = 0.33f;
					}
					target.playSound(PvZSounds.ZOMBIEBITEEVENT, sound, 1f);
					this.setStealthTag(Stealth.FALSE);
					if (target instanceof HypnoshroomEntity hypnoshroomEntity && !hypnoshroomEntity.getIsAsleep() && !this.isCovered()){
						if (!ZOMBIE_SIZE.get(this.getType()).orElse("medium").equals("small")) {
							hypnoshroomEntity.damage(DamageSource.mob(this), hypnoshroomEntity.getMaxHealth() * 5);
						}
						this.damage(HYPNO_DAMAGE, 0);
					}
				}
				return super.tryAttack(target);
			} else {
				return false;
			}
		}
		else {
			return false;
		}
	}
}
