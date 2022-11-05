package io.github.GrassyDev.pvzmod.mixin;

import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoSummonerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedtypes.HypnoZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.PvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.SummonerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
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
				source.getAttacker() instanceof PvZombieEntity ||
				source.getAttacker() instanceof SummonerEntity ||
				source.getAttacker() instanceof HypnoZombieEntity ||
				source.getAttacker() instanceof HypnoSummonerEntity)
            timeUntilRegen = 0;
    }
}
