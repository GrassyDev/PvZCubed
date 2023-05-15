package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.registry.entity.environment.cratertile.CraterTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.goldtile.GoldTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.icetile.IceTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile.ScorchedTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave.BasicGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.darkagesgrave.DarkAgesGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.egyptgravestone.EgyptGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.futuregrave.FutureGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave.NightGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.poolgrave.PoolGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.roofgrave.RoofGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.GardenEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.cherrybomb.CherrybombEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper.ChomperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.peashooter.PeashooterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.potatomine.PotatomineEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.repeater.RepeaterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.snowpea.SnowpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.wallnutentity.WallnutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.fog.seashroom.SeashroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.doomshroom.DoomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom.FumeshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.hypnoshroom.HypnoshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.iceshroom.IceshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom.PuffshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.scaredyshroom.ScaredyshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.FireTrailEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.JalapenoEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilypadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.squash.SquashEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tallnut.TallnutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tanglekelp.TangleKelpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater.ThreepeaterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.torchwood.TorchwoodEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.cabbagepult.CabbagepultEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.scrapped.icebergpult.IcebergpultEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.cattail.CattailEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gatlingpea.GatlingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gloomshroom.GloomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.breezeshroom.BreezeshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.bloomerang.BloomerangEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce.IcebergLettuceEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.farfuture.empeach.EMPeachEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.frostbitecaves.pepperpult.PepperpultEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea.FlamingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.lostcity.goldleaf.GoldLeafEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.coconutcannon.CoconutCannonEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.springbean.SpringbeanEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.lightningreed.LightningReedEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.peapod.PeapodEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.beautyshroom.BeautyshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.charmshroom.CharmshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.dropea.DropeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.magicshroom.MagichatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.magicshroom.MagicshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.narcissus.NarcissusEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.tulimpeter.TulimpeterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.loquat.LoquatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.saucer.SaucerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beeshooter.BeeshooterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.chillypepper.ChillyPepperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.shamrock.ShamrockEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.snowqueenpea.SnowqueenpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.acidshroom.AcidshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.dandelionweed.DandelionWeedEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.perfoomshroom.PerfoomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.guardian.smackadamia.SmackadamiaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.guardian.smallnut.SmallnutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.bombseedling.BombSeedlingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.buttonshroom.ButtonshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.zapricot.ZapricotEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.admiralnavybean.AdmiralNavyBeanEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.jumpingbean.JumpingBeanEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.navybean.NavyBeanEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.weeniebeanie.WeenieBeanieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.bellflower.BellflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.sunflowerseed.SunflowerSeedEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.acidfume.AcidFumeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.acidspore.AcidSporeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.armorbubble.ArmorBubbleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.beespike.ShootingBeeSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.beespike.ShootingPowerBeeSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.boomerang.ShootingBoomerangEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.breeze.BreezeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.bubbles.BubbleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.cabbage.ShootingCabbageEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.card.ShootingCardEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.coconut.CoconutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.dropea.ShootingDropEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.flamingpea.ShootingFlamingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.fume.FumeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.groundbounce.GroundBounceEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.hypnoproj.HypnoProjRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.iceberg.ShootingIcebergEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.icespike.ShootingIceSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.icespike.ShootingPowerIceSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.jingle.JingleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pea.ShootingPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pepper.ShootingPepperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.plasmapea.ShootingPlasmapeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.rainbowbullet.RainbowBulletEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowpea.ShootingSnowPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowqueenpea.ShootingSnowqueenPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spike.ShootingPowerSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spike.ShootingSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spit.SpitEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spore.SporeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.ShootingBasketballEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.locustswarm.LocustswarmEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.backupdancer.BackupDancerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.darkages.PeasantEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.mummy.MummyEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.bully.basic.BullyEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.bully.basketballcarrier.BasketballCarrierEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dancingzombie.DancingZombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dolphinrider.DolphinRiderEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.explorer.ExplorerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.darkages.FlagPeasantEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.mummy.FlagMummyEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football.FootballEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.announcer.AnnouncerImpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.superfan.SuperFanImpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.jetpack.JetpackEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper.NewspaperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pharaoh.PharaohEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.polevaulting.PoleVaultingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.snorkel.SnorkelEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.zombieking.ZombieKingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiegrave.ZombieGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.robocone.RoboConeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet.MetalHelmetEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicobstacle.MetalObstacleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicshield.MetalShieldEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.papershield.NewspaperShieldEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plastichelmet.PlasticHelmetEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.stonehelmet.StoneHelmetEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;


@Environment(EnvType.CLIENT)
public class PvZEntityClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {

		EntityRendererRegistry.register(PvZEntity.GARDEN, GardenEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEASHOOTER, PeashooterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUNFLOWER, SunflowerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CHERRYBOMB, CherrybombEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.WALLNUT, WallnutEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POTATOMINE, PotatomineEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWPEA, SnowpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CHOMPER, ChomperEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.REPEATER, RepeaterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PUFFSHROOM, PuffshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUNSHROOM, SunshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUMESHROOM, FumeshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BREEZESHROOM, BreezeshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GRAVEBUSTER, GravebusterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HYPNOSHROOM, HypnoshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SCAREDYSHROOM, ScaredyshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICESHROOM, IceshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DOOMSHROOM, DoomshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.LILYPAD, LilypadEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SQUASH, SquashEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.THREEPEATER, ThreepeaterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TANGLE_KELP, TangleKelpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.JALAPENO, JalapenoEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FIRETRAIL, FireTrailEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPIKEWEED, SpikeweedEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TORCHWOOD, TorchwoodEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TALLNUT, TallnutEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SEASHROOM, SeashroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CABBAGEPULT, CabbagepultEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GATLINGPEA, GatlingpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TWINSUNFLOWER, TwinSunflowerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GLOOMSHROOM, GloomshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CATTAIL, CattailEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPIKEROCK, SpikerockEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICEBERGPULT, IcebergpultEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SHAMROCK, ShamrockEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CHILLYPEPPER, ChillyPepperEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BEESHOOTER, BeeshooterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWQUEENPEA, SnowqueenpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ACIDSHROOM, AcidshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DANDELIONWEED, DandelionWeedEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PERFOOMSHROOM, PerfoomshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BLOOMERANG, BloomerangEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICEBERGLETTUCE, IcebergLettuceEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPRINGBEAN, SpringbeanEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.COCONUTCANNON, CoconutCannonEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.LIGHTNINGREED, LightningReedEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEAPOD, PeapodEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.EMPEACH, EMPeachEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEPPERPULT, PepperpultEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FLAMINGPEA, FlamingpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GOLDLEAF, GoldLeafEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MAGICSHROOM, MagicshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MAGICHAT, MagichatEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.LOQUAT, LoquatEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SAUCER, SaucerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TULIMPETER, TulimpeterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NARCISSUS, NarcissusEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DROPEA, DropeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BEAUTYSHROOM, BeautyshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CHARMSHROOM, CharmshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SMALLNUT, SmallnutEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SMACKADAMIA, SmackadamiaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BUTTONSHROOM, ButtonshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BOMBSEEDLING, BombSeedlingEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ZAPRICOT, ZapricotEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.WEENIEBEANIE, WeenieBeanieEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NAVYBEAN, NavyBeanEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ADMIRALNAVYBEAN, AdmiralNavyBeanEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.JUMPINGBEAN, JumpingBeanEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUNFLOWERSEED, SunflowerSeedEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BELLFLOWER, BellflowerEntityRenderer::new);


		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.PEA, ShootingPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPIT, SpitEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWPEAPROJ, ShootingSnowPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWQUEENPEAPROJ, ShootingSnowqueenPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPIKEPROJ, ShootingSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POWERSPIKE, ShootingPowerSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POWERICESPIKE, ShootingPowerIceSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POWERBEESPIKE, ShootingPowerBeeSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.RAINBOWBULLET, RainbowBulletEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BEESPIKE, ShootingBeeSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICESPIKEPROJ, ShootingIceSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FIREPEA, ShootingFlamingpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PLASMAPEA, ShootingPlasmapeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPORE, SporeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ACIDSPORE, AcidSporeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUME, FumeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BREEZE, BreezeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ACIDFUME, AcidFumeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CABBAGE, ShootingCabbageEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICEBERG, ShootingIcebergEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BOOMERANGPROJ, ShootingBoomerangEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CARDPROJ, ShootingCardEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.COCONUTPROJ, CoconutEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEPPERPROJ, ShootingPepperEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HYPNOPROJ, HypnoProjRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BUBBLE, BubbleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ARMORBUBBLE, ArmorBubbleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DROP, ShootingDropEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GROUNDBOUNCE, GroundBounceEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.JINGLE, JingleEntityRenderer::new);


		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.BROWNCOAT, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BROWNCOATHYPNO, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.CONEHEAD, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.CONEHEADHYPNO, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BUCKETHEAD, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BUCKETHEADHYPNO, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BRICKHEAD, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BRICKHEADHYPNO, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SCREENDOOR, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SCREENDOORHYPNO, BrowncoatEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIEHYPNO, FlagzombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE_T, FlagzombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE_THYPNO, FlagzombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE_G, FlagzombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE_GHYPNO, FlagzombieEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CONEHEADGEAR, PlasticHelmetEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POLEVAULTING, PoleVaultingEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POLEVAULTINGHYPNO, PoleVaultingEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BUCKETGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MEDALLIONGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BERSERKERGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FOOTBALLGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEENDGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BLASTRONAUTGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.KNIGHTGEAR, MetalHelmetEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TOWERGEAR, StoneHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BRICKGEAR, StoneHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PYRAMIDGEAR, StoneHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SARCOPHAGUS, StoneHelmetEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NEWSPAPER, NewspaperEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.NEWSPAPERHYPNO, NewspaperEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.NEWSPAPERSHIELD, NewspaperShieldEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUNDAYEDITION, NewspaperEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SUNDAYEDITIONHYPNO, NewspaperEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SUNDAYEDITIONSHIELD, NewspaperShieldEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SCREENDOORSHIELD, MetalShieldEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FOOTBALL, FootballEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BERSERKER, FootballEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FOOTBALLHYPNO, FootballEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BERSERKERHYPNO, FootballEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TRASHCAN, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.TRASHCANHYPNO, BrowncoatEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TRASHCANBIN, MetalObstacleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DANCINGZOMBIE, DancingZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.DANCINGZOMBIEHYPNO, DancingZombieEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BACKUPDANCER, BackupDancerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BACKUPDANCERHYPNO, BackupDancerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNORKEL, SnorkelEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SNORKELHYPNO, SnorkelEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DOLPHINRIDER, DolphinRiderEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.DOLPHINRIDERHYPNO, DolphinRiderEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GARGANTUAR, GargantuarEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.GARGANTUARHYPNO, GargantuarEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.IMP, ImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.IMPHYPNO, ImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.IMPDRAGON, ImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.IMPDRAGONHYPNO, ImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MUMMY, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MUMMYHYPNO, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGMUMMY, FlagMummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGMUMMYHYPNO, FlagMummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MUMMYCONE, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MUMMYCONEHYPNO, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MUMMYBUCKET, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MUMMYBUCKETHYPNO, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PYRAMIDHEAD, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PYRAMIDHEADHYPNO, MummyEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.UNDYINGPHARAOH, PharaohEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.UNDYINGPHARAOHHYPNO, PharaohEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PHARAOH, PharaohEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PHARAOHHYPNO, PharaohEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.EXPLORER, ExplorerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.EXPLORERHYPNO, ExplorerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TORCHLIGHT, ExplorerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.TORCHLIGHTHYPNO, ExplorerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.JETPACK, JetpackEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.JETPACKHYPNO, JetpackEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BLASTRONAUT, JetpackEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BLASTRONAUTHYPNO, JetpackEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ROBOCONE, RoboConeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEASANT, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTHYPNO, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGPEASANT, FlagPeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGPEASANTHYPNO, FlagPeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTCONE, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTCONEHYPNO, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTBUCKET, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTBUCKETHYPNO, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTKNIGHT, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTKNIGHTHYPNO, PeasantEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ANNOUNCERIMP, AnnouncerImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.ANNOUNCERIMPHYPNO, AnnouncerImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ZOMBIEKING, ZombieKingEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.ZOMBIEKINGHYPNO, ZombieKingEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUPERFANIMP, SuperFanImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SUPERFANIMPHYPNO, SuperFanImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NEWYEARIMP, SuperFanImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.NEWYEARIMPHYPNO, SuperFanImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DEFENSIVEEND, GargantuarEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEENDHYPNO, GargantuarEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEEND_NEWYEAR, GargantuarEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEEND_NEWYEARHYPNO, GargantuarEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BULLY, BullyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BULLYHYPNO, BullyEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BASKETBALLCARRIER, BasketballCarrierEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BASKETBALLCARRIERHYPNO, BasketballCarrierEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BASKETBALLBIN, MetalObstacleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BASKETBALLPROJ, ShootingBasketballEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.LOCUSTSWARM, LocustswarmEntityRenderer::new);

		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.SCORCHEDTILE, ScorchedTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICETILE, IceTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CRATERTILE, CraterTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GOLDTILE, GoldTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ZOMBIEGRAVESTONE, ZombieGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BASICGRAVESTONE, BasicGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NIGHTGRAVESTONE, NightGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POOLGRAVESTONE, PoolGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ROOFGRAVESTONE, RoofGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.EGYPTGRAVE, EgyptGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUTUREGRAVE, FutureGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DARKAGESGRAVESTONE, DarkAgesGraveRenderer::new);


	}
}
