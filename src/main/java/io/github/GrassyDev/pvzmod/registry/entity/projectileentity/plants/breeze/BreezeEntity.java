package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.breeze;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class BreezeEntity extends ThrownItemEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "breeze");


	@Override
	public void registerControllers(AnimationData animationData) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		animationData.addAnimationController(controller);
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().loop("fume.idle"));
		return PlayState.CONTINUE;
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

    public BreezeEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public BreezeEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public BreezeEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.BREEZE, world);
		updatePosition(x, y, z);
		updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
		setUuid(uuid);
	}

    public void tick() {
		super.tick();
		if (!this.world.isClient && this.isInsideWaterOrBubbleColumn()) {
			this.world.sendEntityStatus(this, (byte) 3);
			this.remove(RemovalReason.DISCARDED);
		}

		if (!this.world.isClient && this.age >= 7) {
			this.world.sendEntityStatus(this, (byte) 3);
			this.remove(RemovalReason.DISCARDED);
		}

		for (int j = 0; j < 8; ++j) {
			RandomGenerator randomGenerator = this.random;
			double d = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double e = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double f = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double g = (double) MathHelper.nextBetween(randomGenerator, 0.25F, 0.66F);
			this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY() + g, this.getZ(), d, e, f);
		}
	}

    @Override
    protected Item getDefaultItem() {
        return null;
    }
	public LivingEntity entityStore = null;
	public LivingEntity entityStoreVehicle = null;

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
		ZombiePropEntity zombiePropEntity = null;
		for (Entity entity1 : entity.getPassengerList()) {
			if (entity1 instanceof ZombiePropEntity zpe) {
				zombiePropEntity = zpe;
			}
		}
        if (!world.isClient && entity instanceof Monster monster &&
				!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
				!(zombiePropEntity != null && !(zombiePropEntity instanceof ZombieShieldEntity)) &&
				  !(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())) {
			Entity entity2 = entityHitResult.getEntity();
			float damage = PVZCONFIG.nestedProjDMG.breezeDMG();
			String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
			if ("paper".equals(zombieMaterial) ) {
				damage = damage * 2;
			}
			else if ("stone".equals(zombieMaterial)) {
				damage = damage / 2;
			}
			SoundEvent sound;
			sound = switch (zombieMaterial) {
				case "metallic" -> PvZCubed.BUCKETHITEVENT;
				case "plastic" -> PvZCubed.CONEHITEVENT;
				case "stone" -> PvZCubed.STONEHITEVENT;
				default -> PvZCubed.PEAHITEVENT;
			};
			if (entity2 != entityStore) {
				entity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
				if (damage > ((LivingEntity) entity).getHealth() &&
						!(entity instanceof ZombieShieldEntity) &&
						entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					float damage2 = damage - ((LivingEntity) entity).getHealth();
					entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
					generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
				} else {
					entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
				}
				entityStore = (LivingEntity) entityHitResult.getEntity();
				entityStoreVehicle = (LivingEntity) entityStore.getVehicle();
			}
			if (entity2.getVehicle() != null && entityStoreVehicle != entity2.getVehicle()){
				String zombieMaterial2 = PvZCubed.ZOMBIE_MATERIAL.get(entity2.getVehicle().getType()).orElse("flesh");
				sound = switch (zombieMaterial2) {
					case "metallic" -> PvZCubed.BUCKETHITEVENT;
					case "plastic" -> PvZCubed.CONEHITEVENT;
					case "stone" -> PvZCubed.STONEHITEVENT;
					default -> PvZCubed.PEAHITEVENT;
				};
				entity2.getVehicle().playSound(sound, 0.2F, (float) (0.5F + Math.random()));
				entity2.getVehicle().damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
			}
			if (!((LivingEntity) entity).hasStatusEffect(PvZCubed.WARM) && !((LivingEntity) entity).isOnFire() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.FROZEN)){
				if (!(((LivingEntity) entity) instanceof ZombieShieldEntity)) {
					((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 60, 1)));
					((LivingEntity) entity).playSound(PvZCubed.ICEBERGEXPLOSIONEVENT, 0.1f, 1);
				}
			}
			if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()){
				Vec3d vec3d = new Vec3d((double) 0.0125, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				if (this.getOwner() != null){
					vec3d = new Vec3d((double) 0.0125, 0.0, 0.0).rotateY(-this.getOwner().getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				}
				entity.addVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());
			}
			entityStore = (LivingEntity) entityHitResult.getEntity();
			entityStoreVehicle = (LivingEntity) entityStore.getVehicle();
        }
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
        if (status == 3) {
			RandomGenerator randomGenerator = this.random;
			double d = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double e = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double f = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double g = (double) MathHelper.nextBetween(randomGenerator, 0.25F, 0.66F);
			this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY() + g, this.getZ(), d, e, f);
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
