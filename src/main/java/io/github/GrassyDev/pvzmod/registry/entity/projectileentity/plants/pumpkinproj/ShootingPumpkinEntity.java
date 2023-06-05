package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pumpkinproj;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pumpkinzombie.PumpkinZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieRiderEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
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
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.ZOMBIE_SIZE;

public class ShootingPumpkinEntity extends PvZProjectileEntity implements IAnimatable {

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

    public ShootingPumpkinEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(false);
    }

    public ShootingPumpkinEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public ShootingPumpkinEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.PUMPKINPROJ, world);
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

        if (!this.world.isClient && this.age >= 120) {
            this.world.sendEntityStatus(this, (byte) 3);
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
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }


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
			ZombiePropEntity zombiePropEntity2 = null;
			for (Entity entity1 : entity.getPassengerList()) {
				if (entity1 instanceof ZombiePropEntity zpe) {
					zombiePropEntity2 = zpe;
				}
			}
			if (!world.isClient && entity instanceof Monster monster &&
					!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
					!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity1 && (generalPvZombieEntity1.isCovered())) &&
					(!(entity instanceof ZombiePropEntity) || entity instanceof ZombieRiderEntity) && ZOMBIE_SIZE.get(entity.getType()).orElse("medium").equals("medium")) {
				if (this.world instanceof ServerWorld serverWorld) {
					this.playSound(PvZSounds.HYPNOTIZINGEVENT, 1.5F, 1.0F);
					PumpkinZombieEntity hypnotizedZombie = (PumpkinZombieEntity) PvZEntity.PUMPKINZOMBIEHYPNO.create(world);
					hypnotizedZombie.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), entity.getYaw(), entity.getPitch());
					hypnotizedZombie.initialize(serverWorld, world.getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.CONVERSION, (EntityData) null, (NbtCompound) null);
					if (entity.hasCustomName()) {
						hypnotizedZombie.setCustomName(entity.getCustomName());
						hypnotizedZombie.setCustomNameVisible(entity.isCustomNameVisible());
					}
					for (Entity entity1 : entity.getPassengerList()) {
						if (entity1 instanceof ZombiePropEntity zpe) {
							zpe.discard();
						}
					}

					hypnotizedZombie.setPersistent();

					hypnotizedZombie.setHeadYaw(entity.getHeadYaw());
					entity.remove(RemovalReason.DISCARDED);
					hypnotizedZombie.createPumpkinProp();
					serverWorld.spawnEntityAndPassengers(hypnotizedZombie);
					for (Entity entity1 : hypnotizedZombie.getPassengerList()) {
						if (entity1 instanceof ZombiePropEntity zpe) {
							zpe.setHypno(GeneralPvZombieEntity.IsHypno.TRUE);
						}
					}
					this.world.sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
					break;
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
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
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
