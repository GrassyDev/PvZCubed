package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;

public abstract class PlantEntity extends GolemEntity {

	public boolean onWater;

	public boolean naturalSpawn;

	protected boolean dryLand;

	@Override
	public boolean canBeLeashedBy(PlayerEntity player) {
		return false;
	}

	protected PlantEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	public void rideLilyPad(LivingEntity livingEntity){
		this.refreshPositionAndAngles(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.bodyYaw, 0.0F);
		this.startRiding(livingEntity);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_ASLEEP, false);
		this.dataTracker.startTracking(DATA_ID_LOWPROF, false);
		this.dataTracker.startTracking(DATA_ID_FIREIMMUNE, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Asleep", this.getIsAsleep());
		tag.putBoolean("lowProf", this.getLowProfile());
		tag.putBoolean("fireImmune", this.getFireImmune());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_ASLEEP, tag.getBoolean("Asleep"));
		this.dataTracker.set(DATA_ID_LOWPROF, tag.getBoolean("lowProf"));
		this.dataTracker.set(DATA_ID_FIREIMMUNE, tag.getBoolean("fireImmune"));
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/


	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (PLANT_LOCATION.get(this.getType()).orElse("normal").equals("ground")){
			this.setLowprof(LowProf.TRUE);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}


	//Flying Tag

	protected static final TrackedData<Boolean> DATA_ID_LOWPROF =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum LowProf {
		FALSE(false),
		TRUE(true);

		LowProf(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getLowProfile() {
		return this.dataTracker.get(DATA_ID_LOWPROF);
	}

	public void setLowprof(PlantEntity.LowProf lowprof) {
		this.dataTracker.set(DATA_ID_LOWPROF, lowprof.getId());
	}

	// Fire Immune

	protected static final TrackedData<Boolean> DATA_ID_FIREIMMUNE =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum FireImmune {
		FALSE(false),
		TRUE(true);

		FireImmune(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getFireImmune() {
		return this.dataTracker.get(DATA_ID_FIREIMMUNE);
	}

	public void setFireImmune(PlantEntity.FireImmune fireImmune) {
		this.dataTracker.set(DATA_ID_FIREIMMUNE, fireImmune.getId());
	}


	protected static final TrackedData<Boolean> DATA_ID_ASLEEP =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum IsAsleep {
		FALSE(false),
		TRUE(true);

		IsAsleep(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getIsAsleep() {
		return this.dataTracker.get(DATA_ID_ASLEEP);
	}

	public void setIsAsleep(PlantEntity.IsAsleep asleep) {
		this.dataTracker.set(DATA_ID_ASLEEP, asleep.getId());
	}

	/** ----------------------------------------------------------------------- **/

	public void tick() {
		if (this.getFireImmune()){
			this.setFireTicks(0);
		}
		super.tick();
		Entity vehicle = this.getVehicle();
		if (vehicle instanceof LilyPadEntity){
			vehicle.setBodyYaw(this.bodyYaw);
		}
	}

	@Override
	public void onDeath(DamageSource source) {
		if (!PLANT_LOCATION.get(this.getType()).orElse("normal").equals("flying")){
			RandomGenerator randomGenerator = this.getRandom();
			BlockState blockState = this.getLandingBlockState();
			for (int i = 0; i < 4; ++i) {
				double d = this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.4F, 0.4F);
				double e = this.getY() + 0.3;
				double f = this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.4F, 0.4F);
				this.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), d, e, f, 0.0, 0.0, 0.0);
			}
		}
		super.onDeath(source);
		super.discard();
	}

	@Override
	protected void dropLoot(DamageSource source, boolean causedByPlayer) {
		if (this.world.getGameRules().getBoolean(PvZCubed.SHOULD_PLANT_DROP)){
			super.dropLoot(source, causedByPlayer);
		}
	}

	public HitResult amphibiousRaycast(double maxDistance) {
		Vec3d vec3d1 = this.getPos();
		Vec3d vec3d2 = new Vec3d(vec3d1.x, vec3d1.y - maxDistance, vec3d1.z);
		return this.world.raycast(new RaycastContext(vec3d1, vec3d2, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, this));
	}

	public static class PlantData implements EntityData {
		public final boolean tryLilyPad;

		public PlantData(boolean tryLilyPad) {
			this.tryLilyPad = tryLilyPad;
		}
	}
}
