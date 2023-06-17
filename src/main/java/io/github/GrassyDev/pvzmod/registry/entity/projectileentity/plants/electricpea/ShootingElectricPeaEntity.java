package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.electricpea;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.torchwood.TorchwoodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.plasmapea.ShootingPlasmaPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ShootingElectricPeaEntity extends PvZProjectileEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);


	private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID;
	private static final TrackedData<Integer> ELECTRIC_BEAM_TARGET_ID;
	private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID2;
	private static final TrackedData<Integer> ELECTRIC_BEAM_TARGET_ID2;
	private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID3;
	private static final TrackedData<Integer> ELECTRIC_BEAM_TARGET_ID3;
	private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID4;
	private static final TrackedData<Integer> ELECTRIC_BEAM_TARGET_ID4;
	private static final TrackedData<Integer> SPARK_TARGET;
	private LivingEntity cachedBeamTarget;
	private LivingEntity cachedBeamTarget2;
	private LivingEntity cachedBeamTarget3;
	private LivingEntity cachedBeamTarget4;
	private LivingEntity cachedBeamTarget5;
	private LivingEntity cachedBeamTarget6;
	private LivingEntity cachedBeamTarget7;
	private LivingEntity cachedBeamTarget8;
	private LivingEntity cachedSparkTarget;
	private int beamTicks;

	private PlantEntity plantOwner;

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID, 0);
		this.dataTracker.startTracking(ELECTRIC_BEAM_TARGET_ID, 0);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID2, 0);
		this.dataTracker.startTracking(ELECTRIC_BEAM_TARGET_ID2, 0);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID3, 0);
		this.dataTracker.startTracking(ELECTRIC_BEAM_TARGET_ID3, 0);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID4, 0);
		this.dataTracker.startTracking(ELECTRIC_BEAM_TARGET_ID4, 0);
		this.dataTracker.startTracking(SPARK_TARGET, 0);
	}

	public void onTrackedDataSet(TrackedData<?> data) {
		if (HYPNO_BEAM_TARGET_ID.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget = null;
		}
		if (ELECTRIC_BEAM_TARGET_ID.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget2 = null;
		}
		if (HYPNO_BEAM_TARGET_ID2.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget3 = null;
		}
		if (ELECTRIC_BEAM_TARGET_ID2.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget4 = null;
		}
		if (HYPNO_BEAM_TARGET_ID3.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget5 = null;
		}
		if (ELECTRIC_BEAM_TARGET_ID3.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget6 = null;
		}
		if (HYPNO_BEAM_TARGET_ID4.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget7 = null;
		}
		if (ELECTRIC_BEAM_TARGET_ID4.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget8 = null;
		}
		if (SPARK_TARGET.equals(data)) {
			this.beamTicks = 0;
			this.cachedSparkTarget = null;
		}

		super.onTrackedDataSet(data);
	}

	static {
		HYPNO_BEAM_TARGET_ID = DataTracker.registerData(ShootingElectricPeaEntity.class, TrackedDataHandlerRegistry.INTEGER);
		ELECTRIC_BEAM_TARGET_ID = DataTracker.registerData(ShootingElectricPeaEntity.class, TrackedDataHandlerRegistry.INTEGER);
		HYPNO_BEAM_TARGET_ID2 = DataTracker.registerData(ShootingElectricPeaEntity.class, TrackedDataHandlerRegistry.INTEGER);
		ELECTRIC_BEAM_TARGET_ID2 = DataTracker.registerData(ShootingElectricPeaEntity.class, TrackedDataHandlerRegistry.INTEGER);
		HYPNO_BEAM_TARGET_ID3 = DataTracker.registerData(ShootingElectricPeaEntity.class, TrackedDataHandlerRegistry.INTEGER);
		ELECTRIC_BEAM_TARGET_ID3 = DataTracker.registerData(ShootingElectricPeaEntity.class, TrackedDataHandlerRegistry.INTEGER);
		HYPNO_BEAM_TARGET_ID4 = DataTracker.registerData(ShootingElectricPeaEntity.class, TrackedDataHandlerRegistry.INTEGER);
		ELECTRIC_BEAM_TARGET_ID4 = DataTracker.registerData(ShootingElectricPeaEntity.class, TrackedDataHandlerRegistry.INTEGER);
		SPARK_TARGET = DataTracker.registerData(ShootingElectricPeaEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	public LivingEntity torchwoodMemory;

	@Override
	public void registerControllers(AnimationData animationData) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		animationData.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable > PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().loop("peashot.idle"));
		return PlayState.CONTINUE;
	}

    public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "electricpea");

    public ShootingElectricPeaEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public ShootingElectricPeaEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public ShootingElectricPeaEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.PLASMAPEA, world);
		updatePosition(x, y, z);
		updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
		setUuid(uuid);
	}

    public void tick() {
        super.tick();
		if (this.getOwner() instanceof PlantEntity plantEntity){
			plantOwner = plantEntity;
		}
		HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
		RandomGenerator randomGenerator = this.random;
		boolean bl = false;
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
			BlockState blockState = this.world.getBlockState(blockPos);
			if (blockState.isOf(Blocks.NETHER_PORTAL)) {
				this.setInNetherPortal(blockPos);
				bl = true;
			} else if (blockState.isOf(Blocks.END_GATEWAY)) {
				BlockEntity blockEntity = this.world.getBlockEntity(blockPos);
				if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
					EndGatewayBlockEntity.tryTeleportingEntity(this.world, blockPos, blockState, this, (EndGatewayBlockEntity)blockEntity);
				}

				bl = true;
			}
		}

		if (hitResult.getType() != HitResult.Type.MISS && !bl) {
			this.onCollision(hitResult);
		}

        if (!this.world.isClient && this.isInsideWaterOrBubbleColumn()) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

        if (!this.world.isClient && this.age >= 60) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

		for (int j = 0; j < 1; ++j) {
			double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			double dd = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double ee = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);;
			double ff = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);;
			this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), dd, ee, ff);
			this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), d, e, f);
			this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), d, e * -1, f);
		}
		if (this.hasBeamTarget()) {
			if (this.beamTicks < this.getWarmupTime()) {
				++this.beamTicks;
			}

			LivingEntity livingEntity = this.getBeamTarget();
			LivingEntity anchorEntity = this.getElectricBeamTarget();

			LivingEntity livingEntity2 = this.getBeamTarget2();
			LivingEntity anchorEntity2 = this.getElectricBeamTarget2();

			LivingEntity livingEntity3 = this.getBeamTarget3();
			LivingEntity anchorEntity3 = this.getElectricBeamTarget3();

			LivingEntity livingEntity4 = this.getBeamTarget4();
			LivingEntity anchorEntity4 = this.getElectricBeamTarget4();
			if (livingEntity != null && anchorEntity != null) {
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity.getX() - anchorEntity.getX();
				double f = livingEntity.getBodyY(0.5D) - anchorEntity.getEyeY();
				double g = livingEntity.getZ() - anchorEntity.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += d + this.random.nextDouble() * d;
					double random = this.random.nextDouble();
					if (random <= 0.5) {
						this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, anchorEntity.getX() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + e * j,
								anchorEntity.getEyeY() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + f * j,
								anchorEntity.getZ() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + g * j, 0, 0, 0);
					}
				}
			}
			if (livingEntity2 != null && anchorEntity2 != null) {
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity2.getX() - anchorEntity2.getX();
				double f = livingEntity2.getBodyY(0.5D) - anchorEntity2.getEyeY();
				double g = livingEntity2.getZ() - anchorEntity2.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += d + this.random.nextDouble() * d;
					double random = this.random.nextDouble();
					if (random <= 0.5) {
						this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, anchorEntity2.getX() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + e * j,
								anchorEntity2.getEyeY() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + f * j,
								anchorEntity2.getZ() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + g * j, 0, 0, 0);
					}
				}
			}
			if (livingEntity3 != null && anchorEntity3 != null) {
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity3.getX() - anchorEntity3.getX();
				double f = livingEntity3.getBodyY(0.5D) - anchorEntity3.getEyeY();
				double g = livingEntity3.getZ() - anchorEntity3.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += d + this.random.nextDouble() * d;
					double random = this.random.nextDouble();
					if (random <= 0.5) {
						this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, anchorEntity3.getX() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + e * j,
								anchorEntity3.getEyeY() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + f * j,
								anchorEntity3.getZ() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + g * j, 0, 0, 0);
					}
				}
			}
			if (livingEntity4 != null && anchorEntity4 != null) {
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity4.getX() - anchorEntity4.getX();
				double f = livingEntity4.getBodyY(0.5D) - anchorEntity4.getEyeY();
				double g = livingEntity4.getZ() - anchorEntity4.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += d + this.random.nextDouble() * d;
					double random = this.random.nextDouble();
					if (random <= 0.5) {
						this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, anchorEntity4.getX() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + e * j,
								anchorEntity4.getEyeY() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + f * j,
								anchorEntity4.getZ() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + g * j, 0, 0, 0);
					}
				}
			}
		}
		if (!this.world.isClient && checkTorchwood(this.getPos()) != null) {
			if (checkTorchwood(this.getPos()) != torchwoodMemory && !checkTorchwood(this.getPos()).isWet()) {
				ShootingPlasmaPeaEntity plasmaPeaEntity = (ShootingPlasmaPeaEntity) PvZEntity.PLASMAPEA.create(world);
				plasmaPeaEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				plasmaPeaEntity.setVelocity(this.getVelocity());
				plasmaPeaEntity.age = this.age;
				plasmaPeaEntity.setOwner(this.getOwner());
				world.spawnEntity(plasmaPeaEntity);
				this.remove(RemovalReason.DISCARDED);
			}
		}
    }

	public TorchwoodEntity checkTorchwood(Vec3d pos) {
		List<TorchwoodEntity> list = world.getNonSpectatingEntities(TorchwoodEntity.class, PvZEntity.PEA.getDimensions().getBoxAt(pos));
		if (!list.isEmpty()){
			return list.get(0);
		}
		else {
			return null;
		}
	}

	protected int lightningCounter;

	public void lightning(LivingEntity origin){
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, origin.getBoundingBox().expand(10.0));
		Iterator var9 = list.iterator();
		while (true) {
			LivingEntity livingEntity;
			do {
				do {
					do {
						do {
							if (!var9.hasNext()) {
								return;
							}

							livingEntity = (LivingEntity) var9.next();
						} while (livingEntity == origin);
					} while (livingEntity.hasPassenger(origin));
				} while (livingEntity.getVehicle() == origin);
			} while (origin.squaredDistanceTo(livingEntity) > 25);

			if (lightningCounter > 0 && livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno())) && !(livingEntity instanceof GraveEntity graveEntity && graveEntity.decorative)) {
				float damage = PVZCONFIG.nestedProjDMG.electricPeaDMG();
				ZombiePropEntity passenger = null;
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe) {
						passenger = zpe;
					}
				}
				LivingEntity damaged = livingEntity;
				if (passenger != null) {
					damaged = passenger;
				}
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(damaged.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic" -> PvZSounds.BUCKETHITEVENT;
					case "plastic" -> PvZSounds.CONEHITEVENT;
					case "stone" -> PvZSounds.STONEHITEVENT;
					default -> PvZSounds.PEAHITEVENT;
				};
				damaged.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
				if (livingEntity.isWet() || livingEntity.hasStatusEffect(PvZCubed.WET)){
					damaged.damage(PvZCubed.LIGHTNING_DAMAGE, damage * 2);
				}
				else {
					damaged.damage(PvZCubed.LIGHTNING_DAMAGE, damage);
				}
				damaged.damage(DamageSource.thrownProjectile(this, this), 0);
				setSparkTarget(damaged.getId());
				this.world.sendEntityStatus(this, (byte) 121);
				if (zombieMaterial.equals("plastic") || zombieMaterial.equals("plant")){
					this.lightningCounter -= 2;
				}
				else if (!zombieMaterial.equals("metallic")){
					--this.lightningCounter;
				}
				if (getBeamTarget2() == null && getElectricBeamTarget2() == null){
					this.setHypnoBeamTarget2(damaged.getId());
					this.setElectricBeamTargetId2(origin.getId());
				}
				else if (getBeamTarget3() == null && getElectricBeamTarget3() == null && getElectricBeamTarget2() != null){
					this.setHypnoBeamTarget3(damaged.getId());
					this.setElectricBeamTargetId3(this.getElectricBeamTarget2().getId());
				}
				else if (getBeamTarget4() == null && getElectricBeamTarget4() == null && getElectricBeamTarget3() != null){
					this.setHypnoBeamTarget4(this.getElectricBeamTarget3().getId());
					this.setElectricBeamTargetId4(damaged.getId());
				}
			}
		}
	}

    @Override
    protected Item getDefaultItem() {
        return null;
    }
	public List<Entity> entityStore = new ArrayList<>();


	@Override
	public void hitEntities() {
		super.hitEntities();
		Iterator var9 = hitEntities.iterator();
		while (true) {
			Entity entity;
			do {
				if (!var9.hasNext()) {
					return;
				}

				entity = (Entity) var9.next();
			} while (entity == this.getOwner());

			ZombiePropEntity zombiePropEntity = null;
			for (Entity entity1 : entity.getPassengerList()) {
				if (entity1 instanceof ZombiePropEntity zpe) {
					zombiePropEntity = zpe;
				}
			}
			Entity et = null;
			for (Entity entityHitList : entityStore) {
				if (entityHitList == entity) {
					et = entity;
					break;
				}
			}
			if (!world.isClient && entity instanceof Monster monster &&
					!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
					!(zombiePropEntity != null && !(zombiePropEntity instanceof ZombieShieldEntity)) &&
					!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth())) {
				float damage = PVZCONFIG.nestedProjDMG.electricPeaDMG();
				if (et == null) {
					entity.playSound(PvZSounds.LIGHTNINGSHOOTEVENT, 0.2F, (float) (0.5F + Math.random()));
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
					this.setElectricBeamTargetId(this.getId());
					this.setHypnoBeamTarget(entity.getId());
					if (damage > ((LivingEntity) entity).getHealth() &&
							!(entity instanceof ZombieShieldEntity) &&
							entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - ((LivingEntity) entity).getHealth();
						if (entity.isWet() || ((LivingEntity) entity).hasStatusEffect(PvZCubed.WET)){
							entity.damage(PvZCubed.LIGHTNING_DAMAGE, damage2 * 2);
						}
						else {
							entity.damage(PvZCubed.LIGHTNING_DAMAGE, damage2);
						}
						entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 0);
						this.lightningCounter = 3;
						if (zombieMaterial.equals("plastic") || zombieMaterial.equals("plant")){
							this.lightningCounter -= 2;
						}
						this.lightning((LivingEntity) entity);
						this.lightningCounter = 3;
					} else {
						if (entity.isWet() || ((LivingEntity) entity).hasStatusEffect(PvZCubed.WET)){
							entity.damage(PvZCubed.LIGHTNING_DAMAGE, damage * 2);
						}
						else {
							entity.damage(PvZCubed.LIGHTNING_DAMAGE, damage);
						}
						entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 0);
						this.lightningCounter = 3;
						if (zombieMaterial.equals("plastic") || zombieMaterial.equals("plant")){
							this.lightningCounter -= 2;
						}
						this.lightning((LivingEntity) entity);
						this.lightningCounter = 3;
					}
					entityStore.add((LivingEntity) entity);
				}
				entityStore.add((LivingEntity) entity);
			}
		}
	}

        @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ELECTRIC_SPARK : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }


    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 121) {
			if (this.getSparkTarget() != null) {
				LivingEntity livingEntity = this.getSparkTarget();
				for (int i = 0; i < 64; ++i) {
					double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
					double e = this.random.nextDouble() / 2 * this.random.range(0, 1);
					double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
					this.world.addParticle(ParticleTypes.ELECTRIC_SPARK, livingEntity.getX() + (this.random.range(-1, 1)), livingEntity.getY() + 0.5 + (this.random.range(-1, 1)), livingEntity.getZ() + (this.random.range(-1, 1)), d, e, f);
				}
			}
		}
    }
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte)3);
            this.remove(RemovalReason.DISCARDED);
        }
    }



	public int getWarmupTime() {
		return 20;
	}

	private void setHypnoBeamTarget(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID, entityId);
	}

	public boolean hasBeamTarget() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID) != 0;
	}

	private void setElectricBeamTargetId(int entityId) {
		this.dataTracker.set(ELECTRIC_BEAM_TARGET_ID, entityId);
	}

	public boolean hasElectricBeamTarget() {
		return (Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID) != 0;
	}

	private void setHypnoBeamTarget2(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID2, entityId);
	}

	public boolean hasBeamTarget2() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID2) != 0;
	}

	private void setElectricBeamTargetId2(int entityId) {
		this.dataTracker.set(ELECTRIC_BEAM_TARGET_ID2, entityId);
	}

	public boolean hasElectricBeamTarget2() {
		return (Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID2) != 0;
	}

	private void setHypnoBeamTarget3(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID3, entityId);
	}

	public boolean hasBeamTarget3() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID3) != 0;
	}

	private void setElectricBeamTargetId3(int entityId) {
		this.dataTracker.set(ELECTRIC_BEAM_TARGET_ID3, entityId);
	}

	public boolean hasElectricBeamTarget3() {
		return (Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID3) != 0;
	}

	private void setHypnoBeamTarget4(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID4, entityId);
	}

	public boolean hasBeamTarget4() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID4) != 0;
	}

	private void setElectricBeamTargetId4(int entityId) {
		this.dataTracker.set(ELECTRIC_BEAM_TARGET_ID4, entityId);
	}

	public boolean hasElectricBeamTarget4() {
		return (Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID4) != 0;
	}

	private void setSparkTarget(int entityId) {
		this.dataTracker.set(SPARK_TARGET, entityId);
	}

	public boolean hasSparkTarget() {
		return (Integer)this.dataTracker.get(SPARK_TARGET) != 0;
	}

	@Nullable
	public LivingEntity getBeamTarget() {
		if (!this.hasBeamTarget()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget != null) {
				return this.cachedBeamTarget;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget = (LivingEntity)entity;
					return this.cachedBeamTarget;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getElectricBeamTarget() {
		if (!this.hasElectricBeamTarget()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget2 != null) {
				return this.cachedBeamTarget2;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget2 = (LivingEntity)entity;
					return this.cachedBeamTarget2;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getBeamTarget2() {
		if (!this.hasBeamTarget2()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget3 != null) {
				return this.cachedBeamTarget3;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID2));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget3 = (LivingEntity)entity;
					return this.cachedBeamTarget3;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getElectricBeamTarget2() {
		if (!this.hasElectricBeamTarget2()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget4 != null) {
				return this.cachedBeamTarget4;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID2));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget4 = (LivingEntity)entity;
					return this.cachedBeamTarget4;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getBeamTarget3() {
		if (!this.hasBeamTarget3()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget5 != null) {
				return this.cachedBeamTarget5;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID3));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget5 = (LivingEntity)entity;
					return this.cachedBeamTarget5;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getElectricBeamTarget3() {
		if (!this.hasElectricBeamTarget3()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget6 != null) {
				return this.cachedBeamTarget6;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID3));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget6 = (LivingEntity)entity;
					return this.cachedBeamTarget6;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getBeamTarget4() {
		if (!this.hasBeamTarget4()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget7 != null) {
				return this.cachedBeamTarget7;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID4));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget7 = (LivingEntity)entity;
					return this.cachedBeamTarget7;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getElectricBeamTarget4() {
		if (!this.hasElectricBeamTarget4()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget8 != null) {
				return this.cachedBeamTarget8;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID4));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget8 = (LivingEntity)entity;
					return this.cachedBeamTarget8;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getSparkTarget() {
		if (!this.hasSparkTarget()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedSparkTarget != null) {
				return this.cachedSparkTarget;
			} else {
				Entity entity = this.world.getEntityById((Integer)this.dataTracker.get(SPARK_TARGET));
				if (entity instanceof LivingEntity) {
					this.cachedSparkTarget = (LivingEntity)entity;
					return this.cachedSparkTarget;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	public float getBeamProgress(float tickDelta) {
		return ((float)this.beamTicks + tickDelta) / (float)this.getWarmupTime();
	}


    public boolean collides() {
        return false;
    }
}
