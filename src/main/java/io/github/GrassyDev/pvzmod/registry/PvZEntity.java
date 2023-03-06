package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave.BasicGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave.NightGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave.NightGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.poolgrave.PoolGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.poolgrave.PoolGraveRenderer;
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
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.FireTrailEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.FireTrailEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.JalapenoEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.JalapenoEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilypadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.squash.SquashEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.squash.SquashEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tanglekelp.TangleKelpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tanglekelp.TangleKelpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater.ThreepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater.ThreepeaterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.cabbagepult.CabbagepultEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.cabbagepult.CabbagepultEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.cattail.CattailEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.cattail.CattailEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gatlingpea.GatlingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gatlingpea.GatlingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gloomshroom.GloomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gloomshroom.GloomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce.IcebergLettuceEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce.IcebergLettuceEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea.FlamingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea.FlamingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.coconutcannon.CoconutCannonEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.coconutcannon.CoconutCannonEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.peapod.PeapodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.peapod.PeapodEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.narcissus.NarcissusEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.narcissus.NarcissusEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beeshooter.BeeshooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beeshooter.BeeshooterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.snowqueenpea.SnowqueenpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.snowqueenpea.SnowqueenpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.dandelionweed.DandelionWeedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.dandelionweed.DandelionWeedEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.perfoomshroom.PerfoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.perfoomshroom.PerfoomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.admiralnavybean.AdmiralNavyBeanEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.admiralnavybean.AdmiralNavyBeanEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bellflower.BellflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bellflower.BellflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bombseedling.BombSeedlingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.bombseedling.BombSeedlingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.buttonshroom.ButtonshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.buttonshroom.ButtonshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.navybean.NavyBeanEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.navybean.NavyBeanEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smallnut.SmallNutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smallnut.SmallnutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.sunflowerseed.SunflowerSeedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.sunflowerseed.SunflowerSeedEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.weeniebeanie.WeenieBeanieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.weeniebeanie.WeenieBeanieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.armorbubble.ArmorBubbleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.armorbubble.ArmorBubbleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.beespike.ShootingBeeSpikeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.beespike.ShootingBeeSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.bubbles.BubbleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.bubbles.BubbleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.cabbage.ShootingCabbageEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.cabbage.ShootingCabbageEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.coconut.CoconutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.coconut.CoconutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.flamingpea.ShootingFlamingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.flamingpea.ShootingFlamingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.fume.FumeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.fume.FumeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.icespike.ShootingIceSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.icespike.ShootingIcespikeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.jingle.JingleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.jingle.JingleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pea.ShootingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pea.ShootingPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.plasmapea.ShootingPlasmaPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.plasmapea.ShootingPlasmapeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowpea.ShootingSnowPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowpea.ShootingSnowPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowqueenpea.ShootingSnowqueenPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowqueenpea.ShootingSnowqueenPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spike.ShootingSpikeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spike.ShootingSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spit.SpitEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spit.SpitEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spore.SporeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spore.SporeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.locustswarm.LocustSwarmEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.locustswarm.LocustswarmEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.backupdancer.BackupDancerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.backupdancer.BackupDancerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.conehead.modernday.ConeheadGearEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.conehead.modernday.ConeheadGearEntityRenderer;
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
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan.SuperFanImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan.SuperFanImpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper.NewspaperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper.NewspaperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.polevaulting.PoleVaultingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.polevaulting.PoleVaultingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet.MetalHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet.MetalHelmetEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicshield.MetalShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicshield.MetalShieldEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.papershield.NewspaperShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.papershield.NewspaperShieldEntityRenderer;
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

    public static final EntityType <FumeshroomEntity> FUMESHROOM = Registry.register(
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

	public static final EntityType<JalapenoEntity> JALAPENO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "jalapeno"),
			QuiltEntityTypeBuilder.<JalapenoEntity>create(SpawnGroup.CREATURE, JalapenoEntity::new).setDimensions(EntityDimensions.fixed(1f,1f)).build()
	);
	public static final EntityType<FireTrailEntity> FIRETRAIL = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "firetrail"),
			QuiltEntityTypeBuilder.<FireTrailEntity>create(SpawnGroup.CREATURE, FireTrailEntity::new).setDimensions(EntityDimensions.fixed(1f,0.8f)).build()
	);

	public static final EntityType<SpikeweedEntity> SPIKEWEED = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "spikeweed"),
			QuiltEntityTypeBuilder.<SpikeweedEntity>create(SpawnGroup.CREATURE, SpikeweedEntity::new).setDimensions(EntityDimensions.fixed(1f,0.125f)).build()
	);


	public static final EntityType<CabbagepultEntity> CABBAGEPULT = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "cabbagepult"),
			QuiltEntityTypeBuilder.<CabbagepultEntity>create(SpawnGroup.CREATURE, CabbagepultEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<GatlingpeaEntity> GATLINGPEA = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "gatlingpea"),
			QuiltEntityTypeBuilder.<GatlingpeaEntity>create(SpawnGroup.CREATURE, GatlingpeaEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<TwinSunflowerEntity> TWINSUNFLOWER = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "twinsunflower"),
			QuiltEntityTypeBuilder.<TwinSunflowerEntity>create(SpawnGroup.CREATURE, TwinSunflowerEntity::new).setDimensions(EntityDimensions.fixed(1f, 1.55f)).build()
	);

	public static final EntityType<GloomshroomEntity> GLOOMSHROOM = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "gloomshroom"),
			QuiltEntityTypeBuilder.<GloomshroomEntity>create(SpawnGroup.CREATURE, GloomshroomEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<CattailEntity> CATTAIL = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "cattail"),
			QuiltEntityTypeBuilder.<CattailEntity>create(SpawnGroup.CREATURE, CattailEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<SpikerockEntity> SPIKEROCK = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "spikerock"),
			QuiltEntityTypeBuilder.<SpikerockEntity>create(SpawnGroup.CREATURE, SpikerockEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.125f)).build()
	);

	public static final EntityType<BeeshooterEntity> BEESHOOTER = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "beeshooter"),
			QuiltEntityTypeBuilder.<BeeshooterEntity>create(SpawnGroup.CREATURE, BeeshooterEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<SnowqueenpeaEntity> SNOWQUEENPEA = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "snowqueenpea"),
			QuiltEntityTypeBuilder.<SnowqueenpeaEntity>create(SpawnGroup.CREATURE, SnowqueenpeaEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<DandelionWeedEntity> DANDELIONWEED = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "dandelionweed"),
			QuiltEntityTypeBuilder.<DandelionWeedEntity>create(SpawnGroup.CREATURE, DandelionWeedEntity::new).setDimensions(EntityDimensions.fixed(1f, 1.55f)).build()
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

	public static final EntityType<CoconutCannonEntity> COCONUTCANNON = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "coconutcannon"),
			QuiltEntityTypeBuilder.<CoconutCannonEntity>create(SpawnGroup.CREATURE, CoconutCannonEntity::new).setDimensions(EntityDimensions.fixed(2f, 1.8f)).build()
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

	public static final EntityType<NarcissusEntity> NARCISSUS = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "narcissus"),
			QuiltEntityTypeBuilder.<NarcissusEntity>create(SpawnGroup.CREATURE, NarcissusEntity::new).setDimensions(EntityDimensions.fixed(1f, 1.55f)).build()
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

	public static final EntityType<NavyBeanEntity> NAVYBEAN = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "navybean"),
			QuiltEntityTypeBuilder.<NavyBeanEntity>create(SpawnGroup.CREATURE, NavyBeanEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
	);

	public static final EntityType<AdmiralNavyBeanEntity> ADMIRALNAVYBEAN = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "admiralnavybean"),
			QuiltEntityTypeBuilder.<AdmiralNavyBeanEntity>create(SpawnGroup.CREATURE, AdmiralNavyBeanEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.8f)).build()
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

	public static final EntityType<SpitEntity> SPIT = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "spit"),
			QuiltEntityTypeBuilder.<SpitEntity>create(SpawnGroup.MISC, SpitEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
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

	public static final EntityType<CoconutEntity> COCONUTPROJ = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "coconutproj"),
			QuiltEntityTypeBuilder.<CoconutEntity>create(SpawnGroup.MISC, CoconutEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingSpikeEntity> SPIKEPROJ = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "spikeproj"),
			QuiltEntityTypeBuilder.<ShootingSpikeEntity>create(SpawnGroup.MISC, ShootingSpikeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingBeeSpikeEntity> BEESPIKE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "beespike"),
			QuiltEntityTypeBuilder.<ShootingBeeSpikeEntity>create(SpawnGroup.MISC, ShootingBeeSpikeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
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

	public static final EntityType<BubbleEntity> BUBBLE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "bubble"),
			QuiltEntityTypeBuilder.<BubbleEntity>create(SpawnGroup.MISC, BubbleEntity::new).setDimensions(EntityDimensions.fixed(1f,.5f)).build()
	);

	public static final EntityType<ArmorBubbleEntity> ARMORBUBBLE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "armorbubble"),
			QuiltEntityTypeBuilder.<ArmorBubbleEntity>create(SpawnGroup.MISC, ArmorBubbleEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
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
            QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
    );
	public static final EntityType<BrowncoatEntity> BROWNCOATHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "browncoat_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> CONEHEAD = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "conehead"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> CONEHEADHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "conehead_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> BUCKETHEAD = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "buckethead"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> BUCKETHEADHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "buckethead_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> SCREENDOOR = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "screendoor"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<BrowncoatEntity> SCREENDOORHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "screendoor_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<ConeheadGearEntity> CONEHEADGEAR = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "coneheadgear"),
			QuiltEntityTypeBuilder.<ConeheadGearEntity>create(SpawnGroup.MONSTER, ConeheadGearEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<MetalHelmetEntity> BUCKETGEAR = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "bucketheadgear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<MetalHelmetEntity> MEDALLIONGEAR = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "medalliongear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<MetalHelmetEntity> FOOTBALLGEAR = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "footballgear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<MetalHelmetEntity> BERSERKERGEAR = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "berserkergear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);

	public static final EntityType<MetalHelmetEntity> DEFENSIVEENDGEAR = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "defensiveendgear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(1.425f, 3.95f)).build()
	);
	public static final EntityType<MetalShieldEntity> SCREENDOORSHIELD = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "screendoorshield"),
			QuiltEntityTypeBuilder.<MetalShieldEntity>create(SpawnGroup.MONSTER, MetalShieldEntity::new).setDimensions(EntityDimensions.fixed(0.85f, 1.8f)).build()
	);

    public static final EntityType<FlagzombieEntity> FLAGZOMBIE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "flagzombie"),
            QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.MONSTER, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
	public static final EntityType<FlagzombieEntity> FLAGZOMBIE_G = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "flagzombie_g"),
			QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.MONSTER, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

	public static final EntityType<FlagzombieEntity> FLAGZOMBIE_T = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "flagzombie_t"),
			QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.MONSTER, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
    public static final EntityType<FlagzombieEntity> FLAGZOMBIEHYPNO = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "flagzombie_hypnotized"),
            QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.CREATURE, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
	public static final EntityType<FlagzombieEntity> FLAGZOMBIE_GHYPNO= Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "flagzombie_g_hypnotized"),
			QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.CREATURE, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<FlagzombieEntity> FLAGZOMBIE_THYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "flagzombie_t_hypnotized"),
			QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.CREATURE, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);


    public static final EntityType<PoleVaultingEntity> POLEVAULTING = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "polevaulting"),
            QuiltEntityTypeBuilder.<PoleVaultingEntity>create(SpawnGroup.MONSTER, PoleVaultingEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
	public static final EntityType<PoleVaultingEntity> POLEVAULTINGHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "polevaulting_hypnotized"),
			QuiltEntityTypeBuilder.<PoleVaultingEntity>create(SpawnGroup.MONSTER, PoleVaultingEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

    public static final EntityType<NewspaperEntity> NEWSPAPER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "newspaper"),
            QuiltEntityTypeBuilder.<NewspaperEntity>create(SpawnGroup.MONSTER, NewspaperEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
	public static final EntityType<NewspaperEntity> NEWSPAPERHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "newspaper_hypnotized"),
			QuiltEntityTypeBuilder.<NewspaperEntity>create(SpawnGroup.MONSTER, NewspaperEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<NewspaperShieldEntity> NEWSPAPERSHIELD = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "newspapershield"),
			QuiltEntityTypeBuilder.<NewspaperShieldEntity>create(SpawnGroup.MONSTER, NewspaperShieldEntity::new).setDimensions(EntityDimensions.fixed(0.85f, 1.8f)).build()
	);

	public static final EntityType<NewspaperEntity> SUNDAYEDITION = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "sundayedition"),
			QuiltEntityTypeBuilder.<NewspaperEntity>create(SpawnGroup.MONSTER, NewspaperEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<NewspaperEntity> SUNDAYEDITIONHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "sundayedition_hypnotized"),
			QuiltEntityTypeBuilder.<NewspaperEntity>create(SpawnGroup.MONSTER, NewspaperEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<NewspaperShieldEntity> SUNDAYEDITIONSHIELD = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "sundayeditionshield"),
			QuiltEntityTypeBuilder.<NewspaperShieldEntity>create(SpawnGroup.MONSTER, NewspaperShieldEntity::new).setDimensions(EntityDimensions.fixed(0.85f, 1.8f)).build()
	);

    public static final EntityType<FootballEntity> FOOTBALL = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "football"),
            QuiltEntityTypeBuilder.<FootballEntity>create(SpawnGroup.MONSTER, FootballEntity::new).setDimensions(EntityDimensions.fixed(0.6f, 1.85f)).build()
    );
	public static final EntityType<FootballEntity> BERSERKER = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "berserker"),
			QuiltEntityTypeBuilder.<FootballEntity>create(SpawnGroup.MONSTER, FootballEntity::new).setDimensions(EntityDimensions.fixed(0.6f, 1.85f)).build()
	);
	public static final EntityType<FootballEntity> FOOTBALLHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "football_hypnotized"),
			QuiltEntityTypeBuilder.<FootballEntity>create(SpawnGroup.MONSTER, FootballEntity::new).setDimensions(EntityDimensions.fixed(0.6f, 1.85f)).build()
	);
	public static final EntityType<FootballEntity> BERSERKERHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "berserker_hypnotized"),
			QuiltEntityTypeBuilder.<FootballEntity>create(SpawnGroup.MONSTER, FootballEntity::new).setDimensions(EntityDimensions.fixed(0.6f, 1.85f)).build()
	);

    public static final EntityType<DancingZombieEntity> DANCINGZOMBIE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "dancing_zombie"),
            QuiltEntityTypeBuilder.<DancingZombieEntity>create(SpawnGroup.MONSTER, DancingZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

	public static final EntityType<DancingZombieEntity> DANCINGZOMBIEHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "dancing_zombie_hypnotized"),
			QuiltEntityTypeBuilder.<DancingZombieEntity>create(SpawnGroup.MONSTER, DancingZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
    public static final EntityType<BackupDancerEntity> BACKUPDANCER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "backup_dancer"),
            QuiltEntityTypeBuilder.<BackupDancerEntity>create(SpawnGroup.MONSTER, BackupDancerEntity::new).setDimensions(EntityDimensions.fixed(0.6f, 1.85f)).build()
    );
    public static final EntityType<BackupDancerEntity> BACKUPDANCERHYPNO = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ModID, "backup_dancer_hypnotized"),
            QuiltEntityTypeBuilder.<BackupDancerEntity>create(SpawnGroup.CREATURE, BackupDancerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

	public static final EntityType<SnorkelEntity> SNORKEL = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "snorkel"),
			QuiltEntityTypeBuilder.<SnorkelEntity>create(SpawnGroup.MONSTER, SnorkelEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<SnorkelEntity> SNORKELHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "snorkel_hypnotized"),
			QuiltEntityTypeBuilder.<SnorkelEntity>create(SpawnGroup.MONSTER, SnorkelEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

	public static final EntityType<GargantuarEntity> GARGANTUAR = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "gargantuar"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.25f, 3.95f)).build()
	);

	public static final EntityType<GargantuarEntity> GARGANTUARHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "gargantuar_hypnotized"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.CREATURE, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.25f, 3.95f)).build()
	);

	public static final EntityType<GargantuarEntity> DEFENSIVEEND = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "defensiveend"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.225f, 3.85f)).build()
	);

	public static final EntityType<GargantuarEntity> DEFENSIVEENDHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "defensiveend_hypnotized"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.225f, 3.85f)).build()
	);

	public static final EntityType<GargantuarEntity> DEFENSIVEEND_NEWYEAR = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "defensiveend_newyear"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.225f, 3.85f)).build()
	);

	public static final EntityType<GargantuarEntity> DEFENSIVEEND_NEWYEARHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "defensiveend_newyear_hypnotized"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.225f, 3.85f)).build()
	);

	public static final EntityType<ImpEntity> IMP = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "imp"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.MONSTER, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 0.95f)).build()
	);

	public static final EntityType<ImpEntity> IMPHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "imp_hypnotized"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.CREATURE, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 0.95f)).build()
	);

	public static final EntityType<SuperFanImpEntity> SUPERFANIMP = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "superfanimp"),
			QuiltEntityTypeBuilder.<SuperFanImpEntity>create(SpawnGroup.MONSTER, SuperFanImpEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 0.95f)).build()
	);

	public static final EntityType<SuperFanImpEntity> SUPERFANIMPHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "superfanimp_hypnotized"),
			QuiltEntityTypeBuilder.<SuperFanImpEntity>create(SpawnGroup.MONSTER, SuperFanImpEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 0.95f)).build()
	);

	public static final EntityType<SuperFanImpEntity> NEWYEARIMP = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "newyearimp"),
			QuiltEntityTypeBuilder.<SuperFanImpEntity>create(SpawnGroup.MONSTER, SuperFanImpEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 0.95f)).build()
	);

	public static final EntityType<SuperFanImpEntity> NEWYEARIMPHYPNO = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(ModID, "newyearimp_hypnotized"),
			QuiltEntityTypeBuilder.<SuperFanImpEntity>create(SpawnGroup.MONSTER, SuperFanImpEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 0.95f)).build()
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

		EntityRendererRegistry.register(PvZEntity.PEA, ShootingPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPIT, SpitEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWPEAPROJ, ShootingSnowPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWQUEENPEAPROJ, ShootingSnowqueenPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPIKEPROJ, ShootingSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BEESPIKE, ShootingBeeSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICESPIKEPROJ, ShootingIceSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FIREPEA, ShootingFlamingpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PLASMAPEA, ShootingPlasmapeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPORE, SporeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUME, FumeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CABBAGE, ShootingCabbageEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.COCONUTPROJ, CoconutEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BUBBLE, BubbleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ARMORBUBBLE, ArmorBubbleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.JINGLE, JingleEntityRenderer::new);

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

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.JALAPENO, JalapenoEntity.createJalapenoAttributes().build());
		EntityRendererRegistry.register(PvZEntity.JALAPENO, JalapenoEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FIRETRAIL, FireTrailEntity.createFireTrailAttributes().build());
		EntityRendererRegistry.register(PvZEntity.FIRETRAIL, FireTrailEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SPIKEWEED, SpikeweedEntity.createSpikeweedAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SPIKEWEED, SpikeweedEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CABBAGEPULT, CabbagepultEntity.createCabbagePultAttributes().build());
		EntityRendererRegistry.register(PvZEntity.CABBAGEPULT, CabbagepultEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GATLINGPEA, GatlingpeaEntity.createGatlingpeaAttributes().build());
		EntityRendererRegistry.register(PvZEntity.GATLINGPEA, GatlingpeaEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TWINSUNFLOWER, TwinSunflowerEntity.createTwinSunflowerAttributes().build());
		EntityRendererRegistry.register(PvZEntity.TWINSUNFLOWER, TwinSunflowerEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GLOOMSHROOM, GloomshroomEntity.createGloomshroomAttributes().build());
		EntityRendererRegistry.register(PvZEntity.GLOOMSHROOM, GloomshroomEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CATTAIL, CattailEntity.createCattailAttributes().build());
		EntityRendererRegistry.register(PvZEntity.CATTAIL, CattailEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SPIKEROCK, SpikerockEntity.createSpikerockAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SPIKEROCK, SpikerockEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BEESHOOTER, BeeshooterEntity.createBeeshooterAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BEESHOOTER, BeeshooterEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SNOWQUEENPEA, SnowqueenpeaEntity.createSnowqueenpeaAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SNOWQUEENPEA, SnowqueenpeaEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DANDELIONWEED, DandelionWeedEntity.createDandelionWeedAttributes().build());
		EntityRendererRegistry.register(PvZEntity.DANDELIONWEED, DandelionWeedEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PERFOOMSHROOM, PerfoomshroomEntity.createPerfoomshroomAttributes().build());
		EntityRendererRegistry.register(PvZEntity.PERFOOMSHROOM, PerfoomshroomEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ICEBERGLETTUCE, IcebergLettuceEntity.createIcebergLettuceAttributes().build());
		EntityRendererRegistry.register(PvZEntity.ICEBERGLETTUCE, IcebergLettuceEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.COCONUTCANNON, CoconutCannonEntity.createCoconutCannonAttributes().build());
		EntityRendererRegistry.register(PvZEntity.COCONUTCANNON, CoconutCannonEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEAPOD, PeapodEntity.createPeapodAttributes().build());
		EntityRendererRegistry.register(PvZEntity.PEAPOD, PeapodEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAMINGPEA, FlamingpeaEntity.createFlamingpeaAttributes().build());
        EntityRendererRegistry.register(PvZEntity.FLAMINGPEA, FlamingpeaEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NARCISSUS, NarcissusEntity.createNarcissusAttributes().build());
		EntityRendererRegistry.register(PvZEntity.NARCISSUS, NarcissusEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SMALLNUT, SmallNutEntity.createSmallnutAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SMALLNUT, SmallnutEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BUTTONSHROOM, ButtonshroomEntity.createButtonshroomAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BUTTONSHROOM, ButtonshroomEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BOMBSEEDLING, BombSeedlingEntity.createBombSeedlingAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BOMBSEEDLING, BombSeedlingEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.WEENIEBEANIE, WeenieBeanieEntity.createWeenieBeanieAttributes().build());
		EntityRendererRegistry.register(PvZEntity.WEENIEBEANIE, WeenieBeanieEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NAVYBEAN, NavyBeanEntity.createNavyBeanAttributes().build());
		EntityRendererRegistry.register(PvZEntity.NAVYBEAN, NavyBeanEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ADMIRALNAVYBEAN, AdmiralNavyBeanEntity.createAdmiralNavyBeanAttributes().build());
		EntityRendererRegistry.register(PvZEntity.ADMIRALNAVYBEAN, AdmiralNavyBeanEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNFLOWERSEED, SunflowerSeedEntity.createSunflowerSeedAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SUNFLOWERSEED, SunflowerSeedEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BELLFLOWER, BellflowerEntity.createBellflowerAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BELLFLOWER, BellflowerEntityRenderer::new);


        /////////////////////////////////////////////////////////////////////////////////////////////////

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BROWNCOAT, BrowncoatEntity.createBrowncoatAttributes().build());
        EntityRendererRegistry.register(PvZEntity.BROWNCOAT, BrowncoatEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BROWNCOATHYPNO, BrowncoatEntity.createBrowncoatAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BROWNCOATHYPNO, BrowncoatEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CONEHEAD, BrowncoatEntity.createBrowncoatAttributes().build());
		EntityRendererRegistry.register(PvZEntity.CONEHEAD, BrowncoatEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CONEHEADHYPNO, BrowncoatEntity.createBrowncoatAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BROWNCOATHYPNO, BrowncoatEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BUCKETHEAD, BrowncoatEntity.createBrowncoatAttributes().build());
		EntityRendererRegistry.register(PvZEntity.CONEHEAD, BrowncoatEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BUCKETHEADHYPNO, BrowncoatEntity.createBrowncoatAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BROWNCOATHYPNO, BrowncoatEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCREENDOOR, BrowncoatEntity.createBrowncoatAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SCREENDOOR, BrowncoatEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCREENDOORHYPNO, BrowncoatEntity.createBrowncoatAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BROWNCOATHYPNO, BrowncoatEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIE, FlagzombieEntity.createFlagzombieZombieAttributes().build());
        EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIEHYPNO, FlagzombieEntity.createFlagzombieZombieAttributes().build());
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIE_G, FlagzombieEntity.createFlagzombieZombieAttributes().build());
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIE_T, FlagzombieEntity.createFlagzombieZombieAttributes().build());
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIE_GHYPNO, FlagzombieEntity.createFlagzombieZombieAttributes().build());
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIE_THYPNO, FlagzombieEntity.createFlagzombieZombieAttributes().build());
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CONEHEADGEAR, ConeheadGearEntity.createConeheadGearAttributes().build());
		EntityRendererRegistry.register(PvZEntity.CONEHEADGEAR, ConeheadGearEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POLEVAULTING, PoleVaultingEntity.createPoleVaultingAttributes().build());
        EntityRendererRegistry.register(PvZEntity.POLEVAULTING, PoleVaultingEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POLEVAULTINGHYPNO, PoleVaultingEntity.createPoleVaultingAttributes().build());
		EntityRendererRegistry.register(PvZEntity.POLEVAULTING, PoleVaultingEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BUCKETGEAR, MetalHelmetEntity.createBucketGearAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BUCKETGEAR, MetalHelmetEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MEDALLIONGEAR, MetalHelmetEntity.createMedallionGearAttributes().build());
		EntityRendererRegistry.register(PvZEntity.MEDALLIONGEAR, MetalHelmetEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FOOTBALLGEAR, MetalHelmetEntity.createFootballGearAttributes().build());
		EntityRendererRegistry.register(PvZEntity.FOOTBALLGEAR, MetalHelmetEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BERSERKERGEAR, MetalHelmetEntity.createBerserkerGearAttributes().build());
		EntityRendererRegistry.register(PvZEntity.BERSERKERGEAR, MetalHelmetEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DEFENSIVEENDGEAR, MetalHelmetEntity.createDefensiveEndGearAttributes().build());
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEENDGEAR, MetalHelmetEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NEWSPAPER, NewspaperEntity.createNewspaperAttributes().build());
        EntityRendererRegistry.register(PvZEntity.NEWSPAPER, NewspaperEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NEWSPAPERHYPNO, NewspaperEntity.createNewspaperAttributes().build());
		EntityRendererRegistry.register(PvZEntity.NEWSPAPER, NewspaperEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NEWSPAPERSHIELD, NewspaperShieldEntity.createNewspaperShieldAttributes().build());
		EntityRendererRegistry.register(PvZEntity.NEWSPAPERSHIELD, NewspaperShieldEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNDAYEDITION, NewspaperEntity.createSundayEditionAttributes().build());
		EntityRendererRegistry.register(PvZEntity.NEWSPAPER, NewspaperEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNDAYEDITIONHYPNO, NewspaperEntity.createSundayEditionAttributes().build());
		EntityRendererRegistry.register(PvZEntity.NEWSPAPER, NewspaperEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNDAYEDITIONSHIELD, NewspaperShieldEntity.createSundayEditionShieldAttributes().build());
		EntityRendererRegistry.register(PvZEntity.NEWSPAPERSHIELD, NewspaperShieldEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCREENDOORSHIELD, MetalShieldEntity.createScreendoorShieldAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SCREENDOORSHIELD, MetalShieldEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FOOTBALL, FootballEntity.createFootballAttributes().build());
        EntityRendererRegistry.register(PvZEntity.FOOTBALL, FootballEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FOOTBALLHYPNO, FootballEntity.createFootballAttributes().build());
		EntityRendererRegistry.register(PvZEntity.FOOTBALL, FootballEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BERSERKER, FootballEntity.createBerserkerAttributes().build());
		EntityRendererRegistry.register(PvZEntity.FOOTBALL, FootballEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BERSERKERHYPNO, FootballEntity.createBerserkerAttributes().build());
		EntityRendererRegistry.register(PvZEntity.FOOTBALL, FootballEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DANCINGZOMBIE, DancingZombieEntity.createDancingZombieAttributes().build());
        EntityRendererRegistry.register(PvZEntity.DANCINGZOMBIE, DancingZombieEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DANCINGZOMBIEHYPNO, DancingZombieEntity.createDancingZombieAttributes().build());
		EntityRendererRegistry.register(PvZEntity.DANCINGZOMBIE, DancingZombieEntityRenderer::new);


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BACKUPDANCER, BackupDancerEntity.createBackupDancerAttributes().build());
        EntityRendererRegistry.register(PvZEntity.BACKUPDANCER, BackupDancerEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BACKUPDANCERHYPNO, BackupDancerEntity.createBackupDancerAttributes().build());
        EntityRendererRegistry.register(PvZEntity.BACKUPDANCERHYPNO, BackupDancerEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SNORKEL, SnorkelEntity.createSnorkelAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SNORKEL, SnorkelEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SNORKELHYPNO, SnorkelEntity.createSnorkelAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SNORKELHYPNO, SnorkelEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GARGANTUAR, GargantuarEntity.createGargantuarAttributes().build());
		EntityRendererRegistry.register(PvZEntity.GARGANTUAR, GargantuarEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GARGANTUARHYPNO, GargantuarEntity.createGargantuarAttributes().build());
		EntityRendererRegistry.register(PvZEntity.GARGANTUARHYPNO, GargantuarEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DEFENSIVEEND, GargantuarEntity.createGargantuarAttributes().build());
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEEND, GargantuarEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DEFENSIVEENDHYPNO, GargantuarEntity.createGargantuarAttributes().build());
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEENDHYPNO, GargantuarEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DEFENSIVEEND_NEWYEAR, GargantuarEntity.createGargantuarAttributes().build());
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEEND, GargantuarEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DEFENSIVEEND_NEWYEARHYPNO, GargantuarEntity.createGargantuarAttributes().build());
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEENDHYPNO, GargantuarEntityRenderer::new);

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.IMP, ImpEntity.createImpAttributes().build());
		EntityRendererRegistry.register(PvZEntity.IMP, ImpEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.IMPHYPNO, ImpEntity.createImpAttributes().build());
		EntityRendererRegistry.register(PvZEntity.IMPHYPNO, ImpEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUPERFANIMP, SuperFanImpEntity.createImpAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SUPERFANIMP, SuperFanImpEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUPERFANIMPHYPNO, SuperFanImpEntity.createImpAttributes().build());
		EntityRendererRegistry.register(PvZEntity.SUPERFANIMPHYPNO, SuperFanImpEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NEWYEARIMP, SuperFanImpEntity.createImpAttributes().build());
		EntityRendererRegistry.register(PvZEntity.NEWYEARIMP, SuperFanImpEntityRenderer::new);
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NEWYEARIMPHYPNO, SuperFanImpEntity.createImpAttributes().build());
		EntityRendererRegistry.register(PvZEntity.NEWYEARIMPHYPNO, SuperFanImpEntityRenderer::new);

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
