package io.github.GrassyDev.pvzmod.registry.entity.statuseffects;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class Hypnotized extends StatusEffect {
    public Hypnotized() {
        super(
                StatusEffectType.HARMFUL, // whether beneficial or harmful for entity
                0xFF66FF); // color in RGB
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
        entity.damage(PvZCubed.HYPNO_DAMAGE, 1);
    }
}
