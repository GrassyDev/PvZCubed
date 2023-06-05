package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.card;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieRiderEntity;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ShootingCardEntity extends PvZProjectileEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public int maxAge = 140;
	public int returnAge = 60;

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
			DataTracker.registerData(ShootingCardEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


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

	public void setReturning(ShootingCardEntity.Returning returning) {
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

    public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "cardproj");

    public ShootingCardEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public ShootingCardEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    public void tick() {
		if (age <= 1){
			if (this.getOwner() != null) {
				this.ownerYaw = this.getOwner().getHeadYaw();
			}
			this.playSound(PvZSounds.BOOMERANGAMBIENTEVENT, 0.0125f, 1f);
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
			if (this.age >= returnAge + returnAge / 2){
				this.retuningStart = true;
			}
			if (this.damageCounter >= 3 && returningTicks <= 0){
				this.retuningStart = true;
			}
			if (--returningTicks <= 0) {
				this.setReturning(Returning.TRUE);
				Vec3d vec3d = new Vec3d((double) -0.04, 0.0, 0.0).rotateY(-this.ownerYaw * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				if (this.age <= this.returnAge + 20) {
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
	public List<Entity> entityStore = new ArrayList<>();
	public List<Entity> entityStoreVehicle = new ArrayList<>();

	@Override
	public void hitEntities() {
		super.hitEntities();
		Iterator var9 = hitEntities.iterator();
		while (true) {
			Entity entity;
				if (!var9.hasNext()) {
					return;
				}

				entity = (Entity) var9.next();

			ZombiePropEntity zombiePropEntity = null;
			for (Entity entity1 : entity.getPassengerList()) {
				if (entity1 instanceof ZombiePropEntity zpe) {
					zombiePropEntity = zpe;
				}
			}
			if (entity == this.getOwner() && this.getReturning()){
				this.remove(RemovalReason.DISCARDED);
			}
			if (entityStoreVehicle.contains(entity) && zombiePropEntity == null && !entityStore.contains(entity)) {
				entityStore.add(entity);
			}
			if (!world.isClient && this.getReturning()) {
				if (entityStore.contains(entity) && this.retuningStart) {
					boolean hasHelmet = false;
					float damage = PVZCONFIG.nestedProjDMG.cardDMG();
					for (Entity entity1 : entity.getPassengerList()) {
						if (entity1 instanceof ZombiePropEntity zpe && !(zpe instanceof ZombieShieldEntity)) {
							hasHelmet = true;
						}
					}
					if (entity instanceof ZombiePropEntity zpe && !(zpe instanceof ZombieShieldEntity)) {
						hasHelmet = true;
					}
					if (hasHelmet || (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isCovered())) {
						damage = damage / 2;
					}
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
					SoundEvent sound;
					sound = switch (zombieMaterial) {
						case "metallic" -> PvZSounds.BUCKETHITEVENT;
						case "plastic" -> PvZSounds.CONEHITEVENT;
						case "stone" -> PvZSounds.STONEHITEVENT;
						default -> PvZSounds.PEAHITEVENT;
					};
					entity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
					if (damage > ((LivingEntity) entity).getHealth() &&
							!(entity instanceof ZombieShieldEntity) &&
							entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno()) && !generalPvZombieEntity.isFlying()) {
						float damage2 = damage - ((LivingEntity) entity).getHealth();
						entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
						generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
					} else {
						entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
					}
					entityStore.remove(entity);
					entityStoreVehicle.remove(entity);
					if (!(entity instanceof ZombieShieldEntity) || (entity instanceof ZombieRiderEntity)) {
						entityStoreVehicle.remove(entity.getVehicle());
					}
				}
			}
			if (!world.isClient && entity instanceof Monster monster &&
					!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
					!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity2 && (generalPvZombieEntity2.isFlying())) &&
					!(zombiePropEntity != null && !(zombiePropEntity instanceof ZombieShieldEntity)) &&
					!(monster instanceof ZombieRiderEntity zombieRiderEntity && zombieRiderEntity.hasVehicle()) &&
					!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) &&
					!this.getReturning() && !this.retuningStart && damageCounter <= 2 && !entityStore.contains(entity) && !entityStoreVehicle.contains(entity)) {
				boolean hasHelmet = false;
				float damage = PVZCONFIG.nestedProjDMG.cardDMG();
				for (Entity entity1 : entity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe && !(zpe instanceof ZombieShieldEntity)) {
						hasHelmet = true;
					}
				}
				if (entity instanceof ZombiePropEntity zpe && !(zpe instanceof ZombieShieldEntity)) {
					hasHelmet = true;
				}
				if (hasHelmet || (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isCovered())) {
					damage = damage / 2;
				}
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic" -> PvZSounds.BUCKETHITEVENT;
					case "plastic" -> PvZSounds.CONEHITEVENT;
					case "stone" -> PvZSounds.STONEHITEVENT;
					default -> PvZSounds.PEAHITEVENT;
				};
				++this.damageCounter;
				entity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
				if (damage > ((LivingEntity) entity).getHealth() &&
						!(entity instanceof ZombieShieldEntity) &&
						entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					float damage2 = damage - ((LivingEntity) entity).getHealth();
					entityStore.add(entity.getVehicle());
					entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
					generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
				} else {
					entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
					entityStore.add(entity);
					if (!(entity instanceof ZombieShieldEntity) || (entity instanceof ZombieRiderEntity)) {
						entityStoreVehicle.add(entity.getVehicle());
					}
				}
			}
			if (this.getReturning() && entity == this.getOwner()){
				this.remove(RemovalReason.DISCARDED);
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
