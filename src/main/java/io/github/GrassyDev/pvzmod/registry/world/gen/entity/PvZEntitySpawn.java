package io.github.GrassyDev.pvzmod.registry.world.gen.entity;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.basicgrave.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.nightgrave.NightGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.planttypes.PlantEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PvZEntitySpawn {

	public static void addEntitySpawn(){

		//Graves
		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BASICGRAVESTONE, 25, 1, 3);
		SpawnRestriction.register(PvZEntity.BASICGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BasicGraveEntity::canBasicGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.NIGHTGRAVESTONE, 25, 1, 3);
		SpawnRestriction.register(PvZEntity.NIGHTGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NightGraveEntity::canNightGraveSpawn);

		//Plants

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.PUFFSHROOM, 25, 1, 2);
		SpawnRestriction.register(PvZEntity.PUFFSHROOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);


		//BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST), SpawnGroup.MONSTER, PvZEntity.NIGHTGRAVESTONE, 100, 1, 6);

	}
}
