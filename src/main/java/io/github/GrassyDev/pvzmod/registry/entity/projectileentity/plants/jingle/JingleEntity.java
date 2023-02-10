package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.jingle;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper.NewspaperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor.ScreendoorEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
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

public class JingleEntity extends ThrownItemEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "jingle");

	@Override
	public void registerControllers(AnimationData animationData) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		animationData.addAnimationController(controller);
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().loop("peashot.idle"));
		return PlayState.CONTINUE;
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

    public JingleEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public JingleEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public JingleEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.JINGLE, world);
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

		if (!this.world.isClient && this.age == 7) {
			this.world.sendEntityStatus(this, (byte) 3);
			this.remove(RemovalReason.DISCARDED);
		}

		for (int j = 0; j < 2; ++j) {
			// RAINBOW
			double d = (double) (this.random.range(0, 255) & 255) / 255.0;
			double e = (double) (this.random.range(0, 255) & 255) / 255.0;
			double f = (double) (this.random.range(0, 255) & 255) / 255.0;
			this.world.addParticle(ParticleTypes.NOTE, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), d, e, f);
		}
	}

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof ScreendoorEntity) {
            float sound = this.random.nextFloat();
            entity.playSound(PvZCubed.BUCKETHITEVENT, 0.125F, 1F);
            entity.playSound(PvZCubed.PEAHITEVENT, 0.125F, 1F);
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 53.31F);
        }
        else if (entity instanceof NewspaperEntity) {
            float sound = this.random.nextFloat();
            entity.playSound(PvZCubed.PEAHITEVENT, 0.125F, 1F);
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 10.39F);
        }
        else if (!world.isClient && entity instanceof Monster && !(entity instanceof HypnoDancingZombieEntity) &&
                !(entity instanceof HypnoFlagzombieEntity) && !(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())) {
			String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
			SoundEvent sound;
			sound = switch (zombieMaterial) {
				case "metallic" -> PvZCubed.BUCKETHITEVENT;
				case "plastic" -> PvZCubed.CONEHITEVENT;
				default -> PvZCubed.PEAHITEVENT;
			};
			entity.playSound(sound, 0.28F, 1F);
			entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 8);
        }
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {

			double d = (double) (180 & 255) / 255.0;
			double e = (double) (30 & 255) / 255.0;
			double f = (double) (200 & 255) / 255.0;

			for (int j = 0; j < 2; ++j) {
				this.world.addParticle(ParticleTypes.NOTE, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), d, e, f);
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
