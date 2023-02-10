package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave.BasicGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave.NightGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave.NightGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.poolgrave.PoolGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.poolgrave.PoolGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.backupdancer.HypnoBackupDancerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.backupdancer.HypnoBackupDancerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.berserker.HypnoBerserkerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.berserker.HypnoBerserkerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.browncoat.modernday.HypnoBrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.browncoat.modernday.HypnoBrowncoatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.buckethead.modernday.HypnoBucketheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.buckethead.modernday.HypnoBucketheadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.conehead.modernday.HypnoConeheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.conehead.modernday.HypnoConeheadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.football.HypnoFootballEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.football.HypnoFootballEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.gargantuar.modernday.HypnoGargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.gargantuar.modernday.HypnoGargantuarEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.imp.modernday.HypnoImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.imp.modernday.HypnoImpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.newspaper.HypnoNewspaperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.newspaper.HypnoNewspaperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.polevaulting.HypnoPoleVaultingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.polevaulting.HypnoPoleVaultingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.screendoor.HypnoScreendoorEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.screendoor.HypnoScreendoorEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.snorkel.HypnoSnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.snorkel.HypnoSnorkelRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.cherrybomb.CherrybombEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.cherrybomb.CherrybombEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper.ChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper.ChomperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.peashooter.PeashooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.peashooter.PeashooterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.potatomine.PotatomineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.potatomine.PotatomineEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.repeater.RepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.repeater.RepeaterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.snowpea.SnowpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.snowpea.SnowpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.wallnutentity.WallnutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.wallnutentity.WallnutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.doomshroom.DoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.doomshroom.DoomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom.FumeshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom.FumeshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.hypnoshroom.HypnoshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.iceshroom.IceshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.iceshroom.IceshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom.PuffshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom.PuffshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.scaredyshroom.ScaredyshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.scaredyshroom.ScaredyshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilypadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.squash.SquashEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.squash.SquashEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tanglekelp.TangleKelpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tanglekelp.TangleKelpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater.ThreepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater.ThreepeaterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.cabbagepult.CabbagepultEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.cabbagepult.CabbagepultEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gatlingpea.GatlingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gatlingpea.GatlingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce.IcebergLettuceEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce.IcebergLettuceEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea.FlamingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea.FlamingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.peapod.PeapodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.peapod.PeapodEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.snowqueenpea.SnowqueenpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.snowqueenpea.SnowqueenpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.perfoomshroom.PerfoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.perfoomshroom.PerfoomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bellflower.BellflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bellflower.BellflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bombseedling.BombSeedlingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bombseedling.BombSeedlingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.buttonshroom.ButtonshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.buttonshroom.ButtonshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smallnut.SmallNutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smallnut.SmallnutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.sunflowerseed.SunflowerSeedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.sunflowerseed.SunflowerSeedEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.weeniebeanie.WeenieBeanieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.weeniebeanie.WeenieBeanieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.cabbage.ShootingCabbageEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.firepea.ShootingFlamingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.fume.FumeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.icespike.ShootingIcespikeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.jingle.JingleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pea.ShootingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.plasmapea.ShootingPlasmaPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowpea.ShootingSnowPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowqueenpea.ShootingSnowqueenPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spore.SporeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.locustswarm.LocustSwarmEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.locustswarm.LocustswarmEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.backupdancer.BackupDancerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.backupdancer.BackupDancerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.berserker.BerserkerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.berserker.BerserkerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.buckethead.modernday.BucketheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.buckethead.modernday.BucketheadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.conehead.modernday.ConeheadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.conehead.modernday.ConeheadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dancingzombie.DancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dancingzombie.DancingZombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football.FootballEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football.FootballEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper.NewspaperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper.NewspaperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.polevaulting.PoleVaultingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.polevaulting.PoleVaultingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor.ScreendoorEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor.ScreendoorEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

public class PvZEntity implements ModInitializer {

    public static final String ModID = "pvzmod"; // This is just so we can refer to our ModID easier.

    public static final EntityType<PeashooterEntity> PEASHOOTER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "peashooter"),
            QuiltEntityTypeBuilder.<PeashooterEntity>create(SpawnGroup.CREATURE, PeashooterEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

    public static final EntityType<SunflowerEntity> SUNFLOWER = Registry.register((
            Registry.ENTITY_TYPE),
            new Identifier(ModID, "sunflower"),
            QuiltEntityTypeBuilder.<SunflowerEntity>create(SpawnGroup.CREATURE, SunflowerEntity::new).setDimensions(EntityDimensions.fixed(1f,0.8f)).build()
            );

    public static final EntityType<CherrybombEntity> CHERRYBOMB = Registry.register((
                    Registry.ENTITY_TYPE),
            new Identifier(ModID, "cherrybomb"),
            QuiltEntityTypeBuilder.<CherrybombEntity>create(SpawnGroup.CREATURE, CherrybombEntity::new).setDimensions(EntityDimensions.fixed(1f,0.8f)).build()
    );

    public static final EntityType<WallnutEntity> WALLNUT = Registry.register((
                    Registry.ENTITY_TYPE),
            new Identifier(ModID, "wallnut"),
            QuiltEntityTypeBuilder.<WallnutEntity>create(SpawnGroup.CREATURE, WallnutEntity::new).setDimensions(EntityDimensions.fixed(0.99f,1.55f)).build()
    );

    public static final EntityType<PotatomineEntity> POTATOMINE = Registry.register((
                    Registry.ENTITY_TYPE),
            new Identifier(ModID, "potatomine"),
            QuiltEntityTypeBuilder.<PotatomineEntity>create(SpawnGroup.CREATURE, PotatomineEntity::new).setDimensions(EntityDimensions.fixed(1f,0.8f)).build()
    );

    public static final EntityType<SnowpeaEntity> SNOWPEA = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "snowpea"),
            QuiltEntityTypeBuilder.<SnowpeaEntity>create(SpawnGroup.CREATURE, SnowpeaEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

    public static final EntityType<ChomperEntity> CHOMPER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "chomper"),
            QuiltEntityTypeBuilder.<ChomperEntity>create(SpawnGroup.CREATURE, ChomperEntity::new).setDimensions(EntityDimensions.fixed(1f, 1.55f)).build()
    );

    public static final EntityType<RepeaterEntity> REPEATER = Registry.register((
                    Registry.ENTITY_TYPE),
            new Identifier(ModID, "repeater"),
            QuiltEntityTypeBuilder.<RepeaterEntity>create(SpawnGroup.CREATURE, RepeaterEntity::new).setDimensions(EntityDimensions.fixed(1f,0.8f)).build()
    );

    public static final EntityType<PuffshroomEntity> PUFFSHROOM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "puffshroom"),
            QuiltEntityTypeBuilder.<PuffshroomEntity>create(SpawnGroup.CREATURE, PuffshroomEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

    public static final EntityType<SunshroomEntity> SUNSHROOM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "sunshroom"),
            QuiltEntityTypeBuilder.<SunshroomEntity>create(SpawnGroup.CREATURE, SunshroomEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

    public static final EntityType<FumeshroomEntity> FUMESHROOM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "fumeshroom"),
            QuiltEntityTypeBuilder.<FumeshroomEntity>create(SpawnGroup.CREATURE, FumeshroomEntity::new).setDimensions(EntityDimensions.fixed(1f, 1.55f)).build()
    );

    public static final EntityType<GravebusterEntity> GRAVEBUSTER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "gravebuster"),
            QuiltEntityTypeBuilder.<GravebusterEntity>create(SpawnGroup.CREATURE, GravebusterEntity::new).setDimensions(EntityDimensions.fixed(1f, 1.55f)).build()
    );

    public static final EntityType<HypnoshroomEntity> HYPNOSHROOM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "hypnoshroom"),
            QuiltEntityTypeBuilder.<HypnoshroomEntity>create(SpawnGroup.CREATURE, HypnoshroomEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

    public static final EntityType<ScaredyshroomEntity> SCAREDYSHROOM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "scaredyshroom"),
            QuiltEntityTypeBuilder.<ScaredyshroomEntity>create(SpawnGroup.CREATURE, ScaredyshroomEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

    public static final EntityType<IceshroomEntity> ICESHROOM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "iceshroom"),
            QuiltEntityTypeBuilder.<IceshroomEntity>create(SpawnGroup.CREATURE, IceshroomEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

    public static final EntityType<DoomshroomEntity> DOOMSHROOM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "doomshroom"),
            QuiltEntityTypeBuilder.<DoomshroomEntity>create(SpawnGroup.CREATURE, DoomshroomEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

	public static final EntityType<LilyPadEntity> LILYPAD = Registry.register((
					Registry.ENTITY_TYPE),
			new Identifier(ModID, "lilypad"),
			QuiltEntityTypeBuilder.<LilyPadEntity>create(SpawnGroup.CREATURE, LilyPadEntity::new).setDimensions(EntityDimensions.fixed(0.99f,0.1f)).build()
	);

	public static final EntityType<SquashEntity> SQUASH = Registry.register((
					Registry.ENTITY_TYPE),
			new Identifier(ModID, "squash"),
			QuiltEntityTypeBuilder.<SquashEntity>create(SpawnGroup.CREATURE, SquashEntity::new).setDimensions(EntityDimensions.fixed(1f,1.55f)).build()
	);

    public static final EntityType<ThreepeaterEntity> THREEPEATER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "threepeater"),
            QuiltEntityTypeBuilder.<ThreepeaterEntity>create(SpawnGroup.CREATURE, ThreepeaterEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

	public static final EntityType<TangleKelpEntity> TANGLE_KELP = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "tanglekelp"),
			QuiltEntityTypeBuilder.<TangleKelpEntity>create(SpawnGroup.CREATURE, TangleKelpEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.1f)).build()
	);

	public static final EntityType<CabbagepultEntity> CABBAGEPULT = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "cabbagepult"),
			QuiltEntityTypeBuilder.<CabbagepultEntity>create(SpawnGroup.CREATURE, CabbagepultEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<GatlingpeaEntity> GATLINGPEA = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "gatlingpea"),
			QuiltEntityTypeBuilder.<GatlingpeaEntity>create(SpawnGroup.CREATURE, GatlingpeaEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<TwinSunflowerEntity> TWINSUNFLOWER = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "twinsunflower"),
			QuiltEntityTypeBuilder.<TwinSunflowerEntity>create(SpawnGroup.CREATURE, TwinSunflowerEntity::new).setDimensions(EntityDimensions.fixed(1f, 1.55f)).build()
	);

	public static final EntityType<SnowqueenpeaEntity> SNOWQUEENPEA = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "snowqueenpea"),
			QuiltEntityTypeBuilder.<SnowqueenpeaEntity>create(SpawnGroup.CREATURE, SnowqueenpeaEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<PerfoomshroomEntity> PERFOOMSHROOM = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "perfoomshroom"),
			QuiltEntityTypeBuilder.<PerfoomshroomEntity>create(SpawnGroup.CREATURE, PerfoomshroomEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<IcebergLettuceEntity> ICEBERGLETTUCE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "iceberglettuce"),
			QuiltEntityTypeBuilder.<IcebergLettuceEntity>create(SpawnGroup.CREATURE, IcebergLettuceEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<PeapodEntity> PEAPOD = Registry.register((
					Registry.ENTITY_TYPE),
			new Identifier(ModID, "peapod"),
			QuiltEntityTypeBuilder.<PeapodEntity>create(SpawnGroup.CREATURE, PeapodEntity::new).setDimensions(EntityDimensions.fixed(1f,1.8f)).build()
	);

    public static final EntityType<FlamingpeaEntity> FLAMINGPEA = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "flamingpea"),
            QuiltEntityTypeBuilder.<FlamingpeaEntity>create(SpawnGroup.CREATURE, FlamingpeaEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

	public static final EntityType<SmallNutEntity> SMALLNUT = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "smallnut"),
			QuiltEntityTypeBuilder.<SmallNutEntity>create(SpawnGroup.CREATURE, SmallNutEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<ButtonshroomEntity> BUTTONSHROOM = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "buttonshroom"),
			QuiltEntityTypeBuilder.<ButtonshroomEntity>create(SpawnGroup.CREATURE, ButtonshroomEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<BombSeedlingEntity> BOMBSEEDLING = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "bombseedling"),
			QuiltEntityTypeBuilder.<BombSeedlingEntity>create(SpawnGroup.CREATURE, BombSeedlingEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<WeenieBeanieEntity> WEENIEBEANIE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "weeniebeanie"),
			QuiltEntityTypeBuilder.<WeenieBeanieEntity>create(SpawnGroup.CREATURE, WeenieBeanieEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<SunflowerSeedEntity> SUNFLOWERSEED = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "sunflowerseed"),
			QuiltEntityTypeBuilder.<SunflowerSeedEntity>create(SpawnGroup.CREATURE, SunflowerSeedEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<BellflowerEntity> BELLFLOWER = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "bellflower"),
			QuiltEntityTypeBuilder.<BellflowerEntity>create(SpawnGroup.CREATURE, BellflowerEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);


    /////////////////////////////////////////////////////////////////////////////////////////////////

    public static final EntityType<ShootingPeaEntity> PEA = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "pea"),
            QuiltEntityTypeBuilder.<ShootingPeaEntity>create(SpawnGroup.MISC, ShootingPeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
    );

	public static final EntityType<ShootingSnowPeaEntity> SNOWPEAPROJ = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "snowpeaproj"),
			QuiltEntityTypeBuilder.<ShootingSnowPeaEntity>create(SpawnGroup.MISC, ShootingSnowPeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingSnowqueenPeaEntity> SNOWQUEENPEAPROJ = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "snowqueenpeaproj"),
			QuiltEntityTypeBuilder.<ShootingSnowqueenPeaEntity>create(SpawnGroup.MISC, ShootingSnowqueenPeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingIcespikeEntity> ICESPIKEPROJ = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "icespikeproj"),
			QuiltEntityTypeBuilder.<ShootingIcespikeEntity>create(SpawnGroup.MISC, ShootingIcespikeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingFlamingPeaEntity> FIREPEA = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "firepea"),
			QuiltEntityTypeBuilder.<ShootingFlamingPeaEntity>create(SpawnGroup.MISC, ShootingFlamingPeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingPlasmaPeaEntity> PLASMAPEA = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "plasmapea"),
			QuiltEntityTypeBuilder.<ShootingPlasmaPeaEntity>create(SpawnGroup.MISC, ShootingPlasmaPeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

    public static final EntityType<SporeEntity> SPORE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "spore"),
            QuiltEntityTypeBuilder.<SporeEntity>create(SpawnGroup.MISC, SporeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.25f)).build()
    );

	public static final EntityType<FumeEntity> FUME = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "fume"),
			QuiltEntityTypeBuilder.<FumeEntity>create(SpawnGroup.MISC, FumeEntity::new).setDimensions(EntityDimensions.fixed(1f,.5f)).build()
	);

	public static final EntityType<ShootingCabbageEntity> CABBAGE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "cabbage"),
			QuiltEntityTypeBuilder.<ShootingCabbageEntity>create(SpawnGroup.MISC, ShootingCabbageEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<JingleEntity> JINGLE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "jingle"),
			QuiltEntityTypeBuilder.<JingleEntity>create(SpawnGroup.MISC, JingleEntity::new).setDimensions(EntityDimensions.fixed(1f,.5f)).build()
	);

	/////////////////////////////////////////////////////////////////////////////////////////////////

    public static final EntityType<BrowncoatEntity> BROWNCOAT = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "browncoat"),
            QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
    public static final EntityType<HypnoBrowncoatEntity> HYPNOBROWNCOAT = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "browncoat_hypnotized"),
            QuiltEntityTypeBuilder.<HypnoBrowncoatEntity>create(SpawnGroup.CREATURE, HypnoBrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

    public static final EntityType<FlagzombieEntity> FLAGZOMBIE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "flagzombie"),
            QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.MONSTER, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
    public static final EntityType<HypnoFlagzombieEntity> HYPNOFLAGZOMBIE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "flagzombie_hypnotized"),
            QuiltEntityTypeBuilder.<HypnoFlagzombieEntity>create(SpawnGroup.CREATURE, HypnoFlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

    public static final EntityType<ConeheadEntity> CONEHEAD = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "conehead"),
            QuiltEntityTypeBuilder.<ConeheadEntity>create(SpawnGroup.MONSTER, ConeheadEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
    public static final EntityType<HypnoConeheadEntity> HYPNOCONEHEAD = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "conehead_hypnotized"),
            QuiltEntityTypeBuilder.<HypnoConeheadEntity>create(SpawnGroup.CREATURE, HypnoConeheadEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

    public static final EntityType<PoleVaultingEntity> POLEVAULTING = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "polevaulting"),
            QuiltEntityTypeBuilder.<PoleVaultingEntity>create(SpawnGroup.MONSTER, PoleVaultingEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
    public static final EntityType<HypnoPoleVaultingEntity> HYPNOPOLEVAULTING = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "polevaulting_hypnotized"),
			QuiltEntityTypeBuilder.<HypnoPoleVaultingEntity>create(SpawnGroup.CREATURE, HypnoPoleVaultingEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

    public static final EntityType<BucketheadEntity> BUCKETHEAD = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "buckethead"),
            QuiltEntityTypeBuilder.<BucketheadEntity>create(SpawnGroup.MONSTER, BucketheadEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
    public static final EntityType<HypnoBucketheadEntity> HYPNOBUCKETHEAD = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "buckethead_hypnotized"),
            QuiltEntityTypeBuilder.<HypnoBucketheadEntity>create(SpawnGroup.CREATURE, HypnoBucketheadEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

    public static final EntityType<NewspaperEntity> NEWSPAPER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "newspaper"),
            QuiltEntityTypeBuilder.<NewspaperEntity>create(SpawnGroup.MONSTER, NewspaperEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
    public static final EntityType<HypnoNewspaperEntity> HYPNONEWSPAPER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "newspaper_hypnotized"),
            QuiltEntityTypeBuilder.<HypnoNewspaperEntity>create(SpawnGroup.CREATURE, HypnoNewspaperEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

    public static final EntityType<ScreendoorEntity> SCREEENDOOR = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "screendoor"),
            QuiltEntityTypeBuilder.<ScreendoorEntity>create(SpawnGroup.MONSTER, ScreendoorEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
    public static final EntityType<HypnoScreendoorEntity> HYPNOSCREENDOOR = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "screendoor_hypnotized"),
            QuiltEntityTypeBuilder.<HypnoScreendoorEntity>create(SpawnGroup.CREATURE, HypnoScreendoorEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

    public static final EntityType<FootballEntity> FOOTBALL = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "football"),
            QuiltEntityTypeBuilder.<FootballEntity>create(SpawnGroup.MONSTER, FootballEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

    public static final EntityType<HypnoFootballEntity> HYPNOFOOTBALL = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "football_hypnotized"),
            QuiltEntityTypeBuilder.<HypnoFootballEntity>create(SpawnGroup.CREATURE, HypnoFootballEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

    public static final EntityType<DancingZombieEntity> DANCINGZOMBIE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "dancing_zombie"),
            QuiltEntityTypeBuilder.<DancingZombieEntity>create(SpawnGroup.MONSTER, DancingZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
    public static final EntityType<HypnoDancingZombieEntity> HYPNODANCINGZOMBIE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "dancing_zombie_hypnotized"),
            QuiltEntityTypeBuilder.<HypnoDancingZombieEntity>create(SpawnGroup.CREATURE, HypnoDancingZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

    public static final EntityType<BackupDancerEntity> BACKUPDANCER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "backup_dancer"),
            QuiltEntityTypeBuilder.<BackupDancerEntity>create(SpawnGroup.MONSTER, BackupDancerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
    public static final EntityType<HypnoBackupDancerEntity> HYPNOBACKUPDANCER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "backup_dancer_hypnotized"),
            QuiltEntityTypeBuilder.<HypnoBackupDancerEntity>create(SpawnGroup.CREATURE, HypnoBackupDancerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

	public static final EntityType<DuckyTubeEntity> DUCKYTUBE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "duckytube"),
			QuiltEntityTypeBuilder.<DuckyTubeEntity>create(SpawnGroup.MONSTER, DuckyTubeEntity::new).setDimensions(EntityDimensions.fixed(0.62f, 0.5f)).build()
	);

	public static final EntityType<SnorkelEntity> SNORKEL = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "snorkel"),
			QuiltEntityTypeBuilder.<SnorkelEntity>create(SpawnGroup.MONSTER, SnorkelEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

	public static final EntityType<HypnoSnorkelEntity> HYPNOSNORKEL = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "snorkel_hypnotized"),
			QuiltEntityTypeBuilder.<HypnoSnorkelEntity>create(SpawnGroup.MONSTER, HypnoSnorkelEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

	public static final EntityType<GargantuarEntity> GARGANTUAR = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "gargantuar"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.25f, 3.95f)).build()
	);

	public static final EntityType<HypnoGargantuarEntity> HYPNOGARGANTUAR = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "gargantuar_hypnotized"),
			QuiltEntityTypeBuilder.<HypnoGargantuarEntity>create(SpawnGroup.CREATURE, HypnoGargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.25f, 3.95f)).build()
	);

	public static final EntityType<ImpEntity> IMP = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "imp"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.MONSTER, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 0.95f)).build()
	);

	public static final EntityType<HypnoImpEntity> HYPNOIMP = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "imp_hypnotized"),
			QuiltEntityTypeBuilder.<HypnoImpEntity>create(SpawnGroup.CREATURE, HypnoImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 0.95f)).build()
	);

	public static final EntityType<BerserkerEntity> BERSERKER = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "berserker"),
			QuiltEntityTypeBuilder.<BerserkerEntity>create(SpawnGroup.MONSTER, BerserkerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

	public static final EntityType<HypnoBerserkerEntity> HYPNOBERSERKER = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "berserker_hypnotized"),
			QuiltEntityTypeBuilder.<HypnoBerserkerEntity>create(SpawnGroup.CREATURE, HypnoBerserkerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

	public static final EntityType<LocustSwarmEntity> LOCUSTSWARM = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "locustswarm"),
			QuiltEntityTypeBuilder.<LocustSwarmEntity>create(SpawnGroup.MONSTER, LocustSwarmEntity::new).setDimensions(EntityDimensions.fixed(0.62f, 0.5f)).build()
	);

    /////////////////////////////////////////////////////////////////////////////////////////////////

    public static final EntityType<BasicGraveEntity> BASICGRAVESTONE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "basicgrave"),
            QuiltEntityTypeBuilder.<BasicGraveEntity>create(SpawnGroup.MONSTER, BasicGraveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
    );

    public static final EntityType<NightGraveEntity> NIGHTGRAVESTONE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "nightgrave"),
            QuiltEntityTypeBuilder.<NightGraveEntity>create(SpawnGroup.MONSTER, NightGraveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
    );

	public static final EntityType<PoolGraveEntity> POOLGRAVESTONE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "poolgrave"),
			QuiltEntityTypeBuilder.<PoolGraveEntity>create(SpawnGroup.MONSTER, PoolGraveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
	);

	@Override
	public void onInitialize(ModContainer mod) {

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEASHOOTER, PeashooterEntity.createPeashooterAttributes().build());
        EntityRendererRegistry.register(PvZEntity.PEASHOOTER, PeashooterEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNFLOWER, SunflowerEntity.createSunflowerAttributes().build());
        EntityRendererRegistry.register(PvZEntity.SUNFLOWER, SunflowerEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CHERRYBOMB, CherrybombEntity.createCherrybombAttributes().build());
        EntityRendererRegistry.register(PvZEntity.CHERRYBOMB, CherrybombEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.WALLNUT, WallnutEntity.createWallnutAttributes().build());
        EntityRendererRegistry.register(PvZEntity.WALLNUT, WallnutEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POTATOMINE, PotatomineEntity.createPotatomineAttributes().build());
        EntityRendererRegistry.register(PvZEntity.POTATOMINE, PotatomineEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SNOWPEA, SnowpeaEntity.createSnowpeaAttributes().build());
        EntityRendererRegistry.register(PvZEntity.SNOWPEA, SnowpeaEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CHOMPER, ChomperEntity.createChomperAttributes().build());
        EntityRendererRegistry.register(PvZEntity.CHOMPER, ChomperEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.REPEATER, RepeaterEntity.createRepeaterAttributes().build());
        EntityRendererRegistry.register(PvZEntity.REPEATER, RepeaterEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PUFFSHROOM, PuffshroomEntity.createPuffshroomAttributes().build());
        EntityRendererRegistry.register(PvZEntity.PUFFSHROOM, PuffshroomEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNSHROOM, SunshroomEntity.createSunshroomAttributes().build());
        EntityRendererRegistry.register(PvZEntity.SUNSHROOM, SunshroomEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FUMESHROOM, FumeshroomEntity.createFumeshroomAttributes().build());
        EntityRendererRegistry.register(PvZEntity.FUMESHROOM, FumeshroomEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GRAVEBUSTER, GravebusterEntity.createGravebusterAttributes().build());
        EntityRendererRegistry.register(PvZEntity.GRAVEBUSTER, GravebusterEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOSHROOM, HypnoshroomEntity.createHypnoshroomAttributes().build());
        EntityRendererRegistry.register(PvZEntity.HYPNOSHROOM, HypnoshroomEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCAREDYSHROOM, ScaredyshroomEntity.createScaredyshroomAttributes().build());
        EntityRendererRegistry.register(PvZEntity.SCAREDYSHROOM, ScaredyshroomEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ICESHROOM, IceshroomEntity.createIceshroomAttributes().build());
        EntityRendererRegistry.register(PvZEntity.ICESHROOM, IceshroomEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DOOMSHROOM, DoomshroomEntity.createDoomshroomAttributes().build());
        EntityRendererRegistry.register(PvZEntity.DOOMSHROOM, DoomshroomEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.LILYPAD, LilyPadEntity.createLilyPadAttributes().build());
		EntityRendererRegistry.register(PvZEntity.LILYPAD, LilypadEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SQUASH, SquashEntity.createSquashAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SQUASH, SquashEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.THREEPEATER, ThreepeaterEntity.createThreepeaterAttributes().build());
        EntityRendererRegistry.register(PvZEntity.THREEPEATER, ThreepeaterEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TANGLE_KELP, TangleKelpEntity.createTangleKelpAttributes().build());
		EntityRendererRegistry.register(PvZEntity.TANGLE_KELP, TangleKelpEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CABBAGEPULT, CabbagepultEntity.createCabbagePultAttributes().build());
		EntityRendererRegistry.register(PvZEntity.CABBAGEPULT, CabbagepultEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GATLINGPEA, GatlingpeaEntity.createGatlingpeaAttributes().build());
		EntityRendererRegistry.register(PvZEntity.GATLINGPEA, GatlingpeaEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TWINSUNFLOWER, TwinSunflowerEntity.createTwinSunflowerAttributes().build());
		EntityRendererRegistry.register(PvZEntity.TWINSUNFLOWER, TwinSunflowerEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SNOWQUEENPEA, SnowqueenpeaEntity.createSnowqueenpeaAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SNOWQUEENPEA, SnowqueenpeaEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PERFOOMSHROOM, PerfoomshroomEntity.createPerfoomshroomAttributes().build());
		EntityRendererRegistry.register(PvZEntity.PERFOOMSHROOM, PerfoomshroomEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ICEBERGLETTUCE, IcebergLettuceEntity.createIcebergLettuceAttributes().build());
		EntityRendererRegistry.register(PvZEntity.ICEBERGLETTUCE, IcebergLettuceEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEAPOD, PeapodEntity.createPeapodAttributes().build());
		EntityRendererRegistry.register(PvZEntity.PEAPOD, PeapodEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAMINGPEA, FlamingpeaEntity.createFlamingpeaAttributes().build());
        EntityRendererRegistry.register(PvZEntity.FLAMINGPEA, FlamingpeaEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SMALLNUT, SmallNutEntity.createSmallnutAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SMALLNUT, SmallnutEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BUTTONSHROOM, ButtonshroomEntity.createButtonshroomAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BUTTONSHROOM, ButtonshroomEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BOMBSEEDLING, BombSeedlingEntity.createBombSeedlingAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BOMBSEEDLING, BombSeedlingEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.WEENIEBEANIE, WeenieBeanieEntity.createWeenieBeanieAttributes().build());
		EntityRendererRegistry.register(PvZEntity.WEENIEBEANIE, WeenieBeanieEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNFLOWERSEED, SunflowerSeedEntity.createSunflowerSeedAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SUNFLOWERSEED, SunflowerSeedEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BELLFLOWER, BellflowerEntity.createBellflowerAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BELLFLOWER, BellflowerEntityRenderer::new);


        /////////////////////////////////////////////////////////////////////////////////////////////////

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BROWNCOAT, BrowncoatEntity.createBrowncoatAttributes().build());
        EntityRendererRegistry.register(PvZEntity.BROWNCOAT, BrowncoatEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOBROWNCOAT, HypnoBrowncoatEntity.createHypnoBrowncoatAttributes().build());
        EntityRendererRegistry.register(PvZEntity.HYPNOBROWNCOAT, HypnoBrowncoatEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIE, FlagzombieEntity.createFlagzombieZombieAttributes().build());
        EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOFLAGZOMBIE, HypnoFlagzombieEntity.createHypnoFlagzombieAttributes().build());
        EntityRendererRegistry.register(PvZEntity.HYPNOFLAGZOMBIE, HypnoFlagzombieEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CONEHEAD, ConeheadEntity.createConeheadAttributes().build());
        EntityRendererRegistry.register(PvZEntity.CONEHEAD, ConeheadEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOCONEHEAD, HypnoConeheadEntity.createHypnoConeheadAttributes().build());
        EntityRendererRegistry.register(PvZEntity.HYPNOCONEHEAD, HypnoConeheadEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POLEVAULTING, PoleVaultingEntity.createPoleVaultingAttributes().build());
        EntityRendererRegistry.register(PvZEntity.POLEVAULTING, PoleVaultingEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOPOLEVAULTING, HypnoPoleVaultingEntity.createHypnoPoleVaultingAttributes().build());
        EntityRendererRegistry.register(PvZEntity.HYPNOPOLEVAULTING, HypnoPoleVaultingEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BUCKETHEAD, BucketheadEntity.createBucketheadAttributes().build());
        EntityRendererRegistry.register(PvZEntity.BUCKETHEAD, BucketheadEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOBUCKETHEAD, HypnoBucketheadEntity.createHypnoBucketheadAttributes().build());
        EntityRendererRegistry.register(PvZEntity.HYPNOBUCKETHEAD, HypnoBucketheadEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NEWSPAPER, NewspaperEntity.createHypnoNewspaperAttributes().build());
        EntityRendererRegistry.register(PvZEntity.NEWSPAPER, NewspaperEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNONEWSPAPER, HypnoNewspaperEntity.createHypnoNewspaperAttributes().build());
        EntityRendererRegistry.register(PvZEntity.HYPNONEWSPAPER, HypnoNewspaperEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCREEENDOOR, ScreendoorEntity.createScreendoorAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SCREEENDOOR, ScreendoorEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOSCREENDOOR, HypnoScreendoorEntity.createHypnoScreendoorAttributes().build());
        EntityRendererRegistry.register(PvZEntity.HYPNOSCREENDOOR, HypnoScreendoorEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FOOTBALL, FootballEntity.createFootballAttributes().build());
        EntityRendererRegistry.register(PvZEntity.FOOTBALL, FootballEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOFOOTBALL, HypnoFootballEntity.createHypnoFootballAttributes().build());
        EntityRendererRegistry.register(PvZEntity.HYPNOFOOTBALL, HypnoFootballEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DANCINGZOMBIE, DancingZombieEntity.createDancingZombieAttributes().build());
        EntityRendererRegistry.register(PvZEntity.DANCINGZOMBIE, DancingZombieEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNODANCINGZOMBIE, HypnoDancingZombieEntity.createHypnoDancingZombieAttributes().build());
        EntityRendererRegistry.register(PvZEntity.HYPNODANCINGZOMBIE, HypnoDancingZombieEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BACKUPDANCER, BackupDancerEntity.createBackupDancerAttributes().build());
        EntityRendererRegistry.register(PvZEntity.BACKUPDANCER, BackupDancerEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOBACKUPDANCER, HypnoBackupDancerEntity.createHypnoBackupDancerAttributes().build());
        EntityRendererRegistry.register(PvZEntity.HYPNOBACKUPDANCER, HypnoBackupDancerEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DUCKYTUBE, DuckyTubeEntity.createDuckyTubeAttributes().build());
		EntityRendererRegistry.register(PvZEntity.DUCKYTUBE, DuckyTubeEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SNORKEL, SnorkelEntity.createSnorkelAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SNORKEL, SnorkelEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOSNORKEL, HypnoSnorkelEntity.createHypnoSnorkelAttributes().build());
		EntityRendererRegistry.register(PvZEntity.HYPNOSNORKEL, HypnoSnorkelRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GARGANTUAR, GargantuarEntity.createGargantuarAttributes().build());
		EntityRendererRegistry.register(PvZEntity.GARGANTUAR, GargantuarEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOGARGANTUAR, HypnoGargantuarEntity.createHypnoGargantuarAttributes().build());
		EntityRendererRegistry.register(PvZEntity.HYPNOGARGANTUAR, HypnoGargantuarEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.IMP, ImpEntity.createImpAttributes().build());
		EntityRendererRegistry.register(PvZEntity.IMP, ImpEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOIMP, HypnoImpEntity.createHypnoImpAttributes().build());
		EntityRendererRegistry.register(PvZEntity.HYPNOIMP, HypnoImpEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BERSERKER, BerserkerEntity.createBerserkerAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BERSERKER, BerserkerEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOBERSERKER, HypnoBerserkerEntity.createHypnoBerserkerAttributes().build());
		EntityRendererRegistry.register(PvZEntity.HYPNOBERSERKER, HypnoBerserkerEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.LOCUSTSWARM, LocustSwarmEntity.createLocustSwarmAttributes().build());
		EntityRendererRegistry.register(PvZEntity.LOCUSTSWARM, LocustswarmEntityRenderer::new);


        /////////////////////////////////////////////////////////////////////////////////////////////////

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BASICGRAVESTONE, BasicGraveEntity.createBasicGraveAttributes().build());
        EntityRendererRegistry.register(PvZEntity.BASICGRAVESTONE, BasicGraveRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NIGHTGRAVESTONE, NightGraveEntity.createNightGraveAttributes().build());
        EntityRendererRegistry.register(PvZEntity.NIGHTGRAVESTONE, NightGraveRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POOLGRAVESTONE, PoolGraveEntity.createPoolGraveAttributes().build());
		EntityRendererRegistry.register(PvZEntity.POOLGRAVESTONE, PoolGraveRenderer::new);


    }
}
