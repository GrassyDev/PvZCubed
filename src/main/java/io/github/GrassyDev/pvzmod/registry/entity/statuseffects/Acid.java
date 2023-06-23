package io.github.GrassyDev.pvzmod.registry.entity.statuseffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class Acid extends StatusEffect {
    public Acid() {
        super(
                StatusEffectType.HARMFUL, // whether beneficial or harmful for entity
                0x70BD84); // color in RGB
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		entity.damage(DamageSource.GENERIC, 4F);
    }
}
