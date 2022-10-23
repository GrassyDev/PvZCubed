package io.github.GrassyDev.pvzmod.registry.statuseffects;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;

public class Warm extends StatusEffect {
    public Warm() {
        super(
                StatusEffectType.BENEFICIAL, // whether beneficial or harmful for entities
                0xFF9933); // color in RGB
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
    }
}
