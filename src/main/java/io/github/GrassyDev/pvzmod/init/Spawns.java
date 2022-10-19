package io.github.GrassyDev.pvzmod.init;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.github.GrassyDev.pvzmod.mixin.SpawnRestrictionAccessor;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.gravestones.gravestoneentity.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.zombies.zombieentity.BrowncoatEntity;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;


// Glow Squid Mod Devs: "big thanks to YanisBft with their MooBlooms mod! https://github.com/YanisBft/Mooblooms"
// Grassy: and big thanks to the glow squid and avdenturez devs

public class Spawns {
    private static final Registry<Biome> REGISTRY = BuiltinRegistries.BIOME;

    public static void init() {
        registerSpawnRestrictions();
        for (Biome biome : BuiltinRegistries.BIOME) {
            addSpawnEntries(biome);
        }
        RegistryEntryAddedCallback.event(BuiltinRegistries.BIOME).register((i, identifier, biome) -> Spawns.addSpawnEntries(biome));
    }

    private static void addSpawnEntries(Biome biome) {
        if (biome.getCategory().equals(Biome.Category.MUSHROOM)) {
            addSpawnToBiome(biome, SpawnGroup.AMBIENT,
                    new SpawnSettings.SpawnEntry(PvZEntity.PUFFSHROOM, 1, 3, 4),
                    new SpawnSettings.SpawnEntry(PvZEntity.SUNSHROOM, 1, 3, 4));
        }

        if (biome.getCategory().equals(Biome.Category.SAVANNA)||
                biome.getCategory().equals(Biome.Category.NETHER)||
                biome.getCategory().equals(Biome.Category.MUSHROOM)||
                biome.getCategory().equals(Biome.Category.MESA)||
                biome.getCategory().equals(Biome.Category.DESERT)||
                biome.getCategory().equals(Biome.Category.PLAINS)||
                biome.getCategory().equals(Biome.Category.FOREST)||
                biome.getCategory().equals(Biome.Category.JUNGLE)||
                biome.getCategory().equals(Biome.Category.RIVER)||
                biome.getCategory().equals(Biome.Category.SWAMP)||
                biome.getCategory().equals(Biome.Category.BEACH)||
                biome.getCategory().equals(Biome.Category.ICY)||
                biome.getCategory().equals(Biome.Category.TAIGA)||
                biome.getCategory().equals(Biome.Category.EXTREME_HILLS)) {
            addSpawnToBiome(biome, SpawnGroup.MONSTER,
                    new SpawnSettings.SpawnEntry(PvZEntity.BASICGRAVESTONE, 5, 1, 1),
            new SpawnSettings.SpawnEntry(PvZEntity.NIGHTGRAVESTONE, 4, 1, 1));
        }

        if (biome.getCategory().equals(Biome.Category.SAVANNA)||
                biome.getCategory().equals(Biome.Category.MUSHROOM)||
                biome.getCategory().equals(Biome.Category.MESA)||
                biome.getCategory().equals(Biome.Category.DESERT)||
                biome.getCategory().equals(Biome.Category.PLAINS)||
                biome.getCategory().equals(Biome.Category.FOREST)||
                biome.getCategory().equals(Biome.Category.JUNGLE)||
                biome.getCategory().equals(Biome.Category.RIVER)||
                biome.getCategory().equals(Biome.Category.SWAMP)||
                biome.getCategory().equals(Biome.Category.BEACH)||
                biome.getCategory().equals(Biome.Category.ICY)||
                biome.getCategory().equals(Biome.Category.TAIGA)||
                biome.getCategory().equals(Biome.Category.EXTREME_HILLS)) {
            addSpawnToBiome(biome, SpawnGroup.AMBIENT,
                    new SpawnSettings.SpawnEntry(PvZEntity.PUFFSHROOM, 2, 1, 2));
        }
    }

    private static void addSpawnToBiome(Biome biome, SpawnGroup classification, SpawnSettings.SpawnEntry... spawners) {
        convertImmutableSpawners(biome);
        List<SpawnSettings.SpawnEntry> zombieSpawnersList = new ArrayList<>(((SpawnSettingsAccessor) biome.getSpawnSettings()).getSpawners().get(SpawnGroup.MONSTER));
        List<SpawnSettings.SpawnEntry> plantsSpawnersList = new ArrayList<>(((SpawnSettingsAccessor) biome.getSpawnSettings()).getSpawners().get(SpawnGroup.AMBIENT));
        zombieSpawnersList.addAll(Arrays.asList(spawners));
        plantsSpawnersList.addAll(Arrays.asList(spawners));
        ((SpawnSettingsAccessor) biome.getSpawnSettings()).getSpawners().put(SpawnGroup.MONSTER, zombieSpawnersList);
        ((SpawnSettingsAccessor) biome.getSpawnSettings()).getSpawners().put(SpawnGroup.AMBIENT, plantsSpawnersList);
    }

    private static void convertImmutableSpawners(Biome biome) {
        if (((SpawnSettingsAccessor) biome.getSpawnSettings()).getSpawners() instanceof ImmutableMap) {
            ((SpawnSettingsAccessor) biome.getSpawnSettings()).setSpawners(new HashMap<>(((SpawnSettingsAccessor) biome.getSpawnSettings()).getSpawners()));
        }
    }

    private static void registerSpawnRestrictions() {
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.BROWNCOAT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BrowncoatEntity::canBrowncoatSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.BASICGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BasicGraveEntity::canBasicGraveSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.NIGHTGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NightGraveEntity::canNightGraveSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.SCREEENDOOR, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScreendoorEntity::canScreendoorSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.NEWSPAPER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NewspaperEntity::canNewspaperSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.FLAGZOMBIE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FlagzombieEntity::canFlagzombieSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.CONEHEAD, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ConeheadEntity::canConeheadSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.POLEVAULTING, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PoleVaultingEntity::canPoleVaultingSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.BUCKETHEAD, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BucketheadEntity::canBucketheadSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.FOOTBALL, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FootballEntity::canFootballSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.PEASHOOTER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PeashooterEntity::canPeashooterSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.SUNFLOWER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SunflowerEntity::canSunflowerSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.REPEATER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RepeaterEntity::canRepeaterSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.WALLNUT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, WallnutEntity::canWallnutSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.POTATOMINE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, PotatomineEntity::canPotatomineSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.SNOWPEA, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SnowpeaEntity::canSnowpeaSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.CHERRYBOMB, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, CherrybombEntity::canCherrybombSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.CHOMPER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ChomperEntity::canChomperSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.PUFFSHROOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PuffshroomEntity::canPuffshroomSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.FUMESHROOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FumeshroomEntity::canFumeshroomSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.SUNSHROOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SunshroomEntity::canSunshroomSpawn);
        SpawnRestrictionAccessor.invokeRegister(PvZEntity.THREEPEATER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ThreepeaterEntity::canThreepeaterSpawn);
    }
}
