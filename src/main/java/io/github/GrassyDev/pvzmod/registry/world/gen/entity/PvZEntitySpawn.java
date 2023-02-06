package io.github.GrassyDev.pvzmod.registry.world.gen.entity;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.basicgrave.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.nightgrave.NightGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.poolgrave.PoolGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom.PuffshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.buttonshroom.ButtonshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.sunflowerseed.SunflowerSeedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.weeniebeanie.WeenieBeanieEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

public class PvZEntitySpawn {

	public static void addEntitySpawn(){

		//Graves
		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BASICGRAVESTONE, 22, 1, 2);
		SpawnRestriction.register(PvZEntity.BASICGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BasicGraveEntity::canBasicGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.NIGHTGRAVESTONE, 28, 2, 2);
		SpawnRestriction.register(PvZEntity.NIGHTGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NightGraveEntity::canNightGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.POOLGRAVESTONE, 12, 1, 3);
		SpawnRestriction.register(PvZEntity.POOLGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PoolGraveEntity::canPoolGraveSpawn);

		//Plants

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.PUFFSHROOM, 70, 2, 3);
		SpawnRestriction.register(PvZEntity.PUFFSHROOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PuffshroomEntity::canPuffshroomSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.WEENIEBEANIE, 70, 2, 3);
		SpawnRestriction.register(PvZEntity.WEENIEBEANIE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WeenieBeanieEntity::canWeenieBeanieSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.SUNFLOWERSEED, 70, 2, 3);
		SpawnRestriction.register(PvZEntity.SUNFLOWERSEED, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SunflowerSeedEntity::canSunflowerSeedSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.LILYPAD, 35, 2, 3);
		SpawnRestriction.register(PvZEntity.LILYPAD, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LilyPadEntity::canLilyPadSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BOMBSEEDLING, 35, 2, 3);
		SpawnRestriction.register(PvZEntity.BOMBSEEDLING, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.SMALLNUT, 35, 1, 2);
		SpawnRestriction.register(PvZEntity.SMALLNUT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.ICEBERGLETTUCE, 20, 2, 3);
		SpawnRestriction.register(PvZEntity.ICEBERGLETTUCE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BUTTONSHROOM, 12, 2, 3);
		SpawnRestriction.register(PvZEntity.BUTTONSHROOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ButtonshroomEntity::canButtonshroomSpawn);


		//BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST), SpawnGroup.MONSTER, PvZEntity.NIGHTGRAVESTONE, 100, 1, 6);

	}
}
