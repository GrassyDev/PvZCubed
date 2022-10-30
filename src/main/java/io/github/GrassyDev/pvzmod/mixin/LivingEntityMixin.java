package io.github.GrassyDev.pvzmod.mixin;

import io.github.GrassyDev.pvzmod.registry.plants.planttypes.PlantEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
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

        if (source.getAttacker() instanceof PlantEntity)
            timeUntilRegen = 0;
    }
}
