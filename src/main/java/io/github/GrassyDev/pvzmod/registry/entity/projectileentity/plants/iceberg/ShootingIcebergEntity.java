package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.iceberg;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
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
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
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
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ShootingIcebergEntity extends ThrownItemEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private LivingEntity target;

	@Override
	public void registerControllers(AnimationData animationData) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		animationData.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().loop("cabbage.idle"));
		return PlayState.CONTINUE;
	}

    public ShootingIcebergEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(false);
    }

    public ShootingIcebergEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public ShootingIcebergEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.PEPPERPROJ, world);
        updatePosition(x, y, z);
        updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
        setUuid(uuid);
    }

	public LivingEntity getTarget (LivingEntity target){
		return this.target = target;
	}

    public void tick() {
        super.tick();
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
			this.world.sendEntityStatus(this, (byte)3);
            this.remove(RemovalReason.DISCARDED);
        }

        if (!this.world.isClient && this.age >= 120) {
			this.world.sendEntityStatus(this, (byte)3);
            this.remove(RemovalReason.DISCARDED);
        }
		if (!this.world.isClient && this.age > 50 && target != null) {
			if (target.getHealth() > 0) {
				this.setVelocity(0,this.getVelocity().getY(), 0);
				this.setPosition(target.getPos().getX(), this.getY() - 0.0005, target.getZ());
			}
		}
		if (target != null){
			if ((target.getHealth() > 0 && (this.getPos().getX() <= target.getPos().getX() + 0.2 && this.getPos().getX() >= target.getPos().getX() - 0.2) &&
					this.getPos().getZ() <= target.getPos().getZ() + 0.2 && this.getPos().getZ() >= target.getPos().getZ() - 0.2)){
				this.setVelocity(0,this.getVelocity().getY(), 0);
				this.setPosition(target.getPos().getX(), this.getY() - 0.0005, target.getZ());
			}
		}

		double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
		double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
		double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;

		for (int j = 0; j < 1; ++j) {
			this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), d, e, f);
			this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), d, e * -1, f);
		}
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		ZombiePropEntity zombiePropEntity2 = null;
		for (Entity entity1 : entity.getPassengerList()) {
			if (entity1 instanceof ZombiePropEntity zpe) {
				zombiePropEntity2 = zpe;
			}
		}
		if (!world.isClient && entity instanceof Monster monster &&
				!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
				!(zombiePropEntity2 instanceof ZombiePropEntity && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
				!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())) {
			String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
			SoundEvent sound;
			sound = switch (zombieMaterial) {
				case "metallic" -> PvZSounds.BUCKETHITEVENT;
				case "plastic" -> PvZSounds.CONEHITEVENT;
				case "stone" -> PvZSounds.STONEHITEVENT;
				default -> PvZSounds.PEAHITEVENT;
			};
			if (entity instanceof ZombieShieldEntity || (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isCovered())) {
				entity.playSound(sound, 0.2F, 1F);
			}
			entity.playSound(PvZSounds.SNOWPEAHITEVENT, 0.2F, 1F);
			float damage = PVZCONFIG.nestedProjDMG.icebergDMG();
			if (damage > ((LivingEntity) entity).getHealth() &&
					!(entity instanceof ZombieShieldEntity) &&
					entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
				float damage2 = damage - ((LivingEntity) entity).getHealth();
				entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
				generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
			} else {
				entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
			}
			Vec3d vec3d = this.getPos();
			List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5.0));
			Iterator var9 = list.iterator();
			while (true) {
				LivingEntity livingEntity;
				do {
					do {
						if (!var9.hasNext()) {
							return;
						}

						livingEntity = (LivingEntity) var9.next();
					} while (livingEntity == this.getOwner());
				} while (entity.squaredDistanceTo(livingEntity) > 2.25);

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
					if (livingEntity instanceof Monster &&
							!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
									&& (generalPvZombieEntity.getHypno()))) {
						ZombiePropEntity zombiePropEntity3 = null;
						for (Entity entity1 : livingEntity.getPassengerList()) {
							if (entity1 instanceof ZombiePropEntity zpe) {
								zombiePropEntity3 = zpe;
							}
						}
						if (!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity))) {
							if (damage > livingEntity.getHealth() &&
									!(livingEntity instanceof ZombieShieldEntity ) &&
									livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())){
								float damage2 = damage - livingEntity.getHealth();
								livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
								generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
							}
							else {
								livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
							}
							if (!livingEntity.hasStatusEffect(PvZCubed.WARM) && !((LivingEntity) entity).hasStatusEffect(PvZCubed.FROZEN)) {
								if (!(livingEntity instanceof ZombieShieldEntity )) {
									livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 120, 1)));
								}
							}
							this.world.sendEntityStatus(this, (byte) 3);
							this.remove(RemovalReason.DISCARDED);
						}
					}
				}
			}
		}
	}

	@Environment(EnvType.CLIENT)
	private ParticleEffect getParticleParameters() {
		ItemStack itemStack = this.getItem();
		return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.SNOWFLAKE : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
	}



	@Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 3) {
			ParticleEffect particleEffect = this.getParticleParameters();

			for(int i = 0; i < 6; ++i) {
				double vx = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double vy = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double vz = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), vx, vy, vz);
			}

			for (int j = 0; j < 8; ++j) {

				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), d, e, f);
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

    public boolean collides() {
        return false;
    }
}
