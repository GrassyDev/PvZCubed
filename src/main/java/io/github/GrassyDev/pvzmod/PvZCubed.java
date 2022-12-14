package io.github.GrassyDev.pvzmod;

import io.github.GrassyDev.pvzmod.registry.ModBlocks;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.damage.HypnoDamage;
import io.github.GrassyDev.pvzmod.registry.entity.statuseffects.Frozen;
import io.github.GrassyDev.pvzmod.registry.entity.statuseffects.Hypnotized;
import io.github.GrassyDev.pvzmod.registry.entity.statuseffects.Ice;
import io.github.GrassyDev.pvzmod.registry.entity.statuseffects.Warm;
import io.github.GrassyDev.pvzmod.registry.world.gen.entity.PvZEntitySpawn;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.entity.damage.DamageSource;
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
	public static final Logger LOGGER = LoggerFactory.getLogger("Plants vs. Zombies Cubed");

	public static final DamageSource HYPNO_DAMAGE = new HypnoDamage().setHypnoDamage();
	public static final StatusEffect HYPNOTIZED = new Hypnotized();

	public static final StatusEffect ICE = new Ice();
	public static final StatusEffect FROZEN = new Frozen();
	public static final StatusEffect WARM = new Warm();

	public static final String MOD_ID = "pvzmod";

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
				stacks.add(new ItemStack(ModItems.LILYPAD_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SQUASH_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.THREEPEATER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.TANGLEKELP_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.GATLINGPEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.TWINSUNFLOWER_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.SNOW_QUEENPEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PERFOOMSHROOM_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PEAPOD_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.FIRE_PEA_SEED_PACKET));
				stacks.add(new ItemStack(ModItems.PEA));
				stacks.add(new ItemStack(ModItems.SNOWPEAPROJ));
				stacks.add(new ItemStack(ModItems.FUME));
				stacks.add(new ItemStack(ModItems.SPORE));
				stacks.add(new ItemStack(ModItems.FIREPEA));
				stacks.add(new ItemStack(ModItems.SNOWQUEENPEAPROJ));
				stacks.add(new ItemStack(ModItems.PLASMAPEA));
				stacks.add(new ItemStack(ModItems.ICESPIKE));
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
				stacks.add(new ItemStack(ModItems.DANCINGZOMBIEEGG));
				stacks.add(new ItemStack(ModItems.BACKUPDANCEREGG));
				stacks.add(new ItemStack(ModItems.GARGANTUAREGG));
				stacks.add(new ItemStack(ModItems.IMPEGG));
				stacks.add(new ItemStack(ModItems.BERSERKEREGG));
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
			.icon(() -> new ItemStack(ModItems.PREMIUM_TILE))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(ModItems.GRASS_TILE));
				stacks.add(new ItemStack(ModItems.DARK_GRASS_TILE));
				stacks.add(new ItemStack(ModItems.ROOF_TILE));
				stacks.add(new ItemStack(ModItems.DARK_ROOF_TILE));
				stacks.add(new ItemStack(ModItems.UPGRADE_TILE));
				stacks.add(new ItemStack(ModItems.PREMIUM_TILE));
				stacks.add(new ItemStack(ModItems.PIRATE_TILE));
				stacks.add(new ItemStack(ModItems.DARK_PIRATE_TILE));
				stacks.add(new ItemStack(ModItems.WEST_TILE));
				stacks.add(new ItemStack(ModItems.DARK_WEST_TILE));
				stacks.add(new ItemStack(ModItems.NIGHT_TILE));
				stacks.add(new ItemStack(ModItems.DARK_NIGHT_TILE));
				stacks.add(new ItemStack(ModItems.SAND_TILE));
				stacks.add(new ItemStack(ModItems.DARK_SAND_TILE));
			}).build();

	public static final Identifier PEASHOOT = new Identifier("pvzmod:pea.shoot");
	public static SoundEvent PEASHOOTEVENT = new SoundEvent(PEASHOOT);
	public static final Identifier PEAHIT = new Identifier("pvzmod:pea.hit");
	public static SoundEvent PEAHITEVENT = new SoundEvent(PEAHIT);
	public static final Identifier GATLINGPEAHIT = new Identifier("pvzmod:gatlingpea.hit");
	public static SoundEvent GATLINGPEAHITEVENT = new SoundEvent(GATLINGPEAHIT);
	public static final Identifier GATLINGPEASHOOT = new Identifier("pvzmod:gatlingpea.shoot");
	public static SoundEvent GATLINGPEASHOOTEVENT = new SoundEvent(GATLINGPEASHOOT);

	public static final Identifier FIREPEAHIT = new Identifier("pvzmod:flaming.pea.hit");
	public static SoundEvent FIREPEAHITEVENT = new SoundEvent(FIREPEAHIT);

	public static final Identifier CONEHIT = new Identifier("pvzmod:cone.hit");
	public static SoundEvent CONEHITEVENT = new SoundEvent(CONEHIT);

	public static final Identifier BUCKETHIT = new Identifier("pvzmod:bucket.hit");
	public static SoundEvent BUCKETHITEVENT = new SoundEvent(BUCKETHIT);

	public static final Identifier SNOWPEASHOOT = new Identifier("pvzmod:snowpea.shoot");
	public static SoundEvent SNOWPEASHOOTEVENT = new SoundEvent(SNOWPEASHOOT);
	public static final Identifier SNOWPEAHIT = new Identifier("pvzmod:snowpea.hit");
	public static SoundEvent SNOWPEAHITEVENT = new SoundEvent(SNOWPEAHIT);

	public static final Identifier MUSHROOMSHOOT = new Identifier("pvzmod:mushroom.shoot");
	public static SoundEvent MUSHROOMSHOOTEVENT = new SoundEvent(MUSHROOMSHOOT);

	public static final Identifier FUMESHROOMSHOOT = new Identifier("pvzmod:fumeshroom.shoot");
	public static SoundEvent FUMESHROOMSHOOTEVENT = new SoundEvent(FUMESHROOMSHOOT);

	public static final Identifier CHERRYBOMBEXPLOSION = new Identifier("pvzmod:cherrybomb.explosion");
	public static SoundEvent CHERRYBOMBEXPLOSIONEVENT = new SoundEvent(CHERRYBOMBEXPLOSION);
	public static final Identifier POTATOMINEEXPLOSION = new Identifier("pvzmod:potatomine.explosion");
	public static SoundEvent POTATOMINEEXPLOSIONEVENT = new SoundEvent(POTATOMINEEXPLOSION);
	public static final Identifier DOOMSHROOMEXPLOSION = new Identifier("pvzmod:doomshroom.explosion");
	public static SoundEvent DOOMSHROOMEXPLOSIONEVENT = new SoundEvent(DOOMSHROOMEXPLOSION);

	public static final Identifier SQUASHHUM = new Identifier("pvzmod:squash.hm");
	public static SoundEvent SQUASHHUMEVENT = new SoundEvent(SQUASHHUM);

	public static final Identifier CHOMPERBITE = new Identifier("pvzmod:chomper.chomp");
	public static SoundEvent CHOMPERBITEVENT = new SoundEvent(CHOMPERBITE);

	public static final Identifier GRAVEBUSTEREATING = new Identifier("pvzmod:gravebuster.eating");
	public static SoundEvent GRAVEBUSTEREATINGEVENT = new SoundEvent(GRAVEBUSTEREATING);

	public static final Identifier HYPNOTIZING = new Identifier("pvzmod:hypnoshroom.hypnotizing");
	public static SoundEvent HYPNOTIZINGEVENT = new SoundEvent(HYPNOTIZING);

	public static final Identifier GRAVERISING = new Identifier("pvzmod:grave.rising");
	public static SoundEvent GRAVERISINGEVENT = new SoundEvent(GRAVERISING);

	public static final Identifier ENTITYRISING = new Identifier("pvzmod:entity.rising");
	public static SoundEvent ENTITYRISINGEVENT = new SoundEvent(ENTITYRISING);

	public static final Identifier PLANTPLANTED = new Identifier("pvzmod:plant.planted");
	public static SoundEvent PLANTPLANTEDEVENT = new SoundEvent(PLANTPLANTED);

	public static final Identifier SUNDROP = new Identifier("pvzmod:sun.drop");
	public static SoundEvent SUNDROPEVENT = new SoundEvent(SUNDROP);

	public static final Identifier ZOMBIEBITE = new Identifier("pvzmod:zombie.bite");
	public static SoundEvent ZOMBIEBITEEVENT = new SoundEvent(ZOMBIEBITE);

	public static final Identifier POLEVAULT = new Identifier("pvzmod:polevaulting.vault");
	public static SoundEvent POLEVAULTEVENT = new SoundEvent(POLEVAULT);

	public static final Identifier GARGANTUARSMASH = new Identifier("pvzmod:gargantuar.smash");
	public static SoundEvent GARGANTUARSMASHEVENT = new SoundEvent(GARGANTUARSMASH);

	public static final Identifier IMPLAUNCH = new Identifier("pvzmod:imp.launch");
	public static SoundEvent IMPLAUNCHEVENT = new SoundEvent(IMPLAUNCH);

	public static final Identifier ZOMBIEMOAN = new Identifier("pvzmod:zombie.moan");
	public static SoundEvent ZOMBIEMOANEVENT = new SoundEvent(ZOMBIEMOAN);

	public static final Identifier GARGANTUARMOAN = new Identifier("pvzmod:gargantuar.moan");
	public static SoundEvent GARGANTUARMOANEVENT = new SoundEvent(GARGANTUARMOAN);

	public static final Identifier IMPMOAN = new Identifier("pvzmod:imp.moan");
	public static SoundEvent IMPMOANEVENT = new SoundEvent(IMPMOAN);

	public static final Identifier NEWSPAPERANGRY = new Identifier("pvzmod:newspaper.angry");
	public static SoundEvent NEWSPAPERANGRYEVENT = new SoundEvent(NEWSPAPERANGRY);

	public static final Identifier ZOMBIEDANCING = new Identifier("pvzmod:zombie.dancing");
	public static SoundEvent ZOMBIEDANCINGEVENT = new SoundEvent(ZOMBIEDANCING);

	public static final Identifier SILENCE = new Identifier("pvzmod:silence");
	public static SoundEvent SILENCEVENET = new SoundEvent(SILENCE);


	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("{} says: Trans Rights are Human Rights!", mod.metadata().name());
		ModItems.registerItems();
		ModBlocks.registerBlocks();
		GeckoLib.initialize();
		PvZEntitySpawn.addEntitySpawn();
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "ice"), ICE);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "frozen"), FROZEN);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "hypnotized"), HYPNOTIZED);
		Registry.register(Registry.STATUS_EFFECT, new Identifier("pvzmod", "warm"), WARM);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.PEASHOOT, PEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.PEAHIT, PEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.FIREPEAHIT, FIREPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GATLINGPEASHOOT, GATLINGPEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GATLINGPEAHIT, GATLINGPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.CONEHIT, CONEHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.BUCKETHIT, BUCKETHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SNOWPEASHOOT, SNOWPEASHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SNOWPEAHIT, SNOWPEAHITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.MUSHROOMSHOOT, MUSHROOMSHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.FUMESHROOMSHOOT, FUMESHROOMSHOOTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.CHERRYBOMBEXPLOSION, CHERRYBOMBEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.POTATOMINEEXPLOSION, POTATOMINEEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.DOOMSHROOMEXPLOSION, DOOMSHROOMEXPLOSIONEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SQUASHHUM, SQUASHHUMEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.CHOMPERBITE, CHOMPERBITEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GRAVEBUSTEREATING, GRAVEBUSTEREATINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.HYPNOTIZING, HYPNOTIZINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GRAVERISING, GRAVERISINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ENTITYRISING, ENTITYRISINGEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.PLANTPLANTED, PLANTPLANTEDEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SUNDROP, SUNDROPEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ZOMBIEBITE, ZOMBIEBITEEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.POLEVAULT, POLEVAULTEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GARGANTUARSMASH, GARGANTUARSMASHEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.IMPLAUNCH, IMPLAUNCHEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ZOMBIEMOAN, ZOMBIEMOANEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.GARGANTUARMOAN, GARGANTUARMOANEVENT);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.SILENCE, SILENCEVENET);
		Registry.register(Registry.SOUND_EVENT, PvZCubed.ZOMBIEDANCING, ZOMBIEDANCINGEVENT);
	}
}
