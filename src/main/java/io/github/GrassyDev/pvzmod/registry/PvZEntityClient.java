package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.registry.gravestones.renderers.BasicGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.gravestones.renderers.NightGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.plants.projectileentity.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.registry.gravestones.renderers.BasicGraveRenderer;
import net.fabricmc.example.registry.gravestones.renderers.NightGraveRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.entity.networking.impl.QuiltEntityNetworkingInitializer;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class PvZEntityClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		/*
		 * Registers our Cube Entity's renderer, which provides a model and texture for the entity.
		 *
		 * Entity Renderers can also manipulate the model before it renders based on entity context (EndermanEntityRenderer#render).
		 */
		EntityRendererRegistry.register(PvZEntity.PEASHOOTER, (dispatcher, context) -> {
			return new PeashooterEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.SUNFLOWER, (dispatcher, context) -> {
			return new SunflowerEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.WALLNUT, (dispatcher, context) -> {
			return new WallnutEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.POTATOMINE, (dispatcher, context) -> {
			return new PotatomineEntityRenderer(dispatcher);
		});
		EntityRendererRegistry.register(PvZEntity.UNARMEDPOTATOMINE, (dispatcher, context) -> {
			return new UnarmedPotatomineEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.SNOWPEA, (dispatcher, context) -> {
			return new SnowpeaEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.CHOMPER, (dispatcher, context) -> {
			return new ChomperEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.REPEATER, (dispatcher, context) -> {
			return new RepeaterEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.PUFFSHROOM, (dispatcher, context) -> {
			return new PuffshroomEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.SUNSHROOM, (dispatcher, context) -> {
			return new SunshroomEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.FUMESHROOM, (dispatcher, context) -> {
			return new FumeshroomEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.GRAVEBUSTER, (dispatcher, context) -> {
			return new GravebusterEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.HYPNOSHROOM, (dispatcher, context) -> {
			return new HypnoshroomEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.SCAREDYSHROOM, (dispatcher, context) -> {
			return new ScaredyshroomEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.ICESHROOM, (dispatcher, context) -> {
			return new IceshroomEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.DOOMSHROOM, (dispatcher, context) -> {
			return new DoomshroomEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.THREEPEATER, (dispatcher, context) -> {
			return new ThreepeaterEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.FLAMINGPEA, (dispatcher, context) -> {
			return new FlamingpeaEntityRenderer(dispatcher);
		});

		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.PEA, ShootingPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWPEAPROJ, ShootingSnowPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FIREPEA, ShootingFlamingpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.REPEA, ShootingRePeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TRIPEA, ShootingTriPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPORE, SporeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUME, FumeEntityRenderer::new);


		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.BROWNCOAT, (dispatcher, context) -> {
			return new BrowncoatEntityRenderer(dispatcher);
		});
		EntityRendererRegistry.register(PvZEntity.HYPNOBROWNCOAT, (dispatcher, context) -> {
			return new HypnoBrowncoatEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, (dispatcher, context) -> {
			return new FlagzombieEntityRenderer(dispatcher);
		});
		EntityRendererRegistry.register(PvZEntity.HYPNOFLAGZOMBIE, (dispatcher, context) -> {
			return new HypnoFlagzombieEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.CONEHEAD, (dispatcher, context) -> {
			return new ConeheadEntityRenderer(dispatcher);
		});
		EntityRendererRegistry.register(PvZEntity.HYPNOCONEHEAD, (dispatcher, context) -> {
			return new HypnoConeheadEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.POLEVAULTING, (dispatcher, context) -> {
			return new PoleVaultingEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.BUCKETHEAD, (dispatcher, context) -> {
			return new BucketheadEntityRenderer(dispatcher);
		});
		EntityRendererRegistry.register(PvZEntity.HYPNOBUCKETHEAD, (dispatcher, context) -> {
			return new HypnoBucketheadEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.NEWSPAPER, (dispatcher, context) -> {
			return new NewspaperEntityRenderer(dispatcher);
		});
		EntityRendererRegistry.register(PvZEntity.HYPNONEWSPAPER, (dispatcher, context) -> {
			return new HypnoNewspaperEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.SCREEENDOOR, (dispatcher, context) -> {
			return new ScreendoorEntityRenderer(dispatcher);
		});
		EntityRendererRegistry.register(PvZEntity.HYPNOSCREENDOOR, (dispatcher, context) -> {
			return new HypnoScreendoorEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.FOOTBALL, (dispatcher, context) -> {
			return new FootballEntityRenderer(dispatcher);
		});
		EntityRendererRegistry.register(PvZEntity.HYPNOFOOTBALL, (dispatcher, context) -> {
			return new HypnoFootballEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.BERSERKER, (dispatcher, context) -> {
			return new BerserkerEntityRenderer(dispatcher);
		});
		EntityRendererRegistry.register(PvZEntity.HYPNOBERSERKER, (dispatcher, context) -> {
			return new HypnoBerserkerEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.DANCINGZOMBIE, (dispatcher, context) -> {
			return new DancingZombieEntityRenderer(dispatcher);
		});
		EntityRendererRegistry.register(PvZEntity.HYPNODANCINGZOMBIE, (dispatcher, context) -> {
			return new HypnoDancingZombieEntityRenderer(dispatcher);
		});

		EntityRendererRegistry.register(PvZEntity.BACKUPDANCER, (dispatcher, context) -> {
			return new BackupDancerEntityRenderer(dispatcher);
		});
		EntityRendererRegistry.register(PvZEntity.HYPNOBACKUPDANCER, (dispatcher, context) -> {
			return new HypnoBackupDancerEntityRenderer(dispatcher);
		});

		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.BASICGRAVESTONE, BasicGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NIGHTGRAVESTONE, NightGraveRenderer::new);

	}
}
