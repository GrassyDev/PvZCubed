package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.coconut;

import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieRiderEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieRiderEntity;
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
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class CoconutEntity extends PvZProjectileEntity implements IAnimatable {

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

	private <P extends IAnimatable > PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().loop("peashot.idle"));
		return PlayState.CONTINUE;
	}

    public CoconutEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
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

        if (!this.world.isClient && this.age >= 60) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }
		double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
		double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
		double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;

		for (int j = 0; j < 3; ++j) {
			this.world.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), d, e, f);
			this.world.addParticle(ParticleTypes.SMALL_FLAME, this.getX(), this.getY(), this.getZ(), d, e * -1, f);
		}
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }


	@Override
	public void hitEntities() {
		super.hitEntities();
		boolean hit = false;
		Iterator var9 = hitEntities.iterator();
		while (true) {
			Entity entity;
			do {
				if (!var9.hasNext()) {
					return;
				}

				entity = (Entity) var9.next();
			} while (entity == this.getOwner());
			ZombiePropEntity zombiePropEntity2 = null;
			for (Entity entity1 : entity.getPassengerList()) {
				if (entity1 instanceof ZombiePropEntity zpe) {
					zombiePropEntity2 = zpe;
				}
			}
			if (!world.isClient && entity instanceof Monster monster &&
					!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
					!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
					!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth()) &&
					!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()) && !hit) {
				entity.playSound(PvZSounds.POTATOMINEEXPLOSIONEVENT, 0.8F, 1F);
				float damage = PVZCONFIG.nestedProjDMG.coconutDMGv2();
				if (damage > ((LivingEntity) entity).getHealth() &&
						!(entity instanceof ZombieShieldEntity) &&
						entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					float damage2 = damage - ((LivingEntity) entity).getHealth();
					entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
					generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
				} else {
					entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
				}
				hit = true;
				this.world.sendEntityStatus(this, (byte) 3);
				this.remove(RemovalReason.DISCARDED);
				Vec3d vec3d = this.getPos();
				List<LivingEntity> list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5.0));
				Iterator var10 = list.iterator();
				while (true) {
					LivingEntity livingEntity;
					do {
						do {
							if (!var10.hasNext()) {
								return;
							}

							livingEntity = (LivingEntity) var10.next();
						} while (livingEntity == this.getOwner());
					} while (this.squaredDistanceTo(livingEntity) > 6);

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
								!(livingEntity instanceof ZombieShieldEntity) &&
								!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
										&& (generalPvZombieEntity.getHypno()))) {
							if (livingEntity != entity) {
								float damage3 = PVZCONFIG.nestedProjDMG.coconutSDMG();
								ZombiePropEntity zombiePropEntity3 = null;
								for (Entity entity1 : livingEntity.getPassengerList()) {
									if (entity1 instanceof ZombiePropEntity zpe) {
										zombiePropEntity3 = zpe;
									}
								}
								if (!(zombiePropEntity3 instanceof ZombieShieldEntity)) {
									if (zombiePropEntity3 == null) {
										if (damage3 > livingEntity.getHealth() &&
												!(livingEntity instanceof ZombieShieldEntity) &&
												livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
											float damage2 = damage3 - livingEntity.getHealth();
											livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage3);
											generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
										} else {
											livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage3);
										}
									}
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
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.SMOKE : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
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

			for (int j = 0; j < 256; ++j) {

				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.world.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), d, e, f);
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
