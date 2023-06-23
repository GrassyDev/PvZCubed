package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ImpVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class SuperFanImpEntity extends ImpEntity implements IAnimatable {
	public SuperFanImpEntity(EntityType<? extends ImpEntity> entityType, World world) {
		super(entityType, world);
	}

	private static final TrackedData<Integer> FUSE_SPEED;
	private static final TrackedData<Boolean> CHARGED;
	private static final TrackedData<Boolean> IGNITED;
	private int currentFuseTime;
	private int fuseTime = 30;
	private int explosionRadius = 1;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private String controllerName = "superfancontroller";

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FUSE_SPEED, -1);
		this.dataTracker.startTracking(CHARGED, false);
		this.dataTracker.startTracking(IGNITED, false);
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, true);
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if ((Boolean)this.dataTracker.get(CHARGED)) {
			nbt.putBoolean("powered", true);
		}

		nbt.putShort("Fuse", (short)this.fuseTime);
		nbt.putByte("ExplosionRadius", (byte)this.explosionRadius);
		nbt.putBoolean("ignited", this.getIgnited());
		nbt.putBoolean("Fire", this.getFireStage());
		nbt.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(CHARGED, nbt.getBoolean("powered"));
		if (nbt.contains("Fuse", 99)) {
			this.fuseTime = nbt.getShort("Fuse");
		}

		if (nbt.contains("ExplosionRadius", 99)) {
			this.explosionRadius = nbt.getByte("ExplosionRadius");
		}

		if (nbt.getBoolean("ignited")) {
			this.ignite();
		}
		this.dataTracker.set(DATA_ID_TYPE_COUNT, nbt.getBoolean("Fire"));
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
	}

	static {
		FUSE_SPEED = DataTracker.registerData(SuperFanImpEntity.class, TrackedDataHandlerRegistry.INTEGER);
		CHARGED = DataTracker.registerData(SuperFanImpEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IGNITED = DataTracker.registerData(SuperFanImpEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		RandomGenerator randomGenerator = this.getRandom();
		if (status == 106) {
			for(int i = 0; i < 170; ++i) {
				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * this.random.range(0, 1);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.world.addParticle(ParticleTypes.SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.world.addParticle(ParticleTypes.FLAME, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 16; ++i) {
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1));
				this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + (this.random.range(-1, 1)),
						this.getZ()  + (double)MathHelper.nextBetween(randomGenerator,
								-0.5F, 0.5F), 0, e, 0);
			}
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(SuperFanImpEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum FireStage {
		FIRE(true),
		EXTINGUISHED(false);

		FireStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getFireStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setFireStage(SuperFanImpEntity.FireStage fireStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, fireStage.getId());
	}

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(SuperFanImpEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		setFireStage(FireStage.FIRE);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public ImpVariants getVariant() {
		return ImpVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(ImpVariants variant) {
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
		if (this.getFireStage()){
			if (this.isInsideWaterOrBubbleColumn()) {
				event.getController().setAnimation(new AnimationBuilder().loop("imp.ducky"));
			} else {
				if (!this.isOnGround()) {
					event.getController().setAnimation(new AnimationBuilder().loop("imp.ball"));
					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					} else if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					} else {
						event.getController().setAnimationSpeed(1);
					}
				} else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("imp.run"));

					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					} else if (this.isIced) {
						event.getController().setAnimationSpeed(0.75);
					} else {
						event.getController().setAnimationSpeed(1.5);
					}
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("imp.idle"));

					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					} else if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					} else {
						event.getController().setAnimationSpeed(1);
					}
				}
			}
		}
		else {
			if (this.isInsideWaterOrBubbleColumn()) {
				event.getController().setAnimation(new AnimationBuilder().loop("imp.ducky.gearless"));
			} else {
				if (!this.isOnGround()) {
					event.getController().setAnimation(new AnimationBuilder().loop("imp.ball.gearless"));
					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					} else if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					} else {
						event.getController().setAnimationSpeed(1);
					}
				} else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("imp.run.gearless"));

					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					} else if (this.isIced) {
						event.getController().setAnimationSpeed(0.75);
					} else {
						event.getController().setAnimationSpeed(1.5);
					}
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("imp.idle.gearless"));

					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					} else if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					} else {
						event.getController().setAnimationSpeed(1);
					}
				}
			}
		}
		return PlayState.CONTINUE;
	}

	/** /~*~//~*AI*~//~*~/ **/

	@Override
	protected void initGoals() {
		super.initGoals();
	}

	@Override
	protected void initCustomGoals() {
		super.initCustomGoals();
		this.goalSelector.add(2, new ImpIgniteGoal(this));
		this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0D, false));
	}

	@Override
	protected void initHypnoGoals() {
		super.initHypnoGoals();
		this.goalSelector.add(2, new ImpIgniteGoal(this));
		this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0D, false));
	}

	public int getFuseSpeed() {
		return (Integer)this.dataTracker.get(FUSE_SPEED);
	}

	public void setFuseSpeed(int fuseSpeed) {
		this.dataTracker.set(FUSE_SPEED, fuseSpeed);
	}

	public boolean getIgnited() {
		return (Boolean)this.dataTracker.get(IGNITED);
	}

	public void ignite() {
		this.dataTracker.set(IGNITED, true);
	}

	private void raycastExplode() {
		double squaredDist;
		if (this.getVariant().equals(ImpVariants.NEWYEAR)){
			squaredDist = 25;
		}
		else {
			squaredDist = 9;
		}
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5));
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
			} while (this.squaredDistanceTo(livingEntity) > squaredDist);

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
				if (this.getHypno()){
					if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						livingEntity.damage(DamageSource.explosion(this), 30);
					}
				}
				else {
					if (livingEntity instanceof PlantEntity || (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
						livingEntity.damage(DamageSource.explosion(this), 30);
					}
				}
			}
		}
	}

	private void spawnEffectsCloud() {
		AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
		areaEffectCloudEntity.setParticleType(ParticleTypes.SMOKE);
		areaEffectCloudEntity.setRadius(6F);
		areaEffectCloudEntity.setRadiusOnUse(-0.5F);
		areaEffectCloudEntity.setWaitTime(5);
		areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 35);
		areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());
		this.world.spawnEntity(areaEffectCloudEntity);
		AreaEffectCloudEntity areaEffectCloudEntity2 = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
		areaEffectCloudEntity2.setParticleType(ParticleTypes.SMALL_FLAME);
		areaEffectCloudEntity2.setRadius(2F);
		areaEffectCloudEntity2.setRadiusOnUse(-0.5F);
		areaEffectCloudEntity2.setWaitTime(5);
		areaEffectCloudEntity2.setDuration(areaEffectCloudEntity2.getDuration() / 80);
		areaEffectCloudEntity2.setRadiusGrowth(-areaEffectCloudEntity2.getRadius() / (float)areaEffectCloudEntity2.getDuration());
		this.world.spawnEntity(areaEffectCloudEntity2);
	}

	public boolean tryAttack(Entity target) {
		if (!this.getFireStage()){
			return super.tryAttack(target);
		}
		else {
			return false;
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	private boolean isBeingRainedOn() {
		BlockPos blockPos = this.getBlockPos();
		return this.world.hasRain(blockPos) || this.world.hasRain(new BlockPos((double)blockPos.getX(), this.getBoundingBox().maxY, (double)blockPos.getZ()));
	}
	private float getAttackDamage(){
		return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
	}

	public void tick() {
		super.tick();
		if (this.isBeingRainedOn() || this.hasStatusEffect(PvZCubed.ICE) || this.hasStatusEffect(PvZCubed.FROZEN) || this.hasStatusEffect(PvZCubed.WET) || this.isSubmergedInWater()){
			this.setFireStage(FireStage.EXTINGUISHED);
		}
		else if (this.isOnFire() || this.hasStatusEffect(PvZCubed.WARM)){
			this.setFireStage(FireStage.FIRE);
		}
		if (this.age < 20){
			this.setTarget(null);
		}
		if (this.isAlive() && this.age > 20 && this.getFireStage()) {
			if (this.getIgnited()) {
				this.setFuseSpeed(1);
			}

			int i = this.getFuseSpeed();
			if (i > 0 && this.currentFuseTime == 0) {
				RandomGenerator randomGenerator = this.getRandom();
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
				for(int j = 0; j < 4; ++j) {
					double e = (double)MathHelper.nextBetween(randomGenerator, 0.025F, 0.075F);
					this.world.addParticle(ParticleTypes.SMALL_FLAME, this.getX(), this.getY() + 1, this.getZ(), 0, e, 0);
				}
			}

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
			}

			if (this.currentFuseTime >= this.fuseTime) {
				this.currentFuseTime = this.fuseTime;
				this.world.sendEntityStatus(this, (byte) 106);
				this.raycastExplode();
				this.playSound(PvZSounds.CHERRYBOMBEXPLOSIONEVENT, 1F, 1F);
				this.spawnEffectsCloud();
				this.dead = true;
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getVariant().equals(ImpVariants.NEWYEAR)){
			itemStack = ModItems.NEWYEARIMPEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.SUPERFANIMPEGG.getDefaultStack();
		}
		return itemStack;
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

	public static DefaultAttributeContainer.Builder createSuperFanImpAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.superFanH());
	}
}
