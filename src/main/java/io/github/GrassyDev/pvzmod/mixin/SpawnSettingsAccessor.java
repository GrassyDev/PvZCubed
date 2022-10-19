package io.github.GrassyDev.pvzmod.mixin;

import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;

@Mixin(SpawnSettings.class)
public interface SpawnSettingsAccessor {

    @Accessor("spawners")
    Map<SpawnGroup, List<SpawnSettings.SpawnEntry>> getSpawners();

    @Accessor("spawners")
    void setSpawners(Map<SpawnGroup, List<SpawnSettings.SpawnEntry>> spawners);
}
