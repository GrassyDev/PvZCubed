package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.registry.gravestones.renderers.BasicGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.gravestones.renderers.NightGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.hypnotizedzombies.renderers.*;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.chomper.ChomperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.doomshroom.DoomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.flamingpea.FlamingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.fumeshroom.FumeshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.gatlingpea.GatlingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.gravebuster.GravebusterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.hypnoshroom.HypnoshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.iceshroom.IceshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.plants.plantentity.repeater.RepeaterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.*;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.FumeEntityVariants.FumeEntityRenderer_G;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.FumeEntityVariants.FumeEntityRenderer_T;
import io.github.GrassyDev.pvzmod.registry.plants.renderers.*;
import io.github.GrassyDev.pvzmod.registry.zombies.renderers.*;
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

		EntityRendererRegistry.register(PvZEntity.UNARMEDPOTATOMINE, UnarmedPotatomineEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.SNOWPEA, SnowpeaEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.CHOMPER, ChomperEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.REPEATER, RepeaterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PUFFSHROOM, PuffshroomEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.SUNSHROOM, SunshroomEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.FUMESHROOM, FumeshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GRAVEBUSTER, GravebusterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HYPNOSHROOM, HypnoshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SCAREDYSHROOM, ScaredyshroomEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.ICESHROOM, IceshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DOOMSHROOM, DoomshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.THREEPEATER, ThreepeaterEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.GATLINGPEA, GatlingpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FLAMINGPEA, FlamingpeaEntityRenderer::new);

		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.PEA, ShootingPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWPEAPROJ, ShootingSnowPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FIREPEA, ShootingFlamingpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.REPEA, ShootingRePeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TRIPEA, ShootingTriPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPORE, SporeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUME, FumeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUME_G, FumeEntityRenderer_G::new);

		EntityRendererRegistry.register(PvZEntity.FUME_T, FumeEntityRenderer_T::new);


		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.BROWNCOAT, BrowncoatEntityRenderer ::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOBROWNCOAT, HypnoBrowncoatEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntityRenderer ::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOFLAGZOMBIE, HypnoFlagzombieEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.CONEHEAD, ConeheadEntityRenderer ::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOCONEHEAD, HypnoConeheadEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.POLEVAULTING, PoleVaultingEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.BUCKETHEAD, BucketheadEntityRenderer ::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOBUCKETHEAD, HypnoBucketheadEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.NEWSPAPER, NewspaperEntityRenderer ::new);
		EntityRendererRegistry.register(PvZEntity.HYPNONEWSPAPER, HypnoNewspaperEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.SCREEENDOOR, ScreendoorEntityRenderer ::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOSCREENDOOR, HypnoScreendoorEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.FOOTBALL, FootballEntityRenderer ::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOFOOTBALL, HypnoFootballEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.BERSERKER, BerserkerEntityRenderer ::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOBERSERKER, HypnoBerserkerEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.DANCINGZOMBIE, DancingZombieEntityRenderer ::new);
		EntityRendererRegistry.register(PvZEntity.HYPNODANCINGZOMBIE, HypnoDancingZombieEntityRenderer ::new);

		EntityRendererRegistry.register(PvZEntity.BACKUPDANCER, BackupDancerEntityRenderer ::new);
		EntityRendererRegistry.register(PvZEntity.HYPNOBACKUPDANCER, HypnoBackupDancerEntityRenderer ::new);

		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.BASICGRAVESTONE, BasicGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NIGHTGRAVESTONE, NightGraveRenderer::new);

	}
}
