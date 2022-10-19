package io.github.GrassyDev.pvzmod;

import io.github.GrassyDev.pvzmod.registry.ModBlocks;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.statuseffects.Frozen;
import io.github.GrassyDev.pvzmod.registry.statuseffects.Hypnotized;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class PvZCubed implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Example Mod");

	public static final StatusEffect HYPNOTIZED = new Hypnotized();
	public static final StatusEffect FROZEN = new Frozen();

	public static final String MOD_ID = "pvzcubed";

	public static final ItemGroup PLANTS = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "plants"))
			.icon(() -> new ItemStack(ModItems.SUN))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.PLANTFOOD));
				stacks.add(new ItemStack(ModItems.DAVES_SHOVEL));
				stacks.add(new ItemStack(ModItems.SMALLSUN));
				stacks.add(new ItemStack(ModItems.SUN));
				stacks.add(new ItemStack(ModItems.LARGESUN));
				stacks.add(new ItemStack(ModItems.PEASHOOTER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SUNFLOWER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.CHERRYBOMB_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.WALLNUT_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.POTATOMINE_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SNOW_PEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.CHOMPER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.REPEATER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PUFFSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SUNSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.FUMESHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.GRAVEBUSTER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.HYPNOSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SCAREDYSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.ICESHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.DOOMSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.THREEPEATER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PEA));
				stacks.add(new ItemStack(ModItems.SNOWPEAPROJ));
				stacks.add(new ItemStack(ModItems.REPEA));
				stacks.add(new ItemStack(ModItems.SPORE));
				stacks.add(new ItemStack(ModItems.FUME));
				stacks.add(new ItemStack(ModItems.TRIPEA));
				stacks.add(new ItemStack(ModItems.FIREPEA));
			}).build();

	public static final ItemGroup ZOMBIES = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "zombies"))
			.icon(() -> new ItemStack(ModItems.WAVE_FLAG))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.BROWNCOATEGG));
				stacks.add(new ItemStack(ModItems.FLAGZOMBIEEGG));
				stacks.add(new ItemStack(ModItems.CONEHEADEGG));
				stacks.add(new ItemStack(ModItems.POLEVAULTINGEGG));
				stacks.add(new ItemStack(ModItems.BUCKETHEADEGG));
				stacks.add(new ItemStack(ModItems.NEWSPAPEREGG));
				stacks.add(new ItemStack(ModItems.SCREENDOOREGG));
				stacks.add(new ItemStack(ModItems.FOOTBALLEGG));
				stacks.add(new ItemStack(ModItems.BERSERKEREGG));
				stacks.add(new ItemStack(ModItems.DANCINGZOMBIEEGG));
				stacks.add(new ItemStack(ModItems.BACKUPDANCEREGG));
				stacks.add(new ItemStack(ModItems.WAVE_FLAG));
				stacks.add(new ItemStack(ModItems.CONE));
			}).build();

	public static final ItemGroup GRAVES = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "graves"))
			.icon(() -> new ItemStack(ModItems.BASICGRAVESPAWN))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.BASICGRAVESPAWN));
				stacks.add(new ItemStack(ModItems.NIGHTGRAVESPAWN));
			}).build();

	public static final ItemGroup PVZBLOCKS = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "blocks"))
			.icon(() -> new ItemStack(ModItems.GRASS_TILE))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.GRASS_TILE));
				stacks.add(new ItemStack(ModItems.DARK_GRASS_TILE));
				stacks.add(new ItemStack(ModItems.NIGHT_TILE));
				stacks.add(new ItemStack(ModItems.DARK_NIGHT_TILE));
			}).build();

	public static final Identifier PEASHOOT = new Identifier("pvzcubed:pea.shoot");
	public static SoundEvent PEASHOOTEVENT = new SoundEvent(PEASHOOT);
	public static final Identifier PEAHIT = new Identifier("pvzcubed:pea.hit");
	public static SoundEvent PEAHITEVENT = new SoundEvent(PEAHIT);
	public static final Identifier REPEASHOOT = new Identifier("pvzcubed:repea.shoot");
	public static SoundEvent REPEASHOOTEVENT = new SoundEvent(REPEASHOOT);
	public static final Identifier REPEAHIT = new Identifier("pvzcubed:repea.hit");
	public static SoundEvent REPEAHITEVENT = new SoundEvent(REPEAHIT);
	public static final Identifier TRIPEAHIT = new Identifier("pvzcubed:tripea.hit");
	public static SoundEvent TRIPEAHITEVENT = new SoundEvent(TRIPEAHIT);
	public static final Identifier TRIPEASHOOT = new Identifier("pvzcubed:tripea.shoot");
	public static SoundEvent TRIPEASHOOTEVENT = new SoundEvent(TRIPEASHOOT);
	public static final Identifier GATLINGPEAHIT = new Identifier("pvzcubed:gatlingpea.hit");
	public static SoundEvent GATLINGPEAHITEVENT = new SoundEvent(GATLINGPEAHIT);
	public static final Identifier GATLINGPEASHOOT = new Identifier("pvzcubed:gatlingpea.shoot");
	public static SoundEvent GATLINGPEASHOOTEVENT = new SoundEvent(GATLINGPEASHOOT);

	public static final Identifier FIREPEAHIT = new Identifier("pvzcubed:flaming.pea.hit");
	public static SoundEvent FIREPEAHITEVENT = new SoundEvent(FIREPEAHIT);
	public static final Identifier FIREREPEAHIT = new Identifier("pvzcubed:flaming.repea.hit");
	public static SoundEvent FIREREPEAHITEVENT = new SoundEvent(FIREREPEAHIT);
	public static final Identifier FIRETRIPEAHIT = new Identifier("pvzcubed:flaming.tripea.hit");
	public static SoundEvent FIRETRIPEAHITEVENT = new SoundEvent(FIRETRIPEAHIT);
	public static final Identifier FIREGATLINGPEAHIT = new Identifier("pvzcubed:flaming.gatlingpea.hit");
	public static SoundEvent FIREGATLINGPEAHITEVENT = new SoundEvent(FIREGATLINGPEAHIT);

	public static final Identifier CONEHIT = new Identifier("pvzcubed:cone.hit");
	public static SoundEvent CONEHITEVENT = new SoundEvent(CONEHIT);
	public static final Identifier REPEACONEHIT = new Identifier("pvzcubed:repea.cone.hit");
	public static SoundEvent REPEACONEHITEVENT = new SoundEvent(REPEACONEHIT);
	public static final Identifier TRIPEACONEHIT = new Identifier("pvzcubed:tripea.cone.hit");
	public static SoundEvent TRIPEACONEHITEVENT = new SoundEvent(TRIPEACONEHIT);
	public static final Identifier GATLINGPEACONEHIT = new Identifier("pvzcubed:gatlingpea.cone.hit");
	public static SoundEvent GATLINGPEACONEHITEVENT = new SoundEvent(GATLINGPEACONEHIT);

	public static final Identifier BUCKETHIT = new Identifier("pvzcubed:bucket.hit");
	public static SoundEvent BUCKETHITEVENT = new SoundEvent(BUCKETHIT);
	public static final Identifier REPEABUCKETHIT = new Identifier("pvzcubed:repea.bucket.hit");
	public static SoundEvent REPEABUCKETHITEVENT = new SoundEvent(REPEABUCKETHIT);
	public static final Identifier TRIPEABUCKETHIT = new Identifier("pvzcubed:tripea.bucket.hit");
	public static SoundEvent TRIPEABUCKETHITEVENT = new SoundEvent(TRIPEABUCKETHIT);
	public static final Identifier GATLINGPEABUCKETHIT = new Identifier("pvzcubed:gatlingpea.bucket.hit");
	public static SoundEvent GATLINGPEABUCKETHITEVENT = new SoundEvent(GATLINGPEABUCKETHIT);

	public static final Identifier SNOWPEASHOOT = new Identifier("pvzcubed:snowpea.shoot");
	public static SoundEvent SNOWPEASHOOTEVENT = new SoundEvent(SNOWPEASHOOT);
	public static final Identifier SNOWPEAHIT = new Identifier("pvzcubed:snowpea.hit");
	public static SoundEvent SNOWPEAHITEVENT = new SoundEvent(SNOWPEAHIT);

	public static final Identifier MUSHROOMSHOOT = new Identifier("pvzcubed:mushroom.shoot");
	public static SoundEvent MUSHROOMSHOOTEVENT = new SoundEvent(MUSHROOMSHOOT);

	public static final Identifier FUMESHROOMSHOOT = new Identifier("pvzcubed:fumeshroom.shoot");
	public static SoundEvent FUMESHROOMSHOOTEVENT = new SoundEvent(FUMESHROOMSHOOT);

	public static final Identifier CHERRYBOMBEXPLOSION = new Identifier("pvzcubed:cherrybomb.explosion");
	public static SoundEvent CHERRYBOMBEXPLOSIONEVENT = new SoundEvent(CHERRYBOMBEXPLOSION);
	public static final Identifier POTATOMINEEXPLOSION = new Identifier("pvzcubed:potatomine.explosion");
	public static SoundEvent POTATOMINEEXPLOSIONEVENT = new SoundEvent(POTATOMINEEXPLOSION);
	public static final Identifier DOOMSHROOMEXPLOSION = new Identifier("pvzcubed:doomshroom.explosion");
	public static SoundEvent DOOMSHROOMEXPLOSIONEVENT = new SoundEvent(DOOMSHROOMEXPLOSION);

	public static final Identifier CHOMPERBITE = new Identifier("pvzcubed:chomper.chomp");
	public static SoundEvent CHOMPERBITEVENT = new SoundEvent(CHOMPERBITE);

	public static final Identifier GRAVEBUSTEREATING = new Identifier("pvzcubed:gravebuster.eating");
	public static SoundEvent GRAVEBUSTEREATINGEVENT = new SoundEvent(GRAVEBUSTEREATING);

	public static final Identifier HYPNOTIZING = new Identifier("pvzcubed:hypnoshroom.hypnotizing");
	public static SoundEvent HYPNOTIZINGEVENT = new SoundEvent(HYPNOTIZING);

	public static final Identifier GRAVERISING = new Identifier("pvzcubed:grave.rising");
	public static SoundEvent GRAVERISINGEVENT = new SoundEvent(GRAVERISING);

	public static final Identifier ENTITYRISING = new Identifier("pvzcubed:entity.rising");
	public static SoundEvent ENTITYRISINGEVENT = new SoundEvent(ENTITYRISING);

	public static final Identifier PLANTPLANTED = new Identifier("pvzcubed:plant.planted");
	public static SoundEvent PLANTPLANTEDEVENT = new SoundEvent(PLANTPLANTED);

	public static final Identifier SUNDROP = new Identifier("pvzcubed:sun.drop");
	public static SoundEvent SUNDROPEVENT = new SoundEvent(SUNDROP);

	public static final Identifier ZOMBIEBITE = new Identifier("pvzcubed:zombie.bite");
	public static SoundEvent ZOMBIEBITEEVENT = new SoundEvent(ZOMBIEBITE);

	public static final Identifier ZOMBIEMOAN = new Identifier("pvzcubed:zombie.moan");
	public static SoundEvent ZOMBIEMOANEVENT = new SoundEvent(ZOMBIEMOAN);

	public static final Identifier ZOMBIEDANCING = new Identifier("pvzcubed:zombie.dancing");
	public static SoundEvent ZOMBIEDANCINGEVENT = new SoundEvent(ZOMBIEDANCING);

	public static final Identifier SILENCE = new Identifier("pvzcubed:silence");
	public static SoundEvent SILENCEVENET = new SoundEvent(SILENCE);


	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
	    // This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItems.registerItems();
		ModBlocks.registerBlocks();
		GeckoLib.initialize();
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzcubed", "hypnotized"), HYPNOTIZED);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzcubed", "frozen"), FROZEN);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.PEASHOOT, PEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.PEAHIT, PEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.FIREPEAHIT, FIREPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.REPEASHOOT, REPEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.REPEAHIT, REPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.FIREREPEAHIT, FIREREPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.TRIPEASHOOT, TRIPEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.TRIPEAHIT, TRIPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.FIRETRIPEAHIT, FIRETRIPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GATLINGPEASHOOT, GATLINGPEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GATLINGPEAHIT, GATLINGPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.FIREGATLINGPEAHIT, FIREGATLINGPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.CONEHIT, CONEHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.REPEACONEHIT, REPEACONEHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.TRIPEACONEHIT, TRIPEACONEHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GATLINGPEACONEHIT, GATLINGPEACONEHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.BUCKETHIT, BUCKETHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.REPEABUCKETHIT, REPEABUCKETHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.TRIPEABUCKETHIT, TRIPEABUCKETHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GATLINGPEABUCKETHIT, GATLINGPEABUCKETHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SNOWPEASHOOT, SNOWPEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SNOWPEAHIT, SNOWPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.MUSHROOMSHOOT, MUSHROOMSHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.FUMESHROOMSHOOT, FUMESHROOMSHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.CHERRYBOMBEXPLOSION, CHERRYBOMBEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.POTATOMINEEXPLOSION, POTATOMINEEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.DOOMSHROOMEXPLOSION, DOOMSHROOMEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.CHOMPERBITE, CHOMPERBITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GRAVEBUSTEREATING, GRAVEBUSTEREATINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.HYPNOTIZING, HYPNOTIZINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GRAVERISING, GRAVERISINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ENTITYRISING, ENTITYRISINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.PLANTPLANTED, PLANTPLANTEDEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SUNDROP, SUNDROPEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ZOMBIEBITE, ZOMBIEBITEEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ZOMBIEMOAN, ZOMBIEMOANEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SILENCE, SILENCEVENET);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ZOMBIEDANCING, ZOMBIEDANCINGEVENT);

		System.out.println("Hello Fabric world!");
	}
}
