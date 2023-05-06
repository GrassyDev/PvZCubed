package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.boomerang;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
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
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ShootingBoomerangEntity extends ThrownItemEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public int maxAge = 80;
	public int returnAge = 28;

	public LivingEntity entityStore = null;
	public LivingEntity entityStoreVehicle = null;

	public LivingEntity entityStore2 = null;
	public LivingEntity entityStoreVehicle2 = null;

	public LivingEntity entityStore3 = null;
	public LivingEntity entityStoreVehicle3 = null;

	private int returningTicks = 7;
	private boolean retuningStart;

	public float ownerYaw;
	public int damageCounter = 0;

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(RETURNING_TAG, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Returning", this.getReturning());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(RETURNING_TAG, tag.getBoolean("Returning"));
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	protected static final TrackedData<Boolean> RETURNING_TAG =
			DataTracker.registerData(ShootingBoomerangEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum Returning {
		FALSE(false),
		TRUE(true);

		Returning(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getReturning() {
		return this.dataTracker.get(RETURNING_TAG);
	}

	public void setReturning(ShootingBoomerangEntity.Returning returning) {
		this.dataTracker.set(RETURNING_TAG, returning.getId());
	}

	//

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
		event.getController().setAnimation(new AnimationBuilder().loop("boomerang.idle"));
		return PlayState.CONTINUE;
	}

    public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "boomerangproj");

    public ShootingBoomerangEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public ShootingBoomerangEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    public void tick() {
		if (age <= 1){
			if (this.getOwner() != null) {
				this.ownerYaw = this.getOwner().getHeadYaw();
			}
			this.playSound(PvZSounds.BOOMERANGAMBIENTEVENT, 0.025f, 1f);
		}
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
		if (!this.world.isClient && this.age >= returnAge || this.damageCounter >= 3) {
			this.retuningStart = true;
			if (--returningTicks <= 0) {
				this.setReturning(Returning.TRUE);
				Vec3d vec3d = new Vec3d((double) -0.04, 0.0, 0.0).rotateY(-this.ownerYaw * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				if (this.age <= 50) {
					this.addVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());
				}
			}
		}
		if (!this.world.isClient && this.age >= maxAge) {
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
		if (this.getReturning()){
			if (entity == this.getOwner()){
				this.remove(RemovalReason.DISCARDED);
			}
		}
		ZombiePropEntity zombiePropEntity = null;
		for (Entity entity1 : entity.getPassengerList()) {
			if (entity1 instanceof ZombiePropEntity zpe) {
				zombiePropEntity = zpe;
			}
		}
		if (!world.isClient && this.getReturning()){
			if (entity == entityStore || entity == entityStoreVehicle ||
					entity == entityStore2 || entity == entityStoreVehicle2 ||
					entity == entityStore3 || entity == entityStoreVehicle3) {
				float damage = PVZCONFIG.nestedProjDMG.boomerangDMG();
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic" -> PvZSounds.BUCKETHITEVENT;
					case "plastic" -> PvZSounds.CONEHITEVENT;
					case "stone" -> PvZSounds.STONEHITEVENT;
					default -> PvZSounds.PEAHITEVENT;
				};
				if (entity == entityStore || entity == entityStoreVehicle ||
						entity == entityStore2 || entity == entityStoreVehicle2 ||
						entity == entityStore3 || entity == entityStoreVehicle3) {
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
					if (entityStore == entity || entityStoreVehicle == entity){
						entityStore = null;
						entityStoreVehicle = null;
					}
					else if (entityStore2 == entity || entityStoreVehicle2 == entity){
						entityStore2 = null;
						entityStoreVehicle2 = null;
					}
					else if (entityStore3 == entity || entityStoreVehicle3 == entity) {
						entityStore3 = null;
						entityStoreVehicle3 = null;
					}
				}
				if (entity.getVehicle() != null && (entityStoreVehicle == entity.getVehicle() || entityStoreVehicle2 == entity.getVehicle() || entityStoreVehicle3 == entity.getVehicle())){
					String zombieMaterial2 = PvZCubed.ZOMBIE_MATERIAL.get(entity.getVehicle().getType()).orElse("flesh");
					sound = switch (zombieMaterial2) {
						case "metallic" -> PvZSounds.BUCKETHITEVENT;
						case "plastic" -> PvZSounds.CONEHITEVENT;
						case "stone" -> PvZSounds.STONEHITEVENT;
						default -> PvZSounds.PEAHITEVENT;
					};
					entity.getVehicle().playSound(sound, 0.2F, (float) (0.5F + Math.random()));
					entity.getVehicle().damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
				}
				if (entityStore == entity){
					entityStore = null;
					entityStoreVehicle = null;
				}
				else if (entityStore2 == entity){
					entityStore2 = null;
					entityStoreVehicle2 = null;
				}
				else if (entityStore3 == entity) {
					entityStore3 = null;
					entityStoreVehicle3 = null;
				}
			}
		}
		if (!world.isClient && entity instanceof Monster monster &&
				!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
				!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
				!this.getReturning() && !this.retuningStart) {
			if (entity != entityStore && entityStoreVehicle != entity &&
				entity != entityStore2 && entityStoreVehicle2 != entity &&
						entity != entityStore3 && entityStoreVehicle3 != entity){
				if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isCovered()){
					this.damageCounter = this.damageCounter + 2;
				}
				else {
					++this.damageCounter;
				}
			}
			else if (entity != entityStore && entity != entityStore2 && entity != entityStore3 && zombiePropEntity instanceof ZombieShieldEntity){
				++this.damageCounter;
			}
		}
		if (!world.isClient && entity instanceof Monster monster &&
				!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
				!(zombiePropEntity != null && !(zombiePropEntity instanceof ZombieShieldEntity)) &&
				!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
				!this.getReturning() && !this.retuningStart) {
			Entity entity2 = entityHitResult.getEntity();
			float damage = PVZCONFIG.nestedProjDMG.boomerangDMG();
			String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
			SoundEvent sound;
			sound = switch (zombieMaterial) {
				case "metallic" -> PvZSounds.BUCKETHITEVENT;
				case "plastic" -> PvZSounds.CONEHITEVENT;
				case "stone" -> PvZSounds.STONEHITEVENT;
				default -> PvZSounds.PEAHITEVENT;
			};
			if (entity2 != entityStore && entity2 != entityStore2 && entity2 != entityStore3) {
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
				if (entityStore == null){
					entityStore = (LivingEntity) entityHitResult.getEntity();
					entityStoreVehicle = (LivingEntity) entityStore.getVehicle();
				}
				else if (entityStore2 == null && entityStore != entity2){
					entityStore2 = (LivingEntity) entityHitResult.getEntity();
					entityStoreVehicle2 = (LivingEntity) entityStore2.getVehicle();
				}
				else if (entityStore3 == null && entityStore != entity2  && entityStore2 != entity2) {
					entityStore3 = (LivingEntity) entityHitResult.getEntity();
					entityStoreVehicle3 = (LivingEntity) entityStore3.getVehicle();
				}
			}
			if (entity2.getVehicle() != null && entityStoreVehicle != entity2.getVehicle() && entityStoreVehicle2 != entity2.getVehicle() && entityStoreVehicle3 != entity2.getVehicle()){
				String zombieMaterial2 = PvZCubed.ZOMBIE_MATERIAL.get(entity2.getVehicle().getType()).orElse("flesh");
				sound = switch (zombieMaterial2) {
					case "metallic" -> PvZSounds.BUCKETHITEVENT;
					case "plastic" -> PvZSounds.CONEHITEVENT;
					case "stone" -> PvZSounds.STONEHITEVENT;
					default -> PvZSounds.PEAHITEVENT;
				};
				entity2.getVehicle().playSound(sound, 0.2F, (float) (0.5F + Math.random()));
				entity2.getVehicle().damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
			}
			if (entityStore == null){
				entityStore = (LivingEntity) entityHitResult.getEntity();
				entityStoreVehicle = (LivingEntity) entityStore.getVehicle();
			}
			else if (entityStore2 == null && entityStore != entity2){
				entityStore2 = (LivingEntity) entityHitResult.getEntity();
				entityStoreVehicle2 = (LivingEntity) entityStore2.getVehicle();
			}
			else if (entityStore3 == null && entityStore != entity2  && entityStore2 != entity2) {
				entityStore3 = (LivingEntity) entityHitResult.getEntity();
				entityStoreVehicle3 = (LivingEntity) entityStore3.getVehicle();
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
