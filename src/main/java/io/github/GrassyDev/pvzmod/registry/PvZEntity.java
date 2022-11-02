package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.NightGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.renderers.BasicGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.renderers.NightGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.*;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.renderers.*;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.cherrybomb.CherrybombEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.cherrybomb.CherrybombEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.chomper.ChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.chomper.ChomperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.doomshroom.DoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.doomshroom.DoomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.flamingpea.FlamingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.flamingpea.FlamingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.fumeshroom.FumeshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.fumeshroom.FumeshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.gatlingpea.GatlingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.gatlingpea.GatlingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.gravebuster.GravebusterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.hypnoshroom.HypnoshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.iceshroom.IceshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.iceshroom.IceshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.peapod.PeapodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.peapod.PeapodEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.peashooter.PeashooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.peashooter.PeashooterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine.PotatomineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine.PotatomineEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine.UnarmedPotatomineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine.UnarmedPotatomineEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.puffshroom.PuffshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.puffshroom.PuffshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.repeater.RepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.repeater.RepeaterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.scaredyshroom.ScaredyshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.scaredyshroom.ScaredyshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.snowpea.SnowpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.snowpea.SnowpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.sunflower.SunflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.sunshroom.SunshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.threepeater.ThreepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.threepeater.ThreepeaterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.wallnutentity.WallnutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.wallnutentity.WallnutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.*;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.FumeEntityVariants.FumeEntity_G;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.FumeEntityVariants.FumeEntity_T;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.renderers.*;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

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
            QuiltEntityTypeBuilder.<WallnutEntity>create(SpawnGroup.CREATURE, WallnutEntity::new).setDimensions(EntityDimensions.fixed(0.99f,0.8f)).build()
    );

    public static final EntityType<PotatomineEntity> POTATOMINE = Registry.register((
                    Registry.ENTITY_TYPE),
            new Identifier(ModID, "potatomine"),
            QuiltEntityTypeBuilder.<PotatomineEntity>create(SpawnGroup.CREATURE, PotatomineEntity::new).setDimensions(EntityDimensions.fixed(1f,0.8f)).build()
    );
    public static final EntityType<UnarmedPotatomineEntity> UNARMEDPOTATOMINE = Registry.register((
                    Registry.ENTITY_TYPE),
            new Identifier(ModID, "unarmedpotatomine"),
            QuiltEntityTypeBuilder.<UnarmedPotatomineEntity>create(SpawnGroup.CREATURE, UnarmedPotatomineEntity::new).setDimensions(EntityDimensions.fixed(1f,0.8f)).build()
    );

    public static final EntityType<SnowpeaEntity> SNOWPEA = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "snowpea"),
            QuiltEntityTypeBuilder.<SnowpeaEntity>create(SpawnGroup.CREATURE, SnowpeaEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

    public static final EntityType<ChomperEntity> CHOMPER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "chomper"),
            QuiltEntityTypeBuilder.<ChomperEntity>create(SpawnGroup.CREATURE, ChomperEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
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
            QuiltEntityTypeBuilder.<FumeshroomEntity>create(SpawnGroup.CREATURE, FumeshroomEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
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

    public static final EntityType<ThreepeaterEntity> THREEPEATER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "threepeater"),
            QuiltEntityTypeBuilder.<ThreepeaterEntity>create(SpawnGroup.CREATURE, ThreepeaterEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

	public static final EntityType<GatlingpeaEntity> GATLINGPEA = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "gatlingpea"),
			QuiltEntityTypeBuilder.<GatlingpeaEntity>create(SpawnGroup.CREATURE, GatlingpeaEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<PeapodEntity> PEAPOD = Registry.register((
					Registry.ENTITY_TYPE),
			new Identifier(ModID, "peapod"),
			QuiltEntityTypeBuilder.<PeapodEntity>create(SpawnGroup.CREATURE, PeapodEntity::new).setDimensions(EntityDimensions.fixed(1f,0.8f)).build()
	);

    public static final EntityType<FlamingpeaEntity> FLAMINGPEA = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "flamingpea"),
            QuiltEntityTypeBuilder.<FlamingpeaEntity>create(SpawnGroup.CREATURE, FlamingpeaEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
    );

    /////////////////////////////////////////////////////////////////////////////////////////////////

    public static final EntityType<ShootingPeaEntity> PEA = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "pea"),
            QuiltEntityTypeBuilder.<ShootingPeaEntity>create(SpawnGroup.MISC, ShootingPeaEntity::new).setDimensions(EntityDimensions.fixed(.25f,.25f)).build()
    );

	public static final EntityType<ShootingSnowPeaEntity> SNOWPEAPROJ = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "snowpeaproj"),
			QuiltEntityTypeBuilder.<ShootingSnowPeaEntity>create(SpawnGroup.MISC, ShootingSnowPeaEntity::new).setDimensions(EntityDimensions.fixed(.25f,.25f)).build()
	);

	public static final EntityType<ShootingFlamingPeaEntity> FIREPEA = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "firepea"),
			QuiltEntityTypeBuilder.<ShootingFlamingPeaEntity>create(SpawnGroup.MISC, ShootingFlamingPeaEntity::new).setDimensions(EntityDimensions.fixed(.25f,.25f)).build()
	);

	public static final EntityType<ShootingRePeaEntity> REPEA = Registry.register(
					Registry.ENTITY_TYPE,
					new Identifier(ModID, "repea"),
			QuiltEntityTypeBuilder.<ShootingRePeaEntity>create(SpawnGroup.MISC, ShootingRePeaEntity::new).setDimensions(EntityDimensions.fixed(.25f,.25f)).build()
    );

	public static final EntityType<ShootingTriPeaEntity> TRIPEA = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "tripea"),
			QuiltEntityTypeBuilder.<ShootingTriPeaEntity>create(SpawnGroup.MISC, ShootingTriPeaEntity::new).setDimensions(EntityDimensions.fixed(.25f,.25f)).build()
	);

    public static final EntityType<SporeEntity> SPORE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "spore"),
            QuiltEntityTypeBuilder.<SporeEntity>create(SpawnGroup.MISC, SporeEntity::new).setDimensions(EntityDimensions.fixed(.25f,.25f)).build()
    );

	public static final EntityType<FumeEntity> FUME = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "fume"),
			QuiltEntityTypeBuilder.<FumeEntity>create(SpawnGroup.MISC, FumeEntity::new).setDimensions(EntityDimensions.fixed(1f,.25f)).build()
	);

	public static final EntityType<FumeEntity_G> FUME_G = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "fume_g"),
			QuiltEntityTypeBuilder.<FumeEntity_G>create(SpawnGroup.MISC, FumeEntity_G::new).setDimensions(EntityDimensions.fixed(1f,.25f)).build()
	);

	public static final EntityType<FumeEntity_T> FUME_T = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "fume_t"),
			QuiltEntityTypeBuilder.<FumeEntity_T>create(SpawnGroup.MISC, FumeEntity_T::new).setDimensions(EntityDimensions.fixed(1f,.25f)).build()
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

	@Override
	public void onInitialize(ModContainer mod) {

		FabricDefaultAttributeRegistry.register(PvZEntity.PEASHOOTER, PeashooterEntity.createPeashooterAttributes());
        EntityRendererRegistry.register(PvZEntity.PEASHOOTER, PeashooterEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.SUNFLOWER, SunflowerEntity.createSunflowerAttributes());
        EntityRendererRegistry.register(PvZEntity.SUNFLOWER, SunflowerEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.CHERRYBOMB, CherrybombEntity.createCherrybombAttributes());
        EntityRendererRegistry.register(PvZEntity.CHERRYBOMB, CherrybombEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.WALLNUT, WallnutEntity.createWallnutAttributes());
        EntityRendererRegistry.register(PvZEntity.WALLNUT, WallnutEntityRenderer::new);


		FabricDefaultAttributeRegistry.register(PvZEntity.POTATOMINE, PotatomineEntity.createPotatomineAttributes());
        EntityRendererRegistry.register(PvZEntity.POTATOMINE, PotatomineEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.UNARMEDPOTATOMINE, UnarmedPotatomineEntity.createUnarmedPotatomineAttributes());
        EntityRendererRegistry.register(PvZEntity.UNARMEDPOTATOMINE, UnarmedPotatomineEntityRenderer::new);


		FabricDefaultAttributeRegistry.register(PvZEntity.SNOWPEA, SnowpeaEntity.createSnowpeaAttributes());
        EntityRendererRegistry.register(PvZEntity.SNOWPEA, SnowpeaEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.CHOMPER, ChomperEntity.createChomperAttributes());
        EntityRendererRegistry.register(PvZEntity.CHOMPER, ChomperEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.REPEATER, RepeaterEntity.createRepeaterAttributes());
        EntityRendererRegistry.register(PvZEntity.REPEATER, RepeaterEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.PUFFSHROOM, PuffshroomEntity.createPuffshroomAttributes());
        EntityRendererRegistry.register(PvZEntity.PUFFSHROOM, PuffshroomEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.SUNSHROOM, SunshroomEntity.createSunshroomAttributes());
        EntityRendererRegistry.register(PvZEntity.SUNSHROOM, SunshroomEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.FUMESHROOM, FumeshroomEntity.createFumeshroomAttributes());
        EntityRendererRegistry.register(PvZEntity.FUMESHROOM, FumeshroomEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.GRAVEBUSTER, GravebusterEntity.createGravebusterAttributes());
        EntityRendererRegistry.register(PvZEntity.GRAVEBUSTER, GravebusterEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.HYPNOSHROOM, HypnoshroomEntity.createHypnoshroomAttributes());
        EntityRendererRegistry.register(PvZEntity.HYPNOSHROOM, HypnoshroomEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.SCAREDYSHROOM, ScaredyshroomEntity.createScaredyshroomAttributes());
        EntityRendererRegistry.register(PvZEntity.SCAREDYSHROOM, ScaredyshroomEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.ICESHROOM, IceshroomEntity.createIceshroomAttributes());
        EntityRendererRegistry.register(PvZEntity.ICESHROOM, IceshroomEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.DOOMSHROOM, DoomshroomEntity.createDoomshroomAttributes());
        EntityRendererRegistry.register(PvZEntity.DOOMSHROOM, DoomshroomEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.THREEPEATER, ThreepeaterEntity.createThreepeaterAttributes());
        EntityRendererRegistry.register(PvZEntity.THREEPEATER, ThreepeaterEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.GATLINGPEA, GatlingpeaEntity.createGatlingpeaAttributes());
		EntityRendererRegistry.register(PvZEntity.GATLINGPEA, GatlingpeaEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.PEAPOD, PeapodEntity.createPeapodAttributes());
		EntityRendererRegistry.register(PvZEntity.PEAPOD, PeapodEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.FLAMINGPEA, FlamingpeaEntity.createFlamingpeaAttributes());
        EntityRendererRegistry.register(PvZEntity.FLAMINGPEA, FlamingpeaEntityRenderer::new);


        /////////////////////////////////////////////////////////////////////////////////////////////////

		FabricDefaultAttributeRegistry.register(PvZEntity.BROWNCOAT, BrowncoatEntity.createBrowncoatAttributes());
        EntityRendererRegistry.register(PvZEntity.BROWNCOAT, BrowncoatEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.HYPNOBROWNCOAT, HypnoBrowncoatEntity.createHypnoBrowncoatAttributes());
        EntityRendererRegistry.register(PvZEntity.HYPNOBROWNCOAT, HypnoBrowncoatEntityRenderer::new);


		FabricDefaultAttributeRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntity.createFlagzombieZombieAttributes());
        EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.HYPNOFLAGZOMBIE, HypnoFlagzombieEntity.createHypnoFlagzombieAttributes());
        EntityRendererRegistry.register(PvZEntity.HYPNOFLAGZOMBIE, HypnoFlagzombieEntityRenderer::new);


		FabricDefaultAttributeRegistry.register(PvZEntity.CONEHEAD, ConeheadEntity.createConeheadAttributes());
        EntityRendererRegistry.register(PvZEntity.CONEHEAD, ConeheadEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.HYPNOCONEHEAD, HypnoConeheadEntity.createHypnoConeheadAttributes());
        EntityRendererRegistry.register(PvZEntity.HYPNOCONEHEAD, HypnoConeheadEntityRenderer::new);


		FabricDefaultAttributeRegistry.register(PvZEntity.POLEVAULTING, PoleVaultingEntity.createPoleVaultingAttributes());
        EntityRendererRegistry.register(PvZEntity.POLEVAULTING, PoleVaultingEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.HYPNOPOLEVAULTING, HypnoPoleVaultingEntity.createHypnoPoleVaultingAttributes());
        EntityRendererRegistry.register(PvZEntity.HYPNOPOLEVAULTING, HypnoPoleVaultingEntityRenderer::new);


		FabricDefaultAttributeRegistry.register(PvZEntity.BUCKETHEAD, BucketheadEntity.createBucketheadAttributes());
        EntityRendererRegistry.register(PvZEntity.BUCKETHEAD, BucketheadEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.HYPNOBUCKETHEAD, HypnoBucketheadEntity.createHypnoBucketheadAttributes());
        EntityRendererRegistry.register(PvZEntity.HYPNOBUCKETHEAD, HypnoBucketheadEntityRenderer::new);


		FabricDefaultAttributeRegistry.register(PvZEntity.NEWSPAPER, NewspaperEntity.createNewspaperAttributes());
        EntityRendererRegistry.register(PvZEntity.NEWSPAPER, NewspaperEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.HYPNONEWSPAPER, HypnoNewspaperEntity.createHypnoNewspaperAttributes());
        EntityRendererRegistry.register(PvZEntity.HYPNONEWSPAPER, HypnoNewspaperEntityRenderer::new);


		FabricDefaultAttributeRegistry.register(PvZEntity.SCREEENDOOR, ScreendoorEntity.createScreendoorAttributes());
		EntityRendererRegistry.register(PvZEntity.SCREEENDOOR, ScreendoorEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.HYPNOSCREENDOOR, HypnoScreendoorEntity.createHypnoScreendoorAttributes());
        EntityRendererRegistry.register(PvZEntity.HYPNOSCREENDOOR, HypnoScreendoorEntityRenderer::new);


		FabricDefaultAttributeRegistry.register(PvZEntity.FOOTBALL, FootballEntity.createFootballAttributes());
        EntityRendererRegistry.register(PvZEntity.FOOTBALL, FootballEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.HYPNOFOOTBALL, HypnoFootballEntity.createHypnoFootballAttributes());
        EntityRendererRegistry.register(PvZEntity.HYPNOFOOTBALL, HypnoFootballEntityRenderer::new);


		FabricDefaultAttributeRegistry.register(PvZEntity.BERSERKER, BerserkerEntity.createBerserkerAttributes());
        EntityRendererRegistry.register(PvZEntity.BERSERKER, BerserkerEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.HYPNOBERSERKER, HypnoBerserkerEntity.createHypnoBerserkerAttributes());
        EntityRendererRegistry.register(PvZEntity.HYPNOBERSERKER, HypnoBerserkerEntityRenderer::new);


		FabricDefaultAttributeRegistry.register(PvZEntity.DANCINGZOMBIE, DancingZombieEntity.createDancingZombieAttributes());
        EntityRendererRegistry.register(PvZEntity.DANCINGZOMBIE, DancingZombieEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.HYPNODANCINGZOMBIE, HypnoDancingZombieEntity.createHypnoDancingZombieAttributes());
        EntityRendererRegistry.register(PvZEntity.HYPNODANCINGZOMBIE, HypnoDancingZombieEntityRenderer::new);


		FabricDefaultAttributeRegistry.register(PvZEntity.BACKUPDANCER, BackupDancerEntity.createBackupDancerAttributes());
        EntityRendererRegistry.register(PvZEntity.BACKUPDANCER, BackupDancerEntityRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.HYPNOBACKUPDANCER, HypnoBackupDancerEntity.createHypnoBackupDancerAttributes());
        EntityRendererRegistry.register(PvZEntity.HYPNOBACKUPDANCER, HypnoBackupDancerEntityRenderer::new);


        /////////////////////////////////////////////////////////////////////////////////////////////////

		FabricDefaultAttributeRegistry.register(PvZEntity.BASICGRAVESTONE, BasicGraveEntity.createBasicGraveAttributes());
        EntityRendererRegistry.register(PvZEntity.BASICGRAVESTONE, BasicGraveRenderer::new);

		FabricDefaultAttributeRegistry.register(PvZEntity.NIGHTGRAVESTONE, NightGraveEntity.createNightGraveAttributes());
        EntityRendererRegistry.register(PvZEntity.NIGHTGRAVESTONE, NightGraveRenderer::new);


    }
}
