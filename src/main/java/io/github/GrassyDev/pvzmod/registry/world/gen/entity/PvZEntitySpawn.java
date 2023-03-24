package io.github.GrassyDev.pvzmod.registry.world.gen.entity;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.darkagesgrave.DarkAgesGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.futuregrave.FutureGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave.NightGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.poolgrave.PoolGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.roofgrave.RoofGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.peashooter.PeashooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom.PuffshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce.IcebergLettuceEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.loquat.LoquatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bellflower.BellflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bombseedling.BombSeedlingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.buttonshroom.ButtonshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smallnut.SmallNutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.sunflowerseed.SunflowerSeedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.weeniebeanie.WeenieBeanieEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

public class PvZEntitySpawn {

	public static void addEntitySpawn(){

		//Graves
		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BASICGRAVESTONE, 10, 1, 1);
		SpawnRestriction.register(PvZEntity.BASICGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BasicGraveEntity::canBasicGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.NIGHTGRAVESTONE, 10, 2, 2);
		SpawnRestriction.register(PvZEntity.NIGHTGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NightGraveEntity::canNightGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.POOLGRAVESTONE, 6, 1, 1);
		SpawnRestriction.register(PvZEntity.POOLGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PoolGraveEntity::canPoolGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.ROOFGRAVESTONE, 6, 1, 1);
		SpawnRestriction.register(PvZEntity.ROOFGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RoofGraveEntity::canRoofGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.FUTUREGRAVE, 8, 2, 3);
		SpawnRestriction.register(PvZEntity.FUTUREGRAVE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FutureGraveEntity::canFutureGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.DARKAGESGRAVESTONE, 10, 2, 2);
		SpawnRestriction.register(PvZEntity.DARKAGESGRAVESTONE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DarkAgesGraveEntity::canDarkAgesGraveSpawn);


		//Plants

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.PEASHOOTER, 50, 1, 1);
		SpawnRestriction.register(PvZEntity.PEASHOOTER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PeashooterEntity::canPeashooterSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BELLFLOWER, 50, 2, 2);
		SpawnRestriction.register(PvZEntity.BELLFLOWER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BellflowerEntity::canBellflowerSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.PUFFSHROOM, 50, 2, 3);
		SpawnRestriction.register(PvZEntity.PUFFSHROOM, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PuffshroomEntity::canPuffshroomSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.WEENIEBEANIE, 20, 3, 3);
		SpawnRestriction.register(PvZEntity.WEENIEBEANIE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WeenieBeanieEntity::canWeenieBeanieSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.SUNFLOWERSEED, 22, 2, 3);
		SpawnRestriction.register(PvZEntity.SUNFLOWERSEED, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SunflowerSeedEntity::canSunflowerSeedSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.LILYPAD, 12, 1, 3);
		SpawnRestriction.register(PvZEntity.LILYPAD, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LilyPadEntity::canLilyPadSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BOMBSEEDLING, 12, 1, 3);
		SpawnRestriction.register(PvZEntity.BOMBSEEDLING, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BombSeedlingEntity::canBombSeedlingSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.SMALLNUT, 20, 1, 3);
		SpawnRestriction.register(PvZEntity.SMALLNUT, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SmallNutEntity::canSmallnutSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.LOQUAT, 12, 1, 3);
		SpawnRestriction.register(PvZEntity.LOQUAT, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LoquatEntity::canLoquatSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.ICEBERGLETTUCE, 12, 1, 3);
		SpawnRestriction.register(PvZEntity.ICEBERGLETTUCE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IcebergLettuceEntity::canIcebergLettuceSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BUTTONSHROOM, 8, 1, 3);
		SpawnRestriction.register(PvZEntity.BUTTONSHROOM, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ButtonshroomEntity::canButtonshroomSpawn);


		//BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST), SpawnGroup.MONSTER, PvZEntity.NIGHTGRAVESTONE, 100, 1, 6);

	}
}
