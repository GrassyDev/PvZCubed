package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowqueenpea;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.torchwood.TorchwoodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pea.ShootingPeaEntity;
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

public class ShootingSnowqueenPeaEntity extends PvZProjectileEntity implements IAnimatable {

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

    public ShootingSnowqueenPeaEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public ShootingSnowqueenPeaEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public ShootingSnowqueenPeaEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.SNOWPEAPROJ, world);
		updatePosition(x, y, z);
		updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
		setUuid(uuid);
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
			this.world.sendEntityStatus(this, (byte) 3);
			this.remove(RemovalReason.DISCARDED);
		}

		if (!this.world.isClient && this.age >= 60) {
			this.world.sendEntityStatus(this, (byte) 3);
			this.remove(RemovalReason.DISCARDED);
		}

		for (int j = 0; j < 2; ++j) {
			double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), d, e, f);
		}

		if (checkTorchwood(this.getPos()) != null) {
			if (!checkTorchwood(this.getPos()).isWet()) {
				ShootingPeaEntity shootingPeaEntity = (ShootingPeaEntity) PvZEntity.PEA.create(world);
				shootingPeaEntity.torchwoodMemory = checkTorchwood(this.getPos());
				shootingPeaEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				shootingPeaEntity.setVelocity(this.getVelocity());
				shootingPeaEntity.setOwner(this.getOwner());
				world.spawnEntity(shootingPeaEntity);
				shootingPeaEntity.age = this.age;
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

	@Override
	protected Item getDefaultItem() {
		return null;
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
					!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying())
					&& !(entity instanceof ZombieShieldEntity && !entity.hasVehicle()) && !hit) {
				if (!((LivingEntity) entity).hasStatusEffect(PvZCubed.WARM) && !entity.isOnFire() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.FROZEN)) {
					if (!(entity instanceof ZombieShieldEntity)) {
						((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 120, 1)));
					}
				}
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
				float damage = PVZCONFIG.nestedProjDMG.snowQueenPeaDMGv2();
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
				if (!(entity instanceof ZombieShieldEntity)) {
					if (!((LivingEntity) entity).hasStatusEffect(PvZCubed.WARM) && !((LivingEntity) entity).hasStatusEffect(PvZCubed.FROZEN)) {
						((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 120, 1)));
					}
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
								if (livingEntity != entity) {
									float damage3 = PVZCONFIG.nestedProjDMG.snowQueenPeaSDMG();
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
											if (!livingEntity.hasStatusEffect(PvZCubed.WARM) && !((LivingEntity) entity).hasStatusEffect(PvZCubed.FROZEN)) {
												if (!(livingEntity instanceof ZombieShieldEntity)) {
													livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 120, 1)));
												}
											}
										}
									}
								}
								this.world.sendEntityStatus(this, (byte) 3);
								this.remove(RemovalReason.DISCARDED);
							}
						}
					}
				} else if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombieShieldEntity)) {
					if (!generalPvZombieEntity.hasStatusEffect(PvZCubed.WARM) && !((LivingEntity) entity).hasStatusEffect(PvZCubed.FROZEN)) {
						generalPvZombieEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 120, 1)));
					}
					this.world.sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
				} else {
					this.world.sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
				}
			}
		}
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }


    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 16; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }

			for (int j = 0; j < 32; ++j) {
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
