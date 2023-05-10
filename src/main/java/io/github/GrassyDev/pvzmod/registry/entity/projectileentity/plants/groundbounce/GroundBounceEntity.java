package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.groundbounce;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
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
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
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

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class GroundBounceEntity extends ThrownItemEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);


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
		event.getController().setAnimation(new AnimationBuilder().loop("peashot.idle"));
		return PlayState.CONTINUE;
	}

    public GroundBounceEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.noClip = false;
    }

    public GroundBounceEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public GroundBounceEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.GROUNDBOUNCE, world);
        updatePosition(x, y, z);
        updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
        setUuid(uuid);
    }

    public void tick() {
        super.tick();
		if (this.isInsideWaterOrBubbleColumn()){
			this.remove(RemovalReason.DISCARDED);
		}
		RandomGenerator randomGenerator = this.world.getRandom();
		int l = MathHelper.floor(this.getPos().x);
		int m = MathHelper.floor(this.getPos().y - (double)0.25);
		int n = MathHelper.floor(this.getPos().z);
		BlockPos blockPos2 = new BlockPos(l, m, n);
		if (!this.world.getBlockState(blockPos2).isAir() && !this.world.getBlockState(blockPos2).getMaterial().isLiquid()) {
			this.setVelocity(this.getVelocity().getX(), 0, this.getVelocity().getZ());
		}
		for(int i = 0; i < 4; ++i) {
			double d = this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
			double e = this.getY() + (double) MathHelper.nextBetween(randomGenerator, 0F, 0.5F);
			double f = this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
			this.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, this.world.getBlockState(blockPos2)), d, e, f, 0.0, 2, 0.0);
		}

        if (!this.world.isClient && this.isInsideWaterOrBubbleColumn()) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

        if (!this.world.isClient && this.age >= 40) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
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
				!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
				!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
				(!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()))) {
			splashDamage();
			bounceZombies(entity.getPos());
			this.remove(RemovalReason.DISCARDED);
		}
	}

	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (!this.world.isClient) {
			this.remove(RemovalReason.DISCARDED);
		}
	}

	protected void splashDamage() {
		List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(2));
		Iterator var9 = list.iterator();
		while (true) {
			LivingEntity livingEntity;
			do {
				if (!var9.hasNext()) {
					return;
				}
				livingEntity = (LivingEntity) var9.next();
			} while (this.squaredDistanceTo(livingEntity) > 3.0625);

			if (livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) {
				ZombiePropEntity zombiePropEntity2 = null;
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe) {
						zombiePropEntity2 = zpe;
					}
				}
				if (livingEntity.getY() < (this.getY() + 1.5) && livingEntity.getY() > (this.getY() - 1.5)) {
					if (!world.isClient &&
							!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
							!(livingEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
							!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying())) {
						String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
						SoundEvent sound;
						sound = switch (zombieMaterial) {
							case "metallic" -> PvZSounds.BUCKETHITEVENT;
							case "plastic" -> PvZSounds.CONEHITEVENT;
							case "stone" -> PvZSounds.STONEHITEVENT;
							default -> PvZSounds.PEAHITEVENT;
						};
						livingEntity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
						float damage = 10F;
						if (damage > livingEntity.getHealth() &&
								!(livingEntity instanceof ZombieShieldEntity) &&
								livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
							float damage2 = damage - ((LivingEntity) livingEntity).getHealth();
							livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
							generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this), damage2);
						} else {
							livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						}
					}
				}
			}
		}
	}

	protected void bounceZombies(Vec3d pos) {
		List<HostileEntity> list = this.world.getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(3));
		Iterator var9 = list.iterator();
		while (true) {
			HostileEntity hostileEntity;
			do {
				if (!var9.hasNext()) {
					return;
				}
				hostileEntity = (HostileEntity) var9.next();
			} while (pos.squaredDistanceTo(hostileEntity.getPos()) > 3.0625);

			if (!(hostileEntity instanceof ZombiePropEntity) && !(hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
				if (hostileEntity.getY() < (this.getY() + 2) && hostileEntity.getY() > (this.getY() - 2) &&
						!(hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying())) {
					Vec3d vec3d = new Vec3d((double) -1, +0.5, 0).rotateY(-hostileEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					hostileEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
					hostileEntity.setVelocity(Vec3d.ZERO);
					hostileEntity.addVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());
				}
			}
		}
	}

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SLIME : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }


    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
    }

    public boolean collides() {
        return false;
    }
}
