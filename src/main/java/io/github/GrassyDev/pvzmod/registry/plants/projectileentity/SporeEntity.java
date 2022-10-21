package io.github.GrassyDev.pvzmod.registry.plants.projectileentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.hypnotizedentity.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import java.util.UUID;

public class SporeEntity extends ThrownItemEntity {

    public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "spore");

    public SporeEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public SporeEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public SporeEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.SPORE, world);
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
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if ((entity instanceof BucketheadEntity) ||
                (entity instanceof ScreendoorEntity) ||
                (entity instanceof BerserkerEntity)) {
            float sound = this.random.nextFloat();
            entity.playSound(PvZCubed.BUCKETHITEVENT, 0.5F, 1F);
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 4);
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.WITHER, 60, 6)));
            this.world.sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }
        else if ((entity instanceof ConeheadEntity) ||
                (entity instanceof FootballEntity) ||
                (entity instanceof BackupDancerEntity)) {
            float sound = this.random.nextFloat();
            entity.playSound(PvZCubed.CONEHITEVENT, 0.5F, 1F);
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 4);
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.WITHER, 60, 6)));
            this.world.sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }
        else if (entity instanceof Monster && !(entity instanceof HypnoDancingZombieEntity) &&
                !(entity instanceof HypnoFlagzombieEntity)) {
            float sound = this.random.nextFloat();
            entity.playSound(PvZCubed.PEAHITEVENT, 0.5F, 1F);
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 4);
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.WITHER, 60, 6)));
            this.world.sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }


    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
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
