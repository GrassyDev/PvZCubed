package io.github.GrassyDev.pvzmod.registry.world.gen.entity;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.darkagesgrave.DarkAgesGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.egyptgravestone.EgyptGraveEntity;
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
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.zapricot.ZapricotEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class PvZEntitySpawn {

	public static void addEntitySpawn(){

		//Graves
		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BASICGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.basicG(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.basicGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.basicGmax());
		SpawnRestriction.register(PvZEntity.BASICGRAVESTONE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BasicGraveEntity::canBasicGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.NIGHTGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.nightG(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.nightGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.nightGmax());
		SpawnRestriction.register(PvZEntity.NIGHTGRAVESTONE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NightGraveEntity::canNightGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.POOLGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.poolG(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.poolGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.poolGmax());
		SpawnRestriction.register(PvZEntity.POOLGRAVESTONE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PoolGraveEntity::canPoolGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.EGYPTGRAVE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.egyptG(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.egyptGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.egyptGmax());
		SpawnRestriction.register(PvZEntity.EGYPTGRAVE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EgyptGraveEntity::canEgyptGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.ROOFGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.roofG(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.roofGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.roofGmax());
		SpawnRestriction.register(PvZEntity.ROOFGRAVESTONE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RoofGraveEntity::canRoofGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.FUTUREGRAVE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.futureG(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.futureGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.futureGmax());
		SpawnRestriction.register(PvZEntity.FUTUREGRAVE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FutureGraveEntity::canFutureGraveSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.DARKAGESGRAVESTONE, PVZCONFIG.nestedSpawns.nestedGraveSpawns.darkagesG(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.darkagesGmin(), PVZCONFIG.nestedSpawns.nestedGraveSpawns.darkagesGmax());
		SpawnRestriction.register(PvZEntity.DARKAGESGRAVESTONE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DarkAgesGraveEntity::canDarkAgesGraveSpawn);


		//Plants

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.PEASHOOTER, PVZCONFIG.nestedSpawns.nestedPlantSpawns.peashooterSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.peashooterSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.peashooterSPmax());
		SpawnRestriction.register(PvZEntity.PEASHOOTER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PeashooterEntity::canPeashooterSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BELLFLOWER, PVZCONFIG.nestedSpawns.nestedPlantSpawns.bellflowerSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.bellflowerSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.bellflowerSPmax());
		SpawnRestriction.register(PvZEntity.BELLFLOWER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BellflowerEntity::canBellflowerSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.PUFFSHROOM, PVZCONFIG.nestedSpawns.nestedPlantSpawns.puffshroomSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.puffshroomSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.puffshroomSPmax());
		SpawnRestriction.register(PvZEntity.PUFFSHROOM, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PuffshroomEntity::canPuffshroomSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.WEENIEBEANIE, PVZCONFIG.nestedSpawns.nestedPlantSpawns.weeniebeanieSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.weeniebeanieSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.weeniebeanieSPmax());
		SpawnRestriction.register(PvZEntity.WEENIEBEANIE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WeenieBeanieEntity::canWeenieBeanieSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.SUNFLOWERSEED, PVZCONFIG.nestedSpawns.nestedPlantSpawns.sunflowerseedSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.sunflowerseedSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.sunflowerseedSPmax());
		SpawnRestriction.register(PvZEntity.SUNFLOWERSEED, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SunflowerSeedEntity::canSunflowerSeedSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.LILYPAD, PVZCONFIG.nestedSpawns.nestedPlantSpawns.lilypadSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.lilypadSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.lilypadSPmax());
		SpawnRestriction.register(PvZEntity.LILYPAD, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LilyPadEntity::canLilyPadSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BOMBSEEDLING, PVZCONFIG.nestedSpawns.nestedPlantSpawns.bombseedlingSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.bombseedlingSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.bombseedlingSPmax());
		SpawnRestriction.register(PvZEntity.BOMBSEEDLING, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BombSeedlingEntity::canBombSeedlingSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.SMALLNUT, PVZCONFIG.nestedSpawns.nestedPlantSpawns.smallnutSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.smallnutSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.smallnutSPmax());
		SpawnRestriction.register(PvZEntity.SMALLNUT, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SmallNutEntity::canSmallnutSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.LOQUAT, PVZCONFIG.nestedSpawns.nestedPlantSpawns.loquatSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.loquatSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.loquatSPmax());
		SpawnRestriction.register(PvZEntity.LOQUAT, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, LoquatEntity::canLoquatSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.ICEBERGLETTUCE, PVZCONFIG.nestedSpawns.nestedPlantSpawns.icebergSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.icebergSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.icebergSPmax());
		SpawnRestriction.register(PvZEntity.ICEBERGLETTUCE, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IcebergLettuceEntity::canIcebergLettuceSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.ZAPRICOT, PVZCONFIG.nestedSpawns.nestedPlantSpawns.zapricotSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.zapricotSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.zapricotSPmax());
		SpawnRestriction.register(PvZEntity.ZAPRICOT, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZapricotEntity::canZapricotSpawn);

		BiomeModifications.addSpawn(BiomeSelectors.all(), SpawnGroup.MONSTER, PvZEntity.BUTTONSHROOM, PVZCONFIG.nestedSpawns.nestedPlantSpawns.buttonshroomSP(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.buttonshroomSPmin(), PVZCONFIG.nestedSpawns.nestedPlantSpawns.buttonshroomSPmax());
		SpawnRestriction.register(PvZEntity.BUTTONSHROOM, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ButtonshroomEntity::canButtonshroomSpawn);


		//BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FOREST), SpawnGroup.MONSTER, PvZEntity.NIGHTGRAVESTONE, 100, 1, 6);

	}
}
