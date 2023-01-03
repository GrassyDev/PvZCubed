package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.basicgrave.BasicGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.gravestoneentity.nightgrave.NightGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.backupdancer.HypnoBackupDancerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.berserker.HypnoBerserkerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.browncoat.modernday.HypnoBrowncoatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.buckethead.modernday.HypnoBucketheadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.conehead.modernday.HypnoConeheadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.dancingzombie.HypnoDancingZombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.flagzombie.modernday.HypnoFlagzombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.football.HypnoFootballEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.gargantuar.modernday.HypnoGargantuarEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.imp.modernday.HypnoImpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.newspaper.HypnoNewspaperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.hypnotizedzombies.hypnotizedentity.screendoor.HypnoScreendoorEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.chomper.ChomperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.doomshroom.DoomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.flamingpea.FlamingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.fumeshroom.FumeshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.gatlingpea.GatlingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.gravebuster.GravebusterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.hypnoshroom.HypnoshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.iceshroom.IceshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.lilypad.LilypadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.peapod.PeapodEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.peashooter.PeashooterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.perfoomshroom.PerfoomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.potatomine.PotatomineEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.puffshroom.PuffshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.repeater.RepeaterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.scaredyshroom.ScaredyshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.snowpea.SnowpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.snowqueenpea.SnowqueenpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.squash.SquashEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.sunflower.SunflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.sunshroom.SunshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.tanglekelp.TangleKelpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.threepeater.ThreepeaterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.twinsunflower.TwinSunflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.wallnutentity.WallnutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.firepea.ShootingFlamingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.fume.FumeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.icespike.ShootingIceSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pea.ShootingPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.plasmapea.ShootingPlasmapeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowpea.ShootingSnowPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.snowqueenpea.ShootingSnowqueenPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spore.SporeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.duckytube.DuckyTubeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.backupdancer.BackupDancerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.berserker.BerserkerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.browncoat.modernday.BrowncoatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.buckethead.modernday.BucketheadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.conehead.modernday.ConeheadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.dancingzombie.DancingZombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.flagzombie.modernday.FlagzombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.football.FootballEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.gargantuar.modernday.GargantuarEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.imp.modernday.ImpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.newspaper.NewspaperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.polevaulting.PoleVaultingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.screendoor.ScreendoorEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;


@Environment(EnvType.CLIENT)
public class PvZEntityClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {

		EntityRendererRegistry.register(PvZEntity.PEASHOOTER, PeashooterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUNFLOWER, SunflowerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.WALLNUT, WallnutEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POTATOMINE, PotatomineEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWPEA, SnowpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CHOMPER, ChomperEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.REPEATER, RepeaterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PUFFSHROOM, PuffshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUNSHROOM, SunshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUMESHROOM, FumeshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GRAVEBUSTER, GravebusterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HYPNOSHROOM, HypnoshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SCAREDYSHROOM, ScaredyshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICESHROOM, IceshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DOOMSHROOM, DoomshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.LILYPAD, LilypadEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SQUASH, SquashEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.THREEPEATER, ThreepeaterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TANGLE_KELP, TangleKelpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GATLINGPEA, GatlingpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TWINSUNFLOWER, TwinSunflowerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWQUEENPEA, SnowqueenpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PERFOOMSHROOM, PerfoomshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEAPOD, PeapodEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FLAMINGPEA, FlamingpeaEntityRenderer::new);

		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.PEA, ShootingPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWPEAPROJ, ShootingSnowPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWQUEENPEAPROJ, ShootingSnowqueenPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICESPIKEPROJ, ShootingIceSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FIREPEA, ShootingFlamingpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PLASMAPEA, ShootingPlasmapeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPORE, SporeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUME, FumeEntityRenderer::new);


		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.BROWNCOAT, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOBROWNCOAT, HypnoBrowncoatEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOFLAGZOMBIE, HypnoFlagzombieEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CONEHEAD, ConeheadEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOCONEHEAD, HypnoConeheadEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POLEVAULTING, PoleVaultingEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BUCKETHEAD, BucketheadEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOBUCKETHEAD, HypnoBucketheadEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NEWSPAPER, NewspaperEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HYPNONEWSPAPER, HypnoNewspaperEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SCREEENDOOR, ScreendoorEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOSCREENDOOR, HypnoScreendoorEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FOOTBALL, FootballEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOFOOTBALL, HypnoFootballEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DANCINGZOMBIE, DancingZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HYPNODANCINGZOMBIE, HypnoDancingZombieEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BACKUPDANCER, BackupDancerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOBACKUPDANCER, HypnoBackupDancerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DUCKYTUBE, DuckyTubeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GARGANTUAR, GargantuarEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOGARGANTUAR, HypnoGargantuarEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.IMP, ImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOIMP, HypnoImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BERSERKER, BerserkerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOBERSERKER, HypnoBerserkerEntityRenderer::new);

		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.BASICGRAVESTONE, BasicGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NIGHTGRAVESTONE, NightGraveRenderer::new);

	}
}
