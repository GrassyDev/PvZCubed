package io.github.GrassyDev.pvzmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damage", at = @At("HEAD"))
    public void pvzmod$getOutAnnoyingIFrames(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {

        if (((source.isProjectile() ||
                source.isMagic() ||
                source.isFire() ||
                source.isExplosive() ||
                source.isOutOfWorld()) &&
                (source.getSource() instanceof ProjectileEntity ||
                source.getSource() instanceof PlayerEntity ||
                source.getSource() instanceof GolemEntity ||
                source.getSource() instanceof HostileEntity)) ||

                source.getSource() instanceof PlayerEntity ||
                source.getSource() instanceof GolemEntity ||
                source.getSource() instanceof HostileEntity)
            timeUntilRegen = 0;
    }
}
