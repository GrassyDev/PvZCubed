package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.metallicvehicle;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile.SnowTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tallnut.TallnutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BobsledPersonalityVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.bobsledteam.BobsledRiderEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.zomboni.ZomboniEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieVehicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.random.RandomGenerator;
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

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class MetalVehicleEntity extends ZombieVehicleEntity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private String controllerName = "shieldcontroller";

    public MetalVehicleEntity(EntityType<? extends MetalVehicleEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.experiencePoints = 3;
	}

	static {

	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SLIDING_TAG, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("isSliding", this.isSliding());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(SLIDING_TAG, tag.getBoolean("isSliding"));
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


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));
	}

	/** /~*~//~*TICKING*~//~*~/ **/


	public void tick() {
		super.tick();
		boolean toDie = false;
		if (this.getType().equals(PvZEntity.ZOMBONIVEHICLE)) {
			GeneralPvZombieEntity zombiePassenger = null;
			for (Entity entity : this.getPassengerList()) {
				if (entity instanceof GeneralPvZombieEntity) {
					zombiePassenger = (GeneralPvZombieEntity) entity;
				}
			}
			if (zombiePassenger == null && this.isAlive() && !this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(DISABLE)){
				Vec3d vec3d2 = new Vec3d((double) 0.08, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				this.setVelocity(vec3d2);
			}
			for (float x = 0; x <= 1; ++x) {
				if (this.CollidesWithPlant(x, 0f) != null && !this.getPassengerList().contains(this.CollidesWithPlant(x, 0f)) && this.isAlive()) {
					if (this.CollidesWithPlant(x, 0f) instanceof SpikerockEntity) {
						this.CollidesWithPlant(x, 0f).damage(DamageSource.mob(this), 90);
						toDie = true;
					} else if (this.CollidesWithPlant(x, 0f) instanceof SpikeweedEntity) {
						this.CollidesWithPlant(x, 0f).damage(DamageSource.mob(this), 360);
						toDie = true;
					} else if (this.CollidesWithPlant(x, 0f) != null) {
						this.CollidesWithPlant(x, 0f).damage(DamageSource.mob(this), 360);
					}
				}
			}
			Vec3d vec3d = new Vec3d((double) 1.25, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			List<PvZProjectileEntity> list = world.getNonSpectatingEntities(PvZProjectileEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
			for (PvZProjectileEntity projectileEntity : list) {
				projectileEntity.moreEntities.add(this);
				projectileEntity.hitEntities();
			}
			if (age >= 150 && !this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(DISABLE)){
				Vec3d vec3d2 = new Vec3d((double) -1, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				BlockPos blockPos = new BlockPos(Vec3d.ofCenter(new Vec3i(this.getX() + vec3d2.x, this.getY() + vec3d2.y, this.getZ() + vec3d2.z)));
				this.createSnowTile(blockPos);
			}
		}

		if (this.getType().equals(PvZEntity.BOBSLEDVEHICLE)) {
			if (isSliding() && this.isAlive()){
				Vec3d vec3d2 = new Vec3d((double) 0.16, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				this.setVelocity(vec3d2);
			}
			GeneralPvZombieEntity zombiePassenger = null;
			GeneralPvZombieEntity zombiePassenger2 = null;
			GeneralPvZombieEntity zombiePassenger3 = null;
			GeneralPvZombieEntity zombiePassenger4 = null;
			for (Entity entity : this.getPassengerList()) {
				if (entity instanceof GeneralPvZombieEntity && zombiePassenger == null) {
					zombiePassenger = (GeneralPvZombieEntity) entity;
				}
				else if (entity instanceof GeneralPvZombieEntity && zombiePassenger2 == null) {
					zombiePassenger2 = (GeneralPvZombieEntity) entity;
				}
				else if (entity instanceof GeneralPvZombieEntity && zombiePassenger3 == null) {
					zombiePassenger3 = (GeneralPvZombieEntity) entity;
				}
				else if (entity instanceof GeneralPvZombieEntity && zombiePassenger4 == null) {
					zombiePassenger4 = (GeneralPvZombieEntity) entity;
				}
			}
			if (isSliding()) {
				for (float z = -1; z <= 1; ++z) {
					Vec3d vec3d = new Vec3d(0.0, 0.0, z + 0.25).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					List<PvZProjectileEntity> list = world.getNonSpectatingEntities(PvZProjectileEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
					for (PvZProjectileEntity projectileEntity : list) {
						projectileEntity.moreEntities.add(this);
						projectileEntity.hitEntities();
					}
				}
				for (float z = -1; z <= 1; ++z) {
					if (this.CollidesWithPlant(0f, z) != null && !this.getPassengerList().contains(this.CollidesWithPlant(0f, z)) && this.isAlive()) {
						if (this.CollidesWithPlant(0f, z) instanceof SpikerockEntity) {
							this.CollidesWithPlant(0f, z).damage(DamageSource.mob(this), 90);
							toDie = true;
						} else if (this.CollidesWithPlant(0f, z) instanceof SpikeweedEntity) {
							this.CollidesWithPlant(0f, z).damage(DamageSource.mob(this), 360);
							toDie = true;
						} else if (this.CollidesWithPlant(0f, z) instanceof TallnutEntity) {
							this.CollidesWithPlant(0f, z).damage(DamageSource.mob(this), 90);
							toDie = true;
							Vec3d vec3d = new Vec3d((double)-0.5, 0.0, 0.0f).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
							this.setVelocity(vec3d);
						} else if (this.CollidesWithPlant(0f, z) != null) {
							this.CollidesWithPlant(0f, z).damage(DamageSource.mob(this), 360);
						}
					}
				}
			}
			else {
				for (float x = -1; x <= 1; ++x) {
					Vec3d vec3d = new Vec3d((double) x + 0.25, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					List<PvZProjectileEntity> list = world.getNonSpectatingEntities(PvZProjectileEntity.class, entityBox.getDimensions().getBoxAt(this.getX() + vec3d.x, this.getY(), this.getZ() + vec3d.z));
					for (PvZProjectileEntity projectileEntity : list) {
						projectileEntity.moreEntities.add(this);
						projectileEntity.hitEntities();
					}
				}
				for (float x = 0; x <= 1; ++x) {
					if (this.CollidesWithPlant(x, 0f) != null && !this.getPassengerList().contains(this.CollidesWithPlant(x, 0f)) && this.isAlive()) {
						if (this.CollidesWithPlant(x, 0f) instanceof SpikerockEntity) {
							this.CollidesWithPlant(x, 0f).damage(DamageSource.mob(this), 90);
							toDie = true;
						} else if (this.CollidesWithPlant(x, 0f) instanceof SpikeweedEntity) {
							this.CollidesWithPlant(x, 0f).damage(DamageSource.mob(this), 360);
							toDie = true;
						} else if (this.CollidesWithPlant(x, 0f) != null) {
							this.CollidesWithPlant(x, 0f).damage(DamageSource.mob(this), 360);
						}
					}
				}
			}
			List<TileEntity> list = world.getNonSpectatingEntities(TileEntity.class, entityBox.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()));
			for (TileEntity tileEntity : list) {
				if (tileEntity instanceof SnowTile){
					this.setSliding(Sliding.TRUE);
				}
			}
		}
		if (toDie){
			this.kill();
		}
	}

	@Override
	public void updatePassengerPosition(Entity passenger) {
		if (this.getType().equals(PvZEntity.BOBSLEDVEHICLE)) {
			if (!this.isSliding()) {
				float g = (float) ((this.isRemoved() ? 0.01F : this.getMountedHeightOffset()) + passenger.getHeightOffset());
				Vec3d vec3d = new Vec3d((double) 1, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				if (this.getPassengerList().size() == 1) {
					vec3d = new Vec3d((double) 0.3, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				}
				if (this.getPassengerList().size() == 2) {
					if (this.getPassengerList().get(1).equals(passenger)) {
						vec3d = new Vec3d((double) 0.5, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					} else {
						vec3d = new Vec3d((double) -0.3, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
				} else if (this.getPassengerList().size() == 3) {
					if (this.getPassengerList().get(1).equals(passenger)) {
						vec3d = new Vec3d((double) 0, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					} else if (this.getPassengerList().get(2).equals(passenger)) {
						vec3d = new Vec3d((double) -0.6, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					} else {
						vec3d = new Vec3d((double) 0.7, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
				} else if (this.getPassengerList().size() == 4) {
					if (this.getPassengerList().get(1).equals(passenger)) {
						vec3d = new Vec3d((double) 0.3, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
					if (this.getPassengerList().get(2).equals(passenger)) {
						vec3d = new Vec3d((double) -0.4, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
					if (this.getPassengerList().get(3).equals(passenger)) {
						vec3d = new Vec3d((double) -1, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
				}
				passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
				passenger.setBodyYaw(this.bodyYaw);
			}
			else {
				float g = (float) ((this.isRemoved() ? 0.01F : this.getMountedHeightOffset()) + passenger.getHeightOffset());
				Vec3d vec3d = new Vec3d((double) 0, 0.0, 1).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				if (this.getPassengerList().size() == 1) {
					vec3d = new Vec3d((double) 0, 0.0, 0.3).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				}
				if (this.getPassengerList().size() == 2) {
					if (this.getPassengerList().get(1).equals(passenger)) {
						vec3d = new Vec3d((double) 0, 0.0, 0.5).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					} else {
						vec3d = new Vec3d((double) 0, 0.0, -0.3).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
				} else if (this.getPassengerList().size() == 3) {
					if (this.getPassengerList().get(1).equals(passenger)) {
						vec3d = new Vec3d((double) 0, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					} else if (this.getPassengerList().get(2).equals(passenger)) {
						vec3d = new Vec3d((double) 0, 0.0, -0.6).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					} else {
						vec3d = new Vec3d((double) 0, 0.0, 0.7).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
				} else if (this.getPassengerList().size() == 4) {
					if (this.getPassengerList().get(1).equals(passenger)) {
						vec3d = new Vec3d((double) 0, 0.0, 0.3).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
					if (this.getPassengerList().get(2).equals(passenger)) {
						vec3d = new Vec3d((double) 0, 0.0, -0.4).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
					if (this.getPassengerList().get(3).equals(passenger)) {
						vec3d = new Vec3d((double) 0, 0.0, -1).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
				}
				passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
				passenger.setBodyYaw(this.bodyYaw);
			}
		}
		else {
			super.updatePassengerPosition(passenger);
		}
	}

	@Override
	public double getMountedHeightOffset() {
		return 0.25f;
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
		if (this.getType().equals(PvZEntity.ZOMBONIVEHICLE)) {
			if (this.isInsideWaterOrBubbleColumn()) {
				event.getController().setAnimation(new AnimationBuilder().loop("zomboni.walking"));
				if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				} else {
					event.getController().setAnimationSpeed(1);
				}
			} else {
				if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(new AnimationBuilder().loop("zomboni.walking"));
				} else {
					event.getController().setAnimation(new AnimationBuilder().loop("zomboni.idle"));
				}
				if (this.isFrozen || this.isStunned) {
					event.getController().setAnimationSpeed(0);
				} else if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				} else {
					event.getController().setAnimationSpeed(1);
				}
			}
		}
		else {
			if (isSliding()){
				event.getController().setAnimation(new AnimationBuilder().loop("bobsled.fast"));
			}
			else {
				event.getController().setAnimation(new AnimationBuilder().loop("bobsled.idle"));
			}
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			} else {
				event.getController().setAnimationSpeed(1);
			}
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*VARIANTS*~//~*~/ **/

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.ZOMBONIVEHICLE)) {
			createZomboniPassenger();
		}
		else {
			createBobsledPassenger();
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public void createZomboniPassenger() {
		if (world instanceof ServerWorld serverWorld) {
			ZomboniEntity zomboniEntity = new ZomboniEntity(PvZEntity.ZOMBONI, this.world);
			zomboniEntity.initialize(serverWorld, this.world.getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			zomboniEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			zomboniEntity.startRiding(this);
		}
	}

	public void createBobsledPassenger() {
		if (world instanceof ServerWorld serverWorld) {
			BobsledRiderEntity bobsledEntity = new BobsledRiderEntity(PvZEntity.BOBSLED, this.world);
			bobsledEntity.setPersonality(BobsledPersonalityVariants.LEADER);
			bobsledEntity.initialize(serverWorld, this.world.getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			bobsledEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			bobsledEntity.startRiding(this, true);

			BobsledRiderEntity bobsledEntity2 = new BobsledRiderEntity(PvZEntity.BOBSLED, this.world);
			bobsledEntity2.setPersonality(BobsledPersonalityVariants.MOVER);
			bobsledEntity2.initialize(serverWorld, this.world.getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			bobsledEntity2.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			bobsledEntity2.startRiding(this, true);

			BobsledRiderEntity bobsledEntity3 = new BobsledRiderEntity(PvZEntity.BOBSLED, this.world);
			bobsledEntity3.setPersonality(BobsledPersonalityVariants.YOUNG);
			bobsledEntity3.initialize(serverWorld, this.world.getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			bobsledEntity3.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			bobsledEntity3.startRiding(this, true);

			BobsledRiderEntity bobsledEntity4 = new BobsledRiderEntity(PvZEntity.BOBSLED, this.world);
			bobsledEntity4.setPersonality(BobsledPersonalityVariants.DEFAULT);
			bobsledEntity4.initialize(serverWorld, this.world.getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			bobsledEntity4.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			bobsledEntity4.startRiding(this, true);
		}
	}

	//Sliding Tag

	protected static final TrackedData<Boolean> SLIDING_TAG =
			DataTracker.registerData(MetalVehicleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Sliding {
		FALSE(false),
		TRUE(true);

		Sliding(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean isSliding() {
		return this.dataTracker.get(SLIDING_TAG);
	}

	public void setSliding(MetalVehicleEntity.Sliding sliding) {
		this.dataTracker.set(SLIDING_TAG, sliding.getId());
	}

	//////

	@Override
	public void onDeath(DamageSource source) {
		if (this.getType().equals(PvZEntity.ZOMBONIVEHICLE)) {
			this.world.sendEntityStatus(this, (byte) 106);
			this.playSound(PvZSounds.CHERRYBOMBEXPLOSIONEVENT, 1F, 1F);
		}
		else {
			this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 1F, 1F);
		}
		super.onDeath(source);
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public static DefaultAttributeContainer.Builder createZomboniVehicleAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.zomboniVH());
	}

	public static DefaultAttributeContainer.Builder createBobsledVehicleAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.bobsledVH());
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ITEM_SHIELD_BREAK;
	}

	protected SoundEvent getAmbientSound() {
		return PvZSounds.SILENCEVENET;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	protected SoundEvent getStepSound() {
		return PvZSounds.SILENCEVENET;
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		if (this.getType().equals(PvZEntity.ZOMBONIVEHICLE)) {
			return ModItems.ZOMBONIEGG.getDefaultStack();
		}
		else {
			return ModItems.BOBSLEDEGG.getDefaultStack();
		}
	}
}
