package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.piercingpea;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class FirePiercePeaEntity extends PvZProjectileEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public int maxAge = 60;

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
		event.getController().setAnimation(new AnimationBuilder().loop("spit.idle"));
		return PlayState.CONTINUE;
	}

    public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "firepiercepea");

    public FirePiercePeaEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public FirePiercePeaEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
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
			this.remove(RemovalReason.DISCARDED);
		}

		if (!this.world.isClient && this.age >= maxAge || this.damageCounter >= 3) {
			this.remove(RemovalReason.DISCARDED);
		}
		double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
		double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
		double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;

		for (int j = 0; j < 1; ++j) {
			this.world.addParticle(ParticleTypes.SMALL_FLAME, this.getX(), this.getY(), this.getZ(), d, e, f);
			this.world.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), d, e * -1, f);
		}

		if (this.isWet()){
			PiercePeaEntity shootingPeaEntity = (PiercePeaEntity) PvZEntity.PIERCEPEA.create(world);
			shootingPeaEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
			shootingPeaEntity.setVelocity(this.getVelocity());
			shootingPeaEntity.setOwner(this.getOwner());
			world.spawnEntity(shootingPeaEntity);
			shootingPeaEntity.age = this.age;
			this.remove(RemovalReason.DISCARDED);
		}
	}

    @Override
    protected Item getDefaultItem() {
        return null;
    }

	public List<Entity> entityStore = new ArrayList<>();

	protected int damageCounter = 0;

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
					!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth()) &&
					!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying())) {
				float damage = PVZCONFIG.nestedProjDMG.firepiercepeaDMG();
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic" -> PvZSounds.BUCKETHITEVENT;
					case "plastic" -> PvZSounds.CONEHITEVENT;
					case "stone" -> PvZSounds.STONEHITEVENT;
					default -> PvZSounds.PEAHITEVENT;
				};
				if (et == null) {
					++this.damageCounter;
					if ((entity instanceof ZombieShieldEntity || entity.isWet() || ((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) ||
							(entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) && !"paper".equals(zombieMaterial) || "plant".equals(zombieMaterial)) {
						entity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
					}
					entity.playSound(PvZSounds.FIREPEAHITEVENT, 0.2F, 1F);
					if ("paper".equals(zombieMaterial) || "plant".equals(zombieMaterial)) {
						if (!entity.isWet() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET)) {
							((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
							entity.setOnFireFor(4);
							if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
								generalPvZombieEntity.fireSplashTicks = 10;
							}
						}
						damage = damage * 2;
					}
					if (damage > ((LivingEntity) entity).getHealth() &&
							!(entity instanceof ZombieShieldEntity) &&
							entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - ((LivingEntity) entity).getHealth();
						entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
						generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
					} else {
						entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
					}
					if (!entity.isWet() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn()) && !(entity instanceof ZombieShieldEntity)) {
						entityStore.add((LivingEntity) entity);
						((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
						entity.setOnFireFor(4);
						((LivingEntity) entity).removeStatusEffect(PvZCubed.FROZEN);
						((LivingEntity) entity).removeStatusEffect(PvZCubed.ICE);
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
										ZombiePropEntity zombiePropEntity3 = null;
										for (Entity entity1 : livingEntity.getPassengerList()) {
											if (entity1 instanceof ZombiePropEntity zpe) {
												zombiePropEntity3 = zpe;
											}
										}
										if (!(zombiePropEntity3 instanceof ZombieShieldEntity)) {
											float damageSplash = PVZCONFIG.nestedProjDMG.firepiercepeaSDMG();
											String zombieMaterial2 = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
											if ("paper".equals(zombieMaterial2)) {
												damageSplash = damageSplash * 2;
											} else if ("plant".equals(zombieMaterial2)) {
												damageSplash = damageSplash * 2;
											}
											if (zombiePropEntity3 == null) {
												if (damageSplash > livingEntity.getHealth() &&
														!(livingEntity instanceof ZombieShieldEntity) &&
														livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
													float damageSplash2 = damageSplash - livingEntity.getHealth();
													livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damageSplash);
													generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damageSplash2);
												} else {
													livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damageSplash);
												}
												if (!livingEntity.hasStatusEffect(PvZCubed.WET) && !livingEntity.isWet() && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn()) && !(livingEntity instanceof ZombieShieldEntity)) {
													livingEntity.setOnFireFor(4);
													if (!(livingEntity instanceof ZombieShieldEntity)) {
														livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 40, 1)));
													}
													livingEntity.removeStatusEffect(PvZCubed.FROZEN);
													livingEntity.removeStatusEffect(PvZCubed.ICE);
												} else if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn() && !(generalPvZombieEntity instanceof ZombieShieldEntity) && !livingEntity.hasStatusEffect(PvZCubed.WET) && !livingEntity.isWet()) {
													livingEntity.removeStatusEffect(PvZCubed.FROZEN);
													livingEntity.removeStatusEffect(PvZCubed.ICE);
													livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
												}
												if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
													generalPvZombieEntity.fireSplashTicks = 10;
												}
												if (livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity) {
													generalPvZombieEntity.fireSplashTicks = 10;
												}
												if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
													generalPvZombieEntity.fireSplashTicks = 10;
												}
											}
										}
									}
								}
							}
						}
					} else if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn() && !(generalPvZombieEntity instanceof ZombieShieldEntity) && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) && !entity.isWet()) {
						((LivingEntity) entity).removeStatusEffect(PvZCubed.FROZEN);
						((LivingEntity) entity).removeStatusEffect(PvZCubed.ICE);
						((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
					}
				}
				entityStore.add((LivingEntity) entity);
			}
		}
	}

    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.world.isClient) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    public boolean collides() {
        return false;
    }
}
