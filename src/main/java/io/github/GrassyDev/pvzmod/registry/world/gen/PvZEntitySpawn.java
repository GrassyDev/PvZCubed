package io.github.GrassyDev.pvzmod.registry.world.gen;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.gravestones.gravestoneentity.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.gravestones.gravestoneentity.NightGraveEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

public class PvZEntitySpawn {

	public static void addEntitySpawn(){
		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BASICGRAVESTONE, 50, 1, 6);
		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.NIGHTGRAVESTONE, 50, 1, 6);
		//BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST), SpawnGroup.MONSTER, PvZEntity.NIGHTGRAVESTONE, 100, 1, 6);

		SpawnRestriction.register(PvZEntity.BASICGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BasicGraveEntity::canBasicGraveSpawn);
		SpawnRestriction.register(PvZEntity.NIGHTGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NightGraveEntity::canNightGraveSpawn);
	}
}
