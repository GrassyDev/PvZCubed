package io.github.GrassyDev.pvzmod.mixin;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Thanks to Cammie's Combat Tweaks https://github.com/CammiePone/Cammies-Combat-Tweaks
 * **/

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damage", at = @At("HEAD"))
    public void pvzmod$getOutAnnoyingIFrames(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {

        if (source.getAttacker() instanceof PlantEntity ||
				source.getAttacker() instanceof GeneralPvZombieEntity ||
				source == PvZCubed.HYPNO_DAMAGE)
            timeUntilRegen = 0;
    }
}
